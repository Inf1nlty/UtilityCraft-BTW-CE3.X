package com.inf1nlty.utilitycraft.item;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;

public interface ISweepAttack {

    void onSweepAttack(EntityPlayer player, Entity target);
}