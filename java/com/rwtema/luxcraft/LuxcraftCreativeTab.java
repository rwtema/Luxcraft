package com.rwtema.luxcraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LuxcraftCreativeTab extends CreativeTabs {

	public static CreativeTabs instance = new LuxcraftCreativeTab();

	public LuxcraftCreativeTab() {
		super("luxcraft");

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Luxcraft.luxGenerator);
	}

}
