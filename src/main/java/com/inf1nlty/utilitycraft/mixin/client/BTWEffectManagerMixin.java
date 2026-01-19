package com.inf1nlty.utilitycraft.mixin.client;

import api.client.EffectHandler;
import com.inf1nlty.utilitycraft.client.ParticleHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import btw.client.fx.BTWEffectManager;

@Mixin(value = BTWEffectManager.class, remap = false)
public abstract class BTWEffectManagerMixin {

    @Inject(method = "initEffects", at = @At("TAIL"))
    private static void utilitycraft$injectSweepAttackEffect(CallbackInfo ci) {

        int sweepEffectId = 99999;

        EffectHandler.effectMap.put(sweepEffectId, (mc, world, player, x, y, z, data) -> {

            if (data == 9999) {
                ParticleHelper.spawnSweepAttack(world, x + 0.5, y + 1.0, z + 0.5);
            }
        });
    }
}