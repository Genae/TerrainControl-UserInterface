package com.genae.terrainControlUI.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


import com.genae.terrainControlUI.biomePanels.BiomesPanel;
import com.genae.terrainControlUI.biomePanels.CustomBiomeList;
import com.genae.terrainControlUI.bo2Panels.Bo2sPanel;
import com.genae.terrainControlUI.inputLabels.InputFile;
import com.genae.terrainControlUI.inputLabels.InputSlider;
import com.genae.terrainControlUI.main.Main;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.genae.terrainControlUI.util.TCFileWriter;
import com.genae.terrainControlUI.worldPanels.WorldPanel;


public class MainFrame extends JFrame 
{
	////////////////////////////
	//Attributes			  //
	////////////////////////////
	
	private static final long serialVersionUID = 1L;

	//Main
	Main main;
	
	//Elements
	JPanel panel;
	GridLayout layout;
	
		//MenuBar
	JMenuBar menuBar;
	public JMenuItem worldNameLabel;
	JMenuItem playWorld;
	JMenuItem testBiome;
	JMenuItem map;
	JMenuItem settings;
	
	//Menu popups
	SettingDialog settingDialog;
	public ProjectDialog projectDialog;
	
		//TabbedPane
	JTabbedPane tabbedPane;
	public WorldPanel world;
	public BiomesPanel biomes;
	public CustomBiomeList customBiomes;
	Bo2sPanel bo2s;
	
	//Images
	ImageIcon play;
	
