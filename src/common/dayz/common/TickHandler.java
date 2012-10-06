package dayz.common;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ScaledResolution;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.asm.transformers.MCPMerger;
import dayz.common.entities.EntityCrawler;
import dayz.common.entities.EntityZombie;

public class TickHandler implements ITickHandler {
	public static int totalKills = 0;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if (type.equals(EnumSet.of(TickType.RENDER))) 
		{
			onRenderTick();
		} 
		else if (type.equals(EnumSet.of(TickType.CLIENT))) 
		{
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
			if (guiscreen != null) 
			{
				onTickInGUI(guiscreen);
			} else 
			{
				onTickInGame();
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return null;
	}

	public void onRenderTick()
    {
    	Minecraft mc = ModLoader.getMinecraftInstance();
    	ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        
    	if (DayZ.showDebug == true)
    	{
	        if (mc.inGameHasFocus && mc.isGuiEnabled())
	        {
	            int zombies = (mc.theWorld.countEntities(EntityZombie.class) + (mc.theWorld.countEntities(EntityCrawler.class)));
	            if (DayZ.showName == true)
	            {
		            FMLClientHandler.instance().getClient().fontRenderer.drawString("Name: " + mc.thePlayer.username, i - 110, 18, 0xffffff);
	            }
	            FMLClientHandler.instance().getClient().fontRenderer.drawString("Blood: " + (mc.thePlayer.getHealth() * 600), i - 110, 28, 0xffffff);
	            FMLClientHandler.instance().getClient().fontRenderer.drawString("Zombies: " + zombies, i - 110, 38, 0xffffff);
	            FMLClientHandler.instance().getClient().fontRenderer.drawString("Version: " + DayZ.dayzVersion, i - 110, 48, 0xffffff);
	            if (DayZ.showCoords == true)
	            {
		            FMLClientHandler.instance().getClient().fontRenderer.drawString("Coords: " + (int)mc.thePlayer.posX + ", " + (int)mc.thePlayer.posZ, i - 110, 58, 0xffffff);
	            }
	            if (DayZ.isUpToDate == false && DayZ.checkUpdate == true)
	            {
	            	if (DayZ.showCoords == false)
	            	{
			            FMLClientHandler.instance().getClient().fontRenderer.drawString("Update Availiable", i - 110, 58, 0xffffff);
	            	}
	            	else
	            	{
			            FMLClientHandler.instance().getClient().fontRenderer.drawString("Update Availiable", i - 110, 68, 0xffffff);
	            	}
	            }
	        }
    	}
    }

	public void onTickInGUI(GuiScreen guiscreen) {

	}

	public void onTickInGame() {

	}
}
