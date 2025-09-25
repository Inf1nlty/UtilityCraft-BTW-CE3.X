package com.inf1nlty.utilitycraft.item;

import com.inf1nlty.utilitycraft.item.saber.DiamondSaber;
import com.inf1nlty.utilitycraft.item.saber.IronSaber;
import com.inf1nlty.utilitycraft.item.saber.SFSSaber;
import net.minecraft.src.*;

public class UCItems {

    public static Item soulforged_steel_sheet;
    public static Item colored_cement_bucket;
    public static Item obsidian_boat;

    public static Item iron_saber;
    public static Item diamond_saber;
    public static Item soulforged_steel_saber;

    public static void initMBItems() {
        soulforged_steel_sheet = new UCItem(23334).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("utilitycraft:soulforged_steel_sheet").setUnlocalizedName("soulforged_steel_sheet");

        colored_cement_bucket = new ItemColoredCementBucket(23335);

        obsidian_boat = new ItemObsidianBoat(23336);

        iron_saber = new IronSaber(23337);
        diamond_saber = new DiamondSaber(23338);
        soulforged_steel_saber = new SFSSaber(23339);
    }
}