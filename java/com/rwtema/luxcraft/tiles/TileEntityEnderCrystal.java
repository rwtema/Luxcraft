package com.rwtema.luxcraft.tiles;

import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityEnderCrystal extends TileEntity {
    public static Random rand = new Random();
    public int loop = rand.nextInt(360);

    public void updateEntity() {
        loop++;
    }
}
