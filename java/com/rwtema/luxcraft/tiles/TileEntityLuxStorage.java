package com.rwtema.luxcraft.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.rwtema.luxcraft.luxapi.ILaserTile;
import com.rwtema.luxcraft.luxapi.LuxStack;

public class TileEntityLuxStorage extends TileEntityLuxContainerBase {

	public TileEntityLuxStorage() {
		super(new LuxStack(1024));
	}

	public void onNeighbourChange() {
		super.onNeighbourChange();
	}

}
