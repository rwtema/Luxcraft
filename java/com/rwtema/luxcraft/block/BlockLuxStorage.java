package com.rwtema.luxcraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.rwtema.luxcraft.Luxcraft;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.tiles.TileEntityLuxContainerBase;
import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxStorage extends BlockLuxContainer {
	private static Random random = new Random();
	public IIcon sideIcon = null;

	public BlockLuxStorage() {
		super(Material.rock);
		this.setCreativeTab(LuxcraftCreativeTab.instance);
		this.setBlockName("luxcraft:luxStorage");
		this.setBlockTextureName("luxcraft:luxContainerTop");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.blockIcon = register.registerIcon("luxcraft:luxContainerTop");
		this.sideIcon = register.registerIcon("luxcraft:luxContainerSide");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (!world.isRemote)
			player.openGui(Luxcraft.instance, 0, world, x, y, z);
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side >= 2)
			return sideIcon;
		else
			return super.getIcon(side, meta);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntityLuxContainerBase createTileEntity(World var1, int meta) {
		return new TileEntityLuxStorage();
	}

}
