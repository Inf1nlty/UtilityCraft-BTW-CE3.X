package com.inf1nlty.utilitycraft.item.rapier;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;

public class GoldRapier extends ItemRapier {

    public GoldRapier(int id) {
        super(id, EnumToolMaterial.GOLD);
        this.setUnlocalizedName("golden_rapier");
        this.setTextureName("utilitycraft:rapier/golden_rapier");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 2.5F;
    }
}
