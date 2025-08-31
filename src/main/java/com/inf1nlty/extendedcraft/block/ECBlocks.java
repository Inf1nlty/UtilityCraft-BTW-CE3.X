package com.inf1nlty.extendedcraft.block;

import com.inf1nlty.extendedcraft.block.tileentity.TileEntityColoredCement;
import com.inf1nlty.extendedcraft.block.tileentity.TileEntityColoredCementBucket;
import com.inf1nlty.extendedcraft.block.tileentity.TileEntitySteelLocker;
import com.inf1nlty.extendedcraft.item.*;
import net.minecraft.src.*;

public class ECBlocks {
    public static BlockSteelLocker steelChest;
    public static BlockColoredCement coloredCement;
    public static BucketBlockColoredCement coloredCementBucketBlock;
    public static BlockConcrete concrete;
    public static BlockObsidianSlab obsidianSlab;
    public static BlockObsidianDecorative obsidianDecorative;
    public static BlockObsidianStairs obsidianStairs;
    public static BlockObsidianTrapdoor obsidianTrapdoor;
    public static BlockObsidianMouldingAndDecorative obsidianMouldingDecorative;

    public static void initMBBlocks() {

        steelChest = new BlockSteelLocker(3100,1);
        Item.itemsList[steelChest.blockID] = new ECItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelLocker.class, "extendedcraft:steel_locker");

        concrete = new BlockConcrete(3500);
        Item.itemsList[concrete.blockID] = new ItemConcrete(concrete.blockID - 256);

            coloredCement = new BlockColoredCement(3501, concrete);
        Item.itemsList[coloredCement.blockID] = new ItemBlockColoredCement(coloredCement.blockID - 256);
        TileEntity.addMapping(TileEntityColoredCement.class, "extendedcraft:colored_cement");

        coloredCementBucketBlock = new BucketBlockColoredCement(3502);
        Item.itemsList[coloredCementBucketBlock.blockID] = new ItemBlockColoredCementBucket(coloredCementBucketBlock.blockID - 256);
        TileEntity.addMapping(TileEntityColoredCementBucket.class, "extendedcraft:colored_cement_bucket_block");

        obsidianSlab = new BlockObsidianSlab(3600);
        Item.itemsList[obsidianSlab.blockID] = new ECSlabBlockItem(obsidianSlab.blockID - 256);

        obsidianStairs = new BlockObsidianStairs(3601);
        Block.blocksList[3601] = obsidianStairs;
        Item.itemsList[obsidianStairs.blockID] = new ECItemBlock(obsidianStairs.blockID - 256);

        obsidianTrapdoor = new BlockObsidianTrapdoor(3602);
        Item.itemsList[obsidianTrapdoor.blockID] = new ECItemBlock(obsidianTrapdoor.blockID - 256);

        obsidianDecorative = new BlockObsidianDecorative(3604);
        Item.itemsList[obsidianDecorative.blockID] = new ItemObsidianDecorative(obsidianDecorative.blockID - 256);

        obsidianMouldingDecorative = new BlockObsidianMouldingAndDecorative(3605, obsidianDecorative.blockID);
        Item.itemsList[obsidianMouldingDecorative.blockID] = new ItemObsidianMouldingAndDecorative(obsidianMouldingDecorative.blockID - 256);

    }
}