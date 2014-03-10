package com.rwtema.luxcraft.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSelectiveInsert extends Slot {

	public SlotSelectiveInsert(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return this.inventory.isItemValidForSlot(slotNumber, par1ItemStack);

	}

}
