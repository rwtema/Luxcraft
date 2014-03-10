package com.rwtema.luxcraft.tiles;

import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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
	protected String customName = "";

	public TileEntityLuxContainerBase(LuxStack maxLevels) {
		this.maxLevels = maxLevels;
	}

	public void setMaxLux(LuxStack newMax) {
		for (byte c = 0; c < LuxColor.n; c++) {
			maxLevels.lux[c] = newMax.lux[c];
			if (contents.lux[c] > newMax.lux[c])
				contents.lux[c] = newMax.lux[c];
		}
	}

	public void setCustomName(String s) {
		customName = s;
	}

	public void validate() {
		super.validate();
	}

	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		contents.readFromNBT(tags);
		customName = tags.getString("Custom Name");
	}

	public void writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		contents.writeToNBT(tags);
		if (!"".equals(customName))
			tags.setString("Custom Name", customName);
	}

	@Override
	public LuxStack getLuxContents() {
		return contents;
	}

	public void setLuxContents(LuxStack other) {
		contents = other;
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

	public String getInventoryName() {
		return hasCustomInventoryName() ? customName : this.getBlockType()
				.getPickBlock(new MovingObjectPosition(xCoord, yCoord, zCoord, 0, Vec3.createVectorHelper(xCoord, yCoord, zCoord)), worldObj, xCoord, yCoord, zCoord).getUnlocalizedName()
				+ ".name";
	}

	public boolean hasCustomInventoryName() {
		return !"".equals(customName);
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
