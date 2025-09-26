package com.inf1nlty.utilitycraft.mixin.world.item;

import com.inf1nlty.utilitycraft.item.rapier.IRapier;
import com.inf1nlty.utilitycraft.item.saber.ISaber;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemSword.class)
public abstract class ItemSwordMixin {

    @Shadow private float weaponDamage;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initCustomDamage(int id, EnumToolMaterial material, CallbackInfo ci) {

        if (this instanceof ISaber) {
            this.weaponDamage = ((ISaber)this).getDamage();
        }

        else if (this instanceof IRapier) {
            this.weaponDamage = ((IRapier)this).getDamage();
        }
    }
}