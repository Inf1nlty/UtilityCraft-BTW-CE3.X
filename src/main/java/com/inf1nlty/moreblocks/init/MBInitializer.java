package com.inf1nlty.moreblocks.init;

import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.item.MoreBlocksItems;
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
    }

    private static void addSoulforgeRecipes() {
        RecipeManager.addSoulforgeRecipe(new ItemStack(MBBlocks.steelChest,4),
                new Object[]{"SSSS", "SCCS", "SCCS", "SSSS",
                        'S', MoreBlocksItems.soulforged_steel_sheet,
                        'C', new ItemStack(BTWBlocks.chest)});

        RecipeManager.addShapelessSoulforgeRecipe(
                new ItemStack(MoreBlocksItems.soulforged_steel_sheet, 8),
                new Object[] {
                        new ItemStack(BTWItems.soulforgedSteelIngot),
                        new ItemStack(BTWItems.soulforgedSteelIngot),
                        new ItemStack(BTWItems.soulforgedSteelIngot),
                        new ItemStack(BTWItems.soulforgedSteelIngot)
                }
        );
    }
}