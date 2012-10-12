package dayz.common.world;

import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.BiomeGenRiver;

public class BiomeGenRiverDayZ extends BiomeGenRiver 
{
	public BiomeGenRiverDayZ(int id) 
	{
		super(id);
		setColor(747097);
		setBiomeName("DayZ River");
        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
		minHeight = -0.5F;
		maxHeight = 0.0F;
    }

	@Override
	protected BiomeDecorator createBiomeDecorator() 
	{
		return new BiomeDecoratorOverride.Builder(this).biomeColour(1456435).flowersPerChunk(0).deadBushPerChunk(0).treesPerChunk(-999).grassPerChunk(15).build();
	}
}
