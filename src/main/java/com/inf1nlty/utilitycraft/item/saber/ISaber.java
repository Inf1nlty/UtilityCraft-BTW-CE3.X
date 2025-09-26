package com.inf1nlty.utilitycraft.item.saber;

import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityPlayer;

public interface ISaber {

    float getDamage();

    default void playAttackSound(EntityPlayer player, EntityLivingBase target) {}
}