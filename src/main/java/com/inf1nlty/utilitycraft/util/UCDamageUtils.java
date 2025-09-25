package com.inf1nlty.utilitycraft.util;

public class UCDamageUtils {

    public static float getSweepDamage(float baseDamage, int sweepLevel) {

        if (sweepLevel <= 0) return 1.0F;

        float multiplier = (float)sweepLevel / (sweepLevel + 1);

        return baseDamage * multiplier + 1.0F;
    }
}