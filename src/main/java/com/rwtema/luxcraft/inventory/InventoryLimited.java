package com.rwtema.luxcraft.inventory;

public class InventoryLimited extends InventorySidedBasic {
    public final int limit;

    public InventoryLimited(String par1Str, boolean par2, int par3, int limit) {
        super(par1Str, par2, par3);
        this.limit = limit;
    }

    public int getInventoryStackLimit() {
        return limit;
    }

}
