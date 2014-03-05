package com.rwtema.luxcraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.rwtema.luxcraft.luxapi.LuxStack;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class LuxLevels {
	private List<LuxLevelsEntry> luxEntries = new ArrayList<LuxLevelsEntry>();
	public static LuxLevels instance = new LuxLevels();
	private Random cols = new Random(0);

	public void initLevels() {

	}

	public void addLuxEntry(ItemStack par1, LuxStack lux, boolean deconstructable) {
		luxEntries.add(new LuxLevelsEntry(par1, lux, deconstructable));
	}

	public void addLuxEntry(Item itemId, LuxStack lux, boolean deconstructable) {
		luxEntries.add(new LuxLevelsEntry(itemId, lux, deconstructable));
	}

	public void addLuxEntry(Item itemId, int metadata, LuxStack lux, boolean deconstructable) {
		luxEntries.add(new LuxLevelsEntry(itemId, metadata, lux, deconstructable));
	}

	public void addLuxEntry(LuxLevelsEntry par1) {
		luxEntries.add(par1);
	}

	public LuxStack GetLuxPacket(ItemStack par1) {
		return null;
	}

}
