package com.inf1nlty.utilitycraft.item.saber;

import com.inf1nlty.utilitycraft.item.ItemSaber;
import com.inf1nlty.utilitycraft.item.ISweepAttack;
import net.minecraft.src.*;

public class IronSaber extends ItemSaber implements ISweepAttack {

    public IronSaber(int id) {
        super(id, EnumToolMaterial.EMERALD);
        this.setUnlocalizedName("iron_saber");
        this.setTextureName("utilitycraft:iron_saber");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 4;
    }
}