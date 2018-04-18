package com.rwtema.luxcraft.block_base;

import net.minecraft.world.IBlockAccess;

public interface IMultiBoxBlock {
    void prepareForRender(String label);

    BoxModel getWorldModel(IBlockAccess world, int x, int y, int z);

    BoxModel getInventoryModel(int metadata);
}
