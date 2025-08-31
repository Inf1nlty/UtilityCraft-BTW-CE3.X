package com.inf1nlty.moreblocks.item;

import btw.item.blockitems.SlabBlockItem;

public class MBSlabBlockItem extends SlabBlockItem {

    public MBSlabBlockItem(int id) {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    @Override
    public String getModId() {
        return "moreblocks";
    }
}