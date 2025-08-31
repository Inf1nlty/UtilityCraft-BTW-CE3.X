package com.inf1nlty.extendedcraft.emi;

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
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

public class ECEmiRecipe {

    private static List<EmiStack> makeMetaStacks(Item item) {
        List<EmiStack> stacks = new ArrayList<>();
        for (int meta = 0; meta < 16; meta++) {
            stacks.add(EmiStack.of(new ItemStack(item, 1, meta)));
        }
        return stacks;
    }

    public static EmiWorldInteractionRecipe makeBucketPourRecipe(Item bucketItem, Item cementBlockItem) {

        List<EmiStack> bucketStacks = makeMetaStacks(bucketItem);
        List<EmiStack> cementStacks = makeMetaStacks(cementBlockItem);

        EmiIngredient bucketIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                bucketStacks,
                1L
        );
        EmiIngredient cementIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                cementStacks,
                1L
        );

        EmiStack emptyBucketStack = EmiStack.of(new ItemStack(Item.bucketEmpty, 1, 0));

        EmiStack fistStack = emi.dev.emi.emi.api.stack.EmiFistStack.HAND;
        EmiIngredient fistIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(fistStack),
                1L
        );

        EmiStack pistonStack = EmiStack.of(new ItemStack(Item.itemsList[Block.pistonBase.blockID], 1, 0));
        EmiIngredient pistonIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(pistonStack),
                1L
        );

        Function<SlotWidget, SlotWidget> fistTooltip = slot -> slot.appendTooltip(Text.translatable("emi.colored_cement_bucket.right_click.tooltip"));
        Function<SlotWidget, SlotWidget> pistonTooltip = slot -> slot.appendTooltip(Text.translatable("emi.colored_cement_bucket.piston.tooltip"));

        // Output stack uses meta 0; EMI input allows switching, output stays meta 0 due to API
        EmiStack cementStack = EmiStack.of(new ItemStack(cementBlockItem, 1, 0));

        ResourceLocation id = new ResourceLocation("extendedcraft", "colored_cement_bucket_pour");

        return EmiWorldInteractionRecipe.builder()
                .id(id)
                .leftInput(bucketIngredient)
                .rightInput(fistIngredient, true, fistTooltip)
                .rightInput(pistonIngredient, true, pistonTooltip)
                .output(cementStack)
                .output(emptyBucketStack)
                .build();

    }

    public static EmiWorldInteractionRecipe makeCementAgingRecipe(Item cementBlockItem, Item concreteItem) {

        List<EmiStack> cementStacks = makeMetaStacks(cementBlockItem);
        List<EmiStack> concreteStacks = makeMetaStacks(concreteItem);

        EmiIngredient cementIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                cementStacks,
                1L
        );
        EmiIngredient concreteIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                concreteStacks,
                1L
        );

        EmiStack clockStack = EmiStack.of(new ItemStack(Item.itemsList[347], 1, 0));
        EmiIngredient clockIngredient = EmiTags.getIngredient(
                emi.dev.emi.emi.Prototype.class,
                Collections.singletonList(clockStack),
                1L
        );
        Function<SlotWidget, SlotWidget> clockTooltip = slot -> slot.appendTooltip(Text.translatable("emi.colored_cement.aging.tooltip"));

        // Output stack uses meta 0; EMI input allows switching, output stays meta 0 due to API
        EmiStack concreteStack = EmiStack.of(new ItemStack(concreteItem, 1, 0));

        ResourceLocation id = new ResourceLocation("extendedcraft", "colored_cement_aging");

        return EmiWorldInteractionRecipe.builder()
                .id(id)
                .leftInput(cementIngredient)
                .rightInput(clockIngredient, true, clockTooltip)
                .output(concreteStack)
                .build();
    }
}