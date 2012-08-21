package com.genae.terrainControlUI.biomePanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.genae.terrainControlUI.configs.BiomeDescription;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.genae.terrainControlUI.util.TCFileWriter;
import com.khorn.terraincontrol.configuration.BiomeConfig;

public class CustomBiomeFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	JTabbedPane tabbedPane;
	JPanel additionalStuff, images;
	JTextArea description;
	public BiomesPanel biome;
	BiomeDescription descr;
	TCFileWriter writer;
	
	public CustomBiomeFrame(BiomeConfig customBiome)
	{
		descr = BiomeDescription.descriptions.get(customBiome.Name);
		tabbedPane = new JTabbedPane();
		additionalStuff = new JPanel(new BorderLayout());
		images = new JPanel(new GridLayout(descr.getImages().size(), 1));
		biome = new BiomesPanel(Main.main, customBiome);
		description = new JTextArea();
		writer = new TCFileWriter(Main.config.testDirectory);
		
		for(String s:descr.getDescription())
		{
			description.append(s);
		}
		
		setLayout(new BorderLayout());
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		tabbedPane.setFont(ResolutionManager.fontSize(Main.font, 20));
		
		tabbedPane.addTab("Describe", additionalStuff);
		tabbedPane.add("Config", biome);

		
		add(tabbedPane, BorderLayout.CENTER);
		
		setSize(ResolutionManager.pixel(new Dimension(1000, 700)));
		setLocation(ResolutionManager.pixel(100), ResolutionManager.pixel(100));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void setBiome(BiomeConfig biomeConfig)
	{
		biome.loadConfig(biomeConfig);
	}
	public void dispose()
	{
		biome.saveConfig();
		try 
		{
			writer.writeWorldConfig(Main.world.settings, true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		setVisible(false);
	}
}
