package com.inf1nlty.moreblocks.block.tileentity;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.minecraft.src.TileEntity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;

public class TileEntityConcretePowder extends TileEntity implements TileEntityDataPacketHandler {
    private static final int TIME_TO_CONVERT = 24000;
    private int convertCounter = 0;
    private boolean isConverting = true;
    public int concreteBlockID = -1;

    public TileEntityConcretePowder() {}

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (isConverting) {
                ++convertCounter;
                if (convertCounter == 1) {
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // 网络包同步
                }
                if (convertCounter >= TIME_TO_CONVERT) {
                    int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                    worldObj.setBlock(xCoord, yCoord, zCoord, concreteBlockID, meta, 3);
                    worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
                }
            }
        } else if (isConverting && worldObj.rand.nextInt(2) == 0) {
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            double xPos = xCoord + 0.5 + (worldObj.rand.nextFloat() - 0.5) * 0.5;
            double yPos = yCoord + 0.7 + worldObj.rand.nextFloat() * 0.2;
            double zPos = zCoord + 0.5 + (worldObj.rand.nextFloat() - 0.5) * 0.5;
            float[] rgb = getMetaColor(meta);
            worldObj.spawnParticle("reddust", xPos, yPos, zPos, rgb[0], rgb[1], rgb[2]);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("convertCounter", convertCounter);
        tag.setBoolean("isConverting", isConverting);
        tag.setInteger("concreteBlockID", concreteBlockID);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        convertCounter = tag.getInteger("convertCounter");
        isConverting = tag.getBoolean("isConverting");
        concreteBlockID = tag.getInteger("concreteBlockID");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("isConverting", isConverting);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        isConverting = tag.getBoolean("isConverting");
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }


    /**
     * 16色meta转RGB（0~1），你可以替换为你喜欢的色表
     */
    public static float[] getMetaColor(int meta) {
        return switch (meta) {
            case 0 -> new float[]{1.0F, 1.0F, 1.0F};    // 白
            case 1 -> new float[]{1.0F, 0.5F, 0.0F};    // 橙
            case 2 -> new float[]{1.0F, 0.0F, 1.0F};    // 品红
            case 3 -> new float[]{0.5F, 0.75F, 1.0F};   // 浅蓝
            case 4 -> new float[]{1.0F, 1.0F, 0.0F};    // 黄
            case 5 -> new float[]{0.5F, 1.0F, 0.0F};    // 黄绿色
            case 6 -> new float[]{1.0F, 0.75F, 0.8F};   // 粉
            case 7 -> new float[]{0.3F, 0.3F, 0.3F};    // 深灰
            case 8 -> new float[]{0.7F, 0.7F, 0.7F};    // 浅灰
            case 9 -> new float[]{0.0F, 0.75F, 0.75F};  // 青色
            case 10 -> new float[]{0.5F, 0.0F, 0.75F};   // 紫
            case 11 -> new float[]{0.0F, 0.0F, 1.0F};    // 蓝
            case 12 -> new float[]{0.4F, 0.2F, 0.0F};    // 棕
            case 13 -> new float[]{0.0F, 0.5F, 0.0F};    // 绿
            case 14 -> new float[]{1.0F, 0.0F, 0.0F};    // 红
            case 15 -> new float[]{0.0F, 0.0F, 0.0F};    // 黑
            default -> new float[]{1.0F, 1.0F, 1.0F};
        };
    }
}