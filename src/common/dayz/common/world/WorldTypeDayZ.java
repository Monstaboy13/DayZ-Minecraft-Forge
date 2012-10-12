package dayz.common.world;

import java.util.Arrays;
import java.util.Set;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldType;
import com.google.common.collect.Sets;
import dayz.common.DayZ;

public class WorldTypeDayZ extends WorldType
{
    public WorldTypeDayZ()
    {
        super(12, "DAYZ");
        this.addNewBiome(DayZ.dayzForest);
        this.addNewBiome(DayZ.dayzPlains);
        this.removeBiome(BiomeGenBase.desert);
        this.removeBiome(BiomeGenBase.desertHills);
        this.removeBiome(BiomeGenBase.extremeHills);
        this.removeBiome(BiomeGenBase.extremeHillsEdge);
        this.removeBiome(BiomeGenBase.forest);
        this.removeBiome(BiomeGenBase.forestHills);
        this.removeBiome(BiomeGenBase.frozenOcean);
        this.removeBiome(BiomeGenBase.frozenRiver);
        this.removeBiome(BiomeGenBase.iceMountains);
        this.removeBiome(BiomeGenBase.icePlains);
        this.removeBiome(BiomeGenBase.jungle);
        this.removeBiome(BiomeGenBase.jungleHills);
        this.removeBiome(BiomeGenBase.mushroomIsland);
        this.removeBiome(BiomeGenBase.mushroomIslandShore);
        this.removeBiome(BiomeGenBase.ocean);
        this.removeBiome(BiomeGenBase.plains);
        this.removeBiome(BiomeGenBase.swampland);
        this.removeBiome(BiomeGenBase.taiga);
        this.removeBiome(BiomeGenBase.taigaHills);
    }

    //Sets up the biome and WorldChunkManager.
    @Override
    public WorldChunkManager getChunkManager(World world)
    {
        return new WorldChunkManager(world);
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