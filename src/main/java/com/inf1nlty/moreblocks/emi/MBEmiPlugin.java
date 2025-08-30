package com.inf1nlty.moreblocks.emi;

import com.inf1nlty.moreblocks.block.MBBlocks;
import emi.dev.emi.emi.api.EmiPlugin;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;

public class MBEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        if (MBBlocks.coloredCement == null || MBBlocks.concrete == null) {
            return;
        }

        int cementId = MBBlocks.coloredCement.blockID;
        int concreteId = MBBlocks.concrete.blockID;

        for (int meta = 0; meta < 16; meta++) {
            EmiWorldInteractionRecipe recipe =
                    MBEmiRecipe.makeColoredCementAgingRecipe(meta, cementId, concreteId);
            registry.addRecipe(recipe);
        }
    }
}