package com.inf1nlty.utilitycraft.item;

import btw.item.blockitems.SlabBlockItem;

public class UCSlabBlockItem extends SlabBlockItem {

    public UCSlabBlockItem(int id) {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    @Override
    public String getModId() {
        return "utilitycraft";
    }
}