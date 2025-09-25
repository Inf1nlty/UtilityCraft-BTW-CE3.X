package com.inf1nlty.utilitycraft.item.rapier;

import btw.item.items.SwordItem;
import net.minecraft.src.*;

import java.util.List;

public abstract class ItemRapier extends SwordItem implements IRapier {

    public ItemRapier(int id, EnumToolMaterial material) {
        super(id, material);
    }

    public String getModId() {
        return "utilitycraft";
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        float baseDamage = getDamage();
        target.attackEntityFrom(new RapierDamageSource(attacker), baseDamage);
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        if (org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_LSHIFT) ||
                org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_RSHIFT)) {
            info.add(StatCollector.translateToLocal("item.utilitycraft.rapier.desc"));
        } else {
            info.add(StatCollector.translateToLocal("item.utilitycraft.rapier.tip"));
        }
    }
}