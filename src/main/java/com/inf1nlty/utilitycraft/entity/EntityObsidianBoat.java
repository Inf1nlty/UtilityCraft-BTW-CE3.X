package com.inf1nlty.utilitycraft.entity;

import btw.block.blocks.BlockDispenserBlock;
import btw.block.tileentity.dispenser.BlockDispenserTileEntity;
import btw.inventory.util.InventoryUtils;
import btw.item.items.PickaxeItem;
import com.inf1nlty.utilitycraft.item.UCItems;
import net.minecraft.src.*;

public class EntityObsidianBoat extends EntityBoat {

    public EntityObsidianBoat(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    public EntityObsidianBoat(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0F;
        this.motionY = 0.0F;
        this.motionZ = 0.0F;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    @Override
    public double getMountedYOffset() {
        return 0.0D;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            this.playSound("step.stone", 0.8F, 0.6F + this.rand.nextFloat() * 0.2F);
            return false;
        }

        if (par1DamageSource.isExplosion()) {
            this.playSound("step.stone", 0.8F, 0.6F + this.rand.nextFloat() * 0.2F);
            return false;
        }

        Entity attacker = par1DamageSource.getEntity();
        if (attacker instanceof EntityPlayer player) {

            if (player.capabilities.isCreativeMode) {
                if (!this.worldObj.isRemote) {
                    this.setDead();
                }
                return true;
            }

            ItemStack heldItem = player.getCurrentEquippedItem();
            if (heldItem == null) {
                this.playSound("step.stone", 0.8F, 0.6F + this.rand.nextFloat() * 0.2F);
                return false;
            }

            Item item = heldItem.getItem();
            int toolLevel = -1;
            if (item instanceof PickaxeItem) {
                toolLevel = ((PickaxeItem) item).toolMaterial.getHarvestLevel();
            }
            int requiredLevel = 3;
            if (toolLevel < requiredLevel) {
                this.playSound("step.stone", 0.8F, 0.6F + this.rand.nextFloat() * 0.2F);
                return false;
            }

            if (!this.worldObj.isRemote && !this.isDead) {
                this.setDead();

                ItemStack drop = new ItemStack(UCItems.obsidian_boat);
                double sx = this.posX, sy = this.posY + 0.5, sz = this.posZ;
                EntityItem entityItem = new EntityItem(this.worldObj, sx, sy, sz, drop);

                double dx = player.posX - sx;
                double dy = (player.posY + player.getEyeHeight()/2) - sy;
                double dz = player.posZ - sz;
                double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
                if (dist > 0.0001) {
                    double speed = 0.6;
                    entityItem.motionX = dx / dist * speed;
                    entityItem.motionY = dy / dist * speed;
                    entityItem.motionZ = dz / dist * speed;
                }

                entityItem.delayBeforeCanPickup = 0;
                this.worldObj.spawnEntityInWorld(entityItem);

                this.playSound("step.stone", 1.0F, 0.7F + this.rand.nextFloat() * 0.2F);
            }
            return true;
        }

        this.playSound("step.stone", 0.8F, 0.6F + this.rand.nextFloat() * 0.2F);
        return false;
    }

    @Override
    protected void fall(float fFallDistance) {
        // do nothing: obsidian boat is unbreakable by falling
    }

    @Override
    public void breakBoat() {
        // do nothing: obsidian boat is unbreakable except by pickaxe
    }

    @Override
    public boolean onBlockDispenserConsume(BlockDispenserBlock blockDispenser, BlockDispenserTileEntity tileEentityDispenser) {
        this.setDead();
        InventoryUtils.addSingleItemToInventory(tileEentityDispenser, UCItems.obsidian_boat.itemID, 0);
        this.worldObj.playAuxSFX(1001, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        return true;
    }
}