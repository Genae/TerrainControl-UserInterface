package com.genae.terrainControlUI.configs;

import com.genae.terrainControlUI.main.Main;


public class UIConfig 
{

	public String testDirectory = System.getProperty("user.home") + "\\TerrainControl\\TestServer";
	public String CustomBiomeDirectory = System.getProperty("user.home") + "\\TerrainControl\\CustomBiomes";
	public String myBukkitPath = "C:\\Bukkit\\craftbukkit-1.3.1-R1.0.jar";
	public String myTCPath = "C:\\Bukkit\\plugins\\TerrainControl v.2.2.3dev.jar";
	public String worldName = "TestWorld";
	public String seed = "tim1998+steps1956";
	public double multiplier = 0.9;
	
	public String getSeedString()
	{
		return seed;
	}
	public long getSeed()
	{
		long lSeed = 0;

		if (seed.length() != 0)
		{
			try
			{
				long l1 = Long.parseLong(seed);

				if (l1 != 0L)
				{
					lSeed = l1;
				}
			}
			catch (NumberFormatException numberformatexception)
			{
				lSeed = seed.hashCode();
			}
		}
		
		return lSeed;
	}
	public void setSeed(String seed)
	{
		this.seed = seed;
		long lSeed = getSeed();
		Main.world.Seed = lSeed;
	}
}
