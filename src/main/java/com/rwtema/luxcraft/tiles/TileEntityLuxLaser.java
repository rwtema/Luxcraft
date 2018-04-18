package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.LuxHelper;
import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.luxapi.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import scala.util.Random;

import java.util.Iterator;
import java.util.List;

public class TileEntityLuxLaser extends TileEntity implements ILaserTile {
    public int[] laserLengths = new int[LuxColor.n];
    public LaserBeam path;
    Random rand = new Random();

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

    public void updateEntity() {
        if (!LuxHelper.shouldProcess(worldObj))
            return;

        Pos inv = (new Pos(xCoord, yCoord, zCoord)).advance(BlockLuxLaser.getDirection(getBlockMetadata()).getOpposite());
        TileEntity tile = worldObj.getTileEntity(inv.x, inv.y, inv.z);

        int[] newLaserLengths = new int[8];

        if (tile instanceof ILuxTransmitter) {
            ILuxTransmitter transmit = (ILuxTransmitter) tile;

            LaserType type = BlockLuxLaser.getLaser(this.getBlockMetadata());

            if (path == null)
                path = new LaserBeam(this.worldObj, this.xCoord, this.yCoord, this.zCoord, BlockLuxLaser.getDirection(this.getBlockMetadata()), type);

            LuxStack pkt = transmit.getTransmissionPacket(getDir().getOpposite());

            for (int i = 0; i < pkt.lux[i]; i++)
                pkt.lux[i] = Math.min(pkt.lux[i], type.maxLux);

            pkt = transmit.extractLux(pkt, Transfer.Simulate);

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
                    if (lists != null && lists.size() > 0) {
                        boolean insert = true;
                        for (int j = 0; insert && j <= 20 && lists.size() > 0 && pkt.totalLux() > 0.0001; j++) {
                            insert = false;

                            if (lists.size() == 1)
                                j = 20;

                            LuxStack p2 = pkt.copy();
                            if (j < 20)
                                p2 = p2.div(lists.size());

                            Iterator<Pos> it2 = lists.iterator();
                            while (it2.hasNext()) {
                                Pos q = it2.next();
                                if (inv.equals(q) || transmit.sameContainer(worldObj.getTileEntity(q.x, q.y, q.z))) {
                                    pkt.extract(p2);
                                    continue;
                                }

                                ILuxContainer t = ((ILuxContainer) worldObj.getTileEntity(q.x, q.y, q.z));

                                LuxStack k = t.insertLux(p2, Transfer.Perform);

                                pkt.extract(k);
                                transmit.extractLux(k, Transfer.Perform);
                                if (k.totalLux() < 0.001) {
                                    it2.remove();
                                } else
                                    insert = true;
                            }
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
        }

        boolean changed = path != null && path.isChanged();

        if (!changed) {
            for (int i = 0; i < 8; i++)
                if (laserLengths[i] != newLaserLengths[i]) {
                    changed = true;
                    break;
                }
        }

        if (changed)
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        System.arraycopy(newLaserLengths, 0, laserLengths, 0, 8);
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