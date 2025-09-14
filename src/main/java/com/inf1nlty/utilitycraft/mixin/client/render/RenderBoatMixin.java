package com.inf1nlty.utilitycraft.mixin.client.render;

import com.inf1nlty.utilitycraft.entity.EntityObsidianBoat;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.RenderBoat;
import net.minecraft.src.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBoat.class)
public class RenderBoatMixin {

    @Unique
    private static final ResourceLocation OBSIDIAN_BOAT_TEXTURE = new ResourceLocation("utilitycraft:textures/entity/obsidian_boat.png");

    @Inject(method = "getBoatTextures", at = @At("HEAD"), cancellable = true)
    private void obsidianBoatTexture(EntityBoat boat, CallbackInfoReturnable<ResourceLocation> cir) {
        if (boat instanceof EntityObsidianBoat) {
            cir.setReturnValue(OBSIDIAN_BOAT_TEXTURE);
        }
    }
}