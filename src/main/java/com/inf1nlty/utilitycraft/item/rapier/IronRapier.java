package com.inf1nlty.utilitycraft.item.rapier;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;

public class IronRapier extends ItemRapier {

    public IronRapier(int id) {
        super(id, EnumToolMaterial.IRON);
        this.setUnlocalizedName("diamond_rapier");
        this.setTextureName("utilitycraft:rapier/diamond_rapier");
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public float getDamage() {
        return 3.5F;
    }
}
