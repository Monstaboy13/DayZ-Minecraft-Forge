package dayz.common;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapGenVillage;
import net.minecraft.src.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class Util
{
	public static final String ID = "Day Z Minecraft";
	public static final String NAME = "Day Z Minecraft";
	public static final String VERSION = "5.5";
	
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
	
    private static ArrayList<BiomeGenBase> villageBiomes = new ArrayList<BiomeGenBase>();
    
	/**
     * Adds the possibility to generate villages in your custom biomes
     * @param biome		Your biome
     * @param canSpawn	True if you want to add it to the list, false if you want to remove it
     */
    public static void setVillageCanSpawnInBiome(BiomeGenBase biome, boolean canSpawn)
    {
        ArrayList<BiomeGenBase> biomesForVillage = new ArrayList<BiomeGenBase>(MapGenVillage.villageSpawnBiomes);
        if (canSpawn)
        {
            if (!villageBiomes.contains(biome))
            {
                villageBiomes.add(biome);
                biomesForVillage.add(biome);
            }
        }
        else
        {
            villageBiomes.remove(biome);
            biomesForVillage.remove(biome);
        }
        MapGenVillage.villageSpawnBiomes = biomesForVillage;
    }
}