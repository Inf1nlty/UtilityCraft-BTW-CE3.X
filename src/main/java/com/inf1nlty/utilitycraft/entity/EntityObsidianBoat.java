package com.inf1nlty.utilitycraft.entity;

import btw.achievement.event.AchievementEventDispatcher;
import btw.achievement.event.BTWAchievementEvents;
import btw.block.blocks.BlockDispenserBlock;
import btw.block.tileentity.dispenser.BlockDispenserTileEntity;
import btw.inventory.util.InventoryUtils;
import btw.item.items.PickaxeItem;
import com.inf1nlty.utilitycraft.item.UtilityCraftItems;
import net.minecraft.src.*;

import java.util.List;

public class EntityObsidianBoat extends EntityBoat {
    private boolean field_70279_a;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public EntityObsidianBoat(World par1World) {
        super(par1World);
        this.field_70279_a = true;
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 0.6F);
        this.yOffset = this.height / 2.0F;
        this.isImmuneToFire = true;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return par1Entity.boundingBox;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public boolean canBePushed() {
        return true;
    }

    public EntityObsidianBoat(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = (double)0.0F;
        this.motionY = (double)0.0F;
        this.motionZ = (double)0.0F;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    public double getMountedYOffset() {
        return (double)this.height * (double)0.0F - (double)0.3F;
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

                ItemStack drop = new ItemStack(UtilityCraftItems.obsidian_boat);
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

    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        if (this.field_70279_a) {
            this.boatPosRotationIncrements = par9 + 5;
        } else {
            double var10 = par1 - this.posX;
            double var12 = par3 - this.posY;
            double var14 = par5 - this.posZ;
            double var16 = var10 * var10 + var12 * var12 + var14 * var14;
            if (var16 <= (double)1.0F) {
                return;
            }

            this.boatPosRotationIncrements = 3;
        }

        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = (double)par7;
        this.boatPitch = (double)par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    public void setVelocity(double par1, double par3, double par5) {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        byte var1 = 5;
        double var2 = (double)0.0F;

        for(int var4 = 0; var4 < var1; ++var4) {
            double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - (double)0.125F;
            double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - (double)0.125F;
            AxisAlignedBB var9 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(var9, Material.water) || this.worldObj.isAABBInMaterial(var9, Material.lava)) {
                var2 += (double)1.0F / (double)var1;
            }
        }

        if (var2 > (double)0.1F) {
            this.fallDistance = 0.0F;
            this.extinguish();
        }

        double var23 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (var23 > 0.26249999999999996) {
            double var6 = Math.cos((double)this.rotationYaw * Math.PI / (double)180.0F);
            double var8 = Math.sin((double)this.rotationYaw * Math.PI / (double)180.0F);

            for(int var10 = 0; (double)var10 < (double)1.0F + var23 * (double)60.0F; ++var10) {
                double var11 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
                double var13 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    double var15 = this.posX - var6 * var11 * 0.8 + var8 * var13;
                    double var17 = this.posZ - var8 * var11 * 0.8 - var6 * var13;
                    this.worldObj.spawnParticle("splash", var15, this.posY - (double)0.125F, var17, this.motionX, this.motionY, this.motionZ);
                } else {
                    double var15 = this.posX + var6 + var8 * var11 * 0.7;
                    double var17 = this.posZ + var8 - var6 * var11 * 0.7;
                    this.worldObj.spawnParticle("splash", var15, this.posY - (double)0.125F, var17, this.motionX, this.motionY, this.motionZ);
                }
            }
        }

        if (this.worldObj.isRemote && this.field_70279_a) {
            if (this.boatPosRotationIncrements > 0) {
                double var6 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double var8 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double var25 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                double var12 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + var12 / (double)this.boatPosRotationIncrements);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(var6, var8, var25);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                double var6 = this.posX + this.motionX;
                double var8 = this.posY + this.motionY;
                double var25 = this.posZ + this.motionZ;
                this.setPosition(var6, var8, var25);
                if (this.onGround) {
                    this.motionX *= (double)0.5F;
                    this.motionY *= (double)0.5F;
                    this.motionZ *= (double)0.5F;
                }

                this.motionX *= (double)0.99F;
                this.motionY *= (double)0.95F;
                this.motionZ *= (double)0.99F;
            }
        } else {
            if (var2 < (double)1.0F) {
                double var6 = var2 * (double)2.0F - (double)1.0F;
                this.motionY += (double)0.04F * var6;
            } else {
                if (this.motionY < (double)0.0F) {
                    this.motionY /= (double)2.0F;
                }

                this.motionY += (double)0.007F;
            }

            double dMaxSpeed = 0.35;
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
                dMaxSpeed *= this.riddenByEntity.movementModifierWhenRidingBoat();
                if ((double)((EntityLivingBase)this.riddenByEntity).moveForward != (double)0.0F || (double)((EntityLivingBase)this.riddenByEntity).moveStrafing != (double)0.0F) {
                    double var8 = -Math.sin((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                    double var25 = Math.cos((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                    double xMovement = (double)((EntityLivingBase)this.riddenByEntity).moveForward * var8 + (double)((EntityLivingBase)this.riddenByEntity).moveStrafing * var25;
                    double zMovement = (double)((EntityLivingBase)this.riddenByEntity).moveForward * var25 - (double)((EntityLivingBase)this.riddenByEntity).moveStrafing * var8;
                    this.motionX += xMovement * this.speedMultiplier * 0.02;
                    this.motionZ += zMovement * this.speedMultiplier * 0.02;
                }

                if (this.riddenByEntity.appliesConstantForceWhenRidingBoat()) {
                    Entity var45 = this.riddenByEntity;
                    if (var45 instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer)var45;
                        AchievementEventDispatcher.triggerEvent(BTWAchievementEvents.BoatWithSailEvent.class, player, BTWAchievementEvents.none());
                    }

                    this.motionX -= Math.cos((double)this.rotationYaw * Math.PI / (double)180.0F) * dMaxSpeed * 0.02;
                    this.motionZ -= Math.sin((double)this.rotationYaw * Math.PI / (double)180.0F) * dMaxSpeed * 0.02;
                }
            }

            double var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var6 > dMaxSpeed) {
                double dSpeedModifier = dMaxSpeed / var6;
                if (dSpeedModifier < 0.9) {
                    dSpeedModifier = 0.9;
                }

                this.motionX *= dSpeedModifier;
                this.motionZ *= dSpeedModifier;
                var6 *= dSpeedModifier;
            }

            if (var6 > var23 && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / (double)35.0F;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            } else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / (double)35.0F;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }

            if (this.onGround) {
                this.motionX *= (double)0.5F;
                this.motionY *= (double)0.5F;
                this.motionZ *= (double)0.5F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            {
                this.motionX *= (double)0.99F;
                this.motionY *= (double)0.95F;
                this.motionZ *= (double)0.99F;
            }

            this.rotationPitch = 0.0F;
            double var8 = (double)this.rotationYaw;
            double var25 = this.prevPosX - this.posX;
            double var12 = this.prevPosZ - this.posZ;
            if (var25 * var25 + var12 * var12 > 0.001) {
                var8 = (double)((float)(Math.atan2(var12, var25) * (double)180.0F / Math.PI));
            }

            double var14 = MathHelper.wrapAngleTo180_double(var8 - (double)this.rotationYaw);
            if (var14 > (double)20.0F) {
                var14 = (double)20.0F;
            }

            if (var14 < (double)-20.0F) {
                var14 = (double)-20.0F;
            }

            this.rotationYaw = (float)((double)this.rotationYaw + var14);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, (double)0.0F, (double)0.2F));
                if (var16 != null && !var16.isEmpty()) {
                    for(int var26 = 0; var26 < var16.size(); ++var26) {
                        Entity var18 = (Entity)var16.get(var26);
                        if (var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityObsidianBoat) {
                            var18.applyEntityCollision(this);
                        }
                    }
                }

