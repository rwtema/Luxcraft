package com.rwtema.luxcraft.luxapi;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface ILuxTransmitter extends ILuxContainer {
    boolean sameContainer(TileEntity other);

    LuxStack extractLux(LuxStack lux, Transfer simulate);

    LuxStack getTransmissionPacket(EnumFacing side);
}
