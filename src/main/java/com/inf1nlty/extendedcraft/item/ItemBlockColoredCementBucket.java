package com.inf1nlty.extendedcraft.item;

import net.minecraft.src.*;

import java.util.List;

public class ItemBlockColoredCementBucket extends ECItemBlock {

    private static final String[] COLOR_NAMES = {
            "white","orange","magenta","light_blue","yellow","lime","pink","gray",
            "light_gray","cyan","purple","blue","brown","green","red","black"
    };

    public ItemBlockColoredCementBucket(int id) {
        super(id);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int dmg) {
        return dmg & 15;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile.colored_cement_bucket_block_" + COLOR_NAMES[stack.getItemDamage() & 15];
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