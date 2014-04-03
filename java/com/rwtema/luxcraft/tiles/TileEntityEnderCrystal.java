package com.rwtema.luxcraft.tiles;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;

public class TileEntityEnderCrystal extends TileEntity {
	public static Random rand = new Random();
	public int loop = rand.nextInt(360);

	public void updateEntity() {
		loop++;
	}
}
