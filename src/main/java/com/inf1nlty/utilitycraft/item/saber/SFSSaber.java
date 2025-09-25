package com.inf1nlty.utilitycraft.item.saber;

import com.inf1nlty.utilitycraft.item.ISweepAttack;
import net.minecraft.src.*;

public class SFSSaber extends ItemSaber implements ISweepAttack {

    public SFSSaber(int id) {
        super(id, EnumToolMaterial.SOULFORGED_STEEL);
        this.setUnlocalizedName("soulforged_steel_saber");
        this.setTextureName("utilitycraft:saber/soulforged_steel_saber");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 6;
    }
}