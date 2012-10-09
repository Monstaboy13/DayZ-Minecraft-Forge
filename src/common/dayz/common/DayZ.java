package dayz.common;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dayz.common.blocks.BlockBarbedWire;
import dayz.common.blocks.BlockBase;
import dayz.common.blocks.BlockChestAll;
import dayz.common.blocks.BlockChestCommon;
import dayz.common.blocks.BlockChestRare;
import dayz.common.blocks.BlockFence;
import dayz.common.blocks.BlockNails;
import dayz.common.entities.EntityBandit;
import dayz.common.entities.EntityBullet;
import dayz.common.entities.EntityCrawler;
import dayz.common.entities.EntityGrenade;
import dayz.common.entities.EntityZombie;
import dayz.common.items.ItemAk74u;
import dayz.common.items.ItemAmmo;
import dayz.common.items.ItemBloodBag;
import dayz.common.items.ItemCamo;
import dayz.common.items.ItemDayzDrink;
import dayz.common.items.ItemDayzFood;
import dayz.common.items.ItemDayzHeal;
import dayz.common.items.ItemEmptyBottle;
import dayz.common.items.ItemFirestarter;
import dayz.common.items.ItemMakarov;
import dayz.common.items.ItemRemington;
import dayz.common.items.ItemWaterbottleDirty;
import dayz.common.items.ItemWaterbottleFull;
import dayz.common.items.ItemWhiskeybottleFull;
import dayz.common.items.WeaponMelee;
import dayz.common.world.BiomeGenForest;
import dayz.common.world.WorldTypeDayZ;

@NetworkMod(clientSideRequired = true, serverSideRequired = false, versionBounds = Util.VERSION)
@Mod(modid = Util.ID, name = Util.NAME, version = Util.VERSION)
public class DayZ
{	
    /****************************** 						Main 							******************************/

    public static Logger logger = Logger.getLogger("Minecraft");
	public static EnumArmorMaterial CAMO = EnumHelper.addArmorMaterial("camo", 29, new int[] {2, 6, 5, 2}, 9);
	public static WorldTypeDayZ DayZ_WorldType = new WorldTypeDayZ();
    public static final BiomeGenBase dayzforest = (new BiomeGenForest(25));
    public static boolean isUpToDate = true;
	
    @Instance(Util.ID)
	public static DayZ INSTANCE;
    
	@SidedProxy(clientSide = "dayz.common.ClientProxy", serverSide = "dayz.common.CommonProxy")
	public static CommonProxy proxy; //This object will be populated with the class that you choose for the environment
   
    
	/****************************** 						Config 							******************************/
	
	public static int barbedwireID;
	public static int dayzchestallID;
	public static int dayzchestrareID;
	public static int dayzchestcommonID;
	public static int chainlinkfenceID;
	public static int sandbagblockID;
	public static int dayzchestallnospawnID;
	public static int dayzchestrarenospawnID;
	public static int dayzchestcommonnospawnID;
	public static int nailsID;
	public static boolean showDebug;
	public static boolean showName;
	public static boolean showCoords;
	public static boolean checkUpdate;
	
    /****************************** 						Items 							******************************/
    
