package com.inf1nlty.utilitycraft.block.tileentity;

import api.block.TileEntityDataPacketHandler;
import com.inf1nlty.utilitycraft.block.BlockColoredCement;
import net.minecraft.src.*;

/**
 * TileEntity storing spread distance and dry time for colored cement.
 */
public class TileEntityColoredCement extends TileEntity implements TileEntityDataPacketHandler {

    private int dryTime;
    private int spreadDist;

    public TileEntityColoredCement() {
        dryTime = 0;
        spreadDist = 0;
    }

    public int getDryTime() {
        return dryTime;
    }

    public int getSpreadDist() {
        return spreadDist;
    }

    public void setDryTime(int t) {
        dryTime = t;
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void setSpreadDist(int d) {
        spreadDist = d;
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("dryTime", dryTime);
        nbt.setInteger("spreadDist", spreadDist);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        dryTime = nbt.hasKey("dryTime")
                ? nbt.getInteger("dryTime")
                : BlockColoredCement.CEMENT_TICKS_TO_DRY;
        spreadDist = nbt.hasKey("spreadDist")
                ? nbt.getInteger("spreadDist")
                : BlockColoredCement.MAX_CEMENT_SPREAD_DIST;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setShort("d", (short) dryTime);
        tag.setShort("s", (short) spreadDist);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        dryTime = tag.getShort("d");
        spreadDist = tag.getShort("s");
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
}