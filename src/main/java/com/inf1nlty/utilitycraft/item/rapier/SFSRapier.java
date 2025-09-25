package com.inf1nlty.utilitycraft.item.rapier;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;

public class SFSRapier extends ItemRapier {

    public SFSRapier(int id) {
        super(id, EnumToolMaterial.SOULFORGED_STEEL);
        this.setUnlocalizedName("diamond_rapier");
        this.setTextureName("utilitycraft:rapier/diamond_rapier");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 5;
    }
}
