package dayz.common;

import net.minecraft.src.ModelBiped;
import net.minecraft.src.ModelZombie;
import net.minecraft.src.RenderBiped;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.registry.TickRegistry;
import dayz.common.entities.EntityBandit;
import dayz.common.entities.EntityBullet;
import dayz.common.entities.EntityCrawler;
import dayz.common.entities.EntityZombie;
import dayz.common.entities.ModelCrawler;
import dayz.common.entities.RenderBullet;
import dayz.common.entities.RenderCrawler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void DayZload() 
	{
		//Basically the same as the old way to render entities.
        RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityCrawler.class, new RenderCrawler(new ModelCrawler(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityBandit.class, new RenderBiped(new ModelBiped(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
        //Preload the textures.
        MinecraftForgeClient.preloadTexture("/DayZ/images/weapons.png");
        MinecraftForgeClient.preloadTexture("/DayZ/images/heal.png");
        MinecraftForgeClient.preloadTexture("/DayZ/images/food.png");
        MinecraftForgeClient.preloadTexture("/dayz/images/terrain.png");
        MinecraftForgeClient.preloadTexture("/DayZ/images/armor.png");
        MinecraftForgeClient.preloadTexture("/DayZ/images/items.png");
        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);        
	}
}