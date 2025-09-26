package com.inf1nlty.utilitycraft.item.rapier;

import btw.community.utilitycraft.UtilityCraftAddon;
import btw.item.items.SwordItem;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

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
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void playAttackSound(EntityPlayer player, EntityLivingBase target) {

        boolean hasArmor = false;

        for (int i = 1; i <= 4; i++) {

            ItemStack armor = target.getCurrentItemOrArmor(i);

            if (armor != null) {

                hasArmor = true;
                break;
            }
        }

        if (hasArmor) {

            int rapierIndex = player.worldObj.rand.nextInt(UtilityCraftAddon.RAPIER_ATTACK_SOUNDS.length);
            String rapierSound = UtilityCraftAddon.RAPIER_ATTACK_SOUNDS[rapierIndex].sound();
            player.worldObj.playSoundAtEntity(player, rapierSound, 0.4F, 1.0F + (player.worldObj.rand.nextFloat() - 0.5F) * 0.4F);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            info.add(StatCollector.translateToLocal("item.utilitycraft.rapier.desc"));
        } else {
            info.add(StatCollector.translateToLocal("item.utilitycraft.rapier.tip"));
        }
    }
}