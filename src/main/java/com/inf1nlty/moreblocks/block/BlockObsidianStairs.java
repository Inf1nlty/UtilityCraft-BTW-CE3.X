package com.inf1nlty.moreblocks.block;

import btw.block.blocks.StairsBlock;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;

public class BlockObsidianStairs extends StairsBlock {
    public BlockObsidianStairs(int blockID) {
        super(blockID, Block.obsidian, 0);
        this.setHardness(10.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundStoneFootstep);
        this.setUnlocalizedName("moreblocks:obsidian");
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return 3;
    }
}