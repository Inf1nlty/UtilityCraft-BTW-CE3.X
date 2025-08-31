package com.inf1nlty.moreblocks.emi;

import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.item.MoreBlocksItems;
import emi.dev.emi.emi.api.EmiPlugin;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import net.minecraft.src.Item;

public class MBEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        if (MBBlocks.coloredCement == null || MBBlocks.concrete == null || MoreBlocksItems.colored_cement_bucket == null) {
            return;
        }

        Item bucketItem = MoreBlocksItems.colored_cement_bucket;
        Item cementBlockItem = Item.itemsList[MBBlocks.coloredCement.blockID];
        Item concreteItem = Item.itemsList[MBBlocks.concrete.blockID];

        for (int meta = 0; meta < 16; meta++) {

            EmiWorldInteractionRecipe pour =
                    MBEmiRecipe.makeBucketPourRecipe(meta, bucketItem, cementBlockItem);
            registry.addRecipe(pour);


            EmiWorldInteractionRecipe aging =
                    MBEmiRecipe.makeCementAgingRecipe(meta, cementBlockItem, concreteItem);
            registry.addRecipe(aging);
        }
    }
}