package com.inf1nlty.moreblocks.emi;

import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.registry.EmiTags;
import emi.dev.emi.emi.api.widget.SlotWidget;
import emi.shims.java.net.minecraft.text.Text;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Item;
import net.minecraft.src.ResourceLocation;

import java.util.List;
import java.util.function.Function;

public class MBEmiRecipe {

    public static EmiWorldInteractionRecipe makeColoredCementAgingRecipe(int colorMeta, int cementBlockId, int concreteBlockId) {
        EmiStack cementStack = EmiStack.of(new ItemStack(cementBlockId, 1, colorMeta));
        EmiIngredient cementIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                List.of(cementStack),
                1L
        );

        EmiStack clockStack = EmiStack.of(new ItemStack(Item.itemsList[347], 1, 0));
        EmiIngredient clockIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                List.of(clockStack),
                1L
        );

        Function<SlotWidget, SlotWidget> clockTooltip = slot ->
                slot.appendTooltip(Text.translatable("emi.colored_cement_aging.tooltip"));

        EmiStack concreteStack = EmiStack.of(new ItemStack(concreteBlockId, 1, colorMeta));

        ResourceLocation id = new ResourceLocation("moreblocks", "colored_cement_aging_" + colorMeta);

        return EmiWorldInteractionRecipe.builder()
                .id(id)
                .leftInput(cementIngredient)
                .rightInput(clockIngredient, true, clockTooltip)
                .output(concreteStack)
                .build();
    }
}