package com.inf1nlty.utilitycraft.mixin;

import com.inf1nlty.utilitycraft.item.rapier.IRapier;
import com.inf1nlty.utilitycraft.item.saber.ISaber;
import net.minecraft.src.*;
import btw.item.BTWItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {

    /**
     * Allows players to use canvas, diamond shovel, and steel shovel as their main weapon to be considered as ship acceleration, which is fully compatible with BTW.
     *
     * @author Inf1nlty
     * @reason 1
     */
    @Overwrite
    public double movementModifierWhenRidingBoat() {

        ItemStack held = ((EntityPlayer) (Object) this).getHeldItem();

        if (held != null) {
            Item item = held.getItem();
            if (item == BTWItems.windMillBlade || item == Item.shovelDiamond || item == BTWItems.steelShovel) {
                return 1.0D;
            }
        }
        return 0.35D;
    }

    /**
     * Allows players to use canvas, diamond shovel, and steel shovel as their main weapon to be considered as ship acceleration, which is fully compatible with BTW.
     *
     * @author Inf1nlty
     * @reason 1
     */
    @Overwrite
    public boolean appliesConstantForceWhenRidingBoat() {

        ItemStack held = ((EntityPlayer) (Object) this).getHeldItem();

        if (held != null) {
            Item item = held.getItem();
            return item == BTWItems.windMillBlade;
        }
        return false;
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemStack;hitEntity(Lnet/minecraft/src/EntityLivingBase;Lnet/minecraft/src/EntityPlayer;)V", shift = At.Shift.AFTER))
    private void playWeaponHitEffects(Entity targetEntity, CallbackInfo ci) {

        EntityPlayer player = (EntityPlayer)(Object)this;
        ItemStack stack = player.getHeldItem();

        if (targetEntity instanceof EntityLivingBase living && living.hurtTime == living.maxHurtTime) {

                if (stack.getItem() instanceof IRapier rapier) {
                    rapier.playAttackSound(player, living);
                }

                else if (stack.getItem() instanceof ISaber saber) {
                    saber.playAttackSound(player, living);

                    if (!player.worldObj.isRemote) {
                        float yaw = player.rotationYaw;
                        double x = player.posX - Math.sin(Math.toRadians(yaw)) * 0.5D;
                        double y = player.posY + player.getEyeHeight() - 0.4D;
                        double z = player.posZ + Math.cos(Math.toRadians(yaw)) * 0.5D;
                        player.worldObj.playAuxSFX(99999, (int)x, (int)y, (int)z, 9999);
                    }
                }
        }
    }
}