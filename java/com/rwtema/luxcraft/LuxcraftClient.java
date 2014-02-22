package com.rwtema.luxcraft;

import com.rwtema.luxcraft.render.RenderPrism;
import com.rwtema.luxcraft.render.RenderTileEntityLaser;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class LuxcraftClient extends LuxcraftProxy {
	public static int prismRenderingID = 0;

	@Override
	public void registerRenderInformation() {
		prismRenderingID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderPrism());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLuxLaserClient.class, new RenderTileEntityLaser());
	}
}