    public static final Item cannedspag = new ItemDayzFood(3000, 4, 1, false).setIconCoord(2, 0).setItemName("cannedspag").setCreativeTab(CreativeTabs.tabFood);
    public static final Item cannedbeans = new ItemDayzFood(3001, 4, 1, false).setIconCoord(0, 0).setItemName("cannedbeans").setCreativeTab(CreativeTabs.tabFood);
    public static final Item cannedfish = new ItemDayzFood(3002, 4, 1, false).setIconCoord(1, 0).setItemName("cannedfish").setCreativeTab(CreativeTabs.tabFood);
    public static final Item waterbottlefull = new ItemWaterbottleFull(3003, 8, 1.0F).setIconCoord(5, 0).setItemName("waterbottlefull").setCreativeTab(CreativeTabs.tabFood);
    public static final Item waterbottleempty = new ItemEmptyBottle(3004, Block.waterMoving.blockID, true).setIconCoord(8, 0).setItemName("waterbottleempty").setCreativeTab(CreativeTabs.tabFood);
    public static final Item whiskeybottleempty = new ItemEmptyBottle(3005, Block.waterMoving.blockID, false).setIconCoord(6, 0).setItemName("whiskeybottleempty").setCreativeTab(CreativeTabs.tabFood);
    public static final Item whiskeybottlefull = new ItemWhiskeybottleFull(3006, 4, 1.0F).setIconCoord(7, 0).setItemName("whiskeybottlefull").setCreativeTab(CreativeTabs.tabFood);
    public static final Item bandage = new ItemDayzHeal(3007, 10, false).setIconCoord(1, 0).setItemName("bandage").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item antibiotics = new ItemDayzHeal(3008, 0, true).setIconCoord(0, 0).setItemName("antibiotics").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item lemonade = new ItemDayzDrink(3009, 4, 1.0F).setIconCoord(4, 0).setItemName("lemonade").setCreativeTab(CreativeTabs.tabFood);
    public static final Item ak74u = new ItemAk74u(3010).setIconCoord(0, 0).setItemName("ak74u").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item ak74uammo = new ItemAmmo(3011).setIconCoord(1, 0).setItemName("ak74uammo").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item makarov = new ItemMakarov(3012).setIconCoord(2, 0).setItemName("markov").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item makarovammo = new ItemAmmo(3013).setIconCoord(3, 0).setItemName("markovammo").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item remington = new ItemRemington(3014).setIconCoord(4, 0).setItemName("remington").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item remingtonammo = new ItemAmmo(3015).setIconCoord(5, 0).setItemName("remingtonammo").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item chocolate = new ItemDayzFood(3016, 4, 0.5F, false).setIconCoord(3, 0).setItemName("chocolate").setCreativeTab(CreativeTabs.tabFood);
    //3017 is missing or something?    
    public static final Item camohelmet = (new ItemCamo(3018, CAMO, 5, 0)).setItemName("camohelmet").setIconCoord(0, 0);
    public static final Item camochest = (new ItemCamo(3019, CAMO, 5, 1)).setItemName("camochest").setIconCoord(1, 0);
    public static final Item camolegs = (new ItemCamo(3020, CAMO, 5, 2)).setItemName("camolegs").setIconCoord(2, 0);
    public static final Item camoboots = (new ItemCamo(3021, CAMO, 5, 3)).setItemName("camoboots").setIconCoord(3, 0);
    public static final Item baseballbat = (new WeaponMelee(3022, EnumToolMaterial.WOOD, 6)).setItemName("baseballbat").setIconCoord(0, 1).setCreativeTab(CreativeTabs.tabCombat);
    public static final Item baseballbatnailed = (new WeaponMelee(3023, EnumToolMaterial.WOOD, 8)).setItemName("baseballbatnailed").setIconCoord(1, 1).setCreativeTab(CreativeTabs.tabCombat);
    public static final Item plank = (new WeaponMelee(3024, EnumToolMaterial.WOOD, 7)).setItemName("plank").setIconCoord(2, 1).setCreativeTab(CreativeTabs.tabCombat);
    public static final Item planknailed = (new WeaponMelee(3025, EnumToolMaterial.WOOD, 8)).setItemName("planknailed").setIconCoord(3, 1).setCreativeTab(CreativeTabs.tabCombat);
    public static final Item pipe = (new WeaponMelee(3026, EnumToolMaterial.WOOD, 8)).setItemName("pipe").setIconCoord(4, 1).setCreativeTab(CreativeTabs.tabCombat);
    public static final Item bloodbag = new ItemBloodBag(3027).setIconCoord(2, 0).setItemName("bloodbag").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item waterbottledirty = new ItemWaterbottleDirty(3028, 0, 2, false).setIconCoord(9, 0).setItemName("waterbottledirty").setCreativeTab(CreativeTabs.tabFood);
    public static final Item matches = (new ItemFirestarter(3029, 8)).setIconCoord(2, 0).setItemName("matches");
     
