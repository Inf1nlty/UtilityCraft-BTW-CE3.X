package com.inf1nlty.moreblocks.block;

import com.inf1nlty.moreblocks.block.tileentity.TileEntityColoredCement;
import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelLocker;
import com.inf1nlty.moreblocks.item.ItemBlockColoredCement;
import com.inf1nlty.moreblocks.item.ItemBlockColoredCementBucket;
import com.inf1nlty.moreblocks.item.ItemConcrete;
import com.inf1nlty.moreblocks.item.MBItemBlock;
import net.minecraft.src.*;

public class MBBlocks {
    public static BlockSteelLocker steelChest;
    public static BlockColoredCement coloredCement;
    public static BucketBlockColoredCement coloredCementBucketBlock;
    public static BlockConcrete concrete;

    public static void initMBBlocks() {

        steelChest = (BlockSteelLocker) new BlockSteelLocker(3100,1).setTextureName("chestSteel");
        Item.itemsList[steelChest.blockID] = new MBItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelLocker.class, "SteelChest");

        concrete = new BlockConcrete(3500);
        Item.itemsList[concrete.blockID] = new ItemConcrete(concrete.blockID - 256);

            coloredCement = new BlockColoredCement(3501, concrete);
            TileEntity.addMapping(TileEntityColoredCement.class, "coloredCement");
        Item.itemsList[coloredCement.blockID] = new ItemBlockColoredCement(coloredCement.blockID - 256);

        coloredCementBucketBlock = new BucketBlockColoredCement(3502);
        Item.itemsList[coloredCementBucketBlock.blockID] =
                new ItemBlockColoredCementBucket(coloredCementBucketBlock.blockID - 256);

    }
}