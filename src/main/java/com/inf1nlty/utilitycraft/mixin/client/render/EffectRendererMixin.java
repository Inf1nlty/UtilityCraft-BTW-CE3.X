package com.inf1nlty.utilitycraft.mixin.client.render;

import com.inf1nlty.utilitycraft.client.ParticleSpawnQueue;
import net.minecraft.src.EffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public abstract class EffectRendererMixin {

    @Inject(method = "updateEffects", at = @At("TAIL"))
    private void utilitycraft$flushQueuedParticles(CallbackInfo ci) {
        ParticleSpawnQueue.flush();
    }
}