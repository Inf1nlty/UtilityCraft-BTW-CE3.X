package com.inf1nlty.utilitycraft.item;

import com.inf1nlty.utilitycraft.entity.EntityObsidianBoat;
import api.world.BlockPos;
import net.minecraft.src.*;

import java.util.List;

public class ItemObsidianBoat extends UCItem {
    public ItemObsidianBoat(int id) {
        super(id);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
        setBuoyant();
        setIncineratedInCrucible();
        setUnlocalizedName("obsidian_boat");
        setTextureName("utilitycraft:obsidian_boat");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        float var4 = 1.0F;
        float var5 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * var4;
        float var6 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * var4;
        double var7 = player.prevPosX + (player.posX - player.prevPosX) * (double)var4;
        double var9 = player.prevPosY + (player.posY - player.prevPosY) * (double)var4 + 1.62D - (double)player.yOffset;
        double var11 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)var4;
        Vec3 var13 = world.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
        MovingObjectPosition var24 = world.clip(var13, var23, true);

        if (var24 == null) {
            return stack;
        } else {
            Vec3 var25 = player.getLook(var4);
            boolean var26 = false;
            float var27 = 1.0F;
            List var28 = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand((double)var27, (double)var27, (double)var27));
            int var29;

            for (var29 = 0; var29 < var28.size(); ++var29) {
                Entity var30 = (Entity)var28.get(var29);
                if (var30.canBeCollidedWith()) {
                    float var31 = var30.getCollisionBorderSize();
                    AxisAlignedBB var32 = var30.boundingBox.expand((double)var31, (double)var31, (double)var31);
                    if (var32.isVecInside(var13)) {
                        var26 = true;
                    }
                }
            }
            if (var26) return stack;

            if (var24.typeOfHit == EnumMovingObjectType.TILE) {
                var29 = var24.blockX;
                int var33 = var24.blockY;
                int var34 = var24.blockZ;

                if (world.getBlockId(var29, var33, var34) == Block.snow.blockID) {
                    --var33;
                }

                EntityObsidianBoat var35 = new EntityObsidianBoat(world, (double)((float)var29 + 0.5F), (double)((float)var33 + 1.0F), (double)((float)var34 + 0.5F));
                var35.rotationYaw = (float)(((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);

                if (!world.getCollidingBoundingBoxes(var35, var35.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
                    return stack;
                }

                if (!world.isRemote) {
                    world.spawnEntityInWorld(var35);
                }
                if (!player.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
            }
            return stack;
        }
    }

    @Override
    public boolean onItemUsedByBlockDispenser(ItemStack stack, World world, int i, int j, int k, int iFacing) {
        BlockPos offsetPos = new BlockPos(0, 0, 0, iFacing);

        double dXPos = i + (offsetPos.x * 1.6D) + 0.5D;
        double dYPos = j + offsetPos.y;
        double dZPos = k + (offsetPos.z * 1.6D) + 0.5D;

        EntityObsidianBoat entity = new EntityObsidianBoat(world, dXPos, dYPos, dZPos);

        world.spawnEntityInWorld(entity);

        world.playAuxSFX(1000, i, j, k, 0); // normal pitch click

        return true;
    }
}