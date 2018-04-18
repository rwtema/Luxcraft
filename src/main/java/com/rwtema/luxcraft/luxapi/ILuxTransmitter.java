package com.rwtema.luxcraft.luxapi;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface ILuxTransmitter extends ILuxContainer {
    public boolean sameContainer(TileEntity other);

    public LuxStack extractLux(LuxStack lux, Transfer simulate);

    public LuxStack getTransmissionPacket(ForgeDirection side);
}
