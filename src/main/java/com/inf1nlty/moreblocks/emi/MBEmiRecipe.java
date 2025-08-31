package com.inf1nlty.moreblocks.emi;

import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.registry.EmiTags;
import emi.dev.emi.emi.api.widget.SlotWidget;
import emi.shims.java.net.minecraft.text.Text;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Item;
import net.minecraft.src.Block;
import net.minecraft.src.ResourceLocation;

import java.util.Collections;
import java.util.function.Function;

public class MBEmiRecipe {

    public static EmiWorldInteractionRecipe makeBucketPourRecipe(int colorMeta, Item bucketItem, Item cementBlockItem) {
        EmiStack bucketStack = EmiStack.of(new ItemStack(bucketItem, 1, colorMeta));
        EmiIngredient bucketIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(bucketStack),
                1L
        );

        EmiStack emptyBucketStack = EmiStack.of(new ItemStack(Item.bucketEmpty, 1, 0));
        EmiIngredient emptyBucketIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(emptyBucketStack),
                1L
        );
        Function<SlotWidget, SlotWidget> emptyBucketTooltip = slot -> slot.appendTooltip(Text.translatable("emi.colored_cement_bucket.right_click.tooltip"));

        EmiStack pistonStack = EmiStack.of(new ItemStack(Item.itemsList[Block.pistonBase.blockID], 1, 0));
        EmiIngredient pistonIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(pistonStack),
                1L
        );
        Function<SlotWidget, SlotWidget> pistonTooltip = slot -> slot.appendTooltip(Text.translatable("emi.colored_cement_bucket.piston.tooltip"));

        EmiStack cementStack = EmiStack.of(new ItemStack(cementBlockItem, 1, colorMeta));
        ResourceLocation id = new ResourceLocation("moreblocks", "colored_cement_bucket_pour_" + colorMeta);

        return EmiWorldInteractionRecipe.builder()
                .id(id)
                .leftInput(bucketIngredient)
                .rightInput(emptyBucketIngredient, true, emptyBucketTooltip)
                .rightInput(pistonIngredient, true, pistonTooltip)
                .output(cementStack)
                .build();
    }

    public static EmiWorldInteractionRecipe makeCementAgingRecipe(int colorMeta, Item cementBlockItem, Item concreteItem) {
        EmiStack cementStack = EmiStack.of(new ItemStack(cementBlockItem, 1, colorMeta));
        EmiIngredient cementIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(cementStack),
                1L
        );

        EmiStack clockStack = EmiStack.of(new ItemStack(Item.itemsList[347], 1, 0));
        EmiIngredient clockIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(clockStack),
                1L
        );
        Function<SlotWidget, SlotWidget> clockTooltip = slot -> slot.appendTooltip(Text.translatable("emi.colored_cement.aging.tooltip"));

        EmiStack concreteStack = EmiStack.of(new ItemStack(concreteItem, 1, colorMeta));
        ResourceLocation id = new ResourceLocation("moreblocks", "colored_cement_aging_" + colorMeta);

        return EmiWorldInteractionRecipe.builder()
                .id(id)
                .leftInput(cementIngredient)
                .rightInput(clockIngredient, true, clockTooltip)
                .output(concreteStack)
                .build();
    }
}