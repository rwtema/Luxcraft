package com.rwtema.luxcraft.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;

public class ContainerLuxStorage extends ContainerLuxContainer {
	public ContainerLuxStorage(TileEntityLuxStorage par2TileEntityLuxStorage) {
		super(par2TileEntityLuxStorage);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		System.out.println("Sent");
	}
}
