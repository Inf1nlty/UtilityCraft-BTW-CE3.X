package com.inf1nlty.utilitycraft.item.rapier;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;

public class DiamondRapier extends ItemRapier {

    public DiamondRapier(int id) {
        super(id, EnumToolMaterial.EMERALD);
        this.setUnlocalizedName("diamond_rapier");
        this.setTextureName("utilitycraft:rapier/diamond_rapier");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 4;
    }

    @Override
    public int getInfernalMaxNumEnchants() {
        return 3;
    }
}