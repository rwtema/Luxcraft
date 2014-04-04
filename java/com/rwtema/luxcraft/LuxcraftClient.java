package com.rwtema.luxcraft;

import com.rwtema.luxcraft.block_base.RenderBlockMultiBlock;
import com.rwtema.luxcraft.debug.CommandClientDebug;
import com.rwtema.luxcraft.render.*;
import com.rwtema.luxcraft.tiles.TileEntityEnderCrystal;
import com.rwtema.luxcraft.tiles.TileEntityLuxLaserClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.MinecraftForgeClient;

public class LuxcraftClient extends LuxcraftProxy {

    @Override
    public void registerRenderInformation() {
        LogHelper.info("Registering Rendering Info");
        prismRenderingID = RenderingRegistry.getNextAvailableRenderId();
        multiBoxRenderingID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderPrism());
        RenderingRegistry.registerBlockHandler(new RenderBlockMultiBlock());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLuxLaserClient.class, new RenderTileEntityLaser());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderCrystal.class, new RenderTileEntityEnderCrystal());

        MinecraftForgeClient.registerItemRenderer(Luxcraft.luxSaber, new ItemRenderLuxSaber());
        MinecraftForgeClient.registerItemRenderer(Luxcraft.luxInfusedItem, new ItemRenderLuxInfused());
        MinecraftForgeClient.registerItemRenderer(Luxcraft.itemCrystal, new RenderCrystal());

        ClientCommandHandler.instance.registerCommand(new CommandClientDebug());
    }
}