package com.inf1nlty.moreblocks.block;

import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelChest;
import net.minecraft.src.*;

public class MBBlocks {
    public static BlockSteelChest steelChest;

    public static void initMBBlocks() {
        steelChest = (BlockSteelChest) new BlockSteelChest(3100,1).setTextureName("chestSteel");
        Item.itemsList[steelChest.blockID] = new ItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelChest.class, "SteelChest");
    }
}