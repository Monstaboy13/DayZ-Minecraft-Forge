package dayz.common;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class Util
{
	public static final String ID = "Day Z Minecraft";
	public static final String NAME = "Day Z Minecraft";
	public static final String VERSION = "5.4";	
	
	@ForgeSubscribe
	public void onSoundsLoaded(SoundLoadEvent event)
	{
		SoundManager manager = event.manager;
		String [] soundFiles = 
		{
			"ak74u.ogg", "makarov.ogg", "remington.ogg", "reload.ogg"
	   	};
		for (int i = 0; i < soundFiles.length; i++)
	    {
			manager.soundPoolSounds.addSound(soundFiles[i], this.getClass().getResource("/dayz/sounds/" + soundFiles[i]));
	    }
	}
	
	/***********************************************************************************/
	/**                               CHEST GENERATION                               **/
	/*********************************************************************************/
	
    private static Random random = new Random();
    
    /**
     * Call this when generating loot that can be any worth(1-5).
     */
	public static ItemStack LootItemsAll()
	{
		int itemstack = random.nextInt(310);
		if (itemstack <= 10)
		{
			return Util.itemWorth5();
		}
		else if (itemstack > 10 && itemstack <= 30)
		{
			return Util.itemWorth4();
		}
		else if (itemstack > 30 && itemstack <= 70)
		{
			return Util.itemWorth3();
		}
		else if (itemstack > 70 && itemstack <= 150)
		{
			return Util.itemWorth2();
		}
		else
		{
			return Util.itemWorth1();
		}
	}
	
    /**
     * Call this when generating rare loot that can be worth 4-5.
     */
	public static ItemStack LootItemsRare()
	{
		int itemstack = random.nextInt(30);
		if (itemstack < 10)
		{
			return Util.itemWorth5();
		}
		else
		{
			return Util.itemWorth4();
		}
	}
	
    /**
     * Call this when generating common loot that can be worth 1-3.
     */
	public static ItemStack LootItemsCommon()
	{
		int itemstack = random.nextInt(70);
		if (itemstack <= 10)
		{
			return Util.itemWorth3();
		}
		else if (itemstack > 10 && itemstack <= 30)
		{
			return Util.itemWorth2();
		}
		else
		{
			return Util.itemWorth1();
		}
	}
	
	/******** Don't worry about this section unless you're adding new items to generate ********/
	private static ItemStack itemWorth1()
	{
		int item = random.nextInt(20) + 1;
		ItemStack itemstack;
		switch (item) 
		{
    	case 1:  itemstack = new ItemStack(Item.beefRaw);
    			 break;
    	case 2:  itemstack = new ItemStack(Item.beefCooked);
    			 break;
    	case 3:  itemstack = new ItemStack(Item.porkRaw);
    			 break;
    	case 4:  itemstack = new ItemStack(Item.porkCooked);
    			 break;
    	case 5:  itemstack = new ItemStack(Item.fishRaw);
    			 break;
    	case 6:  itemstack = new ItemStack(Item.fishCooked);
    			 break;
    	case 7:  itemstack = new ItemStack(Item.appleRed);
    			 break;
    	case 8:  itemstack = new ItemStack(Item.bowlSoup);
    			 break;
    	case 9:  itemstack = new ItemStack(Item.melon);
    			 break;
    	case 10: itemstack = new ItemStack(Item.cookie);
    			 break;
    	case 11: itemstack = new ItemStack(DayZ.cannedbeans);
		 		 break;
    	case 12: itemstack = new ItemStack(DayZ.cannedfish);
		 		 break;
    	case 13: itemstack = new ItemStack(DayZ.cannedspag);
    			 break;
    	case 14: itemstack = new ItemStack(DayZ.chocolate);
    			 break;
    	case 15: itemstack = new ItemStack(DayZ.lemonade);
		 		 break;
    	case 16: itemstack = new ItemStack(DayZ.antibiotics);
		 		 break;
    	case 17: itemstack = new ItemStack(DayZ.waterbottledirty);
		 		 break;
    	case 18: itemstack = new ItemStack(DayZ.waterbottleempty);
    			 break;
    	case 19: itemstack = new ItemStack(DayZ.waterbottlefull);
		 		 break;
    	case 20: itemstack = new ItemStack(DayZ.whiskeybottleempty);
		 		 break;
        default: itemstack = null;
        		 break;
		}
		return itemstack;
	}
	
	private static ItemStack itemWorth2()
	{
		int item = random.nextInt(5) + 1;
		ItemStack itemstack;
		switch (item) 
		{
        case 1:  itemstack = new ItemStack(DayZ.baseballbat);
                 break;
        case 2:  itemstack = new ItemStack(DayZ.makarovammo);
                 break;
        case 3:  itemstack = new ItemStack(DayZ.plank);
                 break;
        case 4:  itemstack = new ItemStack(DayZ.whiskeybottlefull);
                 break;
        case 5: itemstack = new ItemStack(DayZ.nails);
		 		 break;
        default: itemstack = null;
                 break;
		}
		return itemstack;
	}
	
	private static ItemStack itemWorth3()
	{
		int item = random.nextInt(16) + 1;
		ItemStack itemstack;
		switch (item) 
		{
        case 1:  itemstack = new ItemStack(DayZ.ak74uammo);
                 break;
        case 2:  itemstack = new ItemStack(DayZ.bandage);
                 break;
        case 3:  itemstack = new ItemStack(DayZ.baseballbatnailed);
                 break;
        case 4:  itemstack = new ItemStack(DayZ.makarov);
                 break;
        case 5:  itemstack = new ItemStack(DayZ.pipe);
                 break;
        case 6:  itemstack = new ItemStack(DayZ.planknailed);
                 break;
        case 7:  itemstack = new ItemStack(DayZ.remingtonammo);
                 break;
        case 8:  itemstack = new ItemStack(Item.map);
                 break;
        case 9:  itemstack = new ItemStack(Item.coal);
                 break;
        case 10: itemstack = new ItemStack(Item.ingotIron);
                 break;
        case 11: itemstack = new ItemStack(Item.writableBook);
                 break;
        case 12: itemstack = new ItemStack(Item.arrow, random.nextInt(8) + 1);
                 break;
        case 13: itemstack = new ItemStack(Item.bone);
        		 break;
        case 14: itemstack = new ItemStack(DayZ.bloodbag);
		 		 break;
        case 15: itemstack = new ItemStack(Block.tripWireSource);
		 		 break;
        case 16: itemstack = new ItemStack(Item.silk);
		 		 break;
        default: itemstack = null;
                 break;
		}
		return itemstack;
	}
	
	private static ItemStack itemWorth4()
	{
		int item = random.nextInt(7) + 1;
		ItemStack itemstack;
		switch (item) 
		{
        case 1:  itemstack = new ItemStack(DayZ.barbedwire);
                 break;
        case 2:  itemstack =  new ItemStack(DayZ.camoboots);
                 break;
        case 3:  itemstack =  new ItemStack(DayZ.camochest);
                 break;
        case 4:  itemstack =  new ItemStack(DayZ.camohelmet);
                 break;
        case 5:  itemstack =  new ItemStack(DayZ.camolegs);
                 break;
        case 6:  itemstack =  new ItemStack(Item.cake);
                 break;
        case 7:  itemstack =  new ItemStack(Item.bow);
                 break;
        default: itemstack =  null;
                 break;
		}
		return itemstack;
	}
	
	private static ItemStack itemWorth5()
	{
		int item = random.nextInt(2) + 1;
		ItemStack itemstack;
		switch (item) 
		{
        case 1:  itemstack =  new ItemStack(DayZ.ak74u);
                 break;
        case 2:  itemstack =  new ItemStack(DayZ.remington);
                 break;
        default: itemstack =  null;
                 break;
		}
		return itemstack;
	}
}