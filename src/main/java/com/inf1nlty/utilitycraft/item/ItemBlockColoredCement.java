package com.inf1nlty.utilitycraft.item;

import net.minecraft.src.*;

import java.util.List;

public class ItemBlockColoredCement extends UCItemBlock {

    private static final String[] COLOR_NAMES = {
            "white","orange","magenta","light_blue","yellow","lime","pink","gray",
            "light_gray","cyan","purple","blue","brown","green","red","black"
    };

    public ItemBlockColoredCement(int id) {
        super(id);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int dmg) { return dmg & 15; }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage() & 15;
        return "tile.colored_cement_" + COLOR_NAMES[meta];
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        for (int i=0;i<16;i++) list.add(new ItemStack(this,1,i));
    }
}