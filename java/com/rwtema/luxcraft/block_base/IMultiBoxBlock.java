package com.rwtema.luxcraft.block_base;

import net.minecraft.world.IBlockAccess;

public interface IMultiBoxBlock {
	public void prepareForRender(String label);

	public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z);

	public BoxModel getInventoryModel(int metadata);
}
