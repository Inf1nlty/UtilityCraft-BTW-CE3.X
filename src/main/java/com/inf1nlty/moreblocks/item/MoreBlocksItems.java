package com.inf1nlty.moreblocks.item;

import net.minecraft.src.*;

public class MoreBlocksItems {

    public static Item soulforged_steel_sheet;
    public static Item colored_cement_bucket;

    public static void initMBItems() {
        soulforged_steel_sheet = new MBItem(23334)
                .setCreativeTab(CreativeTabs.tabMaterials).setTextureName("moreblocks:soulforged_steel_sheet").setUnlocalizedName("soulforged_steel_sheet");

        colored_cement_bucket = new ItemColoredCementBucket(23340)
                .setCreativeTab(CreativeTabs.tabMaterials);
    }
}