package com.inf1nlty.utilitycraft.mixin;

import btw.item.BTWItems;
import com.inf1nlty.utilitycraft.entity.EntityObsidianBoat;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityBoat.class)
public abstract class EntityBoatMixin {

    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;isAABBInMaterial(Lnet/minecraft/src/AxisAlignedBB;Lnet/minecraft/src/Material;)Z"))
    private boolean utilitycraft$allowObsidianBoatLavaFloat(World world, AxisAlignedBB box, Material material) {
        Object self = this;
        if (self instanceof EntityObsidianBoat) {
            return world.isAABBInMaterial(box, Material.water) || world.isAABBInMaterial(box, Material.lava);
        } else {
            return world.isAABBInMaterial(box, material);
        }
    }

    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;spawnParticle(Ljava/lang/String;DDDDDD)V"))

    private void utilitycraft$dynamicSplash(World world, String particle, double x, double y, double z, double dx, double dy, double dz) {
        EntityBoat boat = (EntityBoat)(Object)this;
        String useParticle = particle;
        if (boat instanceof EntityObsidianBoat) {
            int blockX = MathHelper.floor_double(x);
            int blockY = MathHelper.floor_double(y - 0.2D);
            int blockZ = MathHelper.floor_double(z);
            int blockId = world.getBlockId(blockX, blockY, blockZ);
            if (blockId == Block.lavaStill.blockID || blockId == Block.lavaMoving.blockID) {
                useParticle = "smoke";
            } else if (blockId == Block.waterStill.blockID || blockId == Block.waterMoving.blockID) {
                useParticle = "splash";
            }
        }
        world.spawnParticle(useParticle, x, y, z, dx, dy, dz);
    }

    @Unique
    private int utilitycraft$hungerBoostTimer = 0;

    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void utilitycraft$handleBoostHunger(CallbackInfo ci) {

        EntityBoat boat = (EntityBoat)(Object)this;

        if (boat.riddenByEntity instanceof EntityPlayer player) {

            ItemStack held = player.getHeldItem();

            boolean isShovel = held != null && (held.getItem() == Item.shovelDiamond || held.getItem() == BTWItems.steelShovel);
            boolean isMoving = player.moveForward != 0.0D || player.moveStrafing != 0.0D;

            if (isShovel && isMoving) {
                utilitycraft$hungerBoostTimer++;
                if (utilitycraft$hungerBoostTimer >= 20)
                {
                    utilitycraft$hungerBoostTimer = 0;
                    FoodStats food = player.getFoodStats();
                    if (food.getFoodLevel() > 0) {
                        food.setFoodLevel(food.getFoodLevel() - 1);
                    }
                }
            }
            else {
                utilitycraft$hungerBoostTimer = 0;
            }
        }
        else {
            utilitycraft$hungerBoostTimer = 0;
        }
    }

}