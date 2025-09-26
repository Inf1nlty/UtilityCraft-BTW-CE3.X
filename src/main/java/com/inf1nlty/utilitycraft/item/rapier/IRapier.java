package com.inf1nlty.utilitycraft.item.rapier;

import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityPlayer;

public interface IRapier {

    float getDamage();

    default void playAttackSound(EntityPlayer player, EntityLivingBase target) {}
}