package com.inf1nlty.extendedcraft.item;

import btw.item.blockitems.SlabBlockItem;

public class ECSlabBlockItem extends SlabBlockItem {

    public ECSlabBlockItem(int id) {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    @Override
    public String getModId() {
        return "extendedcraft";
    }
}