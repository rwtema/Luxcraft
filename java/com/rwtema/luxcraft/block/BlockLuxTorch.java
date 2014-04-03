package com.rwtema.luxcraft.block;

import java.util.List;
import java.util.Random;

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
import com.rwtema.luxcraft.block_base.BlockMultiBlock;
import com.rwtema.luxcraft.block_base.BoxModel;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.particles.EntityLaserFX;
import com.rwtema.luxcraft.particles.ParticleHandler;
import com.rwtema.luxcraft.tiles.TileEntityLuxTorch;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxTorch extends BlockMultiBlock {

	public BlockLuxTorch() {
		super(Material.wood);
		this.setCreativeTab(LuxcraftCreativeTab.instance);
		this.setBlockName("luxcraft:luxTorch");
		this.setBlockTextureName("luxcraft:luxTorch");
	}

	@Override
	public void prepareForRender(String label) {

	}

	@Override
	public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z) {
		return getInventoryModel(world.getBlockMetadata(x, y, z));
	}

	@Override
	public BoxModel getInventoryModel(int metadata) {
		BoxModel model = new BoxModel();
		model.addBoxI(1, 0, 1, 15, 3, 15).setTextureSides(1, icons[1], 0, icons[1]);
		model.addBoxI(3, 3, 3, 13, 4, 13).setTextureSides(1, icons[1], 0, icons[1]);

		model.addBoxI(5, 5, 5, 11, 11, 11).setColor(LuxColor.col(metadata).displayColor());
		return model;
	}

	IIcon[] icons = new IIcon[2];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons[0] = register.registerIcon("luxcraft:luxTorch");

		this.blockIcon = icons[1] = register.registerIcon("luxcraft:luxTorch_bottom");
	}

	@Override
	public int damageDropped(int par1) {
		return par1;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		for (int i = 0; i < 10; i++)
			ParticleHandler.spawnParticle(new EntityLaserFX(world, x + 0.5, y + 0.5, z + 0.5, 3, LuxColor.col(world.getBlockMetadata(x, y, z))));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int var4 = 0; var4 < 8; ++var4) {
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	
	@Override
	public  boolean hasTileEntity(int meta){
		return true;
	}

	@Override
	public  TileEntity createTileEntity(World var1, int meta){
		return new TileEntityLuxTorch();
	}

}
