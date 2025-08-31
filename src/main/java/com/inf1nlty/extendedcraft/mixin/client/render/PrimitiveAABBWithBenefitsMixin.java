package com.inf1nlty.extendedcraft.mixin.client.render;

import btw.block.model.BucketModel;
import com.inf1nlty.extendedcraft.block.BucketBlockColoredCement;
import btw.util.PrimitiveAABBWithBenefits;
import com.inf1nlty.extendedcraft.client.render.ClientRenderHelper;
import net.minecraft.src.Block;
import net.minecraft.src.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimitiveAABBWithBenefits.class)
public abstract class PrimitiveAABBWithBenefitsMixin {

    @Shadow
    public abstract int getAssemblyID();

    @Inject(method = "renderAsBlock", at = @At("HEAD"))
    private void onStartRenderAsBlock(RenderBlocks renderBlocks, Block block, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (block instanceof BucketBlockColoredCement) {
            if (this.getAssemblyID() == BucketModel.ASSEMBLY_ID_CONTENTS) {
                int meta = renderBlocks.blockAccess.getBlockMetadata(i, j, k);
                ClientRenderHelper.setCurrentRenderColor(meta & 15);
            }
        }
    }

    @Inject(method = "renderAsBlock", at = @At("RETURN"))
    private void onEndRenderAsBlock(RenderBlocks renderBlocks, Block block, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (block instanceof BucketBlockColoredCement) {
            if (this.getAssemblyID() == BucketModel.ASSEMBLY_ID_CONTENTS) {
                ClientRenderHelper.clearCurrentRenderColor();
            }
        }
    }
}