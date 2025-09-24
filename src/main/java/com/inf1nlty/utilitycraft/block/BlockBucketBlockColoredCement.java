package com.inf1nlty.utilitycraft.block;

import btw.block.blocks.BucketBlockFull;
import com.inf1nlty.utilitycraft.client.render.ClientRenderHelper;
import com.inf1nlty.utilitycraft.item.UCItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

/**
 * Block form of a colored cement bucket (metadata holds color 0..15).
 * Features:
 *  - Can be tipped by an adjacent extended piston facing the bucket to spill wet colored cement.
 *  - When player left-clicks it (any tool or bare hand) it breaks instantly and drops the corresponding colored cement bucket item (torch-like behavior).
 *  - When broken via normal harvesting path, also drops the colored cement bucket item (unless in creative).
 *  - After tipping it becomes an empty placed bucket (BTW placedBucket) with facing stored in metadata (low 3 bits expected by BTW).
 */
public class BlockBucketBlockColoredCement extends BucketBlockFull {

    @Environment(EnvType.CLIENT)
    private Icon[] contentIcons;

    private static final String[] COLOR_NAMES = {
            "white","orange","magenta","light_blue","yellow","lime","pink","gray",
            "light_gray","cyan","purple","blue","brown","green","red","black"
    };

    public BlockBucketBlockColoredCement(int id) {
        super(id);
        setHardness(0.0F);
        setResistance(1.0F);
        setTickRandomly(false);
        setTextureName("utilitycraft:cement/colored_cement_bucket_block");
        setUnlocalizedName("colored_cement_bucket_block");
    }

    @Override
    public boolean attemptToSpillIntoBlock(World world, int i, int i1, int i2) {
        return false;
    }

    /* ---------------- Color / Facing handling ---------------- */

    public static int getColor(int meta) {
        return meta & 15;
    }

    @Override
    public int getFacing(int meta) {
        // Keep rendered as upright bucket (return up)
        return 1;
    }

    @Override
    public int setFacing(int meta, int facing) {
        // Ignore orientation writes; metadata stores only color.
        return meta;
    }

    /**
     * Places this block with the given color.
     */
    public void placeColoredBucket(World w, int x, int y, int z, int color, int ignoredOrient) {
        w.setBlockAndMetadataWithNotify(x, y, z, blockID, color & 15);
    }

