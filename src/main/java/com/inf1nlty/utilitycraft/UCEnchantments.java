package com.inf1nlty.utilitycraft;

import btw.item.BTWItems;
import com.inf1nlty.utilitycraft.item.ISweepAttack;
import net.minecraft.src.*;

public class UCEnchantments extends Enchantment {

    public static UCEnchantments sweepingEdge;
    public static final int SWEEPING_EDGE_ID = 101;
    public static ItemStack arcaneSoulMendingScroll;

    public UCEnchantments(int id, int weight) {
        super(id, weight, EnumEnchantmentType.all);
        this.setName("sweeping_edge");
        this.canBeAppliedByVanillaEnchanter = true;
    }

    public static void registerEnchantments() {
        sweepingEdge = new UCEnchantments(SWEEPING_EDGE_ID, 2);
        arcaneSoulMendingScroll = new ItemStack(BTWItems.arcaneScroll, 1, SWEEPING_EDGE_ID);
    }

    @Override
    public boolean canBeAppliedByVanillaEnchanter() {
        return false;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ISweepAttack;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}