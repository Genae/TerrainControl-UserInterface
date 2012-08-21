package com.genae.terrainControlUI.biomePanels;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.genae.terrainControlUI.inputLabels.InputBlockChooser;
import com.genae.terrainControlUI.inputLabels.InputCheckbox;
import com.genae.terrainControlUI.inputLabels.InputColor;
import com.genae.terrainControlUI.inputLabels.InputComboBox;
import com.genae.terrainControlUI.inputLabels.InputHeight;
import com.genae.terrainControlUI.inputLabels.InputList;
import com.genae.terrainControlUI.inputLabels.InputReplaceBlocks;
import com.genae.terrainControlUI.inputLabels.InputSlider;
import com.genae.terrainControlUI.main.InputTab;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.khorn.terraincontrol.DefaultMaterial;
import com.khorn.terraincontrol.configuration.BiomeConfig;
import com.khorn.terraincontrol.configuration.Resource;
import com.khorn.terraincontrol.generator.resourcegens.ResourceType;



public class BiomesPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	JTabbedPane tabbedPane;
	public JComboBox<String> biomeSelect;
	InputTab generel, terrainGenerator, visualSettings;
	InputResources resources;
	InputMonster mobSpawning;
	ImageIcon open;
	BiomeConfig currentBiome;
	Main main;
	JLabel name;
	boolean custom = false;
	boolean change = false;
	
	Font optionFont = Main.optionFont;
	
	public BiomesPanel(Main main, BiomeConfig biome)
	{
		currentBiome = biome;
		custom = true;
		construct(main);
	}
	public BiomesPanel(Main main)
	{
		construct(main);
	}
	private void construct(Main main)
	{
		///////////////////////////7
		// Init Elements
		this.main = main;
		open = Main.open;
		
		if(!custom)biomeSelect = new JComboBox<String>(Main.objManager.getAllBiomes(false));
		if(!custom)biomeSelect.setFont(ResolutionManager.fontSize(Main.font, 19));
		
		//////////////////////////
		//Init Inputs
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(ResolutionManager.fontSize(Main.font, 15));
		
		generel = new InputTab(10);
		InputSlider biomeSize = new InputSlider(0, 20, 8);
		generel.add("biomeSize", biomeSize, "biome");
		InputSlider biomeRarity = new InputSlider(0, 100, 100);
		generel.add("biomeRarity", biomeRarity, "biome");
		InputColor biomeColor = new InputColor(0xFFFF00);
		generel.add("biomeColor", biomeColor, "biome");
		InputCheckbox biomeRivers = new InputCheckbox(true);
		generel.add("biomeRivers", biomeRivers, "biome");
		InputList isleInBiome = new InputList(Main.objManager.getAllBiomes(true));
		generel.add("isleInBiome", isleInBiome, "biome");
		InputList biomeIsBorder = new InputList(Main.objManager.getAllBiomes(true));
		generel.add("biomeIsBorder", biomeIsBorder, "biome");
		InputList notBorderNear = new InputList(Main.objManager.getAllBiomes(true));
		generel.add("notBorderNear", notBorderNear, "biome");
		InputSlider biomeTemperature = new InputSlider(0, 100, 80, 100);
		generel.add("biomeTemperature", biomeTemperature, "biome");
		InputSlider biomeWetness = new InputSlider(0, 100, 40, 100);
		generel.add("biomeWetness", biomeWetness, "biome");
		String[] biomes = new String[Main.objManager.getAllBiomes(true).length + 1];
		for(int i = 1; i < biomes.length; i++)
		{
			biomes[i] = Main.objManager.getAllBiomes(true)[i-1];
		}
		biomes[0] = "";
		InputComboBox<String> replaceToBiomeName  = new InputComboBox<String>(biomes);
		generel.add("replaceToBiomeName", replaceToBiomeName, "biome");
		generel.finalize();
		
		terrainGenerator = new InputTab(19);
		InputSlider biomeHeight = new InputSlider(-100, 100, 8, 10);
		terrainGenerator.add("biomeHeight", biomeHeight, "biome");
		InputSlider biomeVolatility = new InputSlider(0, 100, 10, 100);
		terrainGenerator.add("biomeVolatility", biomeVolatility, "biome");
		InputSlider maxAverageHeight = new InputSlider(-100, 100, 0, 10);
		terrainGenerator.add("maxAverageHeight", maxAverageHeight, "biome");
		InputSlider maxAverageDepth = new InputSlider(-100, 100, 0, 10);
		terrainGenerator.add("maxAverageDepth", maxAverageDepth, "biome");
		InputSlider volatility1 = new InputSlider(-100, 100, 0, 10);
		terrainGenerator.add("volatility1", volatility1, "biome");
		InputSlider volatility2 = new InputSlider(-100, 100, 0, 10);
		terrainGenerator.add("volatility2", volatility2, "biome");
		InputSlider volatilityWeight1 = new InputSlider(0, 100, 50, 100);
		terrainGenerator.add("volatilityWeight1", volatilityWeight1, "biome");
		InputSlider volatilityWeight2 = new InputSlider(0, 100, 45, 100);
		terrainGenerator.add("volatilityWeight2", volatilityWeight2, "biome");
		InputCheckbox disableBiomeHeight= new InputCheckbox(false);
		terrainGenerator.add("disableBiomeHeight", disableBiomeHeight, "biome");
		InputHeight customHeightControl = new InputHeight();
		terrainGenerator.add("customHeightControl", customHeightControl, "biome");
		InputBlockChooser surfaceBlock = new InputBlockChooser(12, false);
		terrainGenerator.add("surfaceBlock", surfaceBlock, "biome");
		InputBlockChooser groundBlock = new InputBlockChooser(12, false);
		terrainGenerator.add("groundBlock", groundBlock, "biome");
		InputCheckbox useWorldWaterLevel = new InputCheckbox(true);
		terrainGenerator.add("useWorldWaterLevel", useWorldWaterLevel, "biome");
		InputSlider waterLevelMin = new InputSlider(0, 256, 0);
		terrainGenerator.add("waterLevelMin", waterLevelMin, "biome");
		InputSlider waterLevelMax = new InputSlider(0, 256, 64);
		terrainGenerator.add("waterLevelMax", waterLevelMax, "biome");
		InputBlockChooser waterBlock = new InputBlockChooser(9, false);
		terrainGenerator.add("waterBlock", waterBlock, "biome");
		InputBlockChooser iceBlock = new InputBlockChooser(79, false);
		terrainGenerator.add("iceBlock", iceBlock, "biome");
		InputReplaceBlocks replacedBlocks= new InputReplaceBlocks(12);
		terrainGenerator.add("replacedBlocks", replacedBlocks, "biome");
		terrainGenerator.finalize();
		
		visualSettings = new InputTab(4);
		InputColor skyColor = new InputColor(0x7ba5ff);
		visualSettings.add("skyColor", skyColor, "biome");
		InputColor waterColor = new InputColor(0xffffff);
		visualSettings.add("waterColor", waterColor, "biome");
		InputColor grassColor = new InputColor(0xffffff);
		visualSettings.add("grassColor", grassColor, "biome");
		InputColor foliageColor = new InputColor(0xffffff);
		visualSettings.add("foliageColor", foliageColor, "biome");
		visualSettings.finalize();
		
		//////////////////////
		//Set Layout
		resources = new InputResources();
		mobSpawning = new InputMonster();
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addTab("Generel", generel);
		tabbedPane.addTab("Terrain", terrainGenerator);
		tabbedPane.addTab("Visual Settings", visualSettings);
		tabbedPane.addTab("Ressources", resources);
		tabbedPane.addTab("Monster", mobSpawning);
		
		
		setLayout(new BorderLayout());
		if(!custom)add(biomeSelect, BorderLayout.PAGE_START);
		else
		{
			System.out.println(currentBiome.Name);
			JPanel top = new JPanel(new GridLayout(1,1,30, 10));
			name = new JLabel(currentBiome.Name);
			top.add(name);
			name.setFont(ResolutionManager.fontSize(Main.font, 30));
			add(top, BorderLayout.PAGE_START);
		}
		add(tabbedPane, BorderLayout.CENTER);
		
		///////////////////////
		// Load default Ressource Order
		///////////////////////
		resources.add(new Resource(ResourceType.SmallLake, DefaultMaterial.WATER.id, 4, 7, 8, 120), "");
		resources.add(new Resource(ResourceType.SmallLake, DefaultMaterial.LAVA.id, 2, 3, 8, 120), "");
		resources.add(new Resource(ResourceType.UnderGroundLake, 25, 60, 2, 5, 0, 50), "");
		resources.add(new Resource(ResourceType.Dungeon), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.DIRT.id, 0, 32, 20, 100, 0, 128, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.GRAVEL.id, 0, 32, 10, 100, 0, 128, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.CLAY.id, 0, 32, 1, 100, 0, 128, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.COAL_ORE.id, 0, 16, 20, 100, 0, 128, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.IRON_ORE.id, 0, 8, 20, 100, 0, 64, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.GOLD_ORE.id, 0, 8, 2, 100, 0, 32, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.REDSTONE_ORE.id, 0, 7, 8, 100, 0, 16, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.DIAMOND_ORE.id, 0, 7, 1, 100, 0, 16, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.LAPIS_ORE.id, 0, 7, 1, 100, 0, 16, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Ore, DefaultMaterial.EMERALD_ORE.id, 0, 5, 1, 100, 4, 32, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.UnderWaterOre, DefaultMaterial.SAND.id , 0, 7, 4, 100, 0, 128, new int[]{DefaultMaterial.DIRT.id, DefaultMaterial.GRASS.id}), "");
		resources.add(new Resource(ResourceType.UnderWaterOre, DefaultMaterial.CLAY.id , 0, 4, 1, 100, 0, 128, new int[]{DefaultMaterial.DIRT.id, DefaultMaterial.CLAY.id}), "");
		resources.add(new Resource(ResourceType.CustomObject), "");
		resources.add(new Resource(ResourceType.Tree), "");
		resources.add(new Resource(ResourceType.AboveWaterRes, DefaultMaterial.WATER_LILY.id, 0, 0, 1, 100), "");
		resources.add(new Resource(ResourceType.Plant, DefaultMaterial.RED_ROSE.id, 0, 0, 2, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id, DefaultMaterial.SOIL.id}), "");
		resources.add(new Resource(ResourceType.Plant, DefaultMaterial.YELLOW_FLOWER.id, 0, 0, 2, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id, DefaultMaterial.SOIL.id}), "");
		resources.add(new Resource(ResourceType.Plant, DefaultMaterial.RED_MUSHROOM.id, 0, 0, 8, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id}), "");
		resources.add(new Resource(ResourceType.Plant, DefaultMaterial.BROWN_MUSHROOM.id, 0, 0, 8, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id}), "");
		resources.add(new Resource(ResourceType.Grass, DefaultMaterial.LONG_GRASS.id, 1, 0, 10, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id}), "");
		resources.add(new Resource(ResourceType.Grass, DefaultMaterial.DEAD_BUSH.id, 0, 0, 4, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id}), "");
		resources.add(new Resource(ResourceType.Plant, DefaultMaterial.PUMPKIN.id, 0, 0, 1, 3, 0, 128, new int[]{DefaultMaterial.GRASS.id}), "");
		resources.add(new Resource(ResourceType.Reed, DefaultMaterial.SUGAR_CANE_BLOCK.id, 0, 0, 50, 100, 0, 128, new int[]{DefaultMaterial.GRASS.id, DefaultMaterial.DIRT.id, DefaultMaterial.SAND.id}), "");
		resources.add(new Resource(ResourceType.Cactus, DefaultMaterial.CACTUS.id, 0, 0, 10, 100, 0, 128, new int[]{DefaultMaterial.SAND.id}), "");
		resources.add(new Resource(ResourceType.Vines), "");
		resources.add(new Resource(ResourceType.Liquid, DefaultMaterial.WATER.id, 0, 0, 20, 100, 8, 128, new int[]{DefaultMaterial.STONE.id}), "");
		resources.add(new Resource(ResourceType.Liquid, DefaultMaterial.LAVA.id, 0, 0, 10, 100, 8, 128, new int[]{DefaultMaterial.STONE.id}), "");
		
		for(BiomeConfig config : Main.world.settings.biomes)
		{
			resources.loadBiomeSettings(config.ResourceSequence, config.Name);
		}
		
		//
		//    + + + + ALL
		//
		if(!custom) currentBiome = Main.getConfig(biomeSelect.getSelectedItem().toString());
		loadConfig(currentBiome);
		if(!custom) biomeSelect.addActionListener(new ActionListener()
		{
		
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				change = true;
				saveConfig();
				currentBiome = Main.getConfig(biomeSelect.getSelectedItem().toString());
				loadConfig(currentBiome);
				change = false;
			}
		
		});

	}

	public void saveConfig()
	{
		currentBiome.BiomeSize = generel.getSlider("biomeSize").getInt();
		currentBiome.BiomeRarity = generel.getSlider("biomeRarity").getInt();
		currentBiome.BiomeColor = generel.getColor("biomeColor").getString();
		currentBiome.BiomeRivers = generel.getCheckbox("biomeRivers").getValue();
		currentBiome.IsleInBiome = generel.getList("isleInBiome").getArrayList();
		currentBiome.BiomeIsBorder = generel.getList("biomeIsBorder").getArrayList();
		currentBiome.NotBorderNear = generel.getList("notBorderNear").getArrayList();
		currentBiome.BiomeTemperature = generel.getSlider("biomeTemperature").getFloat();
		currentBiome.BiomeWetness = generel.getSlider("biomeWetness").getFloat();
		currentBiome.ReplaceBiomeName = generel.getComboBox("replaceToBiomeName").getString();


		currentBiome.BiomeHeight = terrainGenerator.getSlider("biomeHeight").getFloat();
		currentBiome.BiomeVolatility = terrainGenerator.getSlider("biomeVolatility").getFloat();
		currentBiome.maxAverageHeight = terrainGenerator.getSlider("maxAverageHeight").getDouble();
		currentBiome.maxAverageDepth = terrainGenerator.getSlider("maxAverageDepth").getDouble();
		currentBiome.volatility1 = terrainGenerator.getSlider("volatility1").getDouble();
		currentBiome.volatility2 = terrainGenerator.getSlider("volatility2").getDouble();
		currentBiome.volatilityWeight1 = (terrainGenerator.getSlider("volatilityWeight1").getDouble() - 0.5D) * 24.0D;
		currentBiome.volatilityWeight2 = (0.5D - terrainGenerator.getSlider("volatilityWeight2").getDouble()) * 24.0D;
		currentBiome.disableNotchHeightControl = terrainGenerator.getCheckbox("disableBiomeHeight").getValue();
		currentBiome.heightMatrix = terrainGenerator.getHeightControl("customHeightControl").getDoubleArray();
		currentBiome.SurfaceBlock = terrainGenerator.getBlockChooser("surfaceBlock").getByte();
		currentBiome.GroundBlock = terrainGenerator.getBlockChooser("groundBlock").getByte();
		currentBiome.UseWorldWaterLevel = terrainGenerator.getCheckbox("useWorldWaterLevel").getValue();
		currentBiome.waterLevelMin = terrainGenerator.getSlider("waterLevelMin").getInt();
		currentBiome.waterLevelMax = terrainGenerator.getSlider("waterLevelMax").getInt();
		currentBiome.waterBlock = terrainGenerator.getBlockChooser("waterBlock").getInt();
		currentBiome.iceBlock = terrainGenerator.getBlockChooser("iceBlock").getInt();
		currentBiome.ReplaceMatrixBlocks = terrainGenerator.getReplaceBlocks("replacedBlocks").getShortArray();

		
		currentBiome.SkyColor = visualSettings.getColor("skyColor").getInt();
		currentBiome.WaterColor = visualSettings.getColor("waterColor").getInt();
		currentBiome.GrassColor = visualSettings.getColor("grassColor").getInt();
		currentBiome.FoliageColor = visualSettings.getColor("foliageColor").getInt();
		
		currentBiome.ResourceSequence = resources.getResources(currentBiome.Name);
		currentBiome.spawnCreatures = mobSpawning.getMonsters(currentBiome.Name, MonsterType.Creature);
		currentBiome.spawnWaterCreatures = mobSpawning.getMonsters(currentBiome.Name, MonsterType.WaterCreature);
		currentBiome.spawnMonsters = mobSpawning.getMonsters(currentBiome.Name, MonsterType.Monster);
	}
	public void loadConfig(BiomeConfig config)
	{
		if(custom)name.setText(config.Name);
		currentBiome = config;
		generel.getSlider("biomeSize").setValue(config.BiomeSize);
		generel.getSlider("biomeRarity").setValue(config.BiomeRarity);
		generel.getColor("biomeColor").setValue(config.BiomeColor);
		generel.getCheckbox("biomeRivers").setValue(config.BiomeRivers);
		generel.getList("isleInBiome").setValue(config.IsleInBiome.toArray(new String[]{}));
		generel.getList("biomeIsBorder").setValue(config.BiomeIsBorder.toArray(new String[]{}));
		generel.getList("notBorderNear").setValue(config.NotBorderNear.toArray(new String[]{}));
		generel.getSlider("biomeTemperature").setValue(config.BiomeTemperature);
		generel.getSlider("biomeWetness").setValue(config.BiomeWetness);
		generel.getComboBox("replaceToBiomeName").setValue(config.ReplaceBiomeName);


		terrainGenerator.getSlider("biomeHeight").setValue(config.BiomeHeight);
		terrainGenerator.getSlider("biomeVolatility").setValue(config.BiomeVolatility);
		terrainGenerator.getSlider("maxAverageHeight").setValue(config.maxAverageHeight);
		terrainGenerator.getSlider("maxAverageDepth").setValue(config.maxAverageDepth);
		terrainGenerator.getSlider("volatility1").setValue(config.volatility1);
		terrainGenerator.getSlider("volatility2").setValue(config.volatility2);
		terrainGenerator.getSlider("volatilityWeight1").setValue(config.volatilityWeight1/24+0.5D);
		terrainGenerator.getSlider("volatilityWeight2").setValue(-config.volatilityWeight2/24+0.5D);
		terrainGenerator.getCheckbox("disableBiomeHeight").setValue(config.disableNotchHeightControl);
		terrainGenerator.getHeightControl("customHeightControl").setValue(config.heightMatrix);
		terrainGenerator.getBlockChooser("surfaceBlock").setValue(config.SurfaceBlock);
		terrainGenerator.getBlockChooser("groundBlock").setValue(config.GroundBlock);
		terrainGenerator.getCheckbox("useWorldWaterLevel").setValue(config.UseWorldWaterLevel);
		terrainGenerator.getSlider("waterLevelMin").setValue(config.waterLevelMin);
		terrainGenerator.getSlider("waterLevelMax").setValue(config.waterLevelMax);
		terrainGenerator.getBlockChooser("waterBlock").setValue(config.waterBlock);
		terrainGenerator.getBlockChooser("iceBlock").setValue(config.iceBlock);
		terrainGenerator.getReplaceBlocks("replacedBlocks").setValue(config.ReplaceMatrixBlocks);

		
		visualSettings.getColor("skyColor").setValue(config.SkyColor);
		visualSettings.getColor("waterColor").setValue(config.WaterColor);
		visualSettings.getColor("grassColor").setValue(config.GrassColor);
		visualSettings.getColor("foliageColor").setValue(config.FoliageColor);
		
		resources.loadBiomeSettings(config.ResourceSequence, config.Name);
		mobSpawning.loadBiomeSettings(config.spawnCreatures, config.Name, MonsterType.Creature);
		mobSpawning.loadBiomeSettings(config.spawnWaterCreatures, config.Name, MonsterType.WaterCreature);
		mobSpawning.loadBiomeSettings(config.spawnMonsters, config.Name, MonsterType.Monster);
	}
}
