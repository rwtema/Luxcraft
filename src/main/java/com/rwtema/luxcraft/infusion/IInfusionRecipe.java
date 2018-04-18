package com.rwtema.luxcraft.infusion;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.item.ItemStack;

public interface IInfusionRecipe {

    public abstract LuxStack getLux(ItemStack other);

    public abstract boolean matches(ItemStack other);

    public abstract ItemStack createOutput(ItemStack other);

    public ItemStack[] getInputs();

}