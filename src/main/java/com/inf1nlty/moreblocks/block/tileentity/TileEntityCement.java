package com.inf1nlty.moreblocks.block.tileentity;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.minecraft.src.TileEntity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet132TileEntityData;

public class TileEntityCement extends TileEntity implements TileEntityDataPacketHandler {
    private static final int TIME_TO_CONVERT = 24000;
    private int convertCounter = 0;
    private boolean isConverting = true;
    public int concreteBlockID = -1;

    public TileEntityCement() {}

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (isConverting) {
                ++convertCounter;
                if (convertCounter == 1) {
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
     * Converts 16-color meta to RGB (0-1).
     */
    public static float[] getMetaColor(int meta) {
        return switch (meta) {
            case 0 -> new float[]{1.0F, 1.0F, 1.0F};    // White
            case 1 -> new float[]{1.0F, 0.5F, 0.0F};    // Orange
            case 2 -> new float[]{1.0F, 0.0F, 1.0F};    // Magenta
            case 3 -> new float[]{0.5F, 0.75F, 1.0F};   // Light Blue
            case 4 -> new float[]{1.0F, 1.0F, 0.0F};    // Yellow
            case 5 -> new float[]{0.5F, 1.0F, 0.0F};    // Lime
            case 6 -> new float[]{1.0F, 0.75F, 0.8F};   // Pink
            case 7 -> new float[]{0.3F, 0.3F, 0.3F};    // Gray
            case 8 -> new float[]{0.7F, 0.7F, 0.7F};    // Light Gray
            case 9 -> new float[]{0.0F, 0.75F, 0.75F};  // Cyan
            case 10 -> new float[]{0.5F, 0.0F, 0.75F};  // Purple
            case 11 -> new float[]{0.0F, 0.0F, 1.0F};   // Blue
            case 12 -> new float[]{0.4F, 0.2F, 0.0F};   // Brown
            case 13 -> new float[]{0.0F, 0.5F, 0.0F};   // Green
            case 14 -> new float[]{1.0F, 0.0F, 0.0F};   // Red
            case 15 -> new float[]{0.0F, 0.0F, 0.0F};   // Black
            default -> new float[]{1.0F, 1.0F, 1.0F};
        };
    }
}