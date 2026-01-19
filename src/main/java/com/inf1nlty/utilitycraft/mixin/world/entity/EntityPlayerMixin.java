package com.inf1nlty.utilitycraft.mixin.world.entity;

import net.minecraft.src.*;
import btw.item.BTWItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {

    /**
     * Allows players to use canvas, diamond shovel, and steel shovel as their main weapon to be considered as ship acceleration, which is fully compatible with BTW.
     *
     * @author Inf1nlty
     * @reason 1
     */
    @Overwrite
    public double movementModifierWhenRidingBoat() {

        ItemStack held = ((EntityPlayer) (Object) this).getHeldItem();

        if (held != null) {
            Item item = held.getItem();
            if (item == BTWItems.windMillBlade || item == Item.shovelDiamond || item == BTWItems.steelShovel) {
                return 1.0D;
            }
        }
        return 0.35D;
    }

    /**
     * Allows players to use canvas, diamond shovel, and steel shovel as their main weapon to be considered as ship acceleration, which is fully compatible with BTW.
     *
     * @author Inf1nlty
     * @reason 1
     */
    @Overwrite
    public boolean appliesConstantForceWhenRidingBoat() {

        ItemStack held = ((EntityPlayer) (Object) this).getHeldItem();

        if (held != null) {
            Item item = held.getItem();
            return item == BTWItems.windMillBlade;
        }
        return false;
    }
}