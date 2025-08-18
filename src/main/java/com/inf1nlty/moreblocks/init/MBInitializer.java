package com.inf1nlty.moreblocks.init;

import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.item.MBItems;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Block;

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
                        'S', MBItems.soulforged_steel_sheet,
                        'C', new ItemStack(BTWBlocks.chest)});

        RecipeManager.addSoulforgeRecipe(new ItemStack(MBItems.soulforged_steel_sheet, 8),
                new Object[]{
                        "IIII", "    ", "    ", "    ",
                        'I', new ItemStack(BTWItems.soulforgedSteelIngot)
                }
        );
    }
}