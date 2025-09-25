package com.inf1nlty.utilitycraft.item;

import com.inf1nlty.utilitycraft.item.rapier.*;
import com.inf1nlty.utilitycraft.item.saber.*;
import net.minecraft.src.*;

public class UCItems {

    public static Item soulforged_steel_sheet;
    public static Item colored_cement_bucket;
    public static Item obsidian_boat;

    public static Item gold_saber;
    public static Item iron_saber;
    public static Item diamond_saber;
    public static Item soulforged_steel_saber;

    public static Item gold_rapier;
    public static Item iron_rapier;
    public static Item diamond_rapier;
    public static Item soulforged_steel_rapier;

    public static void initMBItems() {
        soulforged_steel_sheet = new UCItem(23334).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("utilitycraft:soulforged_steel_sheet").setUnlocalizedName("soulforged_steel_sheet");

        colored_cement_bucket = new ItemColoredCementBucket(23335);

        obsidian_boat = new ItemObsidianBoat(23336);

        gold_saber = new GoldenSaber(23337);
        iron_saber = new IronSaber(23338);
        diamond_saber = new DiamondSaber(23339);
        soulforged_steel_saber = new SFSSaber(23340);

        gold_rapier = new GoldRapier(23341);
        iron_rapier = new IronRapier(23342);
        diamond_rapier = new DiamondRapier(23343);
        soulforged_steel_rapier = new SFSRapier(23344);
    }
}