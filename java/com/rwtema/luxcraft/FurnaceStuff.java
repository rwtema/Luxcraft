package com.rwtema.luxcraft;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import com.rwtema.luxcraft.infusion.OreInfusionRecipe;

public class FurnaceStuff {
	public static void addRecipes() {
		for (int damage = 1; damage < OreInfusionRecipe.oreList.size(); damage++) {
			String ore = OreInfusionRecipe.oreList.get(damage);

			List<ItemStack> items = OreDictionary.getOres(ore);

			if (items.size() > 0) {
				ItemStack oreItem = items.get(0);
				ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(oreItem);
				if (result != null) {

					result = result.copy();
					result.stackSize *= 2;
					if (result.stackSize > 64)
						result.stackSize = 64;

					float exp = 1.25F * FurnaceRecipes.smelting().func_151398_b(result);
					FurnaceRecipes.smelting().func_151394_a(new ItemStack(Luxcraft.luxInfusedItem, 1, damage), result, exp);
				}
			}
		}

	}
}
