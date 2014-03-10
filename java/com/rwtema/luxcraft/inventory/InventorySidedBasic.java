package com.rwtema.luxcraft.inventory;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class InventorySidedBasic extends InventoryBasic implements ISidedInventory {
	public final int[] allSlots;

	public InventorySidedBasic(String par1Str, boolean par2, int par3) {
		super(par1Str, par2, par3);
		allSlots = new int[par3];
		for (int i = 0; i < allSlots.length; i++) {
			allSlots[i] = i;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return allSlots;
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		return true;
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return true;
	}

}