    /****************************** 						Preload 							*******************************/

    @PreInit
    public void DayZpreload(FMLPreInitializationEvent event)
    {
    	DayZLog.configureLogging();
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			config.load();
			
			showDebug = Boolean.parseBoolean(config.getOrCreateBooleanProperty("Show Debug Screen", Configuration.CATEGORY_GENERAL, true).value);
			showName = Boolean.parseBoolean(config.getOrCreateBooleanProperty("Show Name on Debug Screen", Configuration.CATEGORY_GENERAL, true).value);
			showCoords = Boolean.parseBoolean(config.getOrCreateBooleanProperty("Show Coords on Debug Screen", Configuration.CATEGORY_GENERAL, true).value);
			checkUpdate = Boolean.parseBoolean(config.getOrCreateBooleanProperty("Check for update", Configuration.CATEGORY_GENERAL, true).value);
			
			barbedwireID = config.getOrCreateBlockIdProperty("barbedwireID", 160).getInt();
			dayzchestallID = config.getOrCreateBlockIdProperty("dayzchestallID", 161).getInt();
			dayzchestrareID = config.getOrCreateBlockIdProperty("dayzchestrareID", 162).getInt();
			dayzchestcommonID = config.getOrCreateBlockIdProperty("dayzchestcommonID", 163).getInt();
			chainlinkfenceID = config.getOrCreateBlockIdProperty("chainlinkfenceID", 164).getInt();
			sandbagblockID = config.getOrCreateBlockIdProperty("sandbagblockID", 165).getInt();
			dayzchestallnospawnID = config.getOrCreateBlockIdProperty("dayzchestallnospawnID", 166).getInt();
			dayzchestrarenospawnID = config.getOrCreateBlockIdProperty("dayzchestrarenospawnID", 167).getInt();
			dayzchestcommonnospawnID = config.getOrCreateBlockIdProperty("dayzchestcommonnospawnID", 168).getInt();
			nailsID = config.getOrCreateBlockIdProperty("nailsID", 170).getInt();
		}
		catch (final Exception e) 
		{
			DayZLog.log(Level.SEVERE, e, "DayZ had a problem loading it's configuration.");
		} 
		finally 
		{
			config.save();
			DayZLog.info("Config Loaded");
		}
		
		if (checkUpdate == true)
		{
			if (Updater.isUpdated() == false)
			{
		        DayZLog.info("Day Z is not up to date. The latest release is " + Updater.getWebVersion() + ". You have " + Util.VERSION);
		        isUpToDate = false;
			}
		}
		
