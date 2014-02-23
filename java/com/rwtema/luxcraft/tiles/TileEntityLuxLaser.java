package com.rwtema.luxcraft.tiles;

import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import scala.util.Random;

import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.block.LaserType;
import com.rwtema.luxcraft.luxapi.ILaserActivated;
import com.rwtema.luxcraft.luxapi.ILaserTile;
import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.ILuxLaserDivertor;
import com.rwtema.luxcraft.luxapi.ILuxTransmitter;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;

public class TileEntityLuxLaser extends TileEntity implements ILaserTile {
	public int[] laserLengths = new int[LuxColor.n];
	public LaserBeam path;

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

		Pos inv = (new Pos(xCoord, yCoord, zCoord)).advance(BlockLuxLaser.getDirection(getBlockMetadata()).getOpposite());
		TileEntity tile = worldObj.getTileEntity(inv.x, inv.y, inv.z);
		if (tile == null || !(tile instanceof ILuxTransmitter))
			return;

		ILuxTransmitter transmit = (ILuxTransmitter) tile;

		LaserType type = BlockLuxLaser.getLaser(this.getBlockMetadata());

		if (path == null)
			path = new LaserBeam(worldObj, xCoord, yCoord, zCoord, BlockLuxLaser.getDirection(this.getBlockMetadata()), type);

		LuxStack pkt = transmit.getTransmissionPacket(getDir().getOpposite());

		for (int i = 0; i < pkt.lux[i]; i++)
			pkt.lux[i] = Math.min(pkt.lux[i], type.maxLux);

		int[] newLaserLengths = new int[8];

		Iterator<Pos> iter = path.iterator();

		while (iter.hasNext()) {
			boolean empty = true;

			for (int i = 0; i < pkt.lux.length; i++)
				if (pkt.lux[i] > 0.0001F) {
					empty = false;
					newLaserLengths[i]++;
				}

			if (empty) {
				iter.remove();
				break;
			}

			Pos p = iter.next();
			TileEntity b = worldObj.getTileEntity(p.x, p.y, p.z);
			if (b == null)
				continue;

			if (b instanceof ILaserActivated) {
				((ILaserActivated) b).hit(path);
			}

			if (b instanceof ILuxLaserDivertor) {
				List<Pos> lists = ((ILuxLaserDivertor) b).getAlternatePositions();
				if (lists.size() > 0) {
					LuxStack p2 = pkt.copy().div(lists.size());
					for (Pos q : lists) {
						if (inv.equals(q) || transmit.sameContainer(worldObj.getTileEntity(q.x, q.y, q.z))) {
							pkt.extract(p2);
							continue;
						}

						ILuxContainer t = ((ILuxContainer) worldObj.getTileEntity(q.x, q.y, q.z));

						LuxStack k = t.insertLux(p2, Transfer.Perform);
						pkt.extract(k);
						transmit.extractLux(k, Transfer.Perform);
					}
				}
			}

			if (b instanceof ILuxContainer) {
				if (inv.equals(p) || transmit.sameContainer(tile)) {
					iter.remove();
					pkt = new LuxStack();

					break;
				}

				LuxStack t = ((ILuxContainer) b).insertLux(pkt, Transfer.Perform);
				pkt.extract(t);
				transmit.extractLux(t, Transfer.Perform);

			}

		}

		boolean empty = true;

		for (int i = 0; i < pkt.lux.length; i++)
			if (pkt.lux[i] > 0.0001F)
				empty = false;

		if (!empty)
			transmit.extractLux(pkt, Transfer.Perform);

		boolean changed = path.isChanged();

		if (!changed) {
			for (int i = 0; i < 8; i++)
				if (laserLengths[i] != newLaserLengths[i]) {
					changed = true;
					break;
				}
		}

		laserLengths = newLaserLengths;

		if (changed) {
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

	public ForgeDirection getDir() {
		return BlockLuxLaser.getDirection(getBlockMetadata());
	}

	@Override
	public boolean isConnected(ForgeDirection dir) {
		return BlockLuxLaser.getDirection(getBlockMetadata()).equals(dir.getOpposite());
	}
}
