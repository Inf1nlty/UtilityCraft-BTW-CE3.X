package com.inf1nlty.extendedcraft.emi;

import com.inf1nlty.extendedcraft.block.ECBlocks;
import com.inf1nlty.extendedcraft.item.ExtendedCraftItems;
import emi.dev.emi.emi.api.EmiPlugin;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import net.minecraft.src.Item;

public class ECEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        if (ECBlocks.coloredCement == null || ECBlocks.concrete == null || ExtendedCraftItems.colored_cement_bucket == null) {
            return;
        }

        Item bucketItem = ExtendedCraftItems.colored_cement_bucket;
        Item cementBlockItem = Item.itemsList[ECBlocks.coloredCement.blockID];
        Item concreteItem = Item.itemsList[ECBlocks.concrete.blockID];

        EmiWorldInteractionRecipe pour =
                ECEmiRecipe.makeBucketPourRecipe(bucketItem, cementBlockItem);
        registry.addRecipe(pour);

        EmiWorldInteractionRecipe aging =
                ECEmiRecipe.makeCementAgingRecipe(cementBlockItem, concreteItem);
        registry.addRecipe(aging);
    }
}