package com.inf1nlty.utilitycraft.emi;

import com.inf1nlty.utilitycraft.block.UCBlocks;
import com.inf1nlty.utilitycraft.item.UtilityCraftItems;
import emi.dev.emi.emi.api.EmiPlugin;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import net.minecraft.src.Item;

public class UCEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        if (UCBlocks.coloredCement == null || UCBlocks.concrete == null || UtilityCraftItems.colored_cement_bucket == null) {
            return;
        }

        Item bucketItem = UtilityCraftItems.colored_cement_bucket;
        Item cementBlockItem = Item.itemsList[UCBlocks.coloredCement.blockID];
        Item concreteItem = Item.itemsList[UCBlocks.concrete.blockID];

        EmiWorldInteractionRecipe pour =
                UCEmiRecipe.makeBucketPourRecipe(bucketItem, cementBlockItem);
        registry.addRecipe(pour);

        EmiWorldInteractionRecipe aging =
                UCEmiRecipe.makeCementAgingRecipe(cementBlockItem, concreteItem);
        registry.addRecipe(aging);
    }
}