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
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World var1, int meta) {
		return new TileEntityLuxDetector();
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par1IBlockAccess.getTileEntity(par2, par3, par4) != null) {
			if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) % 2 == 0) {
				return ((TileEntityLuxDetector) par1IBlockAccess.getTileEntity(par2, par3, par4)).luxDetectedTimer > 0 ? 15 : 0;
			} else {
				return ((TileEntityLuxDetector) par1IBlockAccess.getTileEntity(par2, par3, par4)).luxDetectedTimer == 0 ? 15 : 0;
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
