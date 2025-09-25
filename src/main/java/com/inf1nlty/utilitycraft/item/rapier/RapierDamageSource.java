package com.inf1nlty.utilitycraft.item.rapier;

import net.minecraft.src.EntityDamageSource;
import net.minecraft.src.EntityLivingBase;

public class RapierDamageSource extends EntityDamageSource {

    public RapierDamageSource(EntityLivingBase attacker) {
        super("rapier", attacker);
    }
}