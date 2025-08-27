package com.inf1nlty.moreblocks.block;

import btw.block.BTWBlocks;
import com.inf1nlty.moreblocks.block.tileentity.TileEntityConcretePowder;
import btw.block.blocks.FallingBlock;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Concrete powder block, falls like sand, solidifies near water into concrete.
 * Supports 16 color variants via metadata.
 */
public class BlockConcretePowder extends BlockContainer {
    private final Block concreteBlock;
    private Icon[] icons;
    private static final String[] colorNames = {
            "white", "orange", "magenta", "light_blue",
            "yellow", "lime", "pink", "gray", "light_gray",
            "cyan", "purple", "blue", "brown", "green", "red", "black"
    };

    public BlockConcretePowder(int id, Block concreteBlock) {
        super(id, Material.sand);
        this.setHardness(0.5F);
        this.setResistance(0.5F);
        this.setStepSound(BTWBlocks.dirtStepSound);
        this.setUnlocalizedName("concretePowder");
        this.setTextureName("concretePowder");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.concreteBlock = concreteBlock;
        this.setTickRandomly(true);
        this.setShovelsEffectiveOn();
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldBlockID, int oldMeta) {
        super.breakBlock(world, x, y, z, oldBlockID, oldMeta);
        world.removeBlockTileEntity(x, y, z);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        float hardness = this.getBlockHardness(world, x, y, z);
        if (hardness < 0.0F) return 0.0F;
        return player.getCurrentPlayerStrVsBlock(this, x, y, z) / hardness / 300.0F;
    }

    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        ItemStack stack = player.getCurrentEquippedItem();
        return stack != null && stack.getItem() instanceof ItemSpade;
    }

    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(new ItemStack(this, 1, meta));
        return drops;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        TileEntityConcretePowder te = new TileEntityConcretePowder();
        te.concreteBlockID = concreteBlock.blockID;
        return te;
    }

    public boolean hasTileEntity(int meta) {
        return true;
    }

    public boolean isFallingBlock() {
        return true;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.scheduleCheckForFall(world, x, y, z);
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            TileEntity te = world.getBlockTileEntity(x, y, z);
            if (!(te instanceof TileEntityConcretePowder)) {
                TileEntityConcretePowder powderTE = new TileEntityConcretePowder();
                powderTE.concreteBlockID = concreteBlock.blockID;
                world.setBlockTileEntity(x, y, z, powderTE);
            } else {
                ((TileEntityConcretePowder)te).concreteBlockID = concreteBlock.blockID;
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlockID);
        this.scheduleCheckForFall(world, x, y, z);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te instanceof TileEntityConcretePowder) {
            te.updateEntity();
        }
        this.checkForFall(world, x, y, z);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubBlocks(int itemID, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    public void scheduleCheckForFall(World world, int x, int y, int z) {
        if (!world.isRemote) {
            world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
        }
    }

    public void checkAndDoFall(World world, int x, int y, int z) {
        if (canFallBelow(world, x, y - 1, z) && y >= 0) {
            EntityFallingSand entity = new EntityFallingSand(world, x + 0.5F, y + 0.5F, z + 0.5F, this.blockID, world.getBlockMetadata(x, y, z));
            world.spawnEntityInWorld(entity);
        }
    }

    public boolean canFallBelow(World world, int x, int y, int z) {
        int blockId = world.getBlockId(x, y, z);
        if (blockId == 0) return true;
        if (blockId == Block.fire.blockID) return true;
        Material material = Block.blocksList[blockId].blockMaterial;
        return material.isLiquid();
    }

    @Override
    public void registerIcons(IconRegister reg) {
        icons = new Icon[16];
        for (int i = 0; i < 16; i++) {
            icons[i] = reg.registerIcon("moreblocks:" + colorNames[i] + "_concrete_powder");
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        if (meta < 0 || meta > 15) meta = 0;
        return icons[meta];
    }
}