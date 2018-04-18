package com.rwtema.luxcraft.infusion;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.item.ItemStack;

public interface IInfusionRecipe {

    LuxStack getLux(ItemStack other);

    boolean matches(ItemStack other);

    ItemStack createOutput(ItemStack other);

    ItemStack[] getInputs();

}