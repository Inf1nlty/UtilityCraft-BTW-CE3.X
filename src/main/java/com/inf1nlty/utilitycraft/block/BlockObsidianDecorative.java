package com.inf1nlty.utilitycraft.block;

import btw.block.blocks.SidingAndCornerAndDecorativeWallBlock;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;

public class BlockObsidianDecorative extends SidingAndCornerAndDecorativeWallBlock {

    public BlockObsidianDecorative(int blockID) {
        super(blockID, Material.rock,
                "utilitycraft:obsidian/obsidian",
                10.0F,
                2000.0F,
                Block.soundStoneFootstep,
                "obsidian_decorative");
        this.setTextureName("utilitycraft:obsidian/obsidian");
        this.setUnlocalizedName("obsidian_decorative");
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int x, int y, int z) {
        return 3;
    }
}