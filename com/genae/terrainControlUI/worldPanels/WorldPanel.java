package com.genae.terrainControlUI.worldPanels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.genae.terrainControlUI.inputLabels.InputBlockChooser;
import com.genae.terrainControlUI.inputLabels.InputCheckbox;
import com.genae.terrainControlUI.inputLabels.InputColor;
import com.genae.terrainControlUI.inputLabels.InputComboBox;
import com.genae.terrainControlUI.inputLabels.InputFile;
import com.genae.terrainControlUI.inputLabels.InputList;
import com.genae.terrainControlUI.inputLabels.InputSlider;
import com.genae.terrainControlUI.main.InputTab;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.genae.terrainControlUI.util.TCFileWriter;
import com.khorn.terraincontrol.configuration.BiomeConfig;
import com.khorn.terraincontrol.configuration.WorldConfig;
import com.khorn.terraincontrol.configuration.WorldConfig.BiomeMode;
import com.khorn.terraincontrol.configuration.WorldConfig.ImageMode;
import com.khorn.terraincontrol.configuration.WorldConfig.TerrainMode;


public class WorldPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	JTabbedPane tabbedPane;
	InputTab generel;
	public InputTab biomeGenerator;
	InputTab imageGenerator;
	InputTab terrainGenerator;
	InputTab mapObjects;
	InputTab visualSettings;
	InputTab caves;
	InputTab canyons;
	InputTab oldGenerator;
	ImageIcon open;
	Font optionFont = Main.optionFont;
	
	public WorldPanel()
	{
		open = Main.open;
		
		//////////////////////
		//Set Layout
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(ResolutionManager.fontSize(Main.font, 15));
		
		generel = new InputTab(2);
		final InputComboBox<String> modeTerrain = new InputComboBox<String>(new String[]{"Normal", "OldGenerator", "TerrainTest", "NotGenerate", "Default"});
		generel.add("modeTerrain", modeTerrain, "world");
		final InputComboBox<String> modeBiome = new InputComboBox<String>(new String[]{"Normal", "FromImage", "OldGenerator", "Default"});	
		generel.add("modeBiome", modeBiome, "world");
		
		///////////////////////////
		//Additional Listeners
		///////////////////////////
		
		modeTerrain.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(modeTerrain.getString().equals("OldGenerator"))
				{
					tabbedPane.setEnabledAt(8, true);
					modeBiome.box.setSelectedItem("OldGenerator");
					modeBiome.box.setEnabled(false);
				}
				else
				{
					modeBiome.box.setEnabled(true);
				}
			}
			
		});
		modeBiome.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(modeBiome.getString().equals("FromImage"))
				{
					tabbedPane.setEnabledAt(2, true);
					tabbedPane.setEnabledAt(1, false);
					tabbedPane.setEnabledAt(8, false);
				}
				else if(modeBiome.getString().equals("Normal"))
				{
					tabbedPane.setEnabledAt(1, true);
					tabbedPane.setEnabledAt(2, false);
					tabbedPane.setEnabledAt(8, false);
				}
				else if(modeBiome.getString().equals("OldGenerator"))
				{
					tabbedPane.setEnabledAt(1, false);
					tabbedPane.setEnabledAt(2, false);
					tabbedPane.setEnabledAt(8, true);
				}
				else
				{
					tabbedPane.setEnabledAt(1, false);
					tabbedPane.setEnabledAt(2, false);
					tabbedPane.setEnabledAt(8, false);
				}
			}
			
		});
		generel.finalize();
		
		biomeGenerator = new InputTab(17);
		final InputSlider generationDepth = new InputSlider(0, 20, 10);
		biomeGenerator.add("generationDepth", generationDepth, "world");
		InputSlider biomeRarityScale = new InputSlider(0, 100, 100);
		biomeGenerator.add("biomeRarityScale", biomeRarityScale, "world");
		InputSlider landRarity = new InputSlider(0, 100, 97);
		biomeGenerator.add("landRarity", landRarity, "world");
		final InputSlider landSize = new InputSlider(0, 10, 0);
		biomeGenerator.add("landSize", landSize, "world");
		final InputSlider landFuzzy = new InputSlider(0, 10, 6);
		biomeGenerator.add("landFuzzy", landFuzzy, "world");
		InputSlider iceRarity = new InputSlider(0, 100, 90);
		biomeGenerator.add("iceRarity", iceRarity, "world");
		final InputSlider iceSize = new InputSlider(0, 10, 3);
		biomeGenerator.add("iceSize", iceSize, "world");
		InputCheckbox frozenRivers = new InputCheckbox(true);
		biomeGenerator.add("frozenRivers", frozenRivers, "world");
		InputCheckbox frozenOcean = new InputCheckbox(true);
		biomeGenerator.add("frozenOcean", frozenOcean, "world");
		final InputSlider riverRarity = new InputSlider(0, 10, 4);
		biomeGenerator.add("riverRarity", riverRarity, "world");
		final InputSlider riverSize = new InputSlider(0, 10, 0);
		biomeGenerator.add("riverSize", riverSize, "world");
		InputCheckbox riversEnabled = new InputCheckbox(true);
		biomeGenerator.add("riversEnabled", riversEnabled, "world");
		final InputList normalBiomes = new InputList(Main.objManager.getNormalBiomes());
		biomeGenerator.add("normalBiomes", normalBiomes, "world");
		final InputList iceBiomes = new InputList(Main.objManager.getIceBiomes());
		biomeGenerator.add("iceBiomes", iceBiomes, "world");
		final InputList isleBiomes = new InputList(Main.objManager.getIsleBiomes());
		biomeGenerator.add("isleBiomes", isleBiomes, "world");
		final InputList borderBiomes = new InputList(Main.objManager.getBorderBiomes());
		biomeGenerator.add("borderBiomes", borderBiomes, "world");
		final InputList customBiomes = new InputList(Main.objManager.getCustomBiomes(), Main.objManager.getCustomSelected());
		biomeGenerator.add("customBiomes", customBiomes, "world");
		///////////////////////////
		// Add custom Biomes to normal and ice
		for(String name:Main.main.worldConfig.CustomBiomes)
		{
			float temp = 1;
			for(BiomeConfig biome:Main.main.worldConfig.biomes)
			{
				if(biome.Name.equals(name)) temp = biome.BiomeTemperature;
			}
			if(temp > 0)normalBiomes.insert(name, true);
			else 		iceBiomes.insert(name, true);
		}
		///////////////////////////
		//Additional Listeners
		///////////////////////////
			
		generationDepth.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int max = generationDepth.getInt();
				landSize.setMaxValue(max, "Land Size");
				//From 0 to GenerationDepth-landSize
				landFuzzy.setMaxValue(max - landSize.getInt(), "Land Fuzzy");
				iceSize.setMaxValue(max, "Ice Size");
				riverRarity.setMaxValue(max, "River Rarity");
				//From 0 to GenerationDepth-river Rarity
				riverSize.setMaxValue(max - riverRarity.getInt(), "River Size");
			}
					
		});
		landSize.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//From 0 to GenerationDepth-landSize
				landFuzzy.setMaxValue(generationDepth.getInt() - landSize.getInt(), "Land Fuzzy");
			}
					
		});
		riverRarity.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//From 0 to GenerationDepth-river Rarity
				riverSize.setMaxValue(generationDepth.getInt() - riverRarity.getInt(), "River Size");
			}
		});
		customBiomes.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for(JCheckBox box:customBiomes.boxes)
				{
					BiomeConfig biome = TCFileWriter.loadBiomeFromConfig(box.getText());
					if(box.isSelected())
					{
						boolean contains = false;
						for(int i = 0; i < Main.world.settings.biomes.size(); i++)
						{
							if(Main.world.settings.biomes.get(i).Name.equals(biome.Name)) contains = true;
						}
						if(!contains) Main.world.settings.biomes.add(biome);
						if(!contains) Main.main.worldConfig.biomeConfigs = Main.main.worldConfig.biomes.toArray(Main.main.worldConfig.biomeConfigs);
						Main.world.AddBiome(biome.Name, biome.Biome.getId());
						Main.main.worldConfig.CustomBiomeIds.put(biome.Name, biome.Biome.getId());
						Main.main.worldConfig.CustomBiomes.add(biome.Name);
					}
					float temp = biome.BiomeTemperature;

					if(box.isSelected())
					{
						if(temp > 0 && biome.IsleInBiome.size()+biome.BiomeIsBorder.size() == 0)normalBiomes.insert(box.getText(), true);
						else if(biome.IsleInBiome.size()+biome.BiomeIsBorder.size() == 0) 		iceBiomes.insert(box.getText(), true);
						
						if(biome.IsleInBiome.size() != 0) isleBiomes.insert(box.getText(), true);
						if(biome.BiomeIsBorder.size() != 0) borderBiomes.insert(box.getText(), true);
					}	
					else
					{
						if(temp > 0 && biome.IsleInBiome.size()+biome.BiomeIsBorder.size() == 0)normalBiomes.removeBox(box.getText());
						else if(biome.IsleInBiome.size()+biome.BiomeIsBorder.size() == 0)		iceBiomes.removeBox(box.getText());
						
						if(biome.IsleInBiome.size() != 0) isleBiomes.removeBox(box.getText());
						if(biome.BiomeIsBorder.size() != 0) borderBiomes.removeBox(box.getText());
						
						for(BiomeConfig config:Main.world.settings.biomes)
						{
							if(config.Name.equals(box.getText()))
							{
								Main.world.settings.biomes.remove(config);
								Main.world.RemoveBiome(config.Name, config.Biome.getId());
								Main.main.worldConfig.biomeConfigs = Main.main.worldConfig.biomes.toArray(Main.main.worldConfig.biomeConfigs);
								break;
							}
						}
						
					}
				}
			}
		});
		biomeGenerator.finalize();
		if(customBiomes.boxes.length != 0)
		{
			customBiomes.ok.getActionListeners()[0].actionPerformed(null);
		}
		
		imageGenerator = new InputTab(5);
		InputComboBox<String> imageMode = new InputComboBox<String>(new String[]{"Repeat", "ContinueNormal", "FillEmpty"});
		imageGenerator.add("imageMode", imageMode, "world");
		InputFile imageFile = new InputFile("map.png");
		imageGenerator.add("imageFile", imageFile, "world");
		InputComboBox<String> imageFillBiome = new InputComboBox<String>(Main.objManager.getAllBiomes(true));
		imageGenerator.add("imageFillBiome", imageFillBiome, "world");
		InputSlider imageXOffset = new InputSlider(-1000, 1000, 0);
		imageGenerator.add("imageXOffset", imageXOffset, "world");
		InputSlider imageZOffset = new InputSlider(-1000, 1000, 0);
		imageGenerator.add("imageZOffset", imageZOffset, "world");
		imageGenerator.finalize();
		
		terrainGenerator  = new InputTab(12);
		InputSlider worldHeightBits = new InputSlider(5, 8, 7);
		terrainGenerator.add("worldHeightBits", worldHeightBits, "world");
		InputSlider waterLevelMax = new InputSlider(0, 256, 64);
		terrainGenerator.add("waterLevelMax", waterLevelMax, "world");
		InputSlider waterLevelMin = new InputSlider(0, 256, 0);
		terrainGenerator.add("waterLevelMin", waterLevelMin, "world");
		InputBlockChooser waterBlock = new InputBlockChooser(9, false);
		terrainGenerator.add("waterBlock", waterBlock, "world");
		InputBlockChooser iceBlock = new InputBlockChooser(79, false);
		terrainGenerator.add("iceBlock", iceBlock, "world");
		InputSlider fractureHorizontal = new InputSlider(-100, 100, 0, 100);
		terrainGenerator.add("fractureHorizontal", fractureHorizontal, "world");
		InputSlider fractureVertical = new InputSlider(-100, 100, 0, 100);
		terrainGenerator.add("fractureVertical", fractureVertical, "world");
		InputCheckbox removeSurfaceStone = new InputCheckbox(false);
		terrainGenerator.add("removeSurfaceStone", removeSurfaceStone, "world");
		InputCheckbox disableBedrock = new InputCheckbox(false);
		terrainGenerator.add("disableBedrock", disableBedrock, "world");
		InputCheckbox ceilingBedrock = new InputCheckbox(false);
		terrainGenerator.add("ceilingBedrock", ceilingBedrock, "world");
		InputCheckbox flatBedrock = new InputCheckbox(false);
		terrainGenerator.add("flatBedrock", flatBedrock, "world");
		InputBlockChooser bedrockobBlock = new InputBlockChooser(7, false);
		terrainGenerator.add("bedrockobBlock", bedrockobBlock, "world");
		terrainGenerator.finalize();
		
		mapObjects = new InputTab(8);
		InputCheckbox strongholdsEnabled = new InputCheckbox(true);
		mapObjects.add("strongholdsEnabled", strongholdsEnabled, "world");
		InputCheckbox villagesEnabled = new InputCheckbox(true);
		mapObjects.add("villagesEnabled", villagesEnabled, "world");
		InputCheckbox mineshaftsEnabled = new InputCheckbox(true);
		mapObjects.add("mineshaftsEnabled", mineshaftsEnabled, "world");
		InputCheckbox customObjects = new InputCheckbox(true);
		mapObjects.add("customObjects", customObjects, "world");
		InputCheckbox pyramidsEnabled = new InputCheckbox(true);
		mapObjects.add("pyramidsEnabled", pyramidsEnabled, "world");
		InputSlider objectSpawnRatio = new InputSlider(0, 30, 2);
		mapObjects.add("objectSpawnRatio", objectSpawnRatio, "world");
		InputCheckbox DenyObjectsUnderFill = new InputCheckbox(false);
		mapObjects.add("denyObjectsUnderFill", DenyObjectsUnderFill, "world");
		InputSlider customTreeChance = new InputSlider(0, 100, 50);
		mapObjects.add("customTreeChance", customTreeChance, "world");
		mapObjects.finalize();
		
		visualSettings = new InputTab(2);
		InputColor worldFog = new InputColor(0xc0d8ff);
		visualSettings.add("worldFog", worldFog, "world");
		InputColor worldNightFog = new InputColor(0x0b0d17);
		visualSettings.add("worldNightFog", worldNightFog, "world");
		visualSettings.finalize();
		
		caves = new InputTab(10);
		InputSlider caveRarity = new InputSlider(0, 30, 7);
		caves.add("caveRarity", caveRarity, "world");
		InputSlider caveFrequency = new InputSlider(0, 100, 40);
		caves.add("caveFrequency", caveFrequency, "world");
		InputSlider caveMinAltitude = new InputSlider(0, 256, 8);
		caves.add("caveMinAltitude", caveMinAltitude, "world");
		InputSlider caveMaxAltitude = new InputSlider(0, 256, 128);
		caves.add("caveMaxAltitude", caveMaxAltitude, "world");
		InputSlider individualCaveRarity = new InputSlider(0, 100, 25);
		caves.add("individualCaveRarity", individualCaveRarity, "world");
		InputSlider caveSystemFrequency = new InputSlider(0, 20, 10);
		caves.add("caveSystemFrequency", caveSystemFrequency, "world");
		InputSlider caveSystemPocketChance = new InputSlider(0, 10, 0);
		caves.add("caveSystemPocketChance", caveSystemPocketChance, "world");
		InputSlider caveSystemPocketMinSize = new InputSlider(0, 10, 0);
		caves.add("caveSystemPocketMinSize", caveSystemPocketMinSize, "world");
		InputSlider caveSystemPocketMaxSize = new InputSlider(0, 10, 4);
		caves.add("caveSystemPocketMaxSize", caveSystemPocketMaxSize, "world");
		InputCheckbox evenCaveDistribution = new InputCheckbox(false);
		caves.add("evenCaveDistribution", evenCaveDistribution, "world");				
		caves.finalize();
		
		canyons = new InputTab(6);
		InputSlider canyonRarity = new InputSlider(0, 10, 2);
		canyons.add("canyonRarity", canyonRarity, "world");
		InputSlider canyonMinAltitude = new InputSlider(0, 256, 20);
		canyons.add("canyonMinAltitude", canyonMinAltitude, "world");
		InputSlider canyonMaxAltitude = new InputSlider(0, 256, 64);
		canyons.add("canyonMaxAltitude", canyonMaxAltitude, "world");
		InputSlider canyonMinLength = new InputSlider(0, 512, 84);
		canyons.add("canyonMinLength", canyonMinLength, "world");
		InputSlider canyonMaxLength = new InputSlider(0, 512, 112);
		canyons.add("canyonMaxLength", canyonMaxLength, "world");
		InputSlider canyonDepth = new InputSlider(0, 100, 30, 10);
		canyons.add("canyonDepth", canyonDepth, "world");
		canyons.finalize();
		
		oldGenerator = new InputTab(5);
		InputSlider oldBiomeSize = new InputSlider(0, 50, 15, 10);
		oldGenerator.add("oldBiomeSize", oldBiomeSize, "world");
		InputSlider minMoisture = new InputSlider(0, 50, 0, 10);
		oldGenerator.add("minMoisture", minMoisture, "world");
		InputSlider maxMoisture = new InputSlider(0, 50, 10, 10);
		oldGenerator.add("maxMoisture", maxMoisture, "world");
		InputSlider minTemperature = new InputSlider(-50, 50, 0, 10);
		oldGenerator.add("minTemperature", minTemperature, "world");
		InputSlider maxTemperature = new InputSlider(-50, 50, 10, 10);
		oldGenerator.add("maxTemperature", maxTemperature, "world");
		oldGenerator.finalize();
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addTab("Generel", generel);
		tabbedPane.addTab("Biomes", biomeGenerator);
		tabbedPane.addTab("Image Gen", imageGenerator);
		tabbedPane.addTab("Terrain", terrainGenerator);
		tabbedPane.addTab("Map Objects", mapObjects);
		tabbedPane.addTab("Visual Settings", visualSettings);
		tabbedPane.addTab("Caves", caves);
		tabbedPane.addTab("Canyons", canyons);
		tabbedPane.addTab("Old Generator", oldGenerator);
		
		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.setEnabledAt(8, false);
		tabbedPane.setEnabledAt(2, false);
		
		load(Main.main.worldConfig);
		

	}
	
	public void save()
	{
		WorldConfig config = Main.world.settings;
		////////////////////////
		//Get Terrain Mode
		////////////////////////
		String mode = generel.getComboBox("modeTerrain").getString();
		if(mode.equals("Normal"))
		{
			config.ModeTerrain = TerrainMode.Normal;
		}
		else if(mode.equals("OldGenerator"))
		{
			config.ModeTerrain = TerrainMode.OldGenerator;
		}
		else if(mode.equals("TerrainTest"))
		{
			config.ModeTerrain = TerrainMode.TerrainTest;
		}
		else if(mode.equals("NotGenerate"))
		{
			config.ModeTerrain = TerrainMode.NotGenerate;
		}
		else
		{
			config.ModeTerrain = TerrainMode.Default;
		}
		
		////////////////////////
		//Get Biome Mode
		////////////////////////
		mode = generel.getComboBox("modeBiome").getString();
		if(mode.equals("Normal"))
		{
			config.ModeBiome = BiomeMode.Normal;
		}
		else if(mode.equals("OldGenerator"))
		{
			config.ModeBiome = BiomeMode.OldGenerator;
		}
		else if(mode.equals("FromImage"))
		{
			config.ModeBiome = BiomeMode.FromImage;
		}
		else
		{
			config.ModeBiome = BiomeMode.Default;
		}
		
		////////////////////////
		//Get BiomeGenerator
		////////////////////////
		
		config.GenerationDepth = biomeGenerator.getSlider("generationDepth").getInt();
		config.BiomeRarityScale = biomeGenerator.getSlider("biomeRarityScale").getInt();
		config.LandRarity = biomeGenerator.getSlider("landRarity").getInt();
		config.LandSize = biomeGenerator.getSlider("landSize").getInt();
		config.LandFuzzy = biomeGenerator.getSlider("landFuzzy").getInt();
		config.IceRarity = biomeGenerator.getSlider("iceRarity").getInt();
		config.IceSize = biomeGenerator.getSlider("iceSize").getInt();
		config.FrozenRivers = biomeGenerator.getCheckbox("frozenRivers").getValue();
		config.FrozenOcean = biomeGenerator.getCheckbox("frozenOcean").getValue();
		config.RiverRarity = biomeGenerator.getSlider("riverRarity").getInt();
		config.RiverSize = biomeGenerator.getSlider("riverSize").getInt();
		config.RiversEnabled = biomeGenerator.getCheckbox("riversEnabled").getValue();
		config.NormalBiomes = biomeGenerator.getList("normalBiomes").getArrayList();
		config.IceBiomes = biomeGenerator.getList("iceBiomes").getArrayList();
		config.IsleBiomes = biomeGenerator.getList("isleBiomes").getArrayList();
		config.BorderBiomes = biomeGenerator.getList("borderBiomes").getArrayList();
		config.CustomBiomes = biomeGenerator.getList("customBiomes").getArrayList();
		////////////////////////
		//Get imageGenerator
		////////////////////////
		
		////////////////////////
		//Get Image Mode
		////////////////////////
		mode = imageGenerator.getComboBox("imageMode").getString();
		if(mode.equals("Repeat"))
		{
		config.imageMode = ImageMode.Repeat;
		}
		else if(mode.equals("ContinueNormal"))
		{
		config.imageMode = ImageMode.ContinueNormal;
		}
		else
		{
		config.imageMode = ImageMode.FillEmpty;
		}
		config.imageFile = imageGenerator.getFile("imageFile").getValue();
		config.imageFillBiome = imageGenerator.getComboBox("imageFillBiome").getString();
		config.imageXOffset = imageGenerator.getSlider("imageXOffset").getInt();
		config.imageZOffset = imageGenerator.getSlider("imageZOffset").getInt();
		
		////////////////////////
		//Get terrainGenerator
		////////////////////////
		
		config.worldHeightBits = terrainGenerator.getSlider("worldHeightBits").getInt();
		config.waterLevelMax = terrainGenerator.getSlider("waterLevelMax").getInt();
		config.waterLevelMin = terrainGenerator.getSlider("waterLevelMin").getInt();
		config.waterBlock = terrainGenerator.getBlockChooser("waterBlock").getInt();
		config.iceBlock = terrainGenerator.getBlockChooser("iceBlock").getInt();
		config.fractureHorizontal = terrainGenerator.getSlider("fractureHorizontal").getDouble();
		config.fractureVertical = terrainGenerator.getSlider("fractureVertical").getDouble();
		config.removeSurfaceStone = terrainGenerator.getCheckbox("removeSurfaceStone").getValue();
		config.disableBedrock = terrainGenerator.getCheckbox("disableBedrock").getValue();
		config.ceilingBedrock = terrainGenerator.getCheckbox("ceilingBedrock").getValue();
		config.flatBedrock = terrainGenerator.getCheckbox("flatBedrock").getValue();
		config.bedrockBlock = terrainGenerator.getBlockChooser("bedrockobBlock").getInt();
		
		////////////////////////
		//Get mapObjects
		////////////////////////
		
		config.StrongholdsEnabled = mapObjects.getCheckbox("strongholdsEnabled").getValue();
		config.VillagesEnabled = mapObjects.getCheckbox("villagesEnabled").getValue();
		config.MineshaftsEnabled = mapObjects.getCheckbox("mineshaftsEnabled").getValue();
		config.PyramidsEnabled = mapObjects.getCheckbox("pyramidsEnabled").getValue();
		
		////////////////////////
		//Get VisualSettings
		////////////////////////
		
		config.WorldFog = visualSettings.getColor("worldFog").getInt();
		config.WorldNightFog = visualSettings.getColor("worldNightFog").getInt();
		
		////////////////////////
		//Get bo2settings
		////////////////////////
		
		config.customObjects = mapObjects.getCheckbox("customObjects").getValue();
		config.objectSpawnRatio = mapObjects.getSlider("objectSpawnRatio").getInt();
		config.denyObjectsUnderFill = mapObjects.getCheckbox("denyObjectsUnderFill").getValue();
		config.customTreeChance = mapObjects.getSlider("customTreeChance").getInt();
		
		////////////////////////
		//Get cave
		////////////////////////
		
		config.caveRarity = caves.getSlider("caveRarity").getInt();
		config.caveFrequency = caves.getSlider("caveFrequency").getInt();
		config.caveMinAltitude = caves.getSlider("caveMinAltitude").getInt();
		config.caveMaxAltitude = caves.getSlider("caveMaxAltitude").getInt();
		config.individualCaveRarity = caves.getSlider("individualCaveRarity").getInt();
		config.caveSystemFrequency = caves.getSlider("caveSystemFrequency").getInt();
		config.caveSystemPocketChance = caves.getSlider("caveSystemPocketChance").getInt();
		config.caveSystemPocketMinSize = caves.getSlider("caveSystemPocketMinSize").getInt();
		config.caveSystemPocketMaxSize = caves.getSlider("caveSystemPocketMaxSize").getInt();
		config.evenCaveDistribution = caves.getCheckbox("evenCaveDistribution").getValue();
		
		////////////////////////
		//Get Canyon
		////////////////////////
		
		config.canyonRarity = canyons.getSlider("canyonRarity").getInt();
		config.canyonMinAltitude = canyons.getSlider("canyonMinAltitude").getInt();
		config.canyonMaxAltitude = canyons.getSlider("canyonMaxAltitude").getInt();
		config.canyonMinLength = canyons.getSlider("canyonMinLength").getInt();
		config.canyonMaxLength = canyons.getSlider("canyonMaxLength").getInt();
		config.canyonDepth = canyons.getSlider("canyonDepth").getDouble();
		
		////////////////////////
		//Get OldGenerator
		////////////////////////
		
		config.oldBiomeSize = oldGenerator.getSlider("oldBiomeSize").getDouble();
		config.minMoisture = oldGenerator.getSlider("minMoisture").getFloat();
		config.maxMoisture = oldGenerator.getSlider("maxMoisture").getFloat();
		config.minTemperature = oldGenerator.getSlider("minTemperature").getFloat();
		config.maxTemperature = oldGenerator.getSlider("maxTemperature").getFloat();

	}
	public void load(WorldConfig settings)
	{
		////////////////////////
		//Set Generel
		////////////////////////
		generel.getComboBox("modeTerrain").setValue(settings.ModeTerrain.name());
		generel.getComboBox("modeBiome").setValue(settings.ModeBiome.name());

		
		////////////////////////
		//Set BiomeGenerator
		////////////////////////
		
		biomeGenerator.getSlider("generationDepth").setValue(settings.GenerationDepth);
		biomeGenerator.getSlider("biomeRarityScale").setValue(settings.BiomeRarityScale);
		biomeGenerator.getSlider("landRarity").setValue(settings.LandRarity);
		biomeGenerator.getSlider("landSize").setValue(settings.LandSize);
		biomeGenerator.getSlider("landFuzzy").setValue(settings.LandFuzzy);
		biomeGenerator.getSlider("iceRarity").setValue(settings.IceRarity);
		biomeGenerator.getSlider("iceSize").setValue(settings.IceSize);
		biomeGenerator.getCheckbox("frozenRivers").setValue(settings.FrozenRivers);
		biomeGenerator.getCheckbox("frozenOcean").setValue(settings.FrozenOcean);
		biomeGenerator.getSlider("riverRarity").setValue(settings.RiverRarity);
		biomeGenerator.getSlider("riverSize").setValue(settings.RiverSize);
		biomeGenerator.getCheckbox("riversEnabled").setValue(settings.RiversEnabled);
		biomeGenerator.getList("normalBiomes").setValue(settings.NormalBiomes.toArray(new String[0]));
		biomeGenerator.getList("iceBiomes").setValue(settings.IceBiomes.toArray(new String[0]));
		biomeGenerator.getList("isleBiomes").setValue(settings.IsleBiomes.toArray(new String[0]));
		biomeGenerator.getList("borderBiomes").setValue(settings.BorderBiomes.toArray(new String[0]));
		biomeGenerator.getList("customBiomes").setValue(settings.CustomBiomes.toArray(new String[0]));
		
		////////////////////////
		//Set imageGenerator
		////////////////////////
		imageGenerator.getComboBox("imageMode").setValue(settings.imageMode.name());
		imageGenerator.getFile("imageFile").setValue(settings.imageFile);
		imageGenerator.getComboBox("imageFillBiome").setValue(settings.imageFillBiome);
		imageGenerator.getSlider("imageXOffset").setValue(settings.imageXOffset);
		imageGenerator.getSlider("imageZOffset").setValue(settings.imageZOffset);
		
		////////////////////////
		//Set terrainGenerator
		////////////////////////
		
		terrainGenerator.getSlider("worldHeightBits").setValue(settings.worldHeightBits);
		terrainGenerator.getSlider("waterLevelMax").setValue(settings.waterLevelMax);
		terrainGenerator.getSlider("waterLevelMin").setValue(settings.waterLevelMin);
		terrainGenerator.getBlockChooser("waterBlock").setValue(settings.waterBlock);
		terrainGenerator.getBlockChooser("iceBlock").setValue(settings.iceBlock);
		terrainGenerator.getSlider("fractureHorizontal").setValue(settings.fractureHorizontal);
		terrainGenerator.getSlider("fractureVertical").setValue(settings.fractureVertical);
		terrainGenerator.getCheckbox("removeSurfaceStone").setValue(settings.removeSurfaceStone);
		terrainGenerator.getCheckbox("disableBedrock").setValue(settings.disableBedrock);
		terrainGenerator.getCheckbox("ceilingBedrock").setValue(settings.ceilingBedrock);
		terrainGenerator.getCheckbox("flatBedrock").setValue(settings.flatBedrock);
		terrainGenerator.getBlockChooser("bedrockobBlock").setValue(settings.bedrockBlock);
		
		////////////////////////
		//Set mapObjects
		////////////////////////
		
		mapObjects.getCheckbox("strongholdsEnabled").setValue(settings.StrongholdsEnabled);
		mapObjects.getCheckbox("villagesEnabled").setValue(settings.VillagesEnabled);
		mapObjects.getCheckbox("mineshaftsEnabled").setValue(settings.MineshaftsEnabled);
		mapObjects.getCheckbox("pyramidsEnabled").setValue(settings.PyramidsEnabled);
		
		////////////////////////
		//Set VisualSettings
		////////////////////////
		
		visualSettings.getColor("worldFog").setValue(settings.WorldFog);
		visualSettings.getColor("worldNightFog").setValue(settings.WorldNightFog);
		
		////////////////////////
		//Set bo2settings
		////////////////////////
		
		mapObjects.getCheckbox("customObjects").setValue(settings.customObjects);
		mapObjects.getSlider("objectSpawnRatio").setValue(settings.objectSpawnRatio);
		mapObjects.getCheckbox("denyObjectsUnderFill").setValue(settings.denyObjectsUnderFill);
		mapObjects.getSlider("customTreeChance").setValue(settings.customTreeChance);
		
		////////////////////////
		//Set cave
		////////////////////////
		
		caves.getSlider("caveRarity").setValue(settings.caveRarity);
		caves.getSlider("caveFrequency").setValue(settings.caveFrequency);
		caves.getSlider("caveMinAltitude").setValue(settings.caveMinAltitude);
		caves.getSlider("caveMaxAltitude").setValue(settings.caveMaxAltitude);
		caves.getSlider("individualCaveRarity").setValue(settings.individualCaveRarity);
		caves.getSlider("caveSystemFrequency").setValue(settings.caveSystemFrequency);
		caves.getSlider("caveSystemPocketChance").setValue(settings.caveSystemPocketChance);
		caves.getSlider("caveSystemPocketMinSize").setValue(settings.caveSystemPocketMinSize);
		caves.getSlider("caveSystemPocketMaxSize").setValue(settings.caveSystemPocketMaxSize);
		caves.getCheckbox("evenCaveDistribution").setValue(settings.evenCaveDistribution);
		
		////////////////////////
		//Set Canyon
		////////////////////////
		
		canyons.getSlider("canyonRarity").setValue(settings.canyonRarity);
		canyons.getSlider("canyonMinAltitude").setValue(settings.canyonMinAltitude);
		canyons.getSlider("canyonMaxAltitude").setValue(settings.canyonMaxAltitude);
		canyons.getSlider("canyonMinLength").setValue(settings.canyonMinLength);
		canyons.getSlider("canyonMaxLength").setValue(settings.canyonMaxLength);
		canyons.getSlider("canyonDepth").setValue(settings.canyonDepth);
		
		////////////////////////
		//Set OldGenerator
		////////////////////////
		
		oldGenerator.getSlider("oldBiomeSize").setValue(settings.oldBiomeSize);
		oldGenerator.getSlider("minMoisture").setValue(settings.minMoisture);
		oldGenerator.getSlider("maxMoisture").setValue(settings.maxMoisture);
		oldGenerator.getSlider("minTemperature").setValue(settings.minTemperature);
		oldGenerator.getSlider("maxTemperature").setValue(settings.maxTemperature);

	}
}

