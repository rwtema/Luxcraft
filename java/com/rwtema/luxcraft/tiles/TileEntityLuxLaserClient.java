package com.rwtema.luxcraft.tiles;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.luxapi.LuxColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityLuxLaserClient extends TileEntity {

	private LaserBeamClient path = null;
	public int[] laserLengths = null;

	@Override
	public boolean canUpdate() {
		return false;
	}

	public LaserBeamClient getClientPath() {
		if (path == null && laserLengths != null) {
			path = new LaserBeamClient(worldObj, xCoord, yCoord, zCoord, BlockLuxLaser.getDirection(this.getBlockMetadata()), BlockLuxLaser.getLaser(this.getBlockMetadata()), laserLengths);
			path.preCalcFullPath();
		}
		return path;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		laserLengths = new int[LuxColor.n];
		for (LuxColor col : LuxColor.values())
			laserLengths[col.index] = pkt.func_148857_g().getInteger(col.shortname);

		path = null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}
