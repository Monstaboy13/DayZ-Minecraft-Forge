package dayz.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
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
import dayz.common.entities.EntityZombieDayZ;
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
    public static final BiomeGenBase dayzForest = (new BiomeGenForest(25));
    public static final BiomeGenBase dayzPlains = (new BiomeGenForest(26));
    public static final BiomeGenBase dayzRiver = (new BiomeGenForest(27));
	public static WorldTypeDayZ DayZ_WorldType = new WorldTypeDayZ();
    public static boolean isUpToDate = true;
	
    @Instance(Util.ID)
	public static DayZ INSTANCE;
    
	@SidedProxy(clientSide = "dayz.common.ClientProxy", serverSide = "dayz.common.CommonProxy")
	public static CommonProxy proxy;
   
    
	/****************************** 						Config 							******************************/
	
	public static int barbedwireID;
	public static int dayzchestallID;
	public static int dayzchestrareID;
	public static int dayzchestcommonID;
	public static int chainlinkfenceID;
	public static int sandbagblockID;
	public static int nailsID;
	public static boolean showDebug;
	public static boolean showName;
	public static boolean showCoords;
	public static boolean checkUpdate;
	public static int chanceToRegenChest;

	
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
			
			showDebug = Boolean.parseBoolean(config.get("Show Debug Screen", Configuration.CATEGORY_GENERAL, true).value);
			showName = Boolean.parseBoolean(config.get("Show Name on Debug Screen", Configuration.CATEGORY_GENERAL, true).value);
			showCoords = Boolean.parseBoolean(config.get("Show Coords on Debug Screen", Configuration.CATEGORY_GENERAL, true).value);
			checkUpdate = Boolean.parseBoolean(config.get("Check for update", Configuration.CATEGORY_GENERAL, true).value);
			chanceToRegenChest = config.get(Configuration.CATEGORY_GENERAL, "Chance to regenerate chest items", 5).getInt();

			barbedwireID = config.getBlock("barbedwireID", 160).getInt();
			dayzchestallID = config.getBlock("dayzchestallID", 161).getInt();
			dayzchestrareID = config.getBlock("dayzchestrareID", 162).getInt();
			dayzchestcommonID = config.getBlock("dayzchestcommonID", 163).getInt();
			chainlinkfenceID = config.getBlock("chainlinkfenceID", 164).getInt();
			sandbagblockID = config.getBlock("sandbagblockID", 165).getInt();
			nailsID = config.getBlock("nailsID", 170).getInt();

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
        dayzchestall = new BlockChestAll(dayzchestallID).setBlockName("dayzchestall").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestrare = new BlockChestRare(dayzchestrareID).setBlockName("dayzchestrare").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        dayzchestcommon = new BlockChestCommon(dayzchestcommonID).setBlockName("dayzchestcommon").setBlockUnbreakable().setCreativeTab(CreativeTabs.tabDecorations);
        chainlinkfence = (new BlockFence(chainlinkfenceID, 1, 1, Material.iron, false)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setBlockName("chainlinkfence").setCreativeTab(CreativeTabs.tabDecorations);
        sandbagblock = (new BlockBase(sandbagblockID, 2, Material.clay)).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundGrassFootstep).setBlockName("sandbagblock").setCreativeTab(CreativeTabs.tabDecorations);
        nails = new BlockNails(nailsID, 3, Material.leaves).setBlockName("nails").setHardness(1F).setResistance(1F).setCreativeTab(CreativeTabs.tabDecorations);
        
    	GameRegistry.registerBlock(barbedwire);
    	GameRegistry.registerBlock(dayzchestall);
    	GameRegistry.registerBlock(dayzchestrare);
    	GameRegistry.registerBlock(dayzchestcommon);
    	GameRegistry.registerBlock(chainlinkfence);
    	GameRegistry.registerBlock(sandbagblock);
    	GameRegistry.registerBlock(nails);
   
    /************* 						Entities 							*************/
        
        EntityRegistry.registerGlobalEntityID(EntityZombieDayZ.class, "Zombie", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);        
        EntityRegistry.registerGlobalEntityID(EntityBandit.class, "Bandit", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);        
        EntityRegistry.registerGlobalEntityID(EntityCrawler.class, "Crawler", EntityRegistry.findGlobalUniqueEntityId(), 1, 2);        
        
        EntityRegistry.registerModEntity(EntityBullet.class, "Bullet", 1, this, 250, 5, true);
        EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 1, this, 250, 5, true);
        
        EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster, BiomeGenBase.river);
        EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster, BiomeGenBase.river);
        EntityRegistry.removeSpawn(EntitySpider.class, EnumCreatureType.monster, BiomeGenBase.river);
        EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.monster, BiomeGenBase.river);
        EntityRegistry.removeSpawn(EntityEnderman.class, EnumCreatureType.monster, BiomeGenBase.river);

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
        LanguageRegistry.addName(DayZ.dayzchestall, "Day Z Chest");
        LanguageRegistry.addName(DayZ.dayzchestrare, "Day Z Rare Chest");
        LanguageRegistry.addName(DayZ.dayzchestcommon, "Day Z Common Chest");
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
        
    /************* 						Generation 							*************/

        Util.setVillageCanSpawnInBiome(dayzPlains, true);
        Util.setVillageCanSpawnInBiome(dayzForest, true);

        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.makarov.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.antibiotics.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.makarovammo.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.cannedbeans.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.cannedspag.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.waterbottlefull.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(Item.writableBook.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(Item.boat.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(DayZ.pipe.shiftedIndex, 0, 1, 1, 1));

        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.stick));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Block.planks));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Block.wood));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.axeStone));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.axeWood));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.pickaxeStone));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.pickaxeWood));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.appleRed));
        ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, new ItemStack(Item.bread));
                
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.makarov.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.ak74u.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.remington.shiftedIndex, 0, 1, 1, 1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.makarovammo.shiftedIndex, 0, 1, 1, 2));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.ak74uammo.shiftedIndex, 0, 1, 1, 2));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.remingtonammo.shiftedIndex, 0, 1, 1, 2));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.bandage.shiftedIndex, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.antibiotics.shiftedIndex, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.cannedbeans.shiftedIndex, 0, 1, 1, 4));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.cannedfish.shiftedIndex, 0, 1, 1, 4));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.cannedspag.shiftedIndex, 0, 1, 1, 4));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.waterbottlefull.shiftedIndex, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.waterbottleempty.shiftedIndex, 0, 1, 1, 5));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.whiskeybottlefull.shiftedIndex, 0, 1, 1, 2));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(DayZ.pipe.shiftedIndex, 0, 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(Item.boat.shiftedIndex, 0, 1, 1, 3));

        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.ingotIron));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.ingotGold));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.diamond));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.bread));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.appleRed));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.pickaxeSteel));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.swordSteel));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.plateSteel));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.helmetSteel));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.bootsSteel));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Item.legsSteel));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Block.obsidian));
        ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack(Block.sapling));

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
    public static Block nails;

}