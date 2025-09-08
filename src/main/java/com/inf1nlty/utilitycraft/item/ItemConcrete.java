package com.inf1nlty.utilitycraft.item;

import net.minecraft.src.*;

public class ItemConcrete extends UCItemBlock {

    private static final String[] colorNames = {
            "white","orange","magenta","light_blue", "yellow","lime","pink","gray",
            "light_gray", "cyan","purple","blue","brown","green","red","black"
    };

    public ItemConcrete(int id) {
        super(id);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        if (meta < 0 || meta >= colorNames.length) meta = 0;
        return "tile." + colorNames[meta] + "_concrete";
    }
}