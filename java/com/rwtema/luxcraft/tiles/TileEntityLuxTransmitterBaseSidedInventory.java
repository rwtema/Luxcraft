package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public abstract class TileEntityLuxTransmitterBaseSidedInventory extends TileEntityLuxTransmitterBaseInventory implements ISidedInventory {

    public TileEntityLuxTransmitterBaseSidedInventory(LuxStack maxLevels) {
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
