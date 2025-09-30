package com.inf1nlty.utilitycraft;

import btw.entity.mob.villager.trade.TradeItem;
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

        boolean nightmareLoaded = false;
        Class<?> bloodOrbClass = null;
        try {
            Class<?> nmItemsClass = Class.forName("com.itlesports.nightmaremode.item.NMItems");
            nightmareLoaded = true;
            bloodOrbClass = nmItemsClass;
        } catch (ClassNotFoundException ignored) {}
        if (nightmareLoaded) {
            Item bloodOrb = null;
            try {
                bloodOrb = (Item) bloodOrbClass.getField("bloodOrb").get(null);
            } catch (Exception e) {
                System.err.println("[UtilityCraft] Failed to get NMItems.bloodOrb for sweeping_edge villager trade!");
                System.err.println("[UtilityCraft] Exception: " + e);
            }
            if (bloodOrb != null) {
                TradeProvider.getBuilder()
                        .name("ucBlacksmithSweepingEdgeScroll")
                        .profession(3)
                        .level(5)
                        .convert()
                        .input(TradeItem.fromID(Item.paper.itemID))
                        .secondInput(TradeItem.fromID(bloodOrb.itemID, 24, 32))
                        .output(TradeItem.fromIDAndMetadata(BTWItems.arcaneScroll.itemID, SWEEPING_EDGE_ID))
                        .weight(1.0f)
                        .addToTradeList();
            }
        }
        else {
            TradeProvider.getBuilder()
                    .name("ucBlacksmithSweepingEdgeScroll")
                    .profession(3)
                    .level(5)
                    .arcaneScroll()
                    .scrollEnchant(UCEnchantments.sweepingEdge)
                    .secondaryEmeraldCost(1, 1)
                    .mandatory()
                    .addToTradeList();
        }
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