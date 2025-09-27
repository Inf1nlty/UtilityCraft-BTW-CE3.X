package com.inf1nlty.utilitycraft.mixin.client;

import com.inf1nlty.utilitycraft.block.BlockSteelChest;
import com.inf1nlty.utilitycraft.client.gui.GuiLocker;
import com.inf1nlty.utilitycraft.entity.EntityObsidianBoat;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public abstract class NetClientHandlerMixin {

    @Inject(method = "handleOpenWindow", at = @At("HEAD"), cancellable = true)
    private void utilitycraft$handleOpenWindow(Packet100OpenWindow packet, CallbackInfo ci) {
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

        GuiLocker gui = new GuiLocker(player.inventory, fake);
        mc.displayGuiScreen(gui);

        if (player.openContainer != null) {
            player.openContainer.windowId = packet.windowId & 0xFF;
        }

        ci.cancel();
    }

    @Shadow
    public WorldClient worldClient;

    @Inject(method = "handleVehicleSpawn", at = @At("HEAD"), cancellable = true)
    private void utilitycraft$handleObsidianBoat(Packet23VehicleSpawn packet, CallbackInfo ci) {
        if (packet.type == 110) {
            double x = (double)packet.xPosition / 32.0D;
            double y = (double)packet.yPosition / 32.0D;
            double z = (double)packet.zPosition / 32.0D;

            EntityObsidianBoat boat = new EntityObsidianBoat(worldClient, x, y, z);

            boat.serverPosX = packet.xPosition;
            boat.serverPosY = packet.yPosition;
            boat.serverPosZ = packet.zPosition;
            boat.rotationPitch = (float)(packet.pitch * 360) / 256.0F;
            boat.rotationYaw = (float)(packet.yaw * 360) / 256.0F;
            boat.entityId = packet.entityId;

            worldClient.addEntityToWorld(packet.entityId, boat);

            ci.cancel();
        }
    }

}