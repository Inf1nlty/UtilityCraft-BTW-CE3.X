package com.inf1nlty.utilitycraft.mixin;

import btw.entity.util.BTWEntityMapper;
import com.inf1nlty.utilitycraft.entity.UtilityCraftEntityMapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BTWEntityMapper.class)
public class BTWEntityMapperMixin {

    @Inject(method = "createModEntityMappings", at = @At("TAIL"), remap = false)
    private static void mapCustomEntities(CallbackInfo ci){
        UtilityCraftEntityMapper.createModEntityMappings();
    }
}