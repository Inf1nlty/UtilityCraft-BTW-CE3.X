package com.inf1nlty.moreblocks.item;

import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.network.CtrlPlacementTracker;
import net.minecraft.src.*;

import java.util.List;

/**
 * Item representing a filled colored cement bucket.
 * Right click:
 *  - Normal: pour wet colored cement (gives back empty bucket if not creative).
 *  - CTRL + Right click: place the bucket block itself (consumes the item; no empty bucket returned).
 * Metadata lower 4 bits = color (0..15).
 */
public class ItemColoredCementBucket extends MBItem {

    private static final String[] COLOR_NAMES = {
            "white","orange","magenta","light_blue","yellow","lime","pink","gray",
            "light_gray","cyan","purple","blue","brown","green","red","black"
    };
    private Icon[] icons;

    public ItemColoredCementBucket(int id) {
        super(id);
        setUnlocalizedName("colored_cement_bucket");
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabMaterials);
        setMaxStackSize(1);
    }

    @Override
    public int getMetadata(int dmg) {
        return dmg & 15;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.colored_cement_bucket_" +
                COLOR_NAMES[getMetadata(stack.getItemDamage())];
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int color = getMetadata(stack.getItemDamage());
        boolean ctrl = false;

        if (!world.isRemote) {
            ctrl = CtrlPlacementTracker.consume(player);
            // Fallback flag on stack (client pre-tag) if packet arrived just before
            if (!ctrl && stack.hasTagCompound() && stack.getTagCompound().getBoolean("MB_Ctrl")) {
                ctrl = true;
                stack.getTagCompound().removeTag("MB_Ctrl");
            }
        }

        if (ctrl && !world.isRemote) {
            placeBucketBlock(world, player, stack, color);
            return stack;
        }

        if (!world.isRemote) {
            pourCement(world, player, stack, color);
        }
        return stack;
    }

    /**
     * Places the bucket block (consumes the filled bucket item).
     */
    private void placeBucketBlock(World world, EntityPlayer player, ItemStack stack, int color) {
        MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, false);
        if (mop == null || mop.typeOfHit != EnumMovingObjectType.TILE) return;

        int x = mop.blockX;
        int y = mop.blockY;
        int z = mop.blockZ;
        int side = mop.sideHit;

        if (!world.canMineBlock(player, x, y, z)) return;
        if (!player.canPlayerEdit(x, y, z, side, stack)) return;

        x += Facing.offsetsXForSide[side];
        y += Facing.offsetsYForSide[side];
        z += Facing.offsetsZForSide[side];

        if (!(world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid())) return;
        MBBlocks.coloredCementBucketBlock.placeColoredBucket(world, x, y, z, color, 0);

        // Consume the item (NO empty bucket returned).
        if (!player.capabilities.isCreativeMode) {
            ItemStack current = player.inventory.mainInventory[player.inventory.currentItem];
            if (current != null) {
                current.stackSize--;
                if (current.stackSize <= 0) {
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                }
            }
        }
    }

    /**
     * Normal pour: place wet colored cement source and return empty bucket (non-creative).
     */
    private void pourCement(World world, EntityPlayer player, ItemStack stack, int color) {
        MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, false);
        if (mop == null || mop.typeOfHit != EnumMovingObjectType.TILE) return;

        int x = mop.blockX;
        int y = mop.blockY;
        int z = mop.blockZ;
        int side = mop.sideHit;

        if (!world.canMineBlock(player, x, y, z)) return;
        if (!player.canPlayerEdit(x, y, z, side, stack)) return;

        x += Facing.offsetsXForSide[side];
        y += Facing.offsetsYForSide[side];
        z += Facing.offsetsZForSide[side];

        if (world.isAirBlock(x, y, z) || !world.getBlockMaterial(x, y, z).isSolid()) {
            world.playSoundEffect(
                    x, y, z,
                    "mob.ghast.moan",
                    0.5F,
                    2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F
            );
            if (world.isAirBlock(x, y - 1, z) ||
                    !world.getBlockMaterial(x, y - 1, z).isSolid()) {
                y--;
            }
            world.setBlockAndMetadataWithNotify(x, y, z, MBBlocks.coloredCement.blockID, color);
            world.scheduleBlockUpdate(x, y, z, MBBlocks.coloredCement.blockID, MBBlocks.coloredCement.tickRate(world));

            if (!player.capabilities.isCreativeMode) {
                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Item.bucketEmpty);
            }
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        for (int i=0;i<16;i++) list.add(new ItemStack(this,1,i));
    }

    @Override
    public void registerIcons(IconRegister reg) {
        icons = new Icon[16];
        for (int i=0;i<16;i++) {
            icons[i] = reg.registerIcon("moreblocks:" + COLOR_NAMES[i] + "_cement_bucket");
        }
        itemIcon = icons[0];
    }

    @Override
    public Icon getIconFromDamage(int dmg) {
        return icons[(dmg & 15)];
    }
}