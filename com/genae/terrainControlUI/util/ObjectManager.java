package com.genae.terrainControlUI.util;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.genae.terrainControlUI.biomePanels.CustomBiomeList;
import com.genae.terrainControlUI.configs.BiomeDescription;
import com.genae.terrainControlUI.configs.SimpleBiome;
import com.genae.terrainControlUI.main.Main;
import com.khorn.terraincontrol.DefaultMaterial;
import com.khorn.terraincontrol.DefaultMobType;
import com.khorn.terraincontrol.configuration.BiomeConfig;

public class ObjectManager 
{
	String[] trees = new String[]{"SwampTree", "BigTree", "Forest", "Tree", "GroundBush", "JungleTree", "HugeMushroom", "Taiga1", "Taiga2"};
	String[] custom =  new String[]{""};
	private String[] normalBiomes = new String[]{"Desert", "Forest", "Extreme Hills", "Swampland", "Plains", "Taiga", "Jungle"};
	private String[] iceBiomes = new String[]{"Ice Plains"};
	private String[] isleBiomes = new String[]{"MushroomIsland", "Ice Mountains", "DesertHills", "ForestHills", "TaigaHills", "River", "JungleHills"};
	private String[] borderBiomes = new String[]{"MushroomIslandShore", "Beach", "Extreme Hills Edge"};
	private String[] customBiomes = new String[]{};
	public HashMap<String, String> ids;
	public int[][] idTypes;
	ImageIcon[] imgs;
	public HashMap<String, BiomeConfig> biomes;
	public HashMap<String, ImageIcon> faces;
	public HashMap<String, ImageIcon> body;
	
