package com.inf1nlty.utilitycraft.mixin.world.entity;

import com.inf1nlty.utilitycraft.item.rapier.IRapier;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Unique private static final ThreadLocal<DamageSource> utilitycraft$currentArmorCalcSource = new ThreadLocal<>();

    @Inject(method = "applyArmorCalculations", at = @At("HEAD"))
    private void utilitycraft$storeCurrentArmorCalcSource(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        utilitycraft$currentArmorCalcSource.set(source);
    }

    @Inject(method = "applyArmorCalculations", at = @At("RETURN"))
    private void utilitycraft$clearCurrentArmorCalcSource(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        utilitycraft$currentArmorCalcSource.remove();
    }

    @Redirect(method = "applyArmorCalculations", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityLivingBase;getTotalArmorValue()I"))
    private int utilitycraft$rapierHalveArmor(EntityLivingBase self) {

        DamageSource src = utilitycraft$currentArmorCalcSource.get();
        int orig = self.getTotalArmorValue();

        if (src != null && src.getEntity() instanceof EntityLivingBase attacker) {
            ItemStack held = attacker.getHeldItem();
            if (held != null && held.getItem() instanceof IRapier) {
                return Math.max(0, orig / 2);
            }
        }
        return orig;
    }

    @Inject(method = "attackEntityFrom", at = @At("RETURN"))
    private void adjustRapierInvulnerabilityFrames(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return;

        EntityLivingBase self = (EntityLivingBase)(Object)this;

        if (source != null && source.getEntity() instanceof EntityLivingBase attacker) {

            ItemStack held = attacker.getHeldItem();

            if (held != null && held.getItem() instanceof IRapier) {

                if (self.hurtResistantTime == self.maxHurtResistantTime) {

                    self.hurtResistantTime = 18;

                    self.maxHurtResistantTime = 20;
                }
            }
        }
    }
}