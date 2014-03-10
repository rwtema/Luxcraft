package com.rwtema.luxcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.rwtema.luxcraft.tiles.TileEntityLuxContainerBase;

public abstract class BlockLuxContainer extends Block {

	protected BlockLuxContainer(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		if (item.hasDisplayName()) {
			item.getDisplayName();
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileEntityLuxContainerBase) {
				((TileEntityLuxContainerBase) tile).setCustomName(item.getDisplayName());
			}
		}
		super.onBlockPlacedBy(world, x, y, z, player, item);
	}

	@Override
	public abstract boolean hasTileEntity(int meta);

	@Override
	public abstract TileEntityLuxContainerBase createTileEntity(World var1, int meta);

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		((TileEntityLuxContainerBase) world.getTileEntity(x, y, z)).onNeighbourChange();
	}

}
