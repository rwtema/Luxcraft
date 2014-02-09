package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = "LuxCraft", name = "Luxcraft", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class, channels = {"TileEntityNBT"})


public class Luxcraft {
	@Instance("LuxCraft")
	public static Luxcraft instance;

	public static Block luxGenerator;public static int luxGeneratorId;
	public static Block luxLaser;public static int luxLaserId; 
	public static Block luxLaser2;public static int luxLaser2Id;
	public static Block luxStorage;public static int luxStorageId;
	public static Block luxInsertor;public static int luxInsertorId;
	public static Block prism;public static int prismId;
	public static Block reflector;public static int reflectorId;
	public static Block ritualTable;public static int ritualTableId;
	public static Block synthesizer;public static int synthesizerId;
	public static Block laserDetector;public static int laserDetectorId;
	public static Block cobblestoneCompr;public static int cobblestoneComprId;
	public static Block tunnelingStone;public static int tunnelingStoneId;
	public static Block darkBlock;public static int darkBlockId;
	public static Block deconstructor;public static int deconstructorId;
	public static Block quakeNuke;public static int quakeNukeId;
	public static Block itemLaser;public static int itemLaserId;
	public static Block chandelier;public static int chandelierId;
	public static Block intangibleLight;public static int intangibleLightId;
	public static Block ant;public static int antId;


	public static Block budBlock;public static int budBlockID;        
	public static Block grinderBlock;public static int grinderBlockID;        
	public static Block conveyorBlock;public static int conveyorBlockID;
	public static Block curtainBlock;public static int curtainBlockID;        
	public static Block spikeBlock;public static int spikeBlockID;        
	public static Block invChest;public static int invChestID;
	public static Block cursedEarthBlock;public static int cursedEarthBlockID;




	public static Item extendWand;public static int extendWandID;	    


	public static Item schema;public static int schemaId;
	public static Item antiSword;public static int antiSwordId;
	public static Item deconsRecipe;public static int deconsRecipeId;
	public static Item itemLuxGem;public static int itemLuxGemId;
	public static Item itemWrangler;public static int itemWranglerId;
	public static Item itemCrate;public static int itemCrateId;
	public static Item antiPickaxe;public static int antiPickaxeId;

	protected static float explosion_width;

	@SidedProxy(clientSide = "luxcraft.LuxcraftClient", serverSide = "luxcraft.LuxcraftProxy")
	public static LuxcraftProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		luxGeneratorId = config.getBlock("luxGeneratorId", 3717).getInt();
		luxLaserId = config.getBlock("luxLaserId", 3718).getInt();
		luxLaser2Id = config.getBlock("luxLaser2Id", 3728).getInt();
		luxStorageId = config.getBlock("luxStorageId", 3719).getInt();
		prismId = config.getBlock("prismId", 3720).getInt();
		luxInsertorId = config.getBlock("luxInsertorId", 3721).getInt();
		reflectorId = config.getBlock("reflectorId", 3722).getInt();
		ritualTableId = config.getBlock("ritualTableId", 3723).getInt();
		synthesizerId = config.getBlock("synthesizerId", 3724).getInt();
		laserDetectorId = config.getBlock("laserDetectorId", 3725).getInt();
		cobblestoneComprId = config.getBlock("cobblestoneComprId", 3726).getInt();
		tunnelingStoneId = config.getBlock("tunnelingStoneId", 3728).getInt();
		deconstructorId = config.getBlock("deconstructorId", 3729).getInt();
		darkBlockId = config.getBlock("darkBlockId", 3730).getInt();
		quakeNukeId = config.getBlock("quakeNukeId", 3731).getInt();
		itemLaserId = config.getBlock("itemLaserId", 3732).getInt();
		intangibleLightId = config.getBlock("intangibleLightId", 3733).getInt();
		chandelierId = config.getBlock("chandelierId", 3734).getInt();
		antId = config.getBlock("antId", 3735).getInt();

