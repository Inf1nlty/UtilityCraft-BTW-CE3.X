package com.inf1nlty.moreblocks.block;

import com.inf1nlty.moreblocks.block.tileentity.TileEntityColoredCement;
import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelLocker;
import com.inf1nlty.moreblocks.item.*;
import net.minecraft.src.*;

public class MBBlocks {
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
        Item.itemsList[steelChest.blockID] = new MBItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelLocker.class, "moreblocks:SteelChest");

        concrete = new BlockConcrete(3500);
        Item.itemsList[concrete.blockID] = new ItemConcrete(concrete.blockID - 256);

            coloredCement = new BlockColoredCement(3501, concrete);
            TileEntity.addMapping(TileEntityColoredCement.class, "coloredCement");
        Item.itemsList[coloredCement.blockID] = new ItemBlockColoredCement(coloredCement.blockID - 256);

        coloredCementBucketBlock = new BucketBlockColoredCement(3502);
        Item.itemsList[coloredCementBucketBlock.blockID] =
                new ItemBlockColoredCementBucket(coloredCementBucketBlock.blockID - 256);

        obsidianSlab = new BlockObsidianSlab(3600);
        Item.itemsList[obsidianSlab.blockID] = new MBSlabBlockItem(obsidianSlab.blockID - 256);

        obsidianStairs = new BlockObsidianStairs(3601);
        Block.blocksList[3601] = obsidianStairs;
        Item.itemsList[obsidianStairs.blockID] = new MBItemBlock(obsidianStairs.blockID - 256);

        obsidianTrapdoor = new BlockObsidianTrapdoor(3602);
        Item.itemsList[obsidianTrapdoor.blockID] = new MBItemBlock(obsidianTrapdoor.blockID - 256);

        obsidianDecorative = new BlockObsidianDecorative(3604);
        Item.itemsList[obsidianDecorative.blockID] = new MBObsidianDecorativeItem(obsidianDecorative.blockID - 256);

        obsidianMouldingDecorative = new BlockObsidianMouldingAndDecorative(3605, obsidianDecorative.blockID);
        Item.itemsList[obsidianMouldingDecorative.blockID] = new ItemObsidianMouldingAndDecorative(obsidianMouldingDecorative.blockID - 256);

    }
}