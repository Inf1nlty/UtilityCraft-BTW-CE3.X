package com.inf1nlty.moreblocks.network;

import btw.BTWAddon;
import net.minecraft.src.*;

import java.io.*;

/**
 * Channel for CTRL placement intent. Client sends before right-click is processed.
 */
public final class ColoredCementCtrlNet {

    public static String CHANNEL;
    private static final int OPCODE_CTRL_PLACE = 1;

    private ColoredCementCtrlNet() {}

    public static void register(BTWAddon addon) {

        CHANNEL = addon.getModID() + "|CCBCTRL";

        addon.registerPacketHandler(CHANNEL, (packet, player) -> {
            if (packet == null || packet.data == null || player == null) return;
            if (player.worldObj.isRemote) return;
            try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data))) {
                int op = in.readUnsignedByte();
                if (op == OPCODE_CTRL_PLACE) {
                    CtrlPlacementTracker.set(player);
                }
            } catch (Exception ignored) {}
        });
    }

    public static void sendCtrlIntent() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.thePlayer == null) return;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(OPCODE_CTRL_PLACE);
            dos.close();
            Packet250CustomPayload pkt = new Packet250CustomPayload();
            pkt.channel = CHANNEL;
            pkt.data = bos.toByteArray();
            pkt.length = pkt.data.length;
            mc.thePlayer.sendQueue.addToSendQueue(pkt);
        } catch (Exception ignored) {}
    }
}