	//////////////////////////
	//						//
	//		I N I T			//
	//						//
	//////////////////////////
	public ObjectManager()
	{
		getImages();
		biomes = new HashMap<String, BiomeConfig>();
		for(BiomeConfig b:Main.world.settings.biomes)
		{
			biomes.put(b.Name, b);
		}
		
		customBiomes = TCFileWriter.getAllCustomBiomes();
				
		ids = new HashMap<String, String>();
		for(int id = 0; id < DefaultMaterial.values().length; id++)
		{
			ids.put(id + "", DefaultMaterial.getMaterial(id).name().toLowerCase());
		}
		
		ids.put("5-0", "Oakwood");
		ids.put("5-1", "Pine/Sprucewood");
		ids.put("5-2", "Birchwood");
		ids.put("5-3", "Junglewood");
		ids.put("6-0", "OakSapling ");
		ids.put("6-1", "SpruceSapling");
		ids.put("6-2", "BirchSapling");
		ids.put("6-3", "JungleTreeSapling ");
		ids.put("17-0", "Oakwood");
		ids.put("17-1", "Pine/Sprucewood");
		ids.put("17-2", "Birchwood ");
		ids.put("17-3", "Junglewood ");
		ids.put("24-0", "NormalSandstone");
		ids.put("24-1", "ChiseledSandstone");
		ids.put("24-2", "SmoothSandstone");
		ids.put("31-0", "Deadshrub");
		ids.put("31-1", "Tallgrass");
		ids.put("31-2", "Fern");
		ids.put("35-0", "WhiteWool");
		ids.put("35-1", "OrangeWool");
		ids.put("35-2", "MagentaWool");
		ids.put("35-3", "LightBlueWool");
		ids.put("35-4", "YellowWool");
		ids.put("35-5", "LimeWool");
		ids.put("35-6", "PinkWool");
		ids.put("35-7", "GrayWool");
		ids.put("35-8", "LightGrayWool");
		ids.put("35-9", "CyanWool");
		ids.put("35-10", "PurpleWool");
		ids.put("35-11", "BlueWool");
		ids.put("35-12", "BrownWool");
		ids.put("35-13", "GreenWool");
		ids.put("35-14", "RedWool");
		ids.put("35-15", "BlackWool");
		ids.put("43-0", "StoneDoubleSlab");
		ids.put("43-1", "SandstoneDoubleSlab");
		ids.put("43-2", "WoodenStoneDoubleSlab");
		ids.put("43-3", "CobblestoneDoubleSlab");
		ids.put("43-4", "BrickDoubleSlab");
		ids.put("43-5", "StoneBrickDoubleSlab");
		ids.put("43-6", "unknown DoubleSlab");
		ids.put("44-0", "StoneSlab");
		ids.put("44-1", "SandstoneSlab");
		ids.put("44-2", "WoodenStoneSlab");
		ids.put("44-3", "CobblestoneSlab");
		ids.put("44-4", "BrickSlab");
		ids.put("44-5", "StoneBrickSlab");
		ids.put("44-6", "unknown Slab");
		ids.put("97-0", "StoneHiddenSilverfish");
		ids.put("97-1", "CobblestoneHiddenSilverfish");
		ids.put("97-2", "StoneBrickHiddenSilverfish");
		ids.put("98-0", "NormalStoneBrick");
		ids.put("98-1", "MossyStoneBrick");
		ids.put("98-2", "CrackedStoneBrick");
		ids.put("98-3", "ChiseledStoneBrick");
		ids.put("125-0", "Oak-WoodDoubleSlab");
		ids.put("125-1", "Spruce-WoodDoubleSlab");
		ids.put("125-2", "Birch-WoodDoubleSlab");
		ids.put("125-3", "Jungle-WoodDoubleSlab");
		ids.put("126-0", "Oak-WoodDoubleSlab");
		ids.put("126-1", "Spruce-WoodSlab");
		ids.put("126-2", "Birch-WoodSlab");
		ids.put("126-3", "Jungle-WoodSlab");



	}
	//////////////////////////
	//						//
	//		  G E T 		//
	//						//
	//////////////////////////
	public String[] getTrees()
	{
		return trees;
	}
	public String[] getNormalBiomes()
	{
		return normalBiomes;
	}
	public String[] getIceBiomes()
	{
		return iceBiomes;
	}
	public String[] getBorderBiomes()
	{
		return borderBiomes;
	}
	public String[] getIsleBiomes()
	{
		return isleBiomes;
	}
	public String[] getCustomBiomes()
	{
		return customBiomes;
	}
	public boolean[] getCustomSelected()
	{
		boolean[] selected = new boolean[getCustomBiomes().length];
		for(int i = 0; i < selected.length; i++)
		{
			selected[i] = Main.world.settings.CustomBiomes.contains(getCustomBiomes()[i]);
		}
		return selected;
	}
	public String[] getAllBiomes(boolean custom)
	{
		String[] all;
		all = new String[normalBiomes.length + iceBiomes.length + isleBiomes.length + borderBiomes.length];
		if(custom) all = new String[normalBiomes.length + iceBiomes.length + isleBiomes.length + borderBiomes.length + customBiomes.length];
		{
			int j = 0;
			for(int i = 0; i < normalBiomes.length; i++)
			{
				all[j++] = normalBiomes[i];
			}
			for(int i = 0; i < iceBiomes.length; i++)
			{
				all[j++] = iceBiomes[i];
			}
			for(int i = 0; i < isleBiomes.length; i++)
			{
				all[j++] = isleBiomes[i];
			}
			for(int i = 0; i < borderBiomes.length; i++)
			{
				all[j++] = borderBiomes[i];
			}
			if(custom)for(int i = 0; i < customBiomes.length; i++)
			{
				all[j++] = customBiomes[i];
			}
		}
		return all;
	}
	public BiomeConfig getBiomeByName(String name)
	{
		if(!biomes.containsKey(name))
		biomes.put(name, TCFileWriter.loadBiomeFromConfig(name));
		return biomes.get(name);
	}
	public HashMap<String, String> getIDs()
	{
		return ids;
	}
	public ImageIcon[] getBlockImgs()
	{
		return imgs;
	}
	//////////////////////////
	//						//
	//		I M A G E		//
	//						//
	//////////////////////////
	private void getImages()
	{
		ArrayList<ImageIcon> img = new ArrayList<ImageIcon>();
		ArrayList<Integer> idStart = new ArrayList<Integer>();
		ArrayList<Integer> typAmount = new ArrayList<Integer>();
		int imgId = 1;
		int imgTyp = 0;
		while(true)
		{
			String name = "ID_" + imgId + ".png";
			if(Main.getImage("/resources/images/Blocks/" + name) != null) 	//+++The ID HAS NO data values
			{
				img.add(Main.getImage("/resources/images/Blocks/" + name));
				imgId++;
			}
			else															//+++The ID HAS data values
			{
				idStart.add(imgId);
				imgTyp = 0;
				while(true)
				{
					name = "ID_" + imgId + "-" + imgTyp + ".png";
					if(Main.getImage("/resources/images/Blocks/" + name) != null) 	//++The ID HAS MORE data values
					{
						img.add(Main.getImage("/resources/images/Blocks/" + name));
						imgTyp++;
					}
					else															//++The ID HAS NO MORE data values
					{
						imgId++;
						if(imgTyp != 0)typAmount.add(imgTyp);
						break;
					}
				}
				if(imgTyp == 0) break;												//++The ID NEVER HAD data values->End of all IDs
			}
		}
		imgs = img.toArray(new ImageIcon[0]);
		idTypes = new int[idStart.size()][2];
		for(int i = 0; i < typAmount.size(); i++)
		{
			idTypes[i][0] = idStart.get(i);
			idTypes[i][1] = typAmount.get(i);
		}
		idTypes[typAmount.size()] = new int[]{0,0};
		
		
		faces = new HashMap<String, ImageIcon>();
		body = new HashMap<String, ImageIcon>();
		for (DefaultMobType type : EnumSet.allOf(DefaultMobType.class))
		{
			faces.put(type.getFormalName(), Main.getImage("/resources/images/monsters/" + type.getFormalName() + "Face.png"));
			body.put(type.getFormalName(), Main.getImage("/resources/images/monsters/" + type.getFormalName() + ".png"));
		}
	}
	
