package com.genae.terrainControlUI.Image;

import java.awt.image.BufferedImage;
import java.io.File;

import com.genae.terrainControlUI.configs.SimpleWorld;
import com.genae.terrainControlUI.main.Main;
import com.khorn.terraincontrol.biomelayers.layers.Layer;
import com.khorn.terraincontrol.configuration.BiomeConfig;
import com.khorn.terraincontrol.configuration.WorldConfig;

public class BiomeImage extends BufferedImage
{

	public SimpleWorld world;
	public WorldConfig config;
	public int size, offsetX, offsetZ;
	public File worldDir;
	public ImageFrame progress;

    public BiomeImage(File worldDir, long Seed, int size, int offsetX, int offsetZ, ImageFrame progress, SimpleWorld world)
    {
    	super(size * 16, size * 16, BufferedImage.TYPE_INT_RGB);
    	this.progress = progress;
    	this.size = size;
    	this.offsetX = offsetX;
    	this.offsetZ = offsetZ;
        this.world = world;//new SimpleWorld(Seed, "TestWorld");
        this.worldDir = worldDir;
        world.setSettings(Main.world.settings);
        refreshImage();

        
    }
    public void refreshImage()
    {
    	RefreshThread thread = new RefreshThread();
    	thread.run(this);
    }
}
class RefreshThread extends Thread
{
	public void run(BiomeImage img)
	{
		int height = img.size;
	    int width = img.size;
		try
	    {
			BiomeConfig[] biomes = Main.world.settings.biomes.toArray(new BiomeConfig[]{});
			String[] ice = Main.world.settings.IceBiomes.toArray(new String[]{});
			String[] normal = Main.world.settings.NormalBiomes.toArray(new String[]{});
			Main.world.settings.iceBiomesRarity = 0;
			Main.world.settings.normalBiomesRarity = 0;
			for(BiomeConfig c : biomes)
			{
				for(String iceName:ice)
				{
					if(c.Name.equals(iceName))Main.world.settings.iceBiomesRarity += c.BiomeRarity;
				}
				for(String normalName:normal)
				{
					if(c.Name.equals(normalName))Main.world.settings.normalBiomesRarity += c.BiomeRarity;
				}
			}
			
	        Layer[] layers = Layer.Init(Main.world.getSeed(), Main.world);
	        Layer BiomeLayer = layers[1];
	
	        int[] Colors = new int[Main.world.settings.biomeConfigs.length];
	
	        for (BiomeConfig biomeConfig : Main.world.settings.biomeConfigs)
	        {
	            if (biomeConfig != null)
	            {
	                try
	                {
	                    int color = Integer.decode(biomeConfig.BiomeColor);
	                    if (color <= 0xFFFFFF)
	                        Colors[biomeConfig.Biome.getId()] = color;
	                } catch (NumberFormatException ex)
	                {
	                    System.out.println("TerrainControl: wrong color in " + biomeConfig.Biome.getName());
	                }
	            }
	        }
	
	
	        int[] BiomeBuffer;
	
	
	        int image_x;
	        int image_y;
	
	        long time = System.currentTimeMillis();
	
	
	        for (int x = -height / 2; x < height / 2; x++)
	        {
	            for (int z = -width / 2; z < width / 2; z++)
	            {
	
	                long time2 = System.currentTimeMillis();
	
	                if (time2 < time)
	                {
	                    time = time2;
	                }
	
	                if (time2 > time + 200L)
	                {
	                    System.out.println("Time " + Integer.toString((x + height / 2) * 100 / height) + "%");
	                    img.progress.setProgress((x + height / 2) * 100 / height);
	                    time = time2;
	                }
	
	
	                BiomeBuffer = BiomeLayer.Calculate(img.offsetX + x * 16, img.offsetZ + z * 16, 16, 16);
	                for (int x1 = 0; x1 < 16; x1++)
	                {
	                    for (int z1 = 0; z1 < 16; z1++)
	                    {
	
	
	                        image_x = (x + height / 2) * 16 + x1;
	                        image_y = (z + width / 2) * 16 + z1;
	
	
	                        img.setRGB(image_x, image_y, Colors[BiomeBuffer[x1 + 16 * z1]]);
	
	                    }
	                }
	            }
	        }
	        img.progress.setProgress(100);
	
	    } catch (Exception e1)
	    {
	        e1.printStackTrace();
	    }
	}
}

