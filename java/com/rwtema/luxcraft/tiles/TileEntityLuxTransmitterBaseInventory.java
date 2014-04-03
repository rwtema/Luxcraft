package com.rwtema.luxcraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import com.rwtema.luxcraft.luxapi.LuxStack;

public abstract class TileEntityLuxTransmitterBaseInventory extends TileEntityLuxTransmitterBase implements IInventory, IInvBasic {
	public abstract IInventory getInv();

	public TileEntityLuxTransmitterBaseInventory(LuxStack maxLevels) {
		super(maxLevels);
		if (getInv() instanceof InventoryBasic)
			((InventoryBasic) getInv()).func_110134_a(this);
	}

	@Override
	public void onInventoryChanged(InventoryBasic var1) {
		this.markDirty();
	}

	@Override
	public int getSizeInventory() {
		return getInv().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return getInv().getStackInSlot(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return getInv().decrStackSize(var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return getInv().getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		getInv().setInventorySlotContents(var1, var2);
	}

	@Override
	public String getInventoryName() {
		return super.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return super.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return getInv().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return getInv().isUseableByPlayer(var1);
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return getInv().isItemValidForSlot(var1, var2);
	}

}
