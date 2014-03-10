package com.rwtema.luxcraft;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.MinecraftForgeClient;

import com.rwtema.luxcraft.debug.CommandClientDebug;
import com.rwtema.luxcraft.render.ItemRenderLuxInfused;
import com.rwtema.luxcraft.render.ItemRenderLuxSaber;
import com.rwtema.luxcraft.render.RenderPrism;
import com.rwtema.luxcraft.render.RenderTileEntityLaser;
import com.rwtema.luxcraft.render.TickRenderer;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class LuxcraftClient extends LuxcraftProxy {
	public static int prismRenderingID = 0;

	@Override
	public void registerRenderInformation() {
		prismRenderingID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RenderPrism());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLuxLaserClient.class, new RenderTileEntityLaser());
		MinecraftForgeClient.registerItemRenderer(Luxcraft.luxSaber, new ItemRenderLuxSaber());
		MinecraftForgeClient.registerItemRenderer(Luxcraft.luxInfusedItem, new ItemRenderLuxInfused());
		ClientCommandHandler.instance.registerCommand(new CommandClientDebug());
	}
}