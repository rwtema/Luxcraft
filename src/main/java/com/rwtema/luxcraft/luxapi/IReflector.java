package com.rwtema.luxcraft.luxapi;

import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public interface IReflector {
    EnumFacing newDir(EnumFacing dir, World world, int x, int y, int z, ILaser beam);
}
