package com.rwtema.luxcraft.luxapi;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IReflector {
    public ForgeDirection newDir(ForgeDirection dir, World world, int x, int y, int z, ILaser beam);
}
