package com.rwtema.luxcraft.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.rwtema.luxcraft.LuxcraftCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfusedItems extends Item {

	public ItemInfusedItems() {
		this.setCreativeTab(LuxcraftCreativeTab.instance);
		this.setUnlocalizedName("luxcraft:luxInfused");
		this.setTextureName("luxcraft:unknown");
		this.setHasSubtypes(true);
	}

	public static IIcon glow;

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		glow = par1IconRegister.registerIcon("luxcraft:infusionglow");
	}

	public static ItemStack getRenderItem(ItemStack item) {
		return new ItemStack(Blocks.iron_ore);
	}

}
