package com.rwtema.luxcraft;

import net.minecraftforge.common.MinecraftForge;

import com.rwtema.luxcraft.block.BlockLuxDetector;
import com.rwtema.luxcraft.block.BlockLuxGenerator;
import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.block.BlockLuxReflector;
import com.rwtema.luxcraft.block.BlockLuxStorage;
import com.rwtema.luxcraft.containers.ContainerLuxContainer;
import com.rwtema.luxcraft.containers.GuiHandler;
import com.rwtema.luxcraft.tiles.TileEntityLuxDetector;
import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import com.rwtema.luxcraft.tiles.TileEntityLuxInserter;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaser;
import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "luxcraft", name = "Luxcraft", version = "1.0")
public class Luxcraft {
	@Instance("luxcraft")
	public static Luxcraft instance;

	public static BlockLuxGenerator luxGenerator;
	public static BlockLuxReflector luxReflector;
	public static BlockLuxLaser luxLaser;
	public static BlockLuxDetector luxDetector;
	public static BlockLuxStorage luxStorage;

	@SidedProxy(clientSide = "com.rwtema.luxcraft.LuxcraftClient", serverSide = "com.rwtema.luxcraft.LuxcraftProxy")
	public static LuxcraftProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new LuxEventHandler());

		GameRegistry.registerBlock(luxReflector = new BlockLuxReflector(), "luxcraft:luxReflector");
		GameRegistry.registerBlock(luxLaser = new BlockLuxLaser(), "luxcraft:luxLaser");
		GameRegistry.registerBlock(luxGenerator = new BlockLuxGenerator(), ItemBlockMetadata.class, "luxcraft:luxGenerator");
		GameRegistry.registerBlock(luxDetector = new BlockLuxDetector(), ItemBlockMetadata.class, "luxcraft:luxDetector");
		GameRegistry.registerBlock(luxStorage = new BlockLuxStorage(), "luxcraft:luxStorage");

		GameRegistry.registerTileEntity(TileEntityLuxGenerator.class, "luxGenerator");
		GameRegistry.registerTileEntity(TileEntityLuxLaser.class, "luxLaser");
		GameRegistry.registerTileEntity(TileEntityLuxDetector.class, "luxDetector");
		GameRegistry.registerTileEntity(TileEntityLuxInserter.class, "luxInsertor");
		GameRegistry.registerTileEntity(TileEntityLuxStorage.class, "luxStorage");

		// NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		proxy.registerRenderInformation();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
}