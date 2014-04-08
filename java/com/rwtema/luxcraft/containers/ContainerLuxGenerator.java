package com.rwtema.luxcraft.containers;

import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLuxGenerator extends ContainerLuxContainer {
    public TileEntityLuxGenerator generator;

    public ContainerLuxGenerator(InventoryPlayer player, TileEntityLuxGenerator gen) {
        super(player, gen);
        this.generator = gen;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlotToContainer(new Slot(gen, j + i * 3, 8 + j * 18, 17 + i * 18));
            }
        }

        int var3;

        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(player, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(player, var3, 8 + var3 * 18, 142));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.generator.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or
     * you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot) this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 < 9) {
                if (!this.mergeItemStack(var5, 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(var5, 0, 9, false)) {
                return null;
            }

            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
        }

        return var3;
    }
}
