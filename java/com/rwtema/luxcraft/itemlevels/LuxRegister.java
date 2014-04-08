package com.rwtema.luxcraft.itemlevels;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class LuxRegister {

    public LuxStack toSynthesize(ItemStack item) {
        if(item == null)
            return null;
        return null;
    }

    public LuxStack burn(ItemStack item) {
        return null;
    }

    public LuxStack addBaseLux(ItemStack item) {
        if(item == null)
            return null;

        LuxStack t = new LuxStack();

        if(item.getItem() instanceof ItemBlock){
            ((ItemBlock) item.getItem()).field_150939_a.getMaterial();
        }
        TileEntityFurnace.getItemBurnTime(item);

        return null;
    }


}
