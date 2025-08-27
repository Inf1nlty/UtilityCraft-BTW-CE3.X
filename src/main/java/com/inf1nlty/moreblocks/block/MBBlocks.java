package com.inf1nlty.moreblocks.block;

import com.inf1nlty.moreblocks.block.tileentity.TileEntityConcretePowder;
import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelChest;
import com.inf1nlty.moreblocks.item.ItemConcrete;
import com.inf1nlty.moreblocks.item.ItemConcretePowder;
import net.minecraft.src.*;

public class MBBlocks {
    public static BlockSteelChest steelChest;
    public static BlockConcretePowder concretePowder;
    public static BlockConcrete concrete;

    public static void initMBBlocks() {

        steelChest = (BlockSteelChest) new BlockSteelChest(3100,1).setTextureName("chestSteel");
        Item.itemsList[steelChest.blockID] = new ItemBlock(steelChest.blockID - 256);
        TileEntity.addMapping(TileEntitySteelChest.class, "SteelChest");

        concrete = new BlockConcrete(3500);
        Item.itemsList[concrete.blockID] = new ItemConcrete(concrete.blockID - 256);

        concretePowder = new BlockConcretePowder(3501, concrete);
        Item.itemsList[concretePowder.blockID] = new ItemConcretePowder(concretePowder.blockID - 256);
        TileEntity.addMapping(TileEntityConcretePowder.class, "ConcretePowder");

    }
}