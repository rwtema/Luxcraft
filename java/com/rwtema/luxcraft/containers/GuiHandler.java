package com.rwtema.luxcraft.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null)
			return null;

		else if (tile instanceof TileEntityLuxGenerator)
			return new ContainerLuxGenerator(player.inventory, (TileEntityLuxGenerator) tile);

		else if (tile instanceof TileEntityLuxStorage)
			return new ContainerLuxStorage((TileEntityLuxStorage) tile);

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null)
			return null;

		else if (tile instanceof TileEntityLuxGenerator)
			return new GuiLuxGenerator(player.inventory, (TileEntityLuxGenerator) tile);

		else if (tile instanceof TileEntityLuxStorage)
			return new GuiLuxStorage((TileEntityLuxStorage) tile);

		return null;
	}

}
