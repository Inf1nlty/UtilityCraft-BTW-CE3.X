package com.inf1nlty.utilitycraft.block.tileentity;

import api.block.TileEntityDataPacketHandler;
import net.minecraft.src.*;

/**
 * TileEntity for the placed colored cement bucket block.
 * Stores the color index (0-15). Facing remains in block metadata.
 */
public class TileEntityColoredCementBucket extends TileEntity implements TileEntityDataPacketHandler {

    private int color;

    public TileEntityColoredCementBucket() {
        color = 0;
    }

    public void setColor(int c) {
        color = (c < 0 || c > 15) ? 0 : c;
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public int getColor() {
        return color;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setByte("color", (byte) color);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("color")) {
            color = tag.getByte("color") & 15;
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("c", (byte) color);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        color = tag.getByte("c") & 15;
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
}