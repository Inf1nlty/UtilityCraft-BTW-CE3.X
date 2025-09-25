package com.inf1nlty.utilitycraft.item.saber;

import btw.community.utilitycraft.UtilityCraftAddon;
import btw.item.items.SwordItem;
import com.inf1nlty.utilitycraft.UCEnchantments;
import com.inf1nlty.utilitycraft.item.ISweepAttack;
import com.inf1nlty.utilitycraft.network.SweepParticleNet;
import com.inf1nlty.utilitycraft.util.UCDamageUtils;
import net.minecraft.src.*;

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
            if (!player.isSprinting()) {
                onSweepAttack(player, target);
                return true;
            }
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

        int sweepIndex = player.worldObj.rand.nextInt(UtilityCraftAddon.SWEEP_ATTACK_SOUNDS.length);
        String soundName = UtilityCraftAddon.SWEEP_ATTACK_SOUNDS[sweepIndex].sound();
        player.worldObj.playSoundAtEntity(player, soundName, 0.8F, 1.0F);

        if (player instanceof EntityPlayerMP) {
            float yaw = player.rotationYaw;
            double px = player.posX - Math.sin(Math.toRadians(yaw)) * 0.5D;
            double py = player.posY + player.getEyeHeight() - 0.4D;
            double pz = player.posZ + Math.cos(Math.toRadians(yaw)) * 0.5D;
            SweepParticleNet.sendSweepAttack((EntityPlayerMP)player, px, py, pz, yaw);
        }
    }
}