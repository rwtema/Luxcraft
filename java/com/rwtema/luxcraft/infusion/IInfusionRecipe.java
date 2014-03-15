package com.rwtema.luxcraft.infusion;

import net.minecraft.item.ItemStack;

import com.rwtema.luxcraft.luxapi.LuxStack;

public interface IInfusionRecipe {

	public abstract LuxStack getLux(ItemStack other);

	public abstract boolean matches(ItemStack other);

	public abstract ItemStack createOutput(ItemStack other);

	public ItemStack[] getInputs();

}