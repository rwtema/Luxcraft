package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.LuxHelper;
import com.rwtema.luxcraft.StackHelper;
import com.rwtema.luxcraft.infusion.IInfusionRecipe;
import com.rwtema.luxcraft.infusion.InfusionRegistry;
import com.rwtema.luxcraft.inventory.InventoryLimited;
import com.rwtema.luxcraft.inventory.InventorySidedBasic;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class TileEntityLuxInfuser extends TileEntityLuxTransmitterBaseSidedInventory {
    IInfusionRecipe curRecipe = null;
    InventorySidedBasic inv = new InventoryLimited("", false, 1, 1);

    public TileEntityLuxInfuser() {
        super(new LuxStack());
    }

    public void updateEntity() {
        check();
        checkRecipe();
        if (!LuxHelper.shouldProcess(worldObj))
            return;
        super.updateEntity();
    }

    public void check() {
        if (this.curRecipe != null) {
            LuxStack max = curRecipe.getLux(inv.getStackInSlot(0));
            for (byte c = 0; c < LuxColor.n; c++)
                if (this.getLuxContents().lux[c] < max.lux[c])
                    return;

            inv.setInventorySlotContents(0, curRecipe.createOutput(inv.getStackInSlot(0)));
        }
    }

    public void checkRecipe() {
        if (StackHelper.isNull(inv.getStackInSlot(0))) {
            this.setMaxLux(new LuxStack());
            return;
        }

        curRecipe = InfusionRegistry.getRecipe(inv.getStackInSlot(0));
        if (curRecipe != null)
            this.setMaxLux(curRecipe.getLux(inv.getStackInSlot(0)));
    }

    @Override
    public void onInventoryChanged(InventoryBasic var1) {
        if (StackHelper.isNull(var1.getStackInSlot(0)))
            this.setLuxContents(new LuxStack());
        this.markDirty();
    }

    @Override
    public LuxStack insertLux(LuxStack lux, Transfer simulate) {
        return lux.copy().extract(super.insertLux(lux.limit(1), simulate));
    }

    @Override
    public LuxStack extractLux(LuxStack lux, Transfer simulate) {
        return new LuxStack();
    }

    @Override
    public ISidedInventory getInv() {
        return inv;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return new int[]{0};
    }

    @Override
    public boolean canInsertItem(int var1, ItemStack var2, int var3) {
        return InfusionRegistry.isInfusable(var2);
    }

    @Override
    public boolean canExtractItem(int var1, ItemStack var2, int var3) {
        return !InfusionRegistry.isInfusable(var2);
    }

}
