package com.rwtema.luxcraft.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.rwtema.luxcraft.luxapi.ILaserTile;
import com.rwtema.luxcraft.luxapi.ILuxTransmitter;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;

public class TileEntityLuxContainerBase extends TileEntity implements ILuxTransmitter {
	private LuxStack contents = new LuxStack();
	private final LuxStack maxLevels;
	private int numLasers = 0;
	private LuxStack transmit = new LuxStack();

	public TileEntityLuxContainerBase(LuxStack maxLevels) {
		this.maxLevels = maxLevels;
	}

	public void validate() {
		super.validate();
	}

	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
	}

	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
	}

	@Override
	public LuxStack getLuxContents() {
		return contents;
	}

	@Override
	public float MaxLuxLevel(LuxColor col) {
		return maxLevels.color(col);
	}

	@Override
	public LuxStack insertLux(LuxStack lux, Transfer simulate) {
		LuxStack ret = new LuxStack();
		for (byte c = 0; c < 8; c++) {
			ret.lux[c] = Math.min(lux.lux[c], maxLevels.lux[c] - contents.lux[c]);
			if (simulate.perform)
				contents.lux[c] += ret.lux[c];
		}
		this.markDirty();
		return ret;
	}

	@Override
	public boolean sameContainer(TileEntity other) {
		return false;
	}

	@Override
	public LuxStack extractLux(LuxStack lux, Transfer simulate) {
		LuxStack ret = new LuxStack();
		for (byte c = 0; c < 8; c++) {
			ret.lux[c] = Math.min(lux.lux[c], contents.lux[c]);
			if (simulate.perform)
				contents.lux[c] -= ret.lux[c];
		}
		this.markDirty();
		return ret;
	}

	public void onNeighbourChange() {
		numLasers = 0;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if (tile instanceof ILaserTile)
				if (((ILaserTile) tile).isConnected(dir.getOpposite()))
					numLasers++;
		}
	}

	boolean needsInit = true;

	@Override
	public void updateEntity() {
		if (needsInit) {
			this.onNeighbourChange();
			needsInit = false;
		}
		createTransmitPacket();
	}

	public void createTransmitPacket() {
		if (numLasers == 0)
			transmit = new LuxStack();
		transmit = this.getLuxContents().copy().div(numLasers);
	}

	@Override
	public LuxStack getTransmissionPacket(ForgeDirection side) {
		return transmit;
	}

}
