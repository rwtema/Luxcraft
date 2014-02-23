package com.rwtema.luxcraft.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.ILuxLaserDivertor;

public class TileEntityLuxInserter extends TileEntity implements ILuxLaserDivertor {

	List<Pos> pos = new ArrayList<Pos>();

	public boolean canUpdate() {
		return false;
	}

	public void onNeighbourChange() {

	}

	@Override
	public List<Pos> getAlternatePositions() {
		Pos start = new Pos(xCoord, yCoord, zCoord);
		pos.clear();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Pos p = start.copy().advance(dir);
			if (worldObj.getTileEntity(p.x, p.y, p.z) instanceof ILuxContainer)
				pos.add(p);
		}

		return pos;
	}
}