		schemaId = config.getItem("schemaId", 12000).getInt();
		antiSwordId = config.getItem("antiSwordId", 12001).getInt();
		deconsRecipeId = config.getItem("deconsRecipeId", 12002).getInt();
		itemLuxGemId = config.getItem("itemLuxGemId", 12003).getInt();
		itemWranglerId = config.getItem("itemWranglerId", 12004).getInt();
		itemCrateId = config.getItem("itemCrateId", 12005).getInt();
		antiPickaxeId = config.getItem("antiPickaxeId", 12006).getInt();


		conveyorBlockID = config.getBlock("conveyorBlockID", 3736).getInt();
		curtainBlockID = config.getBlock("curtainBlockID", 3737).getInt();
		grinderBlockID = config.getBlock("grinderBlockID", 3738).getInt();
		budBlockID = config.getBlock("budBlockID", 3739).getInt();
		invChestID = config.getBlock("invBlockID", 3740).getInt();
		spikeBlockID = config.getBlock("spikeBlockID", 3741).getInt();
		cursedEarthBlockID = config.getBlock("cursedEarthBlockID", 3742).getInt();

		extendWandID = config.getItem("extendWandID", 12006).getInt();		


		explosion_width = (float)config.get("values", "quake_size", 32.0D).getDouble(64);

	}

	@Init
	public void load(FMLInitializationEvent event)
	{
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		luxGenerator = new BlockLuxGenerator(luxGeneratorId, Material.rock).setBlockName("luxGenerator");
		//GameRegistry.registerBlock(luxGenerator, ItemLuxGenerator.class);
		GameRegistry.registerBlock(luxGenerator, ItemMultItems.class);
		LanguageRegistry.addName(luxGenerator, "Lux Generator");
		for(int c = 0;c<7;c+=1)
			LanguageRegistry.instance().addStringLocalization("tile.luxGenerator."+c+".name", LuxHelper.color_name[c]+" Lux Generator");


		luxLaser = new BlockLuxLaser(luxLaserId, 0).setBlockName("luxLazer");
		GameRegistry.registerBlock(luxLaser);
		LanguageRegistry.addName(luxLaser, "Lux Lazer");

		luxLaser2 = new BlockLuxLaser(luxLaser2Id, 1).setBlockName("luxLazer2");
		GameRegistry.registerBlock(luxLaser2);
		LanguageRegistry.addName(luxLaser2, "Upgraded Lux Lazer");		


		luxStorage = new BlockLuxStorage(luxStorageId).setBlockName("luxStorage");
		GameRegistry.registerBlock(luxStorage);
		LanguageRegistry.addName(luxStorage, "Lux Storage");
		///		LanguageRegistry.instance().addStringLocalization("tile.luxStorage.name", "Storage");

		prism = new BlockPrism(prismId).setBlockName("Prism");
		GameRegistry.registerBlock(prism);
		LanguageRegistry.addName(prism, "Prism");

		luxInsertor = new BlockLuxInsertor(luxInsertorId).setBlockName("luxInsertor");
		GameRegistry.registerBlock(luxInsertor);
		LanguageRegistry.addName(luxInsertor, "Lux Insertor");

		reflector = new BlockReflector(reflectorId).setBlockName("mirror");
		GameRegistry.registerBlock(reflector);
		LanguageRegistry.addName(reflector, "Reflector");

		ritualTable = new BlockLuxRitualTable(ritualTableId).setBlockName("ritualTable");
		GameRegistry.registerBlock(ritualTable);
		LanguageRegistry.addName(ritualTable, "Ritual Table");

		synthesizer = new BlockLuxSynthesizer(synthesizerId,0).setBlockName("luxAssembler");
		GameRegistry.registerBlock(synthesizer, ItemMultItems.class);
		LanguageRegistry.instance().addStringLocalization("tile.luxAssembler.0.name", "Lux Assembler");
		LanguageRegistry.instance().addStringLocalization("tile.luxAssembler.1.name", "Lux Syntesizer");
		LanguageRegistry.instance().addStringLocalization("tile.luxAssembler.2.name", "Lux General Assembler");

		laserDetector = new BlockLaserDetector(laserDetectorId).setBlockName("laserDetector");
		GameRegistry.registerBlock(laserDetector, ItemMultItems.class);
		LanguageRegistry.instance().addStringLocalization("tile.laserDetector.0.name", "Lazer Detector");
		LanguageRegistry.instance().addStringLocalization("tile.laserDetector.1.name", "Lazer Detector (Inverted)");

		cobblestoneCompr = (new BlockCobblestoneCompressed(cobblestoneComprId, Material.rock)).setTextureFile("/luxcraft/terrain.png").setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setBlockName("comprCobblestone").setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(cobblestoneCompr, ItemMultItems.class);
		LanguageRegistry.instance().addStringLocalization("tile.comprCobblestone.0.name", "Compressed Cobblestone");
		String[] tuple_names = {"Double", "Triple", "Quadruple", "Quintuple", "Sextuple", "Septuple", "Octuple", "Nonuple", "Decuple", "Undecuple",  "Duodecuple", "Tredecuple", "Quattordecuple", "Quindecuple", "Sedecuple"};
		for(int i = 1; i <= BlockCobblestoneCompressed.maxIterations;i++)
			//LanguageRegistry.instance().addStringLocalization("tile.comprCobblestone."+i+".name", (1+i)+"x Compressed Cobblestone");
			LanguageRegistry.instance().addStringLocalization("tile.comprCobblestone."+i+".name", tuple_names[i-1]+" Compressed Cobblestone");

		tunnelingStone = (new BlockTunnelingStone(tunnelingStoneId)).setHardness(30.0F).setBlockName("tunnelingStone").setStepSound(Block.soundStoneFootstep);
		GameRegistry.registerBlock(tunnelingStone);
		LanguageRegistry.addName(tunnelingStone, "Excavating Stone");

		darkBlock = (new Block(darkBlockId, 44, Material.rock)).setTextureFile("/luxcraft/terrain.png").setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setBlockName("darkBlock").setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(darkBlock);
		LanguageRegistry.addName(darkBlock, "Black Rock");

		deconstructor = (new BlockLuxDeconstructor(deconstructorId)).setBlockName("luxDeconstructor");
		GameRegistry.registerBlock(deconstructor);
		LanguageRegistry.addName(deconstructor, "Deconstructor");

		quakeNuke = (new BlockSubsidanceNuke(quakeNukeId, 8)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("quakeNuke");
		GameRegistry.registerBlock(quakeNuke);
		LanguageRegistry.addName(quakeNuke, "Subsidance Nuke");

		itemLaser = new BlockItemLaser(itemLaserId).setBlockName("itemLaser");
		GameRegistry.registerBlock(itemLaser);
		LanguageRegistry.addName(itemLaser, "Item Lazer");


		intangibleLight = (new BlockIntangibleLight(intangibleLightId)).setBlockName("intangLight");
		GameRegistry.registerBlock(intangibleLight,"Intangible Light");

		chandelier = (new BlockChandelier(chandelierId)).setBlockName("chandelier");
		GameRegistry.registerBlock(chandelier,"chandelier");
		LanguageRegistry.addName(chandelier, "Chandelier");


		ant = (new BlockAnt(antId)).setBlockName("ant");
		GameRegistry.registerBlock(ant,"ant");
		LanguageRegistry.addName(ant, "Ant");


		//Items
		antiSword = new ItemAntiSword(antiSwordId);
		LanguageRegistry.addName(antiSword, "Anti-Sword");

		schema = new ItemLuxSchema(schemaId);
		LanguageRegistry.addName(schema, "Schema");

		deconsRecipe = new ItemLuxDeconsRecipe(deconsRecipeId);
		LanguageRegistry.addName(deconsRecipe , "Deconstructor");

		itemLuxGem = new ItemLuxGem(itemLuxGemId).setItemName("luxGem");
		LanguageRegistry.instance().addStringLocalization("item.luxGem.7.name", "Empty Lux Gem");
		for(int i = 0; i < 7;i++)
			LanguageRegistry.instance().addStringLocalization("item.luxGem."+i+".name", LuxHelper.color_name[i] + " Lux Gem");		


		itemWrangler = (new ItemWrangler(itemWranglerId)).setItemName("itemWrangler");
		LanguageRegistry.addName(itemWrangler, "Wrangler");
		
		antiPickaxe = (new ItemAntiPickaxe(antiPickaxeId)).setItemName("antiPickaxe");		

		itemCrate = (new ItemCrate(itemCrateId)).setItemName("itemCrate");
		LanguageRegistry.addName(itemCrate, "Crate");


		extendWand = (new ItemExtensionWand(extendWandID)).setItemName("extendWand");

		budBlock = new BlockBUD(budBlockID).setBlockName("budBlock").setHardness(3.0F).setStepSound(Block.soundStoneFootstep).setRequiresSelfNotify();        
		grinderBlock = new BlockGrinder(grinderBlockID, 22).setBlockName("grinderBlock").setResistance(1.0F).setStepSound(Block.soundStoneFootstep).setHardness(3F);        
		conveyorBlock = new BlockConveyor(conveyorBlockID, 22).setBlockName("Conveyor").setHardness(1F);
		curtainBlock = new BlockCurtain(curtainBlockID, 113).setBlockName("Curtain").setHardness(1F);        
		spikeBlock = new BlockSpike(spikeBlockID, 22).setBlockName("spikeBlock").setResistance(1.0F).setStepSound(Block.soundStoneFootstep).setHardness(3F);
		invChest = (new BlockLinkingChest(invChestID)).setHardness(22.5F).setResistance(1000.0F).setStepSound(Block.soundStoneFootstep).setBlockName("enderChest").setRequiresSelfNotify().setLightValue(0.5F);
		 cursedEarthBlock = new BlockCursedGround(cursedEarthBlockID).setBlockName("cursedEarthBlock").setHardness(0.6F).setStepSound(Block.soundGrassFootstep);

		GameRegistry.registerBlock(spikeBlock,"spikeBlock");
		GameRegistry.registerBlock(conveyorBlock,"Conveyor");
		GameRegistry.registerBlock(curtainBlock,"Curtain");
		GameRegistry.registerBlock(grinderBlock,"Grinder");
		GameRegistry.registerBlock(budBlock,"BUD");
		GameRegistry.registerBlock(invChest,"Inventory Link Chest");
        GameRegistry.registerBlock(cursedEarthBlock,"Cursed Earth");
        
		GameRegistry.addRecipe(new ItemStack(budBlock, 1), new Object[] {"RVC", "RCP", "CSC", 'C', Block.stone, 'P', Block.pistonStickyBase, 'S', Block.torchRedstoneActive, 'R', Item.redstone, 'V', Item.redstoneRepeater});

		GameRegistry.registerTileEntity(TileEntityGrinder.class, "grinder");
		GameRegistry.registerTileEntity(TileEntityLinkingChest.class, "invChest");

		//LanguageRegistry.addName(bluestoneWire, "Blue Stone Wire");
		LanguageRegistry.addName(spikeBlock, "Spike");
		LanguageRegistry.addName(conveyorBlock, "Conveyor Belt");
		LanguageRegistry.addName(curtainBlock, "Curtains");
		LanguageRegistry.addName(grinderBlock, "Grinder");
		LanguageRegistry.addName(budBlock, "BUD Block");
		LanguageRegistry.addName(invChest, "Inventory Chest");
		LanguageRegistry.addName(extendWand, "Extension Wand");
		LanguageRegistry.addName(antiPickaxe, "Anti-Pickaxe");
        LanguageRegistry.addName(cursedEarthBlock, "Cursed Earth");		

		CraftingManager.getInstance().getRecipeList().add(new RecipeSchema());

		//CraftingManager.getInstance().func_92051_a(new ItemStack(ritualTable, 1), new Object[] {"RRS", "SXS", "SRR", 'X', Block.workbench, 'R', Item.redstone, 'S', Item.silk});
		CraftingManager.getInstance().addRecipe(new ItemStack(ritualTable, 1), new Object[] {"RSR", "RXR", "SSS", 'X', Block.workbench, 'R', Item.redstone, 'S', Item.silk});

		CraftingManager.getInstance().addRecipe(new ItemStack(cobblestoneCompr, 1, 0), new Object[] {"XXX", "XXX", "XXX", 'X', Block.cobblestone});
		CraftingManager.getInstance().addRecipe(new ItemStack(Block.cobblestone, 9), new Object[] {"X", 'X', new ItemStack(cobblestoneCompr, 1, 0)});




		for(int i=1;i<BlockCobblestoneCompressed.maxIterations;i++){
			CraftingManager.getInstance().addRecipe(new ItemStack(cobblestoneCompr, 1, i), new Object[] {"XXX", "XXX", "XXX", 'X', new ItemStack(cobblestoneCompr, 1, i-1)});
			CraftingManager.getInstance().addRecipe(new ItemStack(cobblestoneCompr, 9, i-1), new Object[] {"X", 'X', new ItemStack(cobblestoneCompr, 1, i)});
		}

		CraftingManager.getInstance().getRecipeList().add(new RecipeRitualTable());
		CraftingManager.getInstance().getRecipeList().add(new RecipeDeconsRecipe());



		GameRegistry.registerTileEntity(TileEntityLuxGenerator.class, "luxGenerator");
		GameRegistry.registerTileEntity(TileEntityLuxStorage.class, "luxStorage");
		GameRegistry.registerTileEntity(TileEntityLuxLaser.class, "luxLaser");
		GameRegistry.registerTileEntity(TileEntityLuxRitualTable.class, "luxRitualTable");
		GameRegistry.registerTileEntity(TileEntityLuxSynthesizer.class, "luxSynthesizer");
		GameRegistry.registerTileEntity(TileEntityLaserDetector.class, "laserDetector");
		GameRegistry.registerTileEntity(TileEntityLuxDeconstructor.class, "luxDeconstructor");
		GameRegistry.registerTileEntity(TileEntityItemLaser.class, "itemLaser");


		EntityRegistry.registerModEntity(EntitySubsidanceNuke.class, "subsidanceNuke", 1, this, 64, 10, true);
		EntityRegistry.registerGlobalEntityID(EntitySubsidanceNuke.class, "subsidanceNuke", EntityRegistry.findGlobalUniqueEntityId());

		RenderingRegistry.instance().registerEntityRenderingHandler(EntitySubsidanceNuke.class, new RenderEntitySubsidanceNuke());

		LuxLevels.instance.initLevels();
		LuxLevels.instance.addLuxSchemaRecipe(new ItemStack(Item.netherStar, 1), new Object[] {"XXX", "XOX", "XXX", 'X', new ItemStack(Item.diamond, 8), 'O', new ItemStack(Luxcraft.cobblestoneCompr, 1, 3)});
		LuxLevels.instance.addLuxSchemaRecipe(LuxLevels.instance.getEntityEgg("Creeper") , new Object[] {"XXX", "XOX", "XXX", 'X', new ItemStack(Item.gunpowder, 8), 'O', new ItemStack(Item.egg, 1)});
		LuxLevels.instance.addLuxSchemaRecipe(LuxLevels.instance.getEntityEgg("Zombie") , new Object[] {"XXX", "XOX", "XXX", 'X', new ItemStack(Item.rottenFlesh, 8), 'O', new ItemStack(Item.egg, 1)});

		proxy.registerRenderInformation();		

	}


}