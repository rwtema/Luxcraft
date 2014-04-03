package com.rwtema.luxcraft;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.MinecraftForgeClient;

import com.rwtema.luxcraft.block_base.RenderBlockMultiBlock;
import com.rwtema.luxcraft.debug.CommandClientDebug;
import com.rwtema.luxcraft.render.ItemRenderLuxInfused;
import com.rwtema.luxcraft.render.ItemRenderLuxSaber;
import com.rwtema.luxcraft.render.RenderPrism;
import com.rwtema.luxcraft.render.RenderTileEntityEnderCrystal;
import com.rwtema.luxcraft.render.RenderTileEntityLaser;
import com.rwtema.luxcraft.tiles.TileEntityEnderCrystal;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class LuxcraftClient extends LuxcraftProxy {

	@Override
	public void registerRenderInformation() {
		prismRenderingID = RenderingRegistry.getNextAvailableRenderId();
		multiBoxRenderingID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderPrism());
		RenderingRegistry.registerBlockHandler(new RenderBlockMultiBlock());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLuxLaserClient.class, new RenderTileEntityLaser());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderCrystal.class, new RenderTileEntityEnderCrystal());

		MinecraftForgeClient.registerItemRenderer(Luxcraft.luxSaber, new ItemRenderLuxSaber());
		MinecraftForgeClient.registerItemRenderer(Luxcraft.luxInfusedItem, new ItemRenderLuxInfused());
		ClientCommandHandler.instance.registerCommand(new CommandClientDebug());
	}
}