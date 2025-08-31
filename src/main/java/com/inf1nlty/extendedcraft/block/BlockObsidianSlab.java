package com.inf1nlty.extendedcraft.block;

import btw.block.blocks.SlabBlock;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.Block;

public class BlockObsidianSlab extends SlabBlock {

    public BlockObsidianSlab(int blockID) {
        super(blockID, Material.rock);
        this.setHardness(10.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundStoneFootstep);
        this.setUnlocalizedName("obsidian_slab");
        this.setTextureName("extendedcraft:obsidian");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int getCombinedBlockID(int metadata) {
        return Block.obsidian.blockID;
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return 3;
    }
}