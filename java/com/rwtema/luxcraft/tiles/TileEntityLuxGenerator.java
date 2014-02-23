package com.rwtema.luxcraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.rwtema.luxcraft.luxapi.ILuxTransmitter;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;

public class TileEntityLuxGenerator extends TileEntityLuxContainerBase implements IInventory, ILuxTransmitter {

	public TileEntityLuxGenerator() {
		super(null);
	}

	float level = 0;
	public static float maxLevel = 12 * 256;

	LuxColor type = null;

	public LuxColor getType() {
		if (type == null)
			type = LuxColor.col(this.getBlockMetadata());
		return type;
	}

	public void updateEntity() {
		if (level <= maxLevel - 1)
			level += 1;
		super.updateEntity();
	}

	@Override
	public LuxStack getLuxContents() {
		return new LuxStack(getType(), level);
	}

	@Override
	public float MaxLuxLevel(LuxColor color) {
		return color == getType() ? maxLevel : 0;
	}

	@Override
	public LuxStack insertLux(LuxStack lux, Transfer simulate) {
		float p = lux.lux[getType().index];
		p = Math.min(p, this.maxLevel - this.level);

		if (simulate.perform)
			this.level += p;

		return new LuxStack(getType(), p);
	}

	@Override
	public LuxStack extractLux(LuxStack lux, Transfer simulate) {
		float p = lux.lux[getType().index];
		p = Math.min(p, this.level);

		if (simulate.perform)
			this.level -= p;

		return new LuxStack(getType(), p);
	}

	InventoryBasic inv = new InventoryBasic("Lux Generator", true, 9);

	@Override
	public int getSizeInventory() {
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return inv.getStackInSlot(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return inv.decrStackSize(var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return inv.getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		inv.setInventorySlotContents(var1, var2);
	}

	@Override
	public String getInventoryName() {
		return inv.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return inv.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return inv.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return inv.isUseableByPlayer(var1);
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return inv.isItemValidForSlot(var1, var2);
	}

	@Override
	public boolean sameContainer(TileEntity other) {
		return false;
	}

}
