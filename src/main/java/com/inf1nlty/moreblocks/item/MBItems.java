package com.inf1nlty.moreblocks.item;

import net.minecraft.src.*;

public class MBItems {
    public static Item soulforged_steel_sheet;

    public static void initMBItems() {
        soulforged_steel_sheet = new Item(23334)
                .setCreativeTab(CreativeTabs.tabMaterials).setTextureName("moreblocks:soulforged_steel_sheet").setUnlocalizedName("soulforged_steel_sheet");
    }
}