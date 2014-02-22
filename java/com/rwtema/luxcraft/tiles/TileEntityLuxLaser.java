package com.rwtema.luxcraft.tiles;

import scala.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.luxapi.LuxColor;

public class TileEntityLuxLaser extends TileEntity {
	public int[] laserLengths = new int[LuxColor.n];
	public LaserBeam path;
	public int prevLength = -1;

	@Override
	public void validate() {
		super.validate();
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		for (LuxColor col : LuxColor.values())
			laserLengths[col.index] = tag.getInteger("lux_" + col.shortname);
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		for (LuxColor col : LuxColor.values())
			if (laserLengths[col.index] > 0)
				tag.setInteger("lux_" + col.shortname, laserLengths[col.index]);
	}

	Random rand = new Random();

	public void updateEntity() {
		if (path == null)
			path = new LaserBeam(worldObj, xCoord, yCoord, zCoord, BlockLuxLaser.getDirection(this.getBlockMetadata()), BlockLuxLaser.getLaser(this.getBlockMetadata()));

		for (Pos p : path) {

		}

		if (path.isChanged()) {
			int j = -1;
			j = rand.nextInt(8);
			for (int i = 0; i < laserLengths.length; i++)
				if (laserLengths[i] != 0)
					j = i;

			laserLengths[j] = path.getEffectiveLength();
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

	}

	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		for (LuxColor col : LuxColor.values())
			if (laserLengths[col.index] > 0)
				tag.setInteger(col.shortname, laserLengths[col.index]);

		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tag);
	}
}
