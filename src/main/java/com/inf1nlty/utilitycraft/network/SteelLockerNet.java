package com.inf1nlty.utilitycraft.network;

import btw.BTWAddon;
import btw.network.packet.handler.CustomPacketHandler;
import com.inf1nlty.utilitycraft.block.tileentity.TileEntitySteelChest;
import com.inf1nlty.utilitycraft.inventory.ContainerSteelChest;
import net.minecraft.src.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public final class SteelLockerNet {

    public static String CHANNEL;

    private SteelLockerNet(){}

    public static void register(BTWAddon addon){

        String base = addon.getModID() + "|SL";
        if (base.length() > 20) {
            base = base.substring(0, 20);
        }
        CHANNEL = base;

        addon.registerPacketHandler(CHANNEL, new CustomPacketHandler() {
            @Override
            public void handleCustomPacket(Packet250CustomPayload packet, EntityPlayer player) {
                if (packet == null || packet.data == null) return;
                if (!player.worldObj.isRemote) return;
                handleClient(packet, (EntityClientPlayerMP) player);
            }

            private void handleClient(Packet250CustomPayload packet, EntityClientPlayerMP player){
                try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data))) {
                    byte action = in.readByte();
                    if (action == 1) {
                        int windowId = in.readInt();
                        int x = in.readInt();
                        int y = in.readInt();
                        int z = in.readInt();
                        upgradeToRealContainer(windowId, x, y, z, player);
                    }
                } catch (Exception ignored){}
            }

            private void upgradeToRealContainer(int windowId, int x,int y,int z, EntityClientPlayerMP player){
                if (player.openContainer == null) return;
                if ( (windowId & 0xFF) != (player.openContainer.windowId & 0xFF) ) return;

                TileEntity te = player.worldObj.getBlockTileEntity(x,y,z);
                if (!(te instanceof TileEntitySteelChest chest)) return;

                Container oldC = player.openContainer;
                IInventory oldInv = null;
                if (oldC instanceof ContainerSteelChest scOld) {
                    oldInv = scOld.getChestInventory();
                }

                if (oldInv != null) {
                    int limit = Math.min(oldInv.getSizeInventory(), chest.getSizeInventory());
                    for (int i=0;i<limit;i++){
                        ItemStack s = oldInv.getStackInSlot(i);
                        if (s != null && chest.getStackInSlot(i)==null){
                            chest.setInventorySlotContents(i, s.copy());
                        }
                    }
                }

                int wid = player.openContainer.windowId;

                ContainerSteelChest newC = new ContainerSteelChest(player.inventory, chest);
                newC.windowId = wid;

                if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer gui){
                    gui.inventorySlots = newC;
                }
                player.openContainer = newC;
            }
        });
    }
}