                for(int var26 = 0; var26 < 4; ++var26) {
                    int var27 = MathHelper.floor_double(this.posX + ((double)(var26 % 2) - (double)0.5F) * 0.8);
                    int var19 = MathHelper.floor_double(this.posZ + ((double)(var26 / 2) - (double)0.5F) * 0.8);

                    for(int var20 = 0; var20 < 2; ++var20) {
                        int var21 = MathHelper.floor_double(this.posY) + var20;
                        int var22 = this.worldObj.getBlockId(var27, var21, var19);
                        Block tempBlock = Block.blocksList[var22];
                        if (tempBlock != null && tempBlock.isGroundCover()) {
                            this.worldObj.setBlockToAir(var27, var21, var19);
                        } else if (var22 == Block.waterlily.blockID) {
                            this.worldObj.destroyBlock(var27, var21, var19, true);
                        }
                    }
                }

                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
        AxisAlignedBB aabb = this.boundingBox.expand(-0.02, -0.01, -0.02);
        int minX = MathHelper.floor_double(aabb.minX);
        int maxX = MathHelper.floor_double(aabb.maxX);
        int minY = MathHelper.floor_double(aabb.minY);
        int maxY = MathHelper.floor_double(aabb.maxY);
        int minZ = MathHelper.floor_double(aabb.minZ);
        int maxZ = MathHelper.floor_double(aabb.maxZ);
        for (int x = minX; x <= maxX; ++x)
            for (int y = minY; y <= maxY; ++y)
                for (int z = minZ; z <= maxZ; ++z) {
                    int blockId = this.worldObj.getBlockId(x, y, z);
                    if (blockId != 0 && Block.blocksList[blockId] != null && Block.blocksList[blockId].blockMaterial.isSolid()) {
                        double dBack = 0.05;
                        this.setPosition(
                                this.prevPosX - Math.signum(this.motionX) * dBack,
                                this.prevPosY,
                                this.prevPosZ - Math.signum(this.motionZ) * dBack
                        );
                        this.motionX = 0;
                        this.motionY = 0;
                        this.motionZ = 0;
                        return;
                    }
                }

    }

    @Override
    public void applyEntityCollision(Entity entity) {
        if (entity instanceof EntityPlayer) {
            return;
        }
        super.applyEntityCollision(entity);
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double var1 = Math.cos((double)this.rotationYaw * Math.PI / (double)180.0F) * 0.4;
            double var3 = Math.sin((double)this.rotationYaw * Math.PI / (double)180.0F) * 0.4;
            this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
        }

    }

    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    public float getShadowSize() {
        return 0.0F;
    }

    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
            return true;
        } else {
            if (!this.worldObj.isRemote) {
                par1EntityPlayer.mountEntity(this);
            }

            return true;
        }
    }

    public void setDamageTaken(float par1) {
        this.dataWatcher.updateObject(19, par1);
    }

    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    public void setTimeSinceHit(int par1) {
        this.dataWatcher.updateObject(17, par1);
    }

    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public void setForwardDirection(int par1) {
        this.dataWatcher.updateObject(18, par1);
    }

    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void func_70270_d(boolean par1) {
        this.field_70279_a = par1;
    }

    public boolean canCollideWithEntity(Entity entity) {
        return !entity.isItemEntity();
    }

    @Override
    protected void fall(float fFallDistance) {
        // do nothing: obsidian boat is unbreakable by falling
    }

    @Override
    public void breakBoat() {
        // do nothing: obsidian boat is unbreakable except by pickaxe
    }

    public boolean onBlockDispenserConsume(BlockDispenserBlock blockDispenser, BlockDispenserTileEntity tileEentityDispenser) {
        this.setDead();
        InventoryUtils.addSingleItemToInventory(tileEentityDispenser, UtilityCraftItems.obsidian_boat.itemID, 0);
        this.worldObj.playAuxSFX(1001, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        return true;
    }
}