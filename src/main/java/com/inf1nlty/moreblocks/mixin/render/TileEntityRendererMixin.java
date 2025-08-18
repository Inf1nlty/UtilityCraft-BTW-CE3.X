package com.inf1nlty.moreblocks.mixin.render;

import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelChest;
import com.inf1nlty.moreblocks.render.TileEntitySteelChestRenderer;
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
    public Map specialRendererMap;
    @Shadow
    public static TileEntityRenderer instance;
    @Shadow
    public abstract Map getSpecialRendererMap();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addCustomNightmareRendering(CallbackInfo ci) {
        this.specialRendererMap.put(TileEntitySteelChest.class, new TileEntitySteelChestRenderer());
        TileEntityRenderer thisObj = (TileEntityRenderer) (Object) this;

        for (Object var2 : this.specialRendererMap.values()) {
            ((TileEntitySpecialRenderer) var2).setTileEntityRenderer(thisObj);
        }
        ((TileEntitySpecialRenderer) this.specialRendererMap.get(TileEntitySteelChest.class)).setTileEntityRenderer(thisObj);
    }
}