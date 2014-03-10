package com.rwtema.luxcraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import com.rwtema.luxcraft.block.BlockLuxDetector;
import com.rwtema.luxcraft.block.BlockLuxGenerator;
import com.rwtema.luxcraft.block.BlockLuxInfuser;
import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.block.BlockLuxReflector;
import com.rwtema.luxcraft.block.BlockLuxStorage;
import com.rwtema.luxcraft.containers.GuiHandler;
import com.rwtema.luxcraft.debug.CommandClientDebug;
import com.rwtema.luxcraft.item.ItemBlockMetadata;
import com.rwtema.luxcraft.item.ItemInfusedItems;
import com.rwtema.luxcraft.item.ItemLuxGem;
import com.rwtema.luxcraft.item.ItemLuxSaber;
import com.rwtema.luxcraft.tiles.TileEntityLuxDetector;
import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import com.rwtema.luxcraft.tiles.TileEntityLuxInfuser;
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
	public static BlockLuxInfuser luxInfuser;

	public static ItemLuxGem luxGem;
	public static ItemLuxSaber luxSaber;
	public static ItemInfusedItems luxInfusedItem;

	@SidedProxy(clientSide = "com.rwtema.luxcraft.LuxcraftClient", serverSide = "com.rwtema.luxcraft.LuxcraftProxy")
	public static LuxcraftProxy proxy;

	public Item registerItem(Item item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring("item.".length()));
		return item;
	}

	public Block registerBlock(Block block) {
		return this.registerBlock(block, ItemBlock.class);
	}

	public Block registerBlock(Block block, Class<? extends ItemBlock> itemblock) {
		return GameRegistry.registerBlock(block, itemblock, block.getUnlocalizedName().substring("tile.".length()));
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new LuxEventHandler());

		registerBlock(luxReflector = new BlockLuxReflector());
		registerBlock(luxLaser = new BlockLuxLaser());
		registerBlock(luxGenerator = new BlockLuxGenerator(), ItemBlockMetadata.class);
		registerBlock(luxDetector = new BlockLuxDetector(), ItemBlockMetadata.class);
		registerBlock(luxStorage = new BlockLuxStorage());
		registerBlock(luxInfuser = new BlockLuxInfuser());

		registerItem(luxGem = new ItemLuxGem());
		registerItem(luxSaber = new ItemLuxSaber());
		registerItem(luxInfusedItem = new ItemInfusedItems());

		GameRegistry.registerTileEntity(TileEntityLuxGenerator.class, "luxcraft:luxGenerator");
		GameRegistry.registerTileEntity(TileEntityLuxLaser.class, "luxcraft:luxLaser");
		GameRegistry.registerTileEntity(TileEntityLuxDetector.class, "luxcraft:luxDetector");
		GameRegistry.registerTileEntity(TileEntityLuxInserter.class, "luxcraft:luxInsertor");
		GameRegistry.registerTileEntity(TileEntityLuxInfuser.class, "luxcraft:luxInfuser");
		GameRegistry.registerTileEntity(TileEntityLuxStorage.class, "luxcraft:luxStorage");

		proxy.registerRenderInformation();
		
		
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
}