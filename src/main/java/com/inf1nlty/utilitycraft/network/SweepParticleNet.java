//package com.inf1nlty.utilitycraft.network;
//
//import btw.BTWAddon;
//import com.inf1nlty.utilitycraft.client.ParticleHelper;
//import net.minecraft.src.*;
//
//import java.io.*;
//
//public final class SweepParticleNet {
//    public static String CHANNEL;
//    private static final int OPCODE_SWEEP_ATTACK = 1;
//
//    private SweepParticleNet() {}
//
//    public static void register(BTWAddon addon) {
//        CHANNEL = addon.getModID() + "|SweepFX";
//        addon.registerPacketHandler(CHANNEL, (packet, player) -> {
//            if (packet == null || packet.data == null || player == null) return;
//            if (!player.worldObj.isRemote) return;
//            try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data))) {
//                int op = in.readUnsignedByte();
//                if (op == OPCODE_SWEEP_ATTACK) {
//                    double x = in.readDouble();
//                    double y = in.readDouble();
//                    double z = in.readDouble();
//                    float yaw = in.readFloat();
//                    ParticleHelper.spawnSweepAttack(player.worldObj, x, y, z);
//                }
//            } catch (Exception ignored) {}
//        });
//    }
//
//    public static void sendSweepAttack(EntityPlayerMP player, double x, double y, double z, float yaw) {
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            DataOutputStream dos = new DataOutputStream(bos);
//            dos.writeByte(OPCODE_SWEEP_ATTACK);
//            dos.writeDouble(x);
//            dos.writeDouble(y);
//            dos.writeDouble(z);
//            dos.writeFloat(yaw);
//            dos.close();
//            Packet250CustomPayload pkt = new Packet250CustomPayload();
//            pkt.channel = CHANNEL;
//            pkt.data = bos.toByteArray();
//            pkt.length = pkt.data.length;
//            player.playerNetServerHandler.sendPacket(pkt);
//        } catch (Exception ignored) {}
//    }
//}