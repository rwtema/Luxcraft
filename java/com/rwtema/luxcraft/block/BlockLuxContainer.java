package com.rwtema.luxcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.rwtema.luxcraft.tiles.TileEntityLuxContainerBase;

public abstract class BlockLuxContainer extends Block {

	protected BlockLuxContainer(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public abstract boolean hasTileEntity(int meta);

	@Override
	public abstract TileEntityLuxContainerBase createTileEntity(World var1, int meta);

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		((TileEntityLuxContainerBase) world.getTileEntity(x, y, z)).onNeighbourChange();
	}

}
