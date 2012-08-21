package com.genae.terrainControlUI.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.genae.terrainControlUI.Image.ImageFrame;
import com.genae.terrainControlUI.configs.SimpleWorld;
import com.genae.terrainControlUI.configs.UIConfig;
import com.genae.terrainControlUI.util.ObjectManager;
import com.genae.terrainControlUI.util.ResolutionManager;
import com.genae.terrainControlUI.util.TCFileWriter;
import com.khorn.terraincontrol.configuration.BiomeConfig;
import com.khorn.terraincontrol.configuration.WorldConfig;

public class Main 
{
	//////////////////////////////////////
	//									//
	//		Declare Variables			//
	//									//
	//////////////////////////////////////
	public static Main main;
	
	//Configuration Files
	public WorldConfig worldConfig;
	public static SimpleWorld world;
	public static ObjectManager objManager;
	public String[] biomes;
	public static UIConfig config;
	
	//Directories
	File worldDir;
	
	//Images
	public static ImageIcon open;
	public static ImageIcon icon;
	public static ImageIcon play;
	
	//Frames
	ImageFrame imgFrame;
	public MainFrame mainFrame;
	JFrame intro;
	
	//Stuff
	public static ResourceBundle worldLabels;
	public static ResourceBundle biomeLabels;
	public static ResourceBundle worldTooltips;
	public static ResourceBundle biomeTooltips;
	public static ResourceBundle monsterTooltips;
	public static ResourceBundle treeLabels;
	public static Font optionFont = new Font("Josefin Sans", 25, 25);
	public static Font font;
	
	//////////////////////////////////////
	//									//
	//			Main Method				//
	//									//
	//////////////////////////////////////
	public static void main(String[] args)
	{
		config = TCFileWriter.readConfig();
		main = new Main();
		main.startIntro();
		main.loadBundles();
		main.initConfigs();
		main.initFrames();
	}
	
	//////////////////////////////////////
	//									//
	//		Init Configurations			//
	//									//
	//////////////////////////////////////
	public void initConfigs()
	{
		worldDir = new File(config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + getWorldName());
		worldDir.mkdirs();
		world = new SimpleWorld(config.getSeed(), getWorldName());
		worldConfig = new WorldConfig(worldDir, world, false);
		world.settings = worldConfig;
		objManager = new ObjectManager();
		
		biomes = new String[worldConfig.biomes.size()];
		for(int i = 0; i < biomes.length; i++)
		{
			biomes[i] = worldConfig.biomes.get(i).Name;
		}
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/Ubuntu-Regular.ttf") );
			optionFont = ResolutionManager.fontSize(font, 25);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		TCFileWriter.writeUIConfig();
		
	}
	
	//////////////////////////////////////
	//									//
	//		Load Language Bundles		//
	//									//
	//////////////////////////////////////
	protected void loadBundles()
	{
		worldLabels = ResourceBundle.getBundle("lang.labels.WorldLabels");
		biomeLabels = ResourceBundle.getBundle("lang.labels.BiomeLabels");
		worldTooltips = ResourceBundle.getBundle("lang.tooltips.WorldTooltips");
		biomeTooltips = ResourceBundle.getBundle("lang.tooltips.BiomeTooltips");
		monsterTooltips = ResourceBundle.getBundle("lang.tooltips.MonsterTooltips");
		treeLabels = ResourceBundle.getBundle("lang.labels.TreeLabels");
	}
	
	
	//////////////////////////////////////
	//									//
	//		Show the Intro-Pic			//
	//									//
	//////////////////////////////////////
	protected void startIntro()
	{
		//////////////////////////
		// Start Intro
		intro = new JFrame("TerrainControl UI");
		intro.setSize(ResolutionManager.pixel(new Dimension(512, 320)));
		intro.setUndecorated(true);
		//center on screen
		Toolkit tk = Toolkit.getDefaultToolkit();
		intro.setLocation((tk.getScreenSize().width-512)/2, (tk.getScreenSize().height-320)/2);
		//add Image
		intro.add(new JLabel(getImage("/resources/images/intro.png")));
		icon = getImage("/resources/images/icon.png");
		intro.setIconImage(icon.getImage());
		intro.setVisible(true);
	}
	
	//////////////////////////////////////
	//									//
	//		Initiate the Frames			//
	//									//
	//////////////////////////////////////
	protected void initFrames()
	{
		play = getImage("/resources/images/play.png");
		open = getImage("/resources/images/open.png");
		mainFrame = new MainFrame(this);
		imgFrame = new ImageFrame(this);
		mainFrame.setIconImage(icon.getImage());
		imgFrame.setIconImage(icon.getImage());
		intro.setVisible(false);
		mainFrame.setVisible(true);
		imgFrame.setVisible(true);
		
	}
	
	public static String getWorldName()
	{
		return config.worldName;
	}
	
	//////////////////////////////////
	//		Image Loader			//
	//////////////////////////////////
	public static ImageIcon getImage(String path)
	{
		try
		{
			URL url = path.getClass().getResource(path);
			if(url == null) return null;
			ImageIcon icon = new ImageIcon(url);
			return ResolutionManager.resize(icon, icon.getIconWidth(), icon.getIconHeight());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return null;

	}
	
	public static BiomeConfig getConfig(String name)
	{
		for(int i = 0; i < Main.world.settings.biomes.size(); i++)
		{
			if(Main.world.settings.biomes.get(i).Name.equals(name))
			{
				return Main.world.settings.biomes.get(i);
			}
		}
		return null;
	}
	
	//////////////////////////////////
	//		Start Bukkit			//
	//////////////////////////////////
	public void startBukkit(boolean complete)
	{
		//Init Directories
		File testDirectory = new File(config.testDirectory);
		
		//Clear Directories
		if(testDirectory.exists())
		{
			TCFileWriter.deleteDir(new File(config.testDirectory + "\\plugins\\TerrainControl"));
			TCFileWriter.deleteDir(new File(config.testDirectory + "\\" + config.worldName));
			TCFileWriter.deleteDir(new File(config.testDirectory + "\\" + config.worldName + "_nether"));
			TCFileWriter.deleteDir(new File(config.testDirectory + "\\" + config.worldName + "_the_end"));
		}
		//Init File Destinations
		testDirectory.mkdirs();
		File startBat = new File(config.testDirectory + "\\start.bat");
		
		TCFileWriter writer = new TCFileWriter(config.testDirectory);
		if(checkFiles())
		{
			try 
			{	
				writer.copyCraftBukkit();
				writer.copyTerrainControl();
				writer.writeBatFile();
				writer.writeBukkitYml();
				writer.writeServerProperties();
				writer.writeWorldConfig(world.settings, complete);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			try 
			{
				Runtime.getRuntime().exec("cmd /c start " + startBat.getAbsolutePath());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		//end testBiome
		if(!complete)
		{
			try 
			{
				writer.writeWorldConfig(world.settings, false);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	private boolean checkFiles()
	{
		if(!new File(config.myBukkitPath).exists() || !new File(config.myBukkitPath).isFile())
		{
			JOptionPane.showConfirmDialog(mainFrame, "Could not find craftbukkit Version");
			mainFrame.settingDialog.setVisible(true);
			return false;
		}
		if(!new File(config.myTCPath).exists() || !new File(config.myBukkitPath).isFile())
		{
			JOptionPane.showConfirmDialog(mainFrame, "Could not find TerrainControl Version");
			mainFrame.settingDialog.setVisible(true);
			return false;
		}
		return true;
	}

	public static void exit()
	{
		main.imgFrame.setVisible(false);
		main.imgFrame.dispose();
		System.exit(0);
	}
}
