package dayz.common.world;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldChunkManagerHell;
import net.minecraft.src.WorldType;
import dayz.common.DayZ;

public class WorldTypeDayZ extends WorldType
{
    public WorldTypeDayZ()
    {
        super(12, "DAYZ");
    }

    //Sets up the biome and WorldChunkManager.
    @Override
    public WorldChunkManager getChunkManager(World world)
    {
        return new WorldChunkManagerHell(DayZ.dayzforest, 0.5F, 0.5F);
    }

    //Sets up the ChunkProvider
    @Override
    public IChunkProvider getChunkGenerator(World world)
    {
        return new ChunkProviderDayZ(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }
    
    @Override
    public int getSpawnFuzz()
    {
        return 200;
    }
}