package com.rwtema.luxcraft.infusion;

import java.util.ArrayList;
import java.util.List;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InfusionRegistry {
	public static List<IInfusionRecipe> recipes = new ArrayList<IInfusionRecipe>();

	static {
		registerRecipe(new InfusionRecipe(Items.stick, Items.blaze_rod, new LuxStack(LuxColor.Red, 120).add(LuxColor.Yellow, 50)));
		OreInfusionRecipe.registerOres();
	}

	public static void registerRecipe(IInfusionRecipe recipe) {
		recipes.add(recipe);
		for (ItemStack in : recipe.getInputs()) {
			boolean check = recipe.matches(in);
			ItemStack out = recipe.createOutput(in);
			System.out.println(in + " " + check + " " + out);
		}
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
