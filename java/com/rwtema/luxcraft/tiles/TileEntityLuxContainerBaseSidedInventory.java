package com.rwtema.luxcraft.tiles;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import com.rwtema.luxcraft.luxapi.LuxStack;

public abstract class TileEntityLuxContainerBaseSidedInventory extends TileEntityLuxContainerBaseInventory implements ISidedInventory {

	public TileEntityLuxContainerBaseSidedInventory(LuxStack maxLevels) {
		super(maxLevels);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return getInv().getAccessibleSlotsFromSide(var1);
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		return getInv().canInsertItem(var1, var2, var3);
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return getInv().canExtractItem(var1, var2, var3);
	}

	@Override
	public abstract ISidedInventory getInv();

}
