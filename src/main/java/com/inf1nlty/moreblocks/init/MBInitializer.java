package com.inf1nlty.moreblocks.init;

import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.item.MoreBlocksItems;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class MBInitializer {

    private static final int[] DYE_META_FOR_COLOR = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

    public static void initMBRecipes() {
        addCraftingRecipes();
//        addCampfireRecipes();
//        addCrucibleRecipes();
        addCauldronRecipes();
//        addMillstoneRecipes();
//        addOvenRecipes();
        addSoulforgeRecipes();
    }

    private static void addCraftingRecipes() {
    }

    private static void addCauldronRecipes() {

        for (int color = 0; color < 16; color++) {
            ItemStack[] outputs = new ItemStack[]{
                    new ItemStack(MoreBlocksItems.colored_cement_bucket, 1, color),
                    new ItemStack(Item.glassBottle, 1)
            };
            ItemStack[] inputs = new ItemStack[]{
                    new ItemStack(BTWItems.cementBucket),
                    new ItemStack(Item.bone),
                    new ItemStack(Item.dyePowder, 1, DYE_META_FOR_COLOR[color]),
                    new ItemStack(Item.potion, 1, 0)
            };
            RecipeManager.addCauldronRecipe(outputs, inputs);
        }
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