package com.inf1nlty.moreblocks.block;

import net.minecraft.src.*;

import java.util.List;

/**
 * Concrete block with 16 color variants (meta 0~15)
 */
public class BlockConcrete extends Block {
    private Icon[] icons;

    private static final String[] colorNames = {
            "white","orange","magenta","light_blue",
            "yellow","lime","pink","gray","light_gray",
            "cyan","purple","blue","brown","green","red","black"
    };

    public BlockConcrete(int id) {
        super(id, Material.rock);
        this.setHardness(1.8F);
        this.setResistance(10.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("concrete");
        this.setTextureName("concrete");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubBlocks(int itemID, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public void registerIcons(IconRegister reg) {
        icons = new Icon[16];
        for (int i = 0; i < 16; i++) {
            icons[i] = reg.registerIcon("moreblocks:" + colorNames[i] + "_concrete");
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        if (meta < 0 || meta > 15) meta = 0;
        return icons[meta];
    }
}