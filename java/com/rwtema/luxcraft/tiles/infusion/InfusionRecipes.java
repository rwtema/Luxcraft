package com.rwtema.luxcraft.tiles.infusion;

import java.util.ArrayList;
import java.util.List;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InfusionRecipes {
	public static List<IInfusionRecipe> recipes = new ArrayList<IInfusionRecipe>();

	static {
		recipes.add(new InfusionRecipe(Items.stick, Items.blaze_rod, new LuxStack(LuxColor.Red, 120).add(LuxColor.Yellow, 50)));
	}

	public static boolean isInfusable(ItemStack item) {
		if (item != null)
			for (IInfusionRecipe r : recipes)
				if (r.matches(item))
					return true;
		return false;
	}

	public static IInfusionRecipe getRecipe(ItemStack item) {
		if (item != null)
			for (IInfusionRecipe r : recipes)
				if (r.matches(item))
					return r;

		return null;
	}
}