    /* ---------------- Piston tipping logic ---------------- */

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) {
        super.onNeighborBlockChange(world, x, y, z, changedId);
        checkPistonTip(world, x, y, z);
    }

    private void checkPistonTip(World w, int x, int y, int z) {
        // Directions 0..5 (vanilla Facing indices)
        for (int side = 0; side < 6; side++) {
            int nx = x + Facing.offsetsXForSide[side];
            int ny = y + Facing.offsetsYForSide[side];
            int nz = z + Facing.offsetsZForSide[side];

            int nid = w.getBlockId(nx, ny, nz);
            if (nid == 0) continue;

            Block nb = Block.blocksList[nid];
            if (nb == null) continue;

            // Piston base (extended bit)
            if (nb == Block.pistonBase || nb == Block.pistonStickyBase) {
                int nMeta = w.getBlockMetadata(nx, ny, nz);
                boolean extended = (nMeta & 8) != 0;
                int facing = nMeta & 7;
                if (extended && isFacingTowards(facing, nx, ny, nz, x, y, z)) {
                    tipBucket(w, x, y, z, facing);
                    return;
                }
            }
            // Piston extension head
            if (nb == Block.pistonExtension) {
                int nMeta = w.getBlockMetadata(nx, ny, nz);
                int facing = nMeta & 7;
                if (isFacingTowards(facing, nx, ny, nz, x, y, z)) {
                    tipBucket(w, x, y, z, facing);
                    return;
                }
            }
        }
    }

    private boolean isFacingTowards(int pistonFacing, int px, int py, int pz, int tx, int ty, int tz) {
        return (px + Facing.offsetsXForSide[pistonFacing] == tx) &&
                (py + Facing.offsetsYForSide[pistonFacing] == ty) &&
                (pz + Facing.offsetsZForSide[pistonFacing] == tz);
    }

    private void tipBucket(World w, int x, int y, int z, int facing) {
        if (w.isRemote) return;
        int meta = w.getBlockMetadata(x, y, z);
        int color = getColor(meta);

        int tx = x + Facing.offsetsXForSide[facing];
        int ty = y + Facing.offsetsYForSide[facing];
        int tz = z + Facing.offsetsZForSide[facing];

        // Try to spawn the wet cement destination, slight downward adjust if open.
        if (w.isAirBlock(tx, ty, tz) || !w.getBlockMaterial(tx, ty, tz).isSolid()) {
            w.playSoundEffect(tx, ty, tz,
                    "mob.ghast.moan", 0.5F,
                    2.6F + (w.rand.nextFloat() - w.rand.nextFloat()) * 0.8F);

            if (w.isAirBlock(tx, ty - 1, tz) || !w.getBlockMaterial(tx, ty - 1, tz).isSolid()) {
                ty--;
            }

            w.setBlockAndMetadataWithNotify(tx, ty, tz, UCBlocks.coloredCement.blockID, color);
            w.scheduleBlockUpdate(tx, ty, tz,
                    UCBlocks.coloredCement.blockID,
                    ((BlockColoredCement) UCBlocks.coloredCement).tickRate(w));
        }

        // Replace with empty placed bucket (BTW) storing facing.
        w.setBlockAndMetadataWithNotify(x, y, z,
                btw.block.BTWBlocks.placedBucket.blockID,
                facing & 7);
    }

    /* ---------------- Torch-like instant break ---------------- */

    @Override
    public void onBlockClicked(World w, int x, int y, int z, EntityPlayer player) {
        // Single left click instantly breaks and drops the colored cement bucket item.
        if (w.isRemote) return;

        int meta = w.getBlockMetadata(x, y, z);
        if (!player.capabilities.isCreativeMode && UCItems.colored_cement_bucket != null) {
            int color = getColor(meta);
            ItemStack drop = new ItemStack(UCItems.colored_cement_bucket, 1, color);
            EntityItem ent = new EntityItem(w, x + 0.5, y + 0.5, z + 0.5, drop);
            w.spawnEntityInWorld(ent);
        }
        w.setBlockToAir(x, y, z);
    }

    /* ---------------- Standard harvest fallback ---------------- */

    @Override
    public void onBlockHarvested(World w, int x, int y, int z, int meta, EntityPlayer player) {
        // Still handle normal harvest (e.g. explosions or other removal paths).
        if (!w.isRemote && !player.capabilities.isCreativeMode && UCItems.colored_cement_bucket != null) {
            int color = getColor(meta);
            ItemStack drop = new ItemStack(UCItems.colored_cement_bucket, 1, color);
            EntityItem ent = new EntityItem(w, x + 0.5, y + 0.5, z + 0.5, drop);
            w.spawnEntityInWorld(ent);
        }
        super.onBlockHarvested(w, x, y, z, meta, player);
    }

    @Override
    public int idDropped(int meta, Random rand, int fortune) {
        // Drop handled manually.
        return 0;
    }

    /* ---------------- Client rendering ---------------- */

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getContentsIcon() {
        Integer color = ClientRenderHelper.CURRENT_RENDER_COLOR.get();
        if (color == null) color = 0;
        return contentIcons != null ? contentIcons[color & 15] : null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister reg) {
        super.registerIcons(reg);
        contentIcons = new Icon[16];
        for (int i = 0; i < 16; i++) {
            contentIcons[i] = reg.registerIcon("utilitycraft:cement/" + COLOR_NAMES[i] + "_cement");
        }
    }

    @Environment(EnvType.CLIENT)
    public Icon getContentsIconForMeta(int meta) {
        int color = meta & 15;
        return contentIcons != null ? contentIcons[color] : null;
    }
}