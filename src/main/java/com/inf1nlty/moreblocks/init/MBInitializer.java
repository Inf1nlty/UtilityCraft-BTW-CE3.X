package com.inf1nlty.moreblocks.init;

import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.item.MBItems;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class MBInitializer {

    public static void initMBRecipes() {
        addCraftingRecipes();
//        addCampfireRecipes();
//        addCrucibleRecipes();
//        addCauldronRecipes();
//        addMillstoneRecipes();
//        addOvenRecipes();
        addSoulforgeRecipes();
    }

    private static void addCraftingRecipes() {
        int[] dyeMeta = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        for (int i = 0; i < 16; i++) {
            ItemStack cementBucket = new ItemStack(BTWItems.cementBucket, 1, 0);
            ItemStack dye = new ItemStack(Item.dyePowder, 1, dyeMeta[i]);
            ItemStack cement = new ItemStack(MBBlocks.coloredCement, 64, i);
            RecipeManager.addShapelessRecipe(cement, new Object[]{ cementBucket, dye });
        }
    }

    private static void addSoulforgeRecipes() {
        RecipeManager.addSoulforgeRecipe(new ItemStack(MBBlocks.steelChest,4),
                new Object[]{"SSSS", "SCCS", "SCCS", "SSSS",
                        'S', MBItems.soulforged_steel_sheet,
                        'C', new ItemStack(BTWBlocks.chest)});

        RecipeManager.addShapelessSoulforgeRecipe(
                new ItemStack(MBItems.soulforged_steel_sheet, 8),
                new Object[] {
                        new ItemStack(BTWItems.soulforgedSteelIngot),
                        new ItemStack(BTWItems.soulforgedSteelIngot),
                        new ItemStack(BTWItems.soulforgedSteelIngot),
                        new ItemStack(BTWItems.soulforgedSteelIngot)
                }
        );
    }
}