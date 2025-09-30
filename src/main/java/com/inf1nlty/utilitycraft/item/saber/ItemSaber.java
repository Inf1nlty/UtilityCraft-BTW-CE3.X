package com.inf1nlty.utilitycraft.item.saber;

import btw.community.utilitycraft.UtilityCraftAddon;
import btw.item.items.SwordItem;
import com.inf1nlty.utilitycraft.UCEnchantments;
import com.inf1nlty.utilitycraft.item.ISweepAttack;
import com.inf1nlty.utilitycraft.util.UCDamageUtils;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

import java.util.List;

public abstract class ItemSaber extends SwordItem implements ISaber, ISweepAttack {

    public ItemSaber(int id, EnumToolMaterial material) {
        super(id, material);
    }

    public String getModId() {
        return "utilitycraft";
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (attacker instanceof EntityPlayer player) {
                onSweepAttack(player, target);
                stack.damageItem(1, attacker);
                return true;
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void onSweepAttack(EntityPlayer player, Entity target) {

        float mainDamage = this.getDamage();
        double sweepRange = 1.0D;
        int sweepLevel = EnchantmentHelper.getEnchantmentLevel(UCEnchantments.sweepingEdge.effectId, player.getCurrentEquippedItem());
        float sweepDamage = UCDamageUtils.getSweepDamage(mainDamage, sweepLevel);
        target.attackEntityFrom(DamageSource.causePlayerDamage(player), mainDamage);

        @SuppressWarnings("unchecked")
        List<EntityLivingBase> sweepTargets = player.worldObj.getEntitiesWithinAABB(
                EntityLivingBase.class,
                target.boundingBox.expand(sweepRange, 0.25D, sweepRange));

        float knockbackStrength = 0.4F;

        for (EntityLivingBase nearby : sweepTargets) {
            if (nearby != target && nearby != player && player.canEntityBeSeen(nearby) && nearby.isEntityAlive()) {
                double dx = player.posX - nearby.posX;
                double dz = player.posZ - nearby.posZ;
                nearby.knockBack(player, knockbackStrength, dx, dz);
                nearby.attackEntityFrom(DamageSource.causePlayerDamage(player), sweepDamage);
            }
        }
    }

    @Override
    public void playAttackSound(EntityPlayer player, EntityLivingBase target) {
        int sweepIndex = player.worldObj.rand.nextInt(UtilityCraftAddon.SWEEP_ATTACK_SOUNDS.length);
        String sweepSound = UtilityCraftAddon.SWEEP_ATTACK_SOUNDS[sweepIndex].sound();
        player.worldObj.playSoundAtEntity(player, sweepSound, 0.4F, 1.0F + (player.worldObj.rand.nextFloat() - 0.5F) * 0.4F);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {

            int sweepLevel = EnchantmentHelper.getEnchantmentLevel(UCEnchantments.sweepingEdge.effectId, stack);
            float baseDamage = this.getDamage() + 1.0F;
            float sweepDamage = UCDamageUtils.getSweepDamage(baseDamage, sweepLevel);

            info.add(StatCollector.translateToLocalFormatted("item.utilitycraft.saber.desc", sweepDamage));
        } else {
            info.add(StatCollector.translateToLocal("item.utilitycraft.saber.tip"));
        }
    }
}