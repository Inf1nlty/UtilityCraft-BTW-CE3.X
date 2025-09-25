package com.inf1nlty.utilitycraft.item.rapier;

import btw.item.items.SwordItem;
import net.minecraft.src.EnumToolMaterial;

public abstract class ItemRapier extends SwordItem implements IRapier {

    public ItemRapier(int id, EnumToolMaterial material) {
        super(id, material);
    }

    public String getModId() {
        return "utilitycraft";
    }
}