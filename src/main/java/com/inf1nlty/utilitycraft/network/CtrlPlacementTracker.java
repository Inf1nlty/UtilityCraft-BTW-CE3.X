package com.inf1nlty.utilitycraft.network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server side CTRL placement flag with short TTL.
 * Every client tick while CTRL is held a packet is sent and we refresh expiry.
 * onItemRightClick checks consume() which returns true if not yet used in this tick
 * and still within expiry window.
 */
public final class CtrlPlacementTracker {

    private static final class Entry {
        long expireTick;   // world time when flag expires
        boolean consumed;  // set true once used in a tick
        long lastTickUsed; // world time when consumed flagged (to allow reuse next tick)
    }

    private static final Map<String, Entry> FLAGS = new ConcurrentHashMap<>();
    // Lifetime (ticks) the CTRL intent stays valid after last packet
    private static final long TTL = 3;

    private CtrlPlacementTracker(){}

    public static void set(EntityPlayer player) {
        if (player == null) return;
        String k = player.username;
        Entry e = FLAGS.computeIfAbsent(k, k1 -> new Entry());
        World w = player.worldObj;
        long now = w.getWorldTime();
        e.expireTick = now + TTL;
        if (e.lastTickUsed != now) {
            // new tick: allow consumption again
            e.consumed = false;
        }
    }

    /**
     * Returns true once per tick while within expiry window.
     */
    public static boolean consume(EntityPlayer player) {
        if (player == null) return false;
        Entry e = FLAGS.get(player.username);
        if (e == null) return false;
        long now = player.worldObj.getWorldTime();
        if (now > e.expireTick) {
            FLAGS.remove(player.username);
            return false;
        }
        if (!e.consumed || e.lastTickUsed != now) {
            e.consumed = true;
            e.lastTickUsed = now;
            return true;
        }
        return false;
    }
}