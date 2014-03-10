package com.rwtema.luxcraft.tiles.infusion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.rwtema.luxcraft.luxapi.LuxStack;

public class InfusionRecipe implements IInfusionRecipe {
	public ItemStack in, out;
	public LuxStack pkt;

	@Override
	public LuxStack getLux(ItemStack other) {
		return pkt.copy();
	}

	public InfusionRecipe(ItemStack in, ItemStack out, LuxStack pkt) {
		this.in = in;
		this.out = out;
		this.pkt = pkt;
	}

	public InfusionRecipe(Item in, Item out, LuxStack pkt) {
		this(new ItemStack(in, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(out), pkt);
	}

	@Override
	public boolean matches(ItemStack other) {
 		return in.getItem() == other.getItem() && (in.getItemDamage() == OreDictionary.WILDCARD_VALUE || in.getItemDamage() == other.getItemDamage());
	}

	@Override
	public ItemStack createOutput(ItemStack other) {
		return out.copy();
	}
}
