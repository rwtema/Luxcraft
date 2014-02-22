package com.rwtema.luxcraft.tiles;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.rwtema.luxcraft.luxapi.ILaser;
import com.rwtema.luxcraft.luxapi.ILaserTarget;
import com.rwtema.luxcraft.luxapi.ILuxLaserDivertor;

public class TileEntityLuxDetector extends TileEntity implements ILaserTarget, ILuxLaserDivertor {

	public int luxDetectedTimer;

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

	@Override
	public List<Pos> getAlternatePositions() {
		return null;
	}

}
