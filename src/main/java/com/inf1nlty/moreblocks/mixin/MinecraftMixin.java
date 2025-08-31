package com.inf1nlty.moreblocks.mixin;

import com.inf1nlty.moreblocks.network.ColoredCementCtrlNet;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Inject into runTick (1.6.4 exists) to detect a RIGHT MOUSE BUTTON "rising edge"
 * while CTRL is held and current item is a colored cement bucket.
 * Rising edge = mouse was up previous tick, now down.
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Unique
    private boolean moreblocks_prevRightMouse = false;

    @Unique
    private boolean isRightMouseDown() {
        return Mouse.isButtonDown(1);
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    private void moreblocks$sendCtrlIntent(CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) return;
        EntityClientPlayerMP player = mc.thePlayer;
        if (player == null) return;
        boolean l = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        boolean r = Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
        if (!l && !r) return;
        boolean rightDown = isRightMouseDown();
        if (!moreblocks_prevRightMouse && rightDown) {
            ItemStack held = player.inventory.getCurrentItem();
            if (held == null) return;
            Item item = held.getItem();
            if (item == null) return;
            String unloc = item.getUnlocalizedName();
            if (unloc == null) return;
            if (unloc.startsWith("item.colored_cement_bucket")) {
                ColoredCementCtrlNet.sendCtrlIntent();
                if (held.getTagCompound() == null) {
                    held.setTagCompound(new NBTTagCompound());
                }
                held.getTagCompound().setBoolean("MB_Ctrl", true);
            }
        }
        moreblocks_prevRightMouse = rightDown;
    }
}