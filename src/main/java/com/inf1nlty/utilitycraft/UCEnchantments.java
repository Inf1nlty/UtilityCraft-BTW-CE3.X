package com.inf1nlty.utilitycraft;

import btw.entity.mob.villager.trade.TradeProvider;
import btw.item.BTWItems;
import com.inf1nlty.utilitycraft.item.ISweepAttack;
import net.minecraft.src.*;

public class UCEnchantments extends Enchantment {

    public static UCEnchantments sweepingEdge;
    public static final int SWEEPING_EDGE_ID = 101;
    public static ItemStack sweepingEdgeScroll;

    public UCEnchantments(int id, int weight) {
        super(id, weight, EnumEnchantmentType.all);
        this.setName("sweeping_edge");
        this.canBeAppliedByVanillaEnchanter = true;
    }

    public static void registerEnchantments() {
        sweepingEdge = new UCEnchantments(SWEEPING_EDGE_ID, 2);
        sweepingEdgeScroll = new ItemStack(BTWItems.arcaneScroll, 1, SWEEPING_EDGE_ID);

        EntityVillager.removeCustomTrade(3,
                TradeProvider.getBuilder()
                        .name("btw:sell_unbreaking_scroll")
                        .profession(3)
                        .level(5)
                        .arcaneScroll()
                        .scrollEnchant(Enchantment.unbreaking)
                        .secondaryEmeraldCost(48, 64)
                        .mandatory()
                        .build()
        );

        TradeProvider.getBuilder()
                .name("btw:sell_unbreaking_scroll")
                .profession(3)
                .level(5)
                .arcaneScroll()
                .scrollEnchant(Enchantment.unbreaking)
                .secondaryEmeraldCost(48, 64)
                .mandatory()
                .condition(villager -> villager.getRNG().nextFloat() < 0.5f)
                .addToTradeList();

        TradeProvider.getBuilder()
                .name("ucBlacksmithSweepingEdgeScroll")
                .profession(3)
                .level(5)
                .arcaneScroll()
                .scrollEnchant(UCEnchantments.sweepingEdge)
                .secondaryEmeraldCost(32, 64)
                .mandatory()
                .condition(villager -> villager.getRNG().nextFloat() >= 0.5f)
                .addToTradeList();
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