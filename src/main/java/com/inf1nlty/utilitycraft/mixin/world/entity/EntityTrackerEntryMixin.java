package com.inf1nlty.utilitycraft.mixin.world.entity;

import com.inf1nlty.utilitycraft.entity.EntityObsidianBoat;
import net.minecraft.src.EntityTrackerEntry;
import net.minecraft.src.Entity;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet23VehicleSpawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {

    @Inject(method = "getPacketForThisEntity", at = @At("HEAD"), cancellable = true)
    private void utilitycraft$customObsidianBoatPacket(CallbackInfoReturnable<Packet> cir) {
        Entity self = ((EntityTrackerEntry)(Object)this).myEntity;
        if (self instanceof EntityObsidianBoat) {
            cir.setReturnValue(new Packet23VehicleSpawn(self, 110));
        }
    }

}