package com.inf1nlty.moreblocks.mixin.render;

import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelChest;
import net.minecraft.src.Block;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.TileEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestItemRenderHelper.class)
public class ChestItemRenderHelperMixin {

    @Unique private final TileEntitySteelChest steelChest = new TileEntitySteelChest();

    @Inject(method = "renderChest", at = @At("HEAD"),cancellable = true)
    private void renderSteelChestItem(Block block, int f, float par3, CallbackInfo ci){
        if(block.blockID == MBBlocks.steelChest.blockID){
            TileEntityRenderer.instance.renderTileEntityAt(this.steelChest, 0.0, 0.0, 0.0, 0.0f);
            ci.cancel();
        }
    }
}