	//////////////////////////
	//						//
	//		C U S T O M		//
	//						//
	//////////////////////////
	public void addCustomBiome(String name, String copy, int mode)
	{
		new BiomeDescription(name);
		Main.main.worldConfig.CustomBiomes.add(name);
		String[] custom = new String[customBiomes.length + 1];
		int i = 0;
		for(String s : customBiomes)
		{
			custom[i++] = s;
		}
		custom[i] = name;
		SimpleBiome simpleBiome = new SimpleBiome(name, Main.world.getFreeBiomeId());
		File settingsDir = new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + Main.config.worldName + "\\BiomeConfigs");
		BiomeConfig biome = null;
		if(mode == CustomBiomeList.READ)
		{
			TCFileWriter.copyFile(new File(copy), new File(settingsDir, name + "BiomeConfig.ini"));
			biome = new BiomeConfig(settingsDir, simpleBiome, Main.main.worldConfig);
		}
		else
		{
			biome = new BiomeConfig(settingsDir, simpleBiome, Main.main.worldConfig);
			for(BiomeConfig copyBiome:Main.main.worldConfig.biomes)
			{
				if(copy.equals(copyBiome.Name))
				{
					copyBiome(copyBiome, biome);
				}
			}
		}
		Main.main.worldConfig.biomes.add(biome);
		biomes.put(biome.Name, biome);
		Main.main.worldConfig.CustomBiomeIds.put(name, biome.Biome.getId());
		Main.main.worldConfig.biomeConfigs = Main.main.worldConfig.biomes.toArray(Main.main.worldConfig.biomeConfigs);
		Main.main.mainFrame.world.biomeGenerator.getList("customBiomes").insert(name, false);
		
		if(biome.BiomeTemperature > 0 && biome.IsleInBiome.size()+biome.BiomeIsBorder.size() == 0) Main.main.mainFrame.world.biomeGenerator.getList("normalBiomes").insert(name, true);
		else if(biome.IsleInBiome.size()+biome.BiomeIsBorder.size() == 0) 		Main.main.mainFrame.world.biomeGenerator.getList("iceBiomes").insert(biome.Name, true);
		
		if(biome.IsleInBiome.size() != 0) Main.main.mainFrame.world.biomeGenerator.getList("isleBiomes").insert(biome.Name, true);
		if(biome.BiomeIsBorder.size() != 0) Main.main.mainFrame.world.biomeGenerator.getList("borderBiomes").insert(biome.Name, true);
		
		TCFileWriter.writeCustomBiome(biome, settingsDir);
		Main.world.AddBiome(name, biome.Biome.getId());
		this.biomes.put(biome.Name, biome);
	}
	public void copyBiome(BiomeConfig from, BiomeConfig to)
	{
		to.BiomeSize = from.BiomeSize;
		to.BiomeRarity = from.BiomeRarity;
		//to.BiomeColor = from.BiomeColor;
		to.BiomeRivers = from.BiomeRivers;
		to.IsleInBiome = from.IsleInBiome;
		to.BiomeIsBorder = from.BiomeIsBorder;
		to.NotBorderNear = from.NotBorderNear;
		to.BiomeTemperature = from.BiomeTemperature;
		to.BiomeWetness = from.BiomeWetness;
		to.ReplaceBiomeName = from.ReplaceBiomeName;

		to.BiomeHeight = from.BiomeHeight;
		to.BiomeVolatility = from.BiomeVolatility;
		to.maxAverageHeight = from.maxAverageHeight;
		to.maxAverageDepth = from.maxAverageDepth;
		to.volatility1 = from.volatility1;
		to.volatility2 = from.volatility2;
		to.volatilityWeight1 = from.volatilityWeight1;
		to.volatilityWeight2 = from.volatilityWeight2;
		to.disableNotchHeightControl = from.disableNotchHeightControl;
		to.heightMatrix = from.heightMatrix;
		to.SurfaceBlock = from.SurfaceBlock;
		to.GroundBlock = from.GroundBlock;
		to.UseWorldWaterLevel = from.UseWorldWaterLevel;
		to.waterLevelMin = from.waterLevelMin;
		to.waterLevelMax = from.waterLevelMax;
		to.waterBlock = from.waterBlock;
		to.iceBlock = from.iceBlock;
		to.ReplaceMatrixBlocks = from.ReplaceMatrixBlocks;

		
		to.SkyColor = from.SkyColor;
		to.WaterColor = from.WaterColor;
		to.GrassColor = from.GrassColor;
		to.FoliageColor = from.FoliageColor;
		
		to.ResourceSequence = from.ResourceSequence;
		to.spawnCreatures = from.spawnCreatures;
		to.spawnWaterCreatures = from.spawnWaterCreatures;
		to.spawnMonsters = from.spawnMonsters;
	}
}
