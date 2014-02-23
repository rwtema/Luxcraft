package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.LuxStack;

public class TileEntityLuxStorage extends TileEntityLuxContainerBase {

	public TileEntityLuxStorage() {
		super(new LuxStack(1024));
	}

}
