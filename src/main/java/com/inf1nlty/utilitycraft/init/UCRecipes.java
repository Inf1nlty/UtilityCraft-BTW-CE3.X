package com.inf1nlty.utilitycraft.init;

import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import com.inf1nlty.utilitycraft.block.UCBlocks;
import com.inf1nlty.utilitycraft.item.UCItems;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class UCRecipes {

    private static final int[] DYE_META_FOR_COLOR = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

    public static void registerRecipes() {
        addCraftingRecipes();
//        addCampfireRecipes();
        addCrucibleRecipes();
        addCauldronRecipes();
//        addMillstoneRecipes();
//        addOvenRecipes();
        addSoulforgeRecipes();
    }

    private static void addCraftingRecipes() {
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianSlab, 6), new Object[] {"OOO", 'O', Block.obsidian});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianStairs, 6), new Object[] {"O  ", "OO ", "OOO", 'O', Block.obsidian});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianTrapdoor, 1), new Object[] {"OOO", "OOO", 'O', Block.obsidian});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianTrapdoor, 2), new Object[] {"DDM", "DDM", 'D', new ItemStack(UCBlocks.obsidianDecorative, 1, 0), 'M', new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addRecipe(new ItemStack(Block.obsidian, 1), new Object[] {"S", "S", 'S', UCBlocks.obsidianSlab});
        RecipeManager.addShapelessRecipe(new ItemStack(UCBlocks.obsidianDecorative, 1, 0), new Object[] {new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addShapelessRecipe(new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new Object[] {new ItemStack(UCBlocks.obsidianDecorative, 1, 1), new ItemStack(UCBlocks.obsidianDecorative, 1, 1)});
        RecipeManager.addShapelessRecipe(new ItemStack(Block.obsidian, 1), new Object[] {new ItemStack(UCBlocks.obsidianDecorative, 1, 0), new ItemStack(UCBlocks.obsidianDecorative, 1, 0)});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianStairs, 1), new Object[] {"MMM", 'M', new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addShapelessRecipe(new ItemStack(UCBlocks.obsidianDecorative, 6, 14), new Object[] {new ItemStack(Block.obsidian, 1, 0), new ItemStack(Block.obsidian, 1, 0), new ItemStack(Block.obsidian, 1, 0), new ItemStack(Block.obsidian, 1, 0), new ItemStack(Block.obsidian, 1, 0), new ItemStack(Block.obsidian, 1, 0)});
        RecipeManager.addShapelessRecipe(new ItemStack(UCBlocks.obsidianDecorative, 2, 14), new Object[] {new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 12), new Object[] {"M", "M", "M", 'M', new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianDecorative, 4, 12), new Object[] {"DDD", " M ", 'D', new ItemStack(UCBlocks.obsidianDecorative, 1, 0), 'M', new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianMouldingDecorative, 4, 15), new Object[] {"DDD", " M ", " M ", 'D', new ItemStack(UCBlocks.obsidianDecorative, 1, 0), 'M', new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});
        RecipeManager.addRecipe(new ItemStack(UCBlocks.obsidianMouldingDecorative, 6, 13), new Object[] {" D ", "OOO", "OOO", 'D', new ItemStack(UCBlocks.obsidianDecorative, 1, 0), 'O', Block.obsidian});
        RecipeManager.addRecipe(new ItemStack(UCItems.obsidian_boat, 1), new Object[] {"   ", "OMO", "OOO", 'O', Block.obsidian, 'M', Item.magmaCream});
        RecipeManager.addRecipe(new ItemStack(UCItems.obsidian_boat, 1), new Object[] {"   ", "SMS", "SSS", 'S', new ItemStack(UCBlocks.obsidianDecorative, 1, 0), 'M', Item.magmaCream});

        RecipeManager.addRecipe(new ItemStack(UCItems.golden_saber, 1), new Object[] {" G ", " G ", "GH ", 'G', new ItemStack(Item.ingotGold, 1, 0), 'H', BTWItems.haft});
        RecipeManager.addRecipe(new ItemStack(UCItems.iron_saber, 1), new Object[] {" I ", " I ", "IH ", 'I', new ItemStack(Item.ingotIron, 1, 0), 'H', BTWItems.haft});
        RecipeManager.addRecipe(new ItemStack(UCItems.diamond_saber, 1), new Object[] {" D ", " D ", "DH ", 'D', new ItemStack(BTWItems.diamondIngot, 1, 0), 'H', BTWItems.haft});

        RecipeManager.addRecipe(new ItemStack(UCItems.golden_rapier, 1), new Object[] {"  G", "GG ", "HG ", 'G', new ItemStack(Item.ingotGold, 1, 0), 'H', BTWItems.haft});
        RecipeManager.addRecipe(new ItemStack(UCItems.iron_rapier, 1), new Object[] {"  I", "II ", "HI ", 'I', new ItemStack(Item.ingotIron, 1, 0), 'H', BTWItems.haft});
        RecipeManager.addRecipe(new ItemStack(UCItems.diamond_rapier, 1), new Object[] {"  D", "DD ", "HD ", 'D', new ItemStack(BTWItems.diamondIngot, 1, 0), 'H', BTWItems.haft});
    }

    private static void addCauldronRecipes() {

        for (int color = 0; color < 16; color++) {
            ItemStack[] outputs = new ItemStack[]{
                    new ItemStack(UCItems.colored_cement_bucket, 1, color),
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

    private static void addCrucibleRecipes() {
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.goldNugget, 18), new ItemStack[]{new ItemStack(UCItems.golden_saber, 1, Short.MAX_VALUE)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 18), new ItemStack[]{new ItemStack(UCItems.iron_saber, 1, Short.MAX_VALUE)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 3), new ItemStack[]{new ItemStack(UCItems.diamond_saber, 1, Short.MAX_VALUE)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.soulforgedSteelIngot, 4), new ItemStack[]{new ItemStack(UCItems.soulforged_steel_saber, 1, Short.MAX_VALUE)});

        RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.goldNugget, 24), new ItemStack[]{new ItemStack(UCItems.golden_rapier, 1, Short.MAX_VALUE)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 24), new ItemStack[]{new ItemStack(UCItems.iron_rapier, 1, Short.MAX_VALUE)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 4), new ItemStack[]{new ItemStack(UCItems.diamond_rapier, 1, Short.MAX_VALUE)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.soulforgedSteelIngot, 3), new ItemStack[]{new ItemStack(UCItems.soulforged_steel_rapier, 1, Short.MAX_VALUE)});
    }

    private static void addSoulforgeRecipes() {
        RecipeManager.addSoulforgeRecipe(new ItemStack(UCBlocks.steelChest,4), new Object[]{"SSSS", "SCCS", "SCCS", "SSSS", 'S', UCItems.soulforged_steel_sheet, 'C', new ItemStack(BTWBlocks.chest)});
        RecipeManager.addShapelessSoulforgeRecipe(new ItemStack(UCItems.soulforged_steel_sheet, 8), new Object[] {new ItemStack(BTWItems.soulforgedSteelIngot), new ItemStack(BTWItems.soulforgedSteelIngot), new ItemStack(BTWItems.soulforgedSteelIngot), new ItemStack(BTWItems.soulforgedSteelIngot)});
        RecipeManager.addShapelessSoulforgeRecipe(new ItemStack(UCBlocks.obsidianDecorative, 8, 0), new Object[] {Block.obsidian, Block.obsidian, Block.obsidian, Block.obsidian});
        RecipeManager.addShapelessSoulforgeRecipe(new ItemStack(UCBlocks.obsidianMouldingDecorative, 8, 0), new Object[] {new ItemStack(UCBlocks.obsidianDecorative, 1, 0), new ItemStack(UCBlocks.obsidianDecorative, 1, 0), new ItemStack(UCBlocks.obsidianDecorative, 1, 0), new ItemStack(UCBlocks.obsidianDecorative, 1, 0)});
        RecipeManager.addShapelessSoulforgeRecipe(new ItemStack(UCBlocks.obsidianDecorative, 8, 1), new Object[]{new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0), new ItemStack(UCBlocks.obsidianMouldingDecorative, 1, 0)});

        RecipeManager.addSoulforgeRecipe(new ItemStack(UCItems.soulforged_steel_saber, 1), new Object[] {" S  ", " S  ", " S  ", "SH  ", 'S', new ItemStack(BTWItems.soulforgedSteelIngot, 1, 0), 'H', BTWItems.haft});
//        RecipeManager.addSoulforgeRecipe(new ItemStack(UCItems.soulforged_steel_saber, 1), new Object[] {" S  ", " S  ", " S  ", "SH  ", 'S', new ItemStack(BTWItems.soulforgedSteelIngot, 1, 0), 'H', BTWItems.haft});
//        RecipeManager.addSoulforgeRecipe(new ItemStack(UCItems.soulforged_steel_saber, 1), new Object[] {"  S ", "  S ", "  S ", " SH ", 'S', new ItemStack(BTWItems.soulforgedSteelIngot, 1, 0), 'H', BTWItems.haft});
//        RecipeManager.addSoulforgeRecipe(new ItemStack(UCItems.soulforged_steel_saber, 1), new Object[] {"   S", "   S", "   S", "  SH", 'S', new ItemStack(BTWItems.soulforgedSteelIngot, 1, 0), 'H', BTWItems.haft});
        RecipeManager.addSoulforgeRecipe(new ItemStack(UCItems.soulforged_steel_rapier, 1), new Object[] {"   S", "  S ", " S  ", "H   ", 'S', new ItemStack(BTWItems.soulforgedSteelIngot, 1, 0), 'H', BTWItems.haft});
    }
}