    	MinecraftForge.EVENT_BUS.register(new Util());
    }

    /****************************** 						Load 							******************************/

    @Init
    public void DayZload(FMLInitializationEvent event)
    {
    	    	
    /************* 						Blocks 							*************/
    	
        barbedwire = new BlockBarbedWire(barbedwireID, 0).setBlockName("barbedwire").setHardness(3F).setResistance(2F).setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestall = new BlockChestAll(dayzchestallID, true).setBlockName("dayzchestall").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestrare = new BlockChestRare(dayzchestrareID, true).setBlockName("dayzchestrare").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestcommon = new BlockChestCommon(dayzchestcommonID, true).setBlockName("dayzchestcommon").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        chainlinkfence = (new BlockFence(chainlinkfenceID, 1, 1, Material.iron, false)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setBlockName("chainlinkfence").setCreativeTab(CreativeTabs.tabDecorations);
        sandbagblock = (new BlockBase(sandbagblockID, 2, Material.clay)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundGrassFootstep).setBlockName("sandbagblock").setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestallnospawn = new BlockChestAll(dayzchestallnospawnID, false).setBlockName("dayzchestallnospawn").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestrarenospawn = new BlockChestRare(dayzchestrarenospawnID, false).setBlockName("dayzchestrarenospawn").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestcommonnospawn = new BlockChestCommon(dayzchestcommonnospawnID, false).setBlockName("dayzchestcommonnospawn").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        nails = new BlockNails(nailsID, 3, Material.leaves).setBlockName("nails").setHardness(1F).setResistance(1F).setCreativeTab(CreativeTabs.tabDecorations);
        
    	GameRegistry.registerBlock(barbedwire);
    	GameRegistry.registerBlock(dayzchestall);
    	GameRegistry.registerBlock(dayzchestrare);
    	GameRegistry.registerBlock(dayzchestcommon);
    	GameRegistry.registerBlock(chainlinkfence);
    	GameRegistry.registerBlock(sandbagblock);
    	GameRegistry.registerBlock(dayzchestallnospawn);
    	GameRegistry.registerBlock(dayzchestrarenospawn);
    	GameRegistry.registerBlock(dayzchestcommonnospawn);
    	GameRegistry.registerBlock(nails);
   
    /************* 						Entities 							*************/
        
        EntityRegistry.registerGlobalEntityID(EntityZombie.class, "Zombie", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);        
        EntityRegistry.registerGlobalEntityID(EntityBandit.class, "Bandit", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);        
        EntityRegistry.registerGlobalEntityID(EntityCrawler.class, "Crawler", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);        
        
        EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, this, 250, 5, true);
        EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 1, this, 250, 5, true);
        
    /************* 						Names 							*************/
        
        LanguageRegistry.instance().addStringLocalization("entity.Crawler.name", "en_US", "Crawler");
        LanguageRegistry.instance().addStringLocalization("entity.DayZZombie.name", "en_US", "Zombie");
        LanguageRegistry.instance().addStringLocalization("entity.Bandit.name", "en_US", "Bandit");
        LanguageRegistry.instance().addStringLocalization("generator.DAYZ", "en_US", "Day Z");
        
        LanguageRegistry.addName(DayZ.cannedspag, "Can of Spagetti");
        LanguageRegistry.addName(DayZ.cannedbeans, "Can of Beans");
        LanguageRegistry.addName(DayZ.cannedfish, "Can of Sardines");
        LanguageRegistry.addName(DayZ.waterbottlefull, "Full Waterbottle");
        LanguageRegistry.addName(DayZ.waterbottleempty, "Empty Waterbottle");
        LanguageRegistry.addName(DayZ.whiskeybottlefull, "Full Whiskey Bottle");
        LanguageRegistry.addName(DayZ.whiskeybottleempty, "Empty Whiskey Bottle");
        LanguageRegistry.addName(DayZ.bandage, "Bandage");
        LanguageRegistry.addName(DayZ.antibiotics, "Anti-biotics");
        LanguageRegistry.addName(DayZ.lemonade, "Can of Lemonade");
        LanguageRegistry.addName(DayZ.makarovammo, "Makarov Magazine");
        LanguageRegistry.addName(DayZ.makarov, "Makarov");
        LanguageRegistry.addName(DayZ.ak74uammo, "AK74u Magazine");
        LanguageRegistry.addName(DayZ.ak74u, "AK74u");
        LanguageRegistry.addName(DayZ.remingtonammo, "12g Slugs");
        LanguageRegistry.addName(DayZ.remington, "Remington Shotgun");
        LanguageRegistry.addName(DayZ.chocolate, "Chocolate Bar");
        LanguageRegistry.addName(DayZ.camohelmet, "Camo Helmet");
        LanguageRegistry.addName(DayZ.camochest, "Camo Chestplate");
        LanguageRegistry.addName(DayZ.camolegs, "Camo Pants");
        LanguageRegistry.addName(DayZ.camoboots, "Camo Boots");
        LanguageRegistry.addName(DayZ.barbedwire, "Barbed Wire");
        LanguageRegistry.addName(DayZ.sandbagblock, "Sandbag Block");
        LanguageRegistry.addName(DayZ.chainlinkfence, "Chain-link Fence");
        LanguageRegistry.addName(DayZ.dayzchestall, "Day Z Chest with Spawner");
        LanguageRegistry.addName(DayZ.dayzchestrare, "Day Z Rare Chest with Spawner");
        LanguageRegistry.addName(DayZ.dayzchestcommon, "Day Z Common Chest with Spawner");
        LanguageRegistry.addName(DayZ.dayzchestallnospawn, "Day Z Chest");
        LanguageRegistry.addName(DayZ.dayzchestrarenospawn, "Day Z Rare Chest");
        LanguageRegistry.addName(DayZ.dayzchestcommonnospawn, "Day Z Common Chest");
        LanguageRegistry.addName(DayZ.baseballbat, "Baseball Bat");
        LanguageRegistry.addName(DayZ.baseballbatnailed, "Nailed Baseball Bat");
        LanguageRegistry.addName(DayZ.plank, "Plank");
        LanguageRegistry.addName(DayZ.planknailed, "Nailed Plank");
        LanguageRegistry.addName(DayZ.pipe, "Pipe");
        LanguageRegistry.addName(DayZ.waterbottledirty, "Dirty Water Bottle");
        LanguageRegistry.addName(DayZ.bloodbag, "Blood Bag");
        LanguageRegistry.addName(DayZ.matches, "Book of Matches");
        LanguageRegistry.addName(DayZ.nails, "Nails"); 
        
    /************* 						Recipes 							*************/
        
        GameRegistry.addShapelessRecipe(new ItemStack(baseballbatnailed, 1), new Object[] 
        {
        	new ItemStack(baseballbat, 1),
        	new ItemStack(nails, 1, 0)
        });
        
        GameRegistry.addShapelessRecipe(new ItemStack(planknailed, 1), new Object[] 
        {
        	new ItemStack(plank, 1),
        	new ItemStack(nails, 1, 0)
        });
        
        GameRegistry.addRecipe(new ItemStack(nails, 1), new Object [] 
        {
        	"#", "#",
        	Character.valueOf('#'), Item.ingotIron
        });
        
        GameRegistry.addRecipe(new ItemStack(plank, 1), new Object [] 
        {
        	"#", "#", "#",
        	Character.valueOf('#'), Block.planks
        });
        
        GameRegistry.addRecipe(new ItemStack(baseballbat, 1), new Object [] 
        {
        	"#", "#", "!",
        	Character.valueOf('#'), Block.planks,
        	Character.valueOf('!'), Item.stick
        });
        
        GameRegistry.addSmelting(waterbottledirty.shiftedIndex, new ItemStack(waterbottlefull, 1), 0.2F);

    /************* 						Other 							*************/

        //Proxy
        proxy.DayZload();

        DayZLog.info("Day Z " + Util.VERSION + " Loaded");
    }
    
    /****************************** 						PostLoad 							******************************/

	@PostInit
	public void DayZloaded(FMLPostInitializationEvent event) 
	{
		boolean isClient = FMLCommonHandler.instance().getSide().isClient();
		boolean isServer = FMLCommonHandler.instance().getSide().isServer();

    	if (Loader.isModLoaded("ThirstMod"))
    	{
	        DayZLog.info("Thirst Mod Found!.");
    	}
    	
		if (isServer) 
		{
			if (Updater.isUpdated() == false && checkUpdate == true)
			{
				logger.info("Day Z is not up to date. The latest release is " + Updater.getWebVersion() + ". You have " + Util.VERSION);
			}
			else
			{
		    	logger.info("Day Z " + Util.VERSION + " Loaded.");
			}
	    	logger.info("Make sure your server.properties has the line: level-type=DAYZ to create a Day Z world.");
		}	
	}
	
    /****************************** 						Blocks						******************************/
	   
    public static Block barbedwire;
    public static Block dayzchestall;
    public static Block dayzchestrare;
    public static Block dayzchestcommon;
    public static Block chainlinkfence;
    public static Block sandbagblock;
    public static Block dayzchestallnospawn;
    public static Block dayzchestrarenospawn;
    public static Block dayzchestcommonnospawn;
    public static Block nails;

}