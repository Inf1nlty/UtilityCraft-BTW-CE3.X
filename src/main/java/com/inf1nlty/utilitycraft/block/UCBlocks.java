package com.inf1nlty.utilitycraft.block;

import com.inf1nlty.utilitycraft.block.tileentity.TileEntityColoredCement;
import com.inf1nlty.utilitycraft.block.tileentity.TileEntityColoredCementBucket;
import com.inf1nlty.utilitycraft.block.tileentity.TileEntitySteelChest;
import com.inf1nlty.utilitycraft.item.*;
import net.minecraft.src.*;

public class UCBlocks {
    public static BlockSteelChest steelChest;
    public static BlockColoredCement coloredCement;
    public static BlockBucketBlockColoredCement coloredCementBucketBlock;
    public static BlockConcrete concrete;
    public static BlockObsidianSlab obsidianSlab;
    public static BlockObsidianDecorative obsidianDecorative;
    public static BlockObsidianStairs obsidianStairs;
    public static BlockObsidianTrapdoor obsidianTrapdoor;
    public static BlockObsidianMouldingAndDecorative obsidianMouldingDecorative;

    public static void initMBBlocks() {

        steelChest = new BlockSteelChest(3700,1);
        Item.itemsList[steelChest.blockID] = new UCItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelChest.class, "utilitycraft:steel_chest");

        concrete = new BlockConcrete(3701);
        Item.itemsList[concrete.blockID] = new ItemConcrete(concrete.blockID - 256);

        coloredCement = new BlockColoredCement(3702, concrete);
        Item.itemsList[coloredCement.blockID] = new ItemBlockColoredCement(coloredCement.blockID - 256);
        TileEntity.addMapping(TileEntityColoredCement.class, "utilitycraft:colored_cement");

        coloredCementBucketBlock = new BlockBucketBlockColoredCement(3703);
        Item.itemsList[coloredCementBucketBlock.blockID] = new ItemBlockColoredCementBucket(coloredCementBucketBlock.blockID - 256);
        TileEntity.addMapping(TileEntityColoredCementBucket.class, "utilitycraft:colored_cement_bucket_block");

        obsidianSlab = new BlockObsidianSlab(3704);
        Item.itemsList[obsidianSlab.blockID] = new UCSlabBlockItem(obsidianSlab.blockID - 256);

        obsidianStairs = new BlockObsidianStairs(3705);
        Item.itemsList[obsidianStairs.blockID] = new UCItemBlock(obsidianStairs.blockID - 256);

        obsidianTrapdoor = new BlockObsidianTrapdoor(3706);
        Item.itemsList[obsidianTrapdoor.blockID] = new UCItemBlock(obsidianTrapdoor.blockID - 256);

        obsidianDecorative = new BlockObsidianDecorative(3707);
        Item.itemsList[obsidianDecorative.blockID] = new ItemObsidianDecorative(obsidianDecorative.blockID - 256);

        obsidianMouldingDecorative = new BlockObsidianMouldingAndDecorative(3708, obsidianDecorative.blockID);
        Item.itemsList[obsidianMouldingDecorative.blockID] = new ItemObsidianMouldingAndDecorative(obsidianMouldingDecorative.blockID - 256);

    }
}