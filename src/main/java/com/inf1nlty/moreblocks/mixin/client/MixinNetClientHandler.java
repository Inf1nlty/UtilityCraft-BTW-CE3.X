package com.inf1nlty.moreblocks.mixin.client;

import com.inf1nlty.moreblocks.block.BlockSteelChest;
import com.inf1nlty.moreblocks.client.gui.GuiSteelChest;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public abstract class MixinNetClientHandler {

    @Inject(method = "handleOpenWindow", at = @At("HEAD"), cancellable = true)
    private void moreblocks$handleOpenWindow(Packet100OpenWindow packet, CallbackInfo ci) {
        if (packet.inventoryType != BlockSteelChest.CUSTOM_WINDOW_TYPE) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;
        if (player == null) {
            ci.cancel();
            return;
        }

        int size = packet.slotsCount > 0 ? packet.slotsCount : 133;
        String title = packet.windowTitle != null ? packet.windowTitle : "SteelChest";
        boolean localized = packet.useProvidedWindowTitle;

        IInventory fake;
        try {
            fake = new InventoryBasic(title, localized, size);
        } catch (Throwable t) {
            fake = new InventoryBasic("SteelChest", false, 133);
        }

        GuiSteelChest gui = new GuiSteelChest(player.inventory, fake);
        mc.displayGuiScreen(gui);

        if (player.openContainer != null) {
            player.openContainer.windowId = packet.windowId & 0xFF;
        }

        ci.cancel();
    }
}