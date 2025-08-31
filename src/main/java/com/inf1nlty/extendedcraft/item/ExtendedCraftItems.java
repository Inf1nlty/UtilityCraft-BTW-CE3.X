package com.inf1nlty.extendedcraft.item;

import net.minecraft.src.*;

public class ExtendedCraftItems {

    public static Item soulforged_steel_sheet;
    public static Item colored_cement_bucket;

    public static void initMBItems() {
        soulforged_steel_sheet = new ECItem(23334)
                .setCreativeTab(CreativeTabs.tabMaterials).setTextureName("extendedcraft:soulforged_steel_sheet").setUnlocalizedName("soulforged_steel_sheet");

        colored_cement_bucket = new ItemColoredCementBucket(23340)
                .setCreativeTab(CreativeTabs.tabMaterials);
    }
}