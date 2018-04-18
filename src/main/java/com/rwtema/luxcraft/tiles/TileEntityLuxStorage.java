package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.LuxHelper;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityLuxStorage extends TileEntityLuxTransmitterBase {

    public int connectionMask = 0, numConnections = 0;

    public TileEntityLuxStorage() {
        super(new LuxStack(10000));
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (numConnections > 0 && LuxHelper.shouldProcess(worldObj) && this.getLuxContents().isSignificant()) {
            for (EnumFacing dir : EnumFacing.values()) {
                if ((connectionMask & (1 << dir.ordinal())) != 0) {
                    TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                    if (tile != null && tile.getClass() == this.getClass()) {
                        LuxStack l2 = this.getLuxContents().copy().sub(((TileEntityLuxTransmitterBase) tile).getLuxContents()).div(2);
                        if (l2.isSignificant()) {
                            this.extractLux(((TileEntityLuxTransmitterBase) tile).insertLux(this.extractLux(l2.limit(20), Transfer.Simulate), Transfer.Perform), Transfer.Perform);
                        }
                    } else {
                        onNeighbourChange();
                    }
                }
            }
        }

    }

    @Override
    public void onNeighbourChange() {
        connectionMask = 0;
        numConnections = 0;
        for (EnumFacing dir : EnumFacing.values()) {
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile != null && tile.getClass() == this.getClass()) {
                connectionMask = connectionMask | (1 << dir.ordinal());
                numConnections++;
            }
        }
        super.onNeighbourChange();
    }

}