	//Fonts
	Font menuBarFont = ResolutionManager.fontSize(Main.font, 25);
	Font tabsFont = ResolutionManager.fontSize(Main.font, 20);
	
	
	////////////////////////////
	//Constructor			  //
	////////////////////////////
	public MainFrame(Main main)
	{
		////////////////////////
		//Main
		this.main = main;
		construct();
	}
	public void construct()
	{
		
		
		////////////////////////
		//load Images
		play = Main.play;
		
		
		////////////////////////
		//Initialize Elements
		layout = new GridLayout(1, 1, 5, 5);
		panel = new JPanel(layout);
		settingDialog = new SettingDialog();
		projectDialog = new ProjectDialog();
		
		//MenuBar
		menuBar = new JMenuBar();
		worldNameLabel = new JMenuItem(Main.getWorldName());
		playWorld = new JMenuItem("Test World", play);
		testBiome = new JMenuItem("Test Biome", play);
		settings = new JMenuItem("Settings");
		map = new JMenuItem("<<");
		
		//TabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		world = new WorldPanel();
		biomes = new BiomesPanel(main);
		customBiomes = new CustomBiomeList();
		bo2s = new Bo2sPanel();
		
		
		////////////////////////
		//Add TabbedPaneItems
		tabbedPane.addTab("World", null, world, "World Options");
		tabbedPane.addTab("Biomes", null, biomes, "Create Different Biomes");
		tabbedPane.addTab("Custom", null, customBiomes, "Your custom Biomes");
		tabbedPane.addTab("BO2s", null, bo2s, "Objects for your Biomes");
		//Font
		tabbedPane.setFont(tabsFont);
		
		
		////////////////////////
		//Add MenuBarItems
		menuBar.add(worldNameLabel);
		menuBar.add(settings);
		menuBar.add(playWorld);
		menuBar.add(testBiome);
		menuBar.add(map);
		//Font
		worldNameLabel.setFont(menuBarFont);
		playWorld.setFont(menuBarFont);
		testBiome.setFont(menuBarFont);
		settings.setFont(menuBarFont);
		map.setFont(menuBarFont);
		
		////////////////////////
		//Add Elements to Frame
		add(panel);
		setJMenuBar(menuBar);
		panel.add(tabbedPane);
		
		////////////////////////
		//addListener
		playWorld.addActionListener(new ActionListener()
		{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			main.startBukkit(true);
		}
		
		});
		testBiome.addActionListener(new ActionListener()
		{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			main.startBukkit(false);
		}
		
		});
		map.addActionListener(new ActionListener()
		{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if(map.getText().equals("<<"))
			{
				map.setText(">>");
				main.imgFrame.setVisible(false);
			}
			else
			{
				map.setText("<<");
				main.imgFrame.setVisible(true);
			}
		}
		
		});
		settings.addActionListener(new ActionListener()
		{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			settingDialog.setVisible(true);
		}
		
		});
		worldNameLabel.addActionListener(new ActionListener()
		{
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			projectDialog.setVisible(true);
		}
		
		});
		
		////////////////////////
		//Set Frame Options
		setSize(ResolutionManager.pixel(new Dimension(1000, 700)));
		setLocation(ResolutionManager.pixel(50), ResolutionManager.pixel(50));
		setTitle("TerrainControl User Interface");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		tabbedPane.setEnabledAt(3, false);
	}
	public void dispose()
	{
		Main.main.mainFrame.world.save();
		TCFileWriter writer = new TCFileWriter(System.getProperty("user.home") + "\\AppData\\Roaming\\TCUI\\" + Main.config.worldName + ".zip");
		try
		{
			writer.exportZip(Main.world.settings);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		setVisible(false);
		super.dispose();
		Main.exit();
	}
	public void setProjectText(String text)
	{
		projectDialog.name.setText(text);
	}
}
class SettingDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	InputFile tc, cb, test, custom;
	JLabel tcL, cbL, testL, customL, seedL, sizeL;
	JPanel label, input;
	JButton ok;
	JTextField seed;
	InputSlider size;
	
	public SettingDialog()
	{
		tc = new InputFile(Main.config.myTCPath, ".jar");
		cb = new InputFile(Main.config.myBukkitPath, ".jar");
		test = new InputFile(Main.config.testDirectory, "directory");
		custom = new InputFile(Main.config.CustomBiomeDirectory, "directory");
		seed = new JTextField(Main.config.getSeedString());
		size = new InputSlider(0, 200, 90, 100);
		size.setValue(Main.config.multiplier);
		
		tcL = new JLabel("Terrain Control");
		cbL = new JLabel("Craftbukkit");
		testL = new JLabel("Test Directory");
		customL = new JLabel("Custom Biomes");
		seedL = new JLabel("Seed");
		sizeL = new JLabel("Font Size (restart)");
		
		tcL.setFont(Main.optionFont);
		cbL.setFont(Main.optionFont);
		testL.setFont(Main.optionFont);
		customL.setFont(Main.optionFont);
		seedL.setFont(Main.optionFont);
		seed.setFont(ResolutionManager.fontSize(Main.font, 15));
		sizeL.setFont(Main.optionFont);
		
		label = new JPanel(new GridLayout(6, 1));
		input = new JPanel(new GridLayout(6, 1));
		setLayout(new BorderLayout(10, 10));
		
		label.add(tcL);
		input.add(tc);
		label.add(cbL);
		input.add(cb);
		label.add(testL);
		input.add(test);
		label.add(customL);
		input.add(custom);
		label.add(seedL);
		input.add(seed);
		label.add(sizeL);
		input.add(size);
		
		ok = new JButton("OK");
		ok.setFont(Main.optionFont);
		
		add(label, BorderLayout.LINE_START);
		add(input, BorderLayout.CENTER);
		add(ok, BorderLayout.PAGE_END);
		
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Main.config.myTCPath = tc.getValue();
				Main.config.myBukkitPath = cb.getValue();
				Main.config.testDirectory = test.getValue();
				Main.config.setSeed(seed.getText());
				Main.config.multiplier = size.getDouble();
				System.out.println(size.getDouble());
				TCFileWriter.writeUIConfig();
				setVisible(false);
			}
			
		});
		setSize(ResolutionManager.pixel(new Dimension(750, 300)));
		setLocation(ResolutionManager.pixel(100), ResolutionManager.pixel(100));
	}
}
class ProjectDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	JLabel nameL, exportL, newProjectL, loadL;
	JTextField name;
	JButton export, load, newProject, ok;
	JPanel label, input;
	public ProjectDialog()
	{
		nameL = new JLabel("Name");
		exportL = new JLabel("Export into ZIP");
		loadL = new JLabel("load from zip");
		newProjectL = new JLabel("new Project");
		
		nameL.setFont(Main.optionFont);
		exportL.setFont(Main.optionFont);
		loadL.setFont(Main.optionFont);
		newProjectL.setFont(Main.optionFont);
		
		name = new JTextField(Main.config.worldName);
		export = new JButton("Export");
		load = new JButton("Load", Main.open);
		newProject = new JButton("new Project");
		
		name.setFont(Main.optionFont);
		export.setFont(Main.optionFont);
		load.setFont(Main.optionFont);
		newProject.setFont(Main.optionFont);
		
		label = new JPanel(new GridLayout(4, 1));
		input = new JPanel(new GridLayout(4, 1));
		setLayout(new BorderLayout(10, 10));
		
		label.add(nameL);
		input.add(name);
		label.add(exportL);
		input.add(export);
		label.add(loadL);
		input.add(load);
		label.add(newProjectL);
		input.add(newProject);
		
		ok = new JButton("OK");
		ok.setFont(Main.optionFont);
		
		add(label, BorderLayout.LINE_START);
		add(input, BorderLayout.CENTER);
		add(ok, BorderLayout.PAGE_END);
		
		ok.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Main.config.worldName = name.getText();
				TCFileWriter.writeUIConfig();
				Main.main.mainFrame.worldNameLabel.setText(Main.config.worldName);
				setVisible(false);
			}
			
		});
		newProject.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
		    	String name = JOptionPane.showInputDialog("What is the name of that world?", null);
		    	Main.config.worldName = name;
		    	new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\BiomeConfigs").mkdirs();
		        TCFileWriter.writeUIConfig();
		        Main.main.initConfigs();
		        Main.main.mainFrame.worldNameLabel.setText(name);
		        Main.main.mainFrame.projectDialog.name.setText(name);
		        //Main.main.mainFrame.biomes.biomeSelect.setSelectedIndex(0);
			}
			
		});
		export.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setSelectedFile(new File(Main.config.worldName + ".zip"));
				chooser.showSaveDialog(Main.main.mainFrame.projectDialog);
				Main.main.mainFrame.world.save();
				TCFileWriter writer = new TCFileWriter(chooser.getSelectedFile().getAbsolutePath());
				try
				{
					writer.exportZip(Main.world.settings);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		});
		load.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(Main.main.mainFrame.projectDialog);
				Main.main.mainFrame.world.save();
				try
				{
					TCFileWriter.importZip(chooser.getSelectedFile());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		});
		setSize(ResolutionManager.pixel(new Dimension(700, 300)));
		setLocation(ResolutionManager.pixel(100), ResolutionManager.pixel(100));
	}
}