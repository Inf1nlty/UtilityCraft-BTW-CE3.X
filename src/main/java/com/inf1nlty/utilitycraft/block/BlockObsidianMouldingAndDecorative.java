package com.inf1nlty.utilitycraft.block;

import btw.block.blocks.MouldingAndDecorativeBlock;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;

public class BlockObsidianMouldingAndDecorative extends MouldingAndDecorativeBlock {

    public BlockObsidianMouldingAndDecorative(int blockID, int matchingCornerBlockID) {
        super(blockID, Material.rock,
                "utilitycraft:obsidian/obsidian",
                "utilitycraft:obsidian/obsidian",
                "utilitycraft:obsidian/obsidian",
                "utilitycraft:obsidian/obsidian",
                "utilitycraft:obsidian/obsidian",
                matchingCornerBlockID,
                10.0F, 2000.0F,
                Block.soundStoneFootstep,
                "obsidian_moulding_decorative");
        setTextureName("utilitycraft:obsidian/obsidian");
        setUnlocalizedName("obsidian_moulding_decorative");
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int x, int y, int z) {
        return 3;
    }
}