package com.rwtema.luxcraft;

import com.rwtema.luxcraft.block.*;
import com.rwtema.luxcraft.block.fluid.BlockLiquidMirror;
import com.rwtema.luxcraft.block.fluid.BlockStygianBlack;
import com.rwtema.luxcraft.block.fluid.BlockVoidGas;
import com.rwtema.luxcraft.containers.GuiHandler;
import com.rwtema.luxcraft.item.*;
import com.rwtema.luxcraft.textures.connectedtextures.BlockConnectedTexturesTest;
import com.rwtema.luxcraft.textures.connectedtextures.ConnectedTexturesHelper;
import com.rwtema.luxcraft.tiles.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

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
    public static BlockVoidGas voidGas;
    public static BlockEnderCrystal enderCrystal;
    public static BlockLiquidMirror liquidMirror;
    public static BlockStygian stygianBlock;

    public static ItemLuxGem luxGem;
    public static ItemLuxSaber luxSaber;
    public static ItemInfusedItems luxInfusedItem;
    public static ItemDecorative itemDecorative;
    public static ItemCrystal itemCrystal;

    public static boolean colorBlind;

    public static BlockLuxTorch luxTorch;
    public static BlockStygianBlack StygianBlack;


    @SidedProxy(clientSide = "com.rwtema.luxcraft.LuxcraftClient", serverSide = "com.rwtema.luxcraft.LuxcraftProxy")
    public static LuxcraftProxy proxy;


    public Item registerItem(Item item) {
        if ("item.null".equals(item.getUnlocalizedName()))
            throw (new RuntimeException(String.format("Item is missing a proper name: %s", item.getClass().getName())));

        LogHelper.info("Registering Item: \"%s\" as \"%s\"", item.getClass().getSimpleName(), item.getUnlocalizedName().substring("item.".length()));
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring("item.".length()).replaceAll("luxcraft:",""));
        return item;
    }

    public Block registerBlock(Block block) {
        return this.registerBlock(block, ItemBlock.class);
    }

    public Block registerBlock(Block block, Class<? extends ItemBlock> itemblock) {
        if ("tile.null".equals(block.getUnlocalizedName()))
            throw (new RuntimeException(String.format("Block is missing a proper name: %s", block.getClass().getName())));

        LogHelper.info("Registering Block: \"%s\" as \"%s\" with item \"%s\"", block.getClass().getSimpleName(), block.getUnlocalizedName().substring("tile.".length()), itemblock.getSimpleName());
        return GameRegistry.registerBlock(block, itemblock, block.getUnlocalizedName().substring("tile.".length()).replaceAll("luxcraft:", "") );
    }

    public Property setComment(Property p, String c) {
        p.comment = c;
        return p;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("It's alive... It's ALIVE!");




        LogHelper.info("Loading Config");
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        config.save();


        if (event.getSide().isClient()) {
            LogHelper.info("Loading Client-side Config");
            Configuration config_client = new Configuration(new File(event.getModConfigurationDirectory(), "luxcraft_client.cfg"));
            config_client.load();
            colorBlind = setComment(config_client.get("Client", "Color_Blind_Mode", false),"Color Blind Mode").getBoolean(false);
            config_client.save();
        }

        MinecraftForge.EVENT_BUS.register(new LuxEventHandler());

        registerBlock(luxReflector = new BlockLuxReflector());
        registerBlock(luxLaser = new BlockLuxLaser());
        registerBlock(luxGenerator = new BlockLuxGenerator(), ItemBlockMetadata.class);
        registerBlock(luxTorch = new BlockLuxTorch(), ItemBlockMetadata.class);
        registerBlock(luxDetector = new BlockLuxDetector(), ItemBlockMetadata.class);
        registerBlock(luxStorage = new BlockLuxStorage());
        registerBlock(luxInfuser = new BlockLuxInfuser());
        registerBlock(voidGas = new BlockVoidGas());
        registerBlock(StygianBlack = new BlockStygianBlack());
        registerBlock(enderCrystal = new BlockEnderCrystal());
        registerBlock(stygianBlock = new BlockStygian(), ItemBlockMetadata.class);
        registerBlock(liquidMirror = new BlockLiquidMirror());
        registerBlock(new BlockConnectedTexturesTest());

        registerItem(luxGem = new ItemLuxGem());
        registerItem(luxSaber = new ItemLuxSaber());
        registerItem(luxInfusedItem = new ItemInfusedItems());
        registerItem(itemDecorative = new ItemDecorative("luxcraft:unknown", "luxcraft:lasing_circuit", "luxcraft:luxInterface"));
        registerItem(itemCrystal = new ItemCrystal());

        registerTileEntity(TileEntityLuxGenerator.class);
        registerTileEntity(TileEntityLuxLaser.class);
        registerTileEntity(TileEntityLuxDetector.class);
        registerTileEntity(TileEntityLuxInserter.class);
        registerTileEntity(TileEntityLuxInfuser.class);
        registerTileEntity(TileEntityLuxStorage.class);
        registerTileEntity(TileEntityEnderCrystal.class);

        proxy.registerRenderInformation();
    }

    public void registerTileEntity(Class<? extends TileEntity> tile) {
        String name = tile.getSimpleName().replaceAll("TileEntity", "TE");

        LogHelper.info("Registering Tile Entity \"%s\" as \"%s\"", tile.getSimpleName(), name);
        GameRegistry.registerTileEntity(tile, name);
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        FurnaceStuff.addRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
}