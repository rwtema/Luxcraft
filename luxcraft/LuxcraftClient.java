package luxcraft;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class LuxcraftClient extends LuxcraftProxy
{
    public static int prismRenderingID = 0;
    public static int laserBlockRenderingID = 0;
    public static int spikeRendererID = 0;
    public static int conveyorRendererID = 0;
    public static int linkingChestID = 0;

    @Override
    public void registerRenderInformation()
    {
        prismRenderingID = RenderingRegistry.instance().getNextAvailableRenderId();
        laserBlockRenderingID = RenderingRegistry.instance().getNextAvailableRenderId();
        RenderingRegistry.instance().registerBlockHandler(new RenderPrism());
        RenderingRegistry.instance().registerBlockHandler(new RenderLaserBlock());
        spikeRendererID = RenderingRegistry.instance().getNextAvailableRenderId();
        conveyorRendererID = RenderingRegistry.instance().getNextAvailableRenderId();
        linkingChestID = RenderingRegistry.instance().getNextAvailableRenderId();
        RenderingRegistry.instance().registerBlockHandler(new RenderSpike());
        RenderingRegistry.instance().registerBlockHandler(new RenderConveyor());
        RenderingRegistry.instance().registerBlockHandler(new RenderLinkingChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrinder.class, new TileEntityGrinderRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLinkingChest.class, new TileEntityLinkingChestRenderer());     
        
        
//        RenderingRegistry.instance().registerBlockHandler(new renderConveyor());
//        RenderingRegistry.instance().registerBlockHandler(new RenderTilingBlock());
//        RenderingRegistry.instance().registerBlockHandler(new RenderTilingBlock2());
//        RenderingRegistry.instance().registerBlockHandler(new RenderEncasedTilingBlock());
//        RenderingRegistry.instance().registerBlockHandler(new RenderFlipBlock());
//        RenderingRegistry.instance().registerBlockHandler(new RenderLinkingChest());
//        RenderingRegistry.instance().registerEntityRenderingHandler(EntityBloodOrb.class, new RenderBloodOrb());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrinder.class, new TileEntityGrinderRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLinkingChest.class, new TileEntityLinkingChestRenderer());
        
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLuxLaser.class, new TileEntityLaserRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemLaser.class, new TileEntityLaserRenderer());
        MinecraftForgeClient.preloadTexture("/luxcraft/terrain.png");
    }
}