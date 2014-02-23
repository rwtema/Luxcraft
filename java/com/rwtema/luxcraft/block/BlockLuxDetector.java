package com.rwtema.luxcraft.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.tiles.TileEntityLuxDetector;
import com.rwtema.luxcraft.tiles.TileEntityLuxInserter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxDetector extends Block {

	public BlockLuxDetector() {
		super(Material.iron);
		this.setCreativeTab(LuxcraftCreativeTab.instance);
		this.setLightOpacity(0);
		this.setBlockTextureName("luxcraft:detector0");
		float size = 3.0F / 16.0F;
		this.setBlockBounds(size, size, size, 1 - size, 1 - size, 1 - size);
	}

	IIcon[] icons = new IIcon[3];

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.blockIcon = register.registerIcon("luxcraft:detector0");
		for (int i = 0; i < 3; i++)
			this.icons[i] = register.registerIcon("luxcraft:detector" + i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[meta % 3];
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		if (meta == 0)
			return new TileEntityLuxInserter();
		else
			return new TileEntityLuxDetector(world.isRemote);
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		if (world.getTileEntity(x, y, z) != null) {

			switch (world.getBlockMetadata(x, y, z)) {
			case 0:
				return 15;
			case 1:
				return ((TileEntityLuxDetector) world.getTileEntity(x, y, z)).luxDetectedTimer > 0 ? 15 : 0;
			case 2:
				return ((TileEntityLuxDetector) world.getTileEntity(x, y, z)).luxDetectedTimer == 0 ? 15 : 0;
			}
		}
		return 0;
	}

	// public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	// {
	// return this.blockIndexInTexture+par2;
	// }

	@Override
	public boolean canProvidePower() {
		return true;
	}

	//
	// /**
	// * Determines the damage on the item the block drops. Used in cloth and
	// wood.
	// */
	@Override
	public int damageDropped(int par1) {
		return par1;
	}

	//
	//
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}

}
