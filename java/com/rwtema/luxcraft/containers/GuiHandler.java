package com.rwtema.luxcraft.containers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import com.rwtema.luxcraft.tiles.TileEntityLuxInfuser;
import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static Map<Class, Class<? extends Container>> serverGuis = new HashMap<Class, Class<? extends Container>>();
	public static Map<Class, Class<? extends GuiContainer>> clientGuis = new HashMap<Class, Class<? extends GuiContainer>>();

	static {
		serverGuis.put(TileEntityLuxGenerator.class, ContainerLuxGenerator.class);
		clientGuis.put(TileEntityLuxGenerator.class, GuiLuxGenerator.class);

		serverGuis.put(TileEntityLuxStorage.class, ContainerLuxStorage.class);
		clientGuis.put(TileEntityLuxStorage.class, GuiLuxStorage.class);

		serverGuis.put(TileEntityLuxInfuser.class, ContainerLuxInfuser.class);
		clientGuis.put(TileEntityLuxInfuser.class, GuiLuxInfuser.class); 
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null)
			return null;

		Class<? extends Container> container = serverGuis.get(tile.getClass());

		if (container != null)
			try {
				return container.getConstructor(InventoryPlayer.class, tile.getClass()).newInstance(player.inventory, tile);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null)
			return null;

		Class<? extends GuiContainer> container = clientGuis.get(tile.getClass());

		if (container != null)
			try {
				return container.getConstructor(InventoryPlayer.class, tile.getClass()).newInstance(player.inventory, tile);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		return null;
	}

}
