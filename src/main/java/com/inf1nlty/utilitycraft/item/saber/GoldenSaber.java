package com.inf1nlty.utilitycraft.item.saber;

import com.inf1nlty.utilitycraft.item.ISweepAttack;
import net.minecraft.src.*;

public class GoldenSaber extends ItemSaber implements ISweepAttack {

    public GoldenSaber(int id) {
        super(id, EnumToolMaterial.EMERALD);
        this.setUnlocalizedName("gold_saber");
        this.setTextureName("utilitycraft:saber/gold_saber");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 4.5F;
    }
}