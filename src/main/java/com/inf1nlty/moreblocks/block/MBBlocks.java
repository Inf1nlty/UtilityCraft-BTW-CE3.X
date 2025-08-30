package com.inf1nlty.moreblocks.block;

import com.inf1nlty.moreblocks.block.tileentity.TileEntityCement;
import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelLocker;
import com.inf1nlty.moreblocks.item.ItemConcrete;
import com.inf1nlty.moreblocks.item.ItemCement;
import net.minecraft.src.*;

public class MBBlocks {
    public static BlockSteelLocker steelChest;
    public static BlockCement coloredCement;
    public static BlockConcrete concrete;

    public static void initMBBlocks() {

        steelChest = (BlockSteelLocker) new BlockSteelLocker(3100,1).setTextureName("chestSteel");
        Item.itemsList[steelChest.blockID] = new ItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelLocker.class, "SteelChest");

        concrete = new BlockConcrete(3500);
        Item.itemsList[concrete.blockID] = new ItemConcrete(concrete.blockID - 256);

        coloredCement = new BlockCement(3501, concrete);
        Item.itemsList[coloredCement.blockID] = new ItemCement(coloredCement.blockID - 256);
        TileEntity.addMapping(TileEntityCement.class, "coloredCement");

    }
}