package com.inf1nlty.utilitycraft.item.saber;

import com.inf1nlty.utilitycraft.item.ISweepAttack;
import net.minecraft.src.*;

public class DiamondSaber extends ItemSaber implements ISweepAttack {

    public DiamondSaber(int id) {
        super(id, EnumToolMaterial.EMERALD);
        this.setUnlocalizedName("diamond_saber");
        this.setTextureName("utilitycraft:saber/diamond_saber");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public int getInfernalMaxNumEnchants() {
        return 3;
    }
}