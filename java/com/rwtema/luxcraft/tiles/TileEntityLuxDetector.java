package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.ILaser;
import com.rwtema.luxcraft.luxapi.ILaserActivated;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLuxDetector extends TileEntity implements ILaserActivated {
    public int luxDetectedTimer = 0;
    boolean client;

    public TileEntityLuxDetector() {
        this.client = false;
    }

    public TileEntityLuxDetector(boolean client) {
        this.client = client;
    }

    public boolean canUpdate() {
        return !client;
    }

    public void updateEntity() {
        if (luxDetectedTimer > 0) {
            if (!this.worldObj.isRemote) {
                luxDetectedTimer -= 1;
                if (luxDetectedTimer == 0) {
                    this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
                }
            }
        }
    }

    public void hit(ILaser laser) {
        if (luxDetectedTimer == 0) {
            luxDetectedTimer = 10;
            this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
        } else
            luxDetectedTimer = 10;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        luxDetectedTimer = par1NBTTagCompound.getByte("Timer");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("Timer", (byte) luxDetectedTimer);
    }

}
