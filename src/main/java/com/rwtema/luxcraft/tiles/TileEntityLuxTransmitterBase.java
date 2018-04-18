package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.ILaserTile;
import com.rwtema.luxcraft.luxapi.ILuxTransmitter;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityLuxTransmitterBase extends TileEntityLuxContainerBase implements ILuxTransmitter {
    private LuxStack contents = new LuxStack();
    private int numLasers = 0;
    private LuxStack transmit = new LuxStack();
    private boolean needsInit = true;

    public TileEntityLuxTransmitterBase(LuxStack maxLevels) {
        super(maxLevels);
    }

    @Override
    public boolean sameContainer(TileEntity other) {
        return false;
    }

    @Override
    public LuxStack extractLux(LuxStack lux, Transfer simulate) {
        LuxStack ret = new LuxStack();
        for (byte c = 0; c < 8; c++) {
            ret.lux[c] = Math.min(lux.lux[c], contents.lux[c]);
            if (simulate.perform)
                contents.lux[c] -= ret.lux[c];
        }
        this.markDirty();
        return ret;
    }

    @Override
    public void onNeighbourChange() {
        numLasers = 0;
        for (EnumFacing dir : EnumFacing.values()) {
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile instanceof ILaserTile)
                if (((ILaserTile) tile).isConnected(dir.getOpposite()))
                    numLasers++;
        }
    }

    @Override
    public void updateEntity() {
        if (needsInit) {
            this.onNeighbourChange();
            needsInit = false;
        }
        createTransmitPacket();
    }

    public void createTransmitPacket() {
        if (numLasers == 0)
            transmit = new LuxStack();
        transmit = this.getLuxContents().copy().div(numLasers);
    }

    @Override
    public LuxStack getTransmissionPacket(EnumFacing side) {
        return transmit;
    }
}
