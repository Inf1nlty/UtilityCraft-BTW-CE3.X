package com.inf1nlty.utilitycraft.mixin.client.render;

import com.inf1nlty.utilitycraft.block.tileentity.TileEntitySteelChest;
import com.inf1nlty.utilitycraft.client.render.TileEntitySteelChestRenderer;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySpecialRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(TileEntityRenderer.class)
public abstract class TileEntityRendererMixin {

    @Shadow
    public Map<Class<?>, TileEntitySpecialRenderer> specialRendererMap;
    @Shadow
    public static TileEntityRenderer instance;
    @Shadow
    public abstract Map<Class<?>, TileEntitySpecialRenderer> getSpecialRendererMap();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void customChestRendering(CallbackInfo ci) {
        specialRendererMap.put(TileEntitySteelChest.class, new TileEntitySteelChestRenderer());
        TileEntityRenderer thisObj = (TileEntityRenderer) (Object) this;

        for (TileEntitySpecialRenderer renderer : specialRendererMap.values()) {
            renderer.setTileEntityRenderer(thisObj);
        }
        specialRendererMap.get(TileEntitySteelChest.class).setTileEntityRenderer(thisObj);
    }
}