package com.genae.terrainControlUI.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.genae.terrainControlUI.configs.BiomeDescription;
import com.genae.terrainControlUI.configs.SimpleBiome;
import com.genae.terrainControlUI.configs.SimpleWorld;
import com.genae.terrainControlUI.configs.UIConfig;
import com.genae.terrainControlUI.main.Main;
import com.khorn.terraincontrol.configuration.BiomeConfig;
import com.khorn.terraincontrol.configuration.TCDefaultValues;
import com.khorn.terraincontrol.configuration.WorldConfig;

public class TCFileWriter 
{
	//Init Streams
	static FileWriter outStream;
	static BufferedWriter out;
	static FileReader inStream;
	static BufferedReader in;
	static InputStream copyIn;
	static OutputStream copyOut;
	static String JavaHome = System.getProperty("java.home");
	static String UserHome = System.getProperty("user.home");
	static String testDir;
	
	static HashMap<String, String> rename;
	static HashMap<String, Boolean> copy;
	
	static
	{
		rename = new HashMap<String, String>();
		copy = new HashMap<String, Boolean>();
	}
	
	public TCFileWriter(String testDir)
	{
		TCFileWriter.testDir = testDir;
	}
	
	//////////////////////////
	//						//
	//		C O P Y			//
	//						//
	//////////////////////////
	///////////////////
	//copy Craftbukkit
	public void copyCraftBukkit() throws IOException
	{
		File craftbukkit = new File(testDir + "\\craftbukkit.jar");
		if(!craftbukkit.exists())
		{
			copyFile(new File(Main.config.myBukkitPath), craftbukkit);
		}
	}
	
	
	///////////////////
	//copy TerrainControl
	public void copyTerrainControl() throws IOException
	{
		File terrainControl = new File(testDir + "\\plugins\\TerrainControl.jar");
		new File(testDir + "\\plugins").mkdir();
		if(!terrainControl.exists())
		{
			copyFile(new File(Main.config.myTCPath), terrainControl);
		}
	}
	
	//////////////////////////
	//						//
	//		Z I P			//
	//						//
	//////////////////////////
	
	///////////////////
	//export ZipFile
	public void exportZip(WorldConfig config) throws IOException
	{
		writeWorldConfig(config, true);

		FileOutputStream fos = new FileOutputStream(new File(testDir));
		ZipOutputStream zos = new ZipOutputStream(fos);
		writeZipEntry(zos, "WorldConfig.ini", new File(config.SettingsDir, TCDefaultValues.WorldSettingsName.stringValue()));
		File biomeFolder = new File(config.SettingsDir, TCDefaultValues.WorldBiomeConfigDirectoryName.stringValue());
		for(BiomeConfig biomeConfig:config.biomes)
		{
			
			if(biomeConfig != null)
			{
				writeZipEntry(zos, "BiomeConfigs\\" + biomeConfig.Name + "BiomeConfig.ini", new File(biomeFolder, biomeConfig.Name + TCDefaultValues.WorldBiomeConfigName.stringValue()));
				for(String biome : config.CustomBiomes)
		        {
		        	if(biome.equals(biomeConfig.Name))
		        	{
		        		File from = new File(Main.config.CustomBiomeDirectory + "\\" + biomeConfig.Name);
		        		String to = "CustomBiomes\\" + biomeConfig.Name + "\\";
		        		writeZipEntry(zos, to + "Images.txt", new File(from, "\\Images.txt"));
		        		writeZipEntry(zos, to + "Description.txt", new File(from, "\\Description.txt"));
		        		inStream = new FileReader(new File(from, "\\Images.txt"));
		        		in = new BufferedReader(inStream);
		        		String line;
		        		while((line = in.readLine()) != null)
		        		{
		        			writeZipEntry(zos, to + "Images\\" + line, new File(from, "Images\\" + line));
		        		}
		        	}
		        }
			}
		}
		zos.close();
	}
	private void writeZipEntry(ZipOutputStream zos, String name, File source) throws IOException
	{
		try
		{
			ZipEntry ze = new ZipEntry(name);
			zos.putNextEntry(ze);
			copyIn = new FileInputStream(source);
			copy(copyIn, zos);
			zos.closeEntry();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	///////////////////
	//load from zip
	public static SimpleWorld importZip(File zip, String name)
	{
		try
		{
			deleteDir(new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name));
			new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\BiomeConfigs").mkdirs();
	        FileInputStream fis = new FileInputStream(zip);
	        ZipInputStream zis = new ZipInputStream(fis);

	        loadEntry(zis, name);
	        zis.close();
	        renameCustomBiomes(new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\WorldConfig.ini"));
	        

	        writeUIConfig();
	        Main.main.initConfigs();
	        Main.main.mainFrame.worldNameLabel.setText(name);
	        Main.main.mainFrame.setProjectText(name);
	        Main.main.mainFrame.biomes.biomeSelect.setSelectedIndex(0);
	        Main.main.mainFrame.world.load(Main.world.settings);
	        BiomeConfig selected = null;
	        final JComboBox<String> biomeSelect = Main.main.mainFrame.biomes.biomeSelect;
	        String selectedString = biomeSelect.getSelectedItem().toString();
	        for(BiomeConfig config:Main.world.settings.biomeConfigs)
	        {
	        	if(config != null)
	        	{
		        	Main.main.mainFrame.biomes.loadConfig(config);
		        	if(selectedString.equals(config.Name)) selected = config;
	        	}
	        }
	        if(selected != null)
	        {
	        	Main.main.mainFrame.biomes.loadConfig(selected);
	        }
	        else
	        {
	        	Main.main.mainFrame.biomes.biomeSelect.setSelectedIndex(0);
	        	Main.main.mainFrame.biomes.loadConfig(Main.world.settings.biomeConfigs[0]);
	        }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return null;
	}
	private static void loadEntry(ZipInputStream zis, String name) throws Exception
	{
        ZipEntry ze;
        if((ze = zis.getNextEntry()) != null)
        {
	        if(ze.getName().contains(".ini"))
	        {
			    String biomeName = "";
			    if(ze.getName().contains("\\"))
			    {
			        biomeName = ze.getName().substring(ze.getName().indexOf("\\")+1, ze.getName().length() - 15);
			        System.out.println(biomeName);
			        boolean found = false;
			        for(String biome : Main.objManager.getAllBiomes(false))
			        {
			        	if(biome.equals(biomeName)) //Default Biome
			        	{
			        		found = true;
				        	copyOut = new FileOutputStream(new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\" + ze.getName()));
						    copy(zis, copyOut);
						    copyOut.close();
						    zis.closeEntry();
						    loadEntry(zis, name);
			        	}
			        	if(biomeName.equals("FrozenOcean") || biomeName.equals("Ocean") || biomeName.equals("FrozenRiver") || biomeName.equals("Hell") || biomeName.equals("Sky"))
			        	{
			        		found = true;
				        	copyOut = new FileOutputStream(new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\" + ze.getName()));
						    copy(zis, copyOut);
						    copyOut.close();
						    zis.closeEntry();
						    loadEntry(zis, name);
			        	}
			        }
			        if(!found) //Custom Biome
			        {
			        	if(!found)
			        	{
			        		checkCustomBiomeExists(biomeName);
			        		if(copy.get(biomeName))
			        		{
			        			copyOut = new FileOutputStream(new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\BiomeConfigs\\" + rename.get(biomeName) + TCDefaultValues.WorldBiomeConfigName.stringValue()));
							    copy(zis, copyOut);
							    copyOut.close();
				        		//get TerrainControl Directory
				        		String settingsDir = Main.config.CustomBiomeDirectory + "\\" + rename.get(biomeName);
				        		new File(settingsDir).mkdirs();
				        		copyOut = new FileOutputStream(new File(settingsDir, rename.get(biomeName) + TCDefaultValues.WorldBiomeConfigName.stringValue()));
							    copy(zis, copyOut);
							    copyOut.close();
							    zis.closeEntry();
							    loadEntry(zis, name);
			        		}
			        	}
			        }
	        	}
			    else	//WorldConfig.ini
			    {
		        	copyOut = new FileOutputStream(new File(Main.config.testDirectory + "\\plugins\\TerrainControl\\worlds\\" + name + "\\" + ze.getName()));
				    copy(zis, copyOut);
				    copyOut.close();
				    zis.closeEntry();
				    loadEntry(zis, name);
			    }
	        }
	        if(ze.getName().contains("CustomBiomes")) //Description files
	        {
	        	File dir = new File(Main.config.CustomBiomeDirectory + ze.getName().substring(ze.getName().indexOf("\\CustomBiomes\\")+13, ze.getName().lastIndexOf("\\")));
	        	String biomeName = ze.getName().substring(ze.getName().indexOf("\\CustomBiomes\\")+14, ze.getName().indexOf("\\", ze.getName().indexOf("\\CustomBiomes\\")+15));
	        	
	        	checkCustomBiomeExists(biomeName);
	        	if(copy.get(biomeName))
	        	{
	        		if(ze.getName().substring(ze.getName().indexOf("\\CustomBiomes\\")+13, ze.getName().lastIndexOf("\\")).contains("Image"))
	        		{
	        			dir = new File(Main.config.CustomBiomeDirectory + "\\" + rename.get(biomeName) + "\\Images");
	        		}
	        		else
	        		{
	        			dir = new File(Main.config.CustomBiomeDirectory + "\\" + rename.get(biomeName));
	        		}
	        		dir.mkdirs();
	        		copyOut = new FileOutputStream(new File(dir, ze.getName().substring(ze.getName().lastIndexOf("\\"))));
	        		copy(zis, copyOut);
	        	}
	        }
	        zis.closeEntry();
		    loadEntry(zis, name);
        }
	}
	public static SimpleWorld importZip(File zip)
	{
	    String name = JOptionPane.showInputDialog("What is the name of that world?", zip.getName().substring(0, zip.getName().length()-4));
	    Main.config.worldName = name;
	    
	    return importZip(zip, name);
	}
	private static void checkCustomBiomeExists(String biomeName)
	{
		if(new File(Main.config.CustomBiomeDirectory, biomeName).exists() && !copy.containsKey(biomeName))
    	{
    		int answ = JOptionPane.showConfirmDialog(Main.main.mainFrame, "There already is a custom Biome with the name " + biomeName + ". Rename(yes), override(no) or use existing description(cancel)", "Custom Biome already exists", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    		if(answ == JOptionPane.OK_OPTION)
    		{
    			copy.put(biomeName, true);
    			String newName = JOptionPane.showInputDialog(Main.main.mainFrame, "Rename Custom Biome to: ", biomeName);
    			if(newName != null) 
    			{
    				rename.put(biomeName, newName);
    			}
    			else
    			{
    				rename.put(biomeName, biomeName);
    			}
    		}
    		else if(answ == JOptionPane.NO_OPTION)
    		{
    			copy.put(biomeName, true);
    			rename.put(biomeName, biomeName);
    		}
    		else
    		{
    			copy.put(biomeName, false);
    		}
    	}
		else if(!copy.containsKey(biomeName))
		{
			copy.put(biomeName, true);
			rename.put(biomeName, biomeName);
		}
	}
	private static void renameCustomBiomes(File configFile)
	{
		try 
		{
			inStream = new FileReader(configFile);
			in = new BufferedReader(inStream);
			String[] biomes = rename.keySet().toArray(new String[]{});
			String line = "";
			ArrayList<String> lines = new ArrayList<String>();
			while((line = in.readLine())!= null)
			{
				for(String variable : new String[]{"NormalBiomes:", "IceBiomes:", "IsleBiomes:", "BorderBiomes:", "CustomBiomes:"})
				{
					if(line.contains(variable))
						for(String biome:biomes)
						{
							line = line.replace(biome, rename.get(biome));
						}
					lines.add(line);
				}
			}
			in.close();
			inStream.close();
			outStream = new FileWriter(configFile);
			out = new BufferedWriter(outStream);
			for(String writeLine : lines)
			{
				out.write(writeLine);
				out.newLine();
			}
			rename.clear();
			copy.clear();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	//////////////////////////
	//						//
	//	U I  C O N F I G	//
	//						//
	//////////////////////////
	
	///////////////////
	//write UIconfigs
	public static void writeUIConfig()
	{
		writeUIConfig(Main.config);
	}
	public static void writeUIConfig(UIConfig config)
	{
		try
		{
			new File(UserHome + "\\AppData\\Roaming\\TCUI\\").mkdir();
			outStream = new FileWriter(UserHome + "\\AppData\\Roaming\\TCUI\\UIConfig.txt");
			out = new BufferedWriter(outStream);
			out.write(config.testDirectory);
			out.newLine();
			out.write(config.myBukkitPath);
			out.newLine();
			out.write(config.myTCPath);
			out.newLine();
			out.write(config.worldName);
			out.newLine();
			out.write(config.getSeedString());
			out.newLine();
			out.write(config.multiplier + "");
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	///////////////////
	//read UIconfigs
	public static UIConfig readConfig()
	{
		UIConfig config = new UIConfig();
		UIConfig defaultConfig = new UIConfig();
		File configFile = new File(UserHome + "\\AppData\\Roaming\\TCUI\\UIConfig.txt");
		if(configFile.exists())
		try
		{
			String line;
			inStream = new FileReader(configFile);
			in = new BufferedReader(inStream);
			line = in.readLine();
			if(line!=null)	config.testDirectory = line;
			else			config.testDirectory = defaultConfig.testDirectory;
			line = in.readLine();
			if(line!=null)	config.myBukkitPath = line;
			else			config.testDirectory = defaultConfig.testDirectory;
			line = in.readLine();
			if(line!=null)	config.myTCPath = line;
			else			config.testDirectory = defaultConfig.testDirectory;
			line = in.readLine();
			if(line!=null)	config.worldName = line;
			else			config.testDirectory = defaultConfig.testDirectory;
			line = in.readLine();
			if(line!=null)	config.seed = line;
			else			config.testDirectory = defaultConfig.testDirectory;
			line = in.readLine();
			if(line!=null)	config.multiplier = Double.parseDouble(line);
			else			config.multiplier = defaultConfig.multiplier;
			if(line == null) writeUIConfig(config);
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			config = new UIConfig();
		}
		return config;
	}
	
	//////////////////////////
	//						//
	//		C U S T O M		//
	//						//
	//////////////////////////
	
	//////////////////////////
	//Write CustomBiome
	public static void writeCustomBiome(BiomeConfig config, File path)
	{
		path.mkdirs();
		Main.main.mainFrame.biomes.saveConfig();
		if(config != null)
		{
			config.WriteSettingsFile(new File(path, config.Name + TCDefaultValues.WorldBiomeConfigName.stringValue()));
			//get TerrainControl Directory
			String settingsDir = Main.config.CustomBiomeDirectory + "\\" + config.Name;
			new File(settingsDir).mkdirs();
			config.WriteSettingsFile(new File(settingsDir, config.Name + TCDefaultValues.WorldBiomeConfigName.stringValue()));
			writeDescription(BiomeDescription.descriptions.get(config.Name), new File(settingsDir));
		}
	}
	public static BiomeConfig loadBiomeFromConfig(String name) 
	{
		SimpleBiome biome = new SimpleBiome(name, Main.world.getFreeBiomeId());
		File dir = new File(Main.config.CustomBiomeDirectory + "\\" + name);
		BiomeConfig loaded = new BiomeConfig(dir, biome, Main.world.settings);
		return loaded;
	}
	public static void writeDescription(BiomeDescription descr, File path)
	{
		path.mkdir();
		try
		{
			//write Description.txt
			outStream = new FileWriter(new File(path, "Description.txt"));
			out = new BufferedWriter(outStream);
			for(String line:descr.getDescription())
			{
				out.write(line);
				out.newLine();
			}
			out.close();
			outStream.close();
			
			//write Images.txt
			outStream = new FileWriter(new File(path, "Images.txt"));
			out = new BufferedWriter(outStream);
			for(File img:descr.getImages())
			{
				out.write(img.getName());
				out.newLine();
			}
			out.close();
			outStream.close();
			//copy Images
			new File(path, "Images").mkdir();
			for(File img:descr.getImages())
			{
				if(!new File(path, "Images\\" + img.getName()).exists())
				{
					copyFile(img, new File(path, "Images\\" + img.getName()));
				}
					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void readDescription(String name)
	{
		File path = new File(Main.config.CustomBiomeDirectory + "\\" + name);
		try
		{
			BiomeDescription descr = new BiomeDescription(name);
			
			//read Description.txt
			inStream = new FileReader(new File(path, "Description.txt"));
			in = new BufferedReader(inStream);
			String line = in.readLine();
			while(line != null)
			{
				descr.appendDescription(line);
				line = in.readLine();
			}
			in.close();
			inStream.close();
			
			//read Images.txt
			inStream = new FileReader(new File(path, "Images.txt"));
			in = new BufferedReader(inStream);
			String img = in.readLine();
			while(line != null)
			{
				descr.addImage(new File(path,img));
				img = in.readLine();
			}
			in.close();
			inStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//////////////////////////
	//						//
	//		W R I T E		//
	//						//
	//////////////////////////
	
	///////////////////
	//write configs
	public void writeWorldConfig(WorldConfig config, boolean complete) throws IOException
	{
		Main.main.mainFrame.world.save();
		//TestBiome
		if(!complete)
		{
			String biome = Main.main.mainFrame.biomes.biomeSelect.getSelectedItem().toString();
			ArrayList<String> biomeTyp = null;
			if(Main.world.settings.NormalBiomes.contains(biome)) biomeTyp = Main.world.settings.NormalBiomes;
			Main.world.settings.NormalBiomes.clear();
			Main.world.settings.BorderBiomes.clear();
			if(Main.world.settings.IceBiomes.contains(biome)) biomeTyp = Main.world.settings.IceBiomes;
			Main.world.settings.IceBiomes.clear();
			Main.world.settings.IsleBiomes.clear();
			if(Main.world.settings.CustomBiomes.contains(biome)) biomeTyp = Main.world.settings.CustomBiomes;
			Main.world.settings.CustomBiomes.clear();
			if(biomeTyp == null) biomeTyp = Main.world.settings.NormalBiomes;
			biomeTyp.add(biome);
		}
		config.SettingsDir.mkdirs();
		System.out.println(config.GenerationDepth);
		File dir = new File(config.SettingsDir, TCDefaultValues.WorldSettingsName.stringValue());
		File biomeFolder = new File(config.SettingsDir, TCDefaultValues.WorldBiomeConfigDirectoryName.stringValue());
		config.WriteSettingsFile(dir);
		writeBiomeConfig(config.biomeConfigs, biomeFolder);
	}
	public void writeBiomeConfig(BiomeConfig[] configs, File path) throws IOException
	{
		path.mkdirs();
		Main.main.mainFrame.biomes.saveConfig();
		for(BiomeConfig config:configs)
		{
			if(config != null)
				if(config.Biome.isCustom()) writeCustomBiome(config, path);
				else config.WriteSettingsFile(new File(path, config.Name + TCDefaultValues.WorldBiomeConfigName.stringValue()));
		}
	}
	
	//////////////////////////
	//write server.properties
	public void writeServerProperties() throws IOException
	{
		outStream = new FileWriter(testDir + "\\server.properties");
		out = new BufferedWriter(outStream);
		out.write("#Minecraft server properties");
		out.newLine();
		out.write("#Mon Jul 16 15:29:59 CEST 2012");
		out.newLine();
		out.write("allow-nether=true");
		out.newLine();
		out.write("level-name=" + Main.getWorldName());
		out.newLine();
		out.write("enable-query=false");
		out.newLine();
		out.write("allow-flight=false");
		out.newLine();
		out.write("server-port=25565");
		out.newLine();
		out.write("level-type=DEFAULT");
		out.newLine();
		out.write("enable-rcon=false");
		out.newLine();
		out.write("level-seed=" + Main.config.getSeedString());
		System.out.println(Main.config.getSeed());
		out.newLine();
		out.write("server-ip=");
		out.newLine();
		out.write("max-build-height=256");
		out.newLine();
		out.write("spawn-npcs=true");
		out.newLine();
		out.write("white-list=false");
		out.newLine();
		out.write("spawn-animals=true");
		out.newLine();
		out.write("online-mode=false");
		out.newLine();
		out.write("pvp=true");
		out.newLine();
		out.write("difficulty=1");
		out.newLine();
		out.write("gamemode=1");
		out.newLine();
		out.write("max-players=20");
		out.newLine();
		out.write("spawn-monsters=true");
		out.newLine();
		out.write("generate-structures=true");
		out.newLine();
		out.write("view-distance=10");
		out.newLine();
		out.write("motd=A Minecraft Server");
		out.newLine();
		out.close();
	}
	
	////////////////////////
	//write bukkit.yml
	public void writeBukkitYml() throws IOException
	{
		outStream = new FileWriter(testDir + "\\bukkit.yml");
		out = new BufferedWriter(outStream);
		out.write(	"# This is the main configuration file for Bukkit. " +
				"# As you can see, there\'s actually not that much to configure without any plugins." +
				"# For a reference for any variable inside this file, check out the bukkit wiki at" +
				"# http://wiki.bukkit.org/Bukkit.yml" +
				"# " +
				"# If you need help on this file, feel free to join us on irc or leave a message" +
				"# on the forums asking for advice." +
				"# " +
				"# IRC: #bukkit @ esper.net" +
				"#    (If this means nothing to you, just go to http://webchat.esper.net/?channels=bukkit )" +
				"# Forums: http://forums.bukkit.org/forums/bukkit-help.6/" +
				"# Twitter: http://twitter.com/Craftbukkit" +
				"# Bug tracker: http://leaky.bukkit.org/" +
				"settings:" +
				"  allow-end: true" +
				"  warn-on-overload: true" +
				"  spawn-radius: 16" +
				"  permissions-file: permissions.yml" +
				"  update-folder: update" +
				"  ping-packet-limit: 100" +
				"  use-exact-login-location: false" +
				"  plugin-profiling: false" +
				"  connection-throttle: 4000" +
				"spawn-limits:" +
				"  monsters: 70" +
				"  animals: 15" +
				"  water-animals: 5" +
				"ticks-per:" +
				"  animal-spawns: 400" +
				"  monster-spawns: 1" +
				"auto-updater:" +
				"  enabled: true" +
				"  on-broken:" +
				"  - warn-console" +
				"  - warn-ops" +
				"  on-update:" +
				"  - warn-console" +
				"  - warn-ops" +
				"  preferred-channel: rb" +
				"  host: dl.bukkit.org" +
				"  suggest-channels: true" +
				"database:" +
				"  username: bukkit" +
				"  isolation: SERIALIZABLE" +
				"  driver: org.sqlite.JDBC" +
				"  password: walrus" +
				"  url: jdbc:sqlite:{DIR}{NAME}.db");
		out.newLine();
		out.write("worlds:");
		out.newLine();
		out.write("  " + Main.getWorldName() + ":");
		out.newLine();
		out.write("    generator: TerrainControl");
		out.newLine();
		out.close();
	}
	
	///////////////////
	//write BatFile
	public void writeBatFile() throws IOException
	{
		outStream = new FileWriter(testDir + "\\start.bat");
		out = new BufferedWriter(outStream);
		out.write("@ECHO OFF");
		out.newLine();
		out.write("SET BINDIR=%~dp0");
		out.newLine();
		out.write("CD /D \"%BINDIR%\"");
		out.newLine();
		out.write("\"" + JavaHome + "\\bin\\java.exe\" -Xmx1024M -Xms1024M -jar craftbukkit.jar");
		out.newLine();
		out.write("PAUSE");
		out.close();
	}
	
	//////////////////////////
	//						//
	//		U T I L			//
	//						//
	//////////////////////////
	
	public static String[] getAllCustomBiomes()
	{
		File path = new File(Main.config.CustomBiomeDirectory);
		String[] dirs = path.list();
		if(dirs == null) return new String[]{};
		ArrayList<String> customBiomes = new ArrayList<String>();
		for(String dir:dirs)
		{
			if(new File(path, dir + "\\" + dir + "BiomeConfig.ini").exists()) customBiomes.add(dir);
		}
		return customBiomes.toArray(dirs);
	}
	public static void copyFile(File in, File out)
	{
		try
		{
			copyIn = new FileInputStream(in);
			copyOut = new FileOutputStream(out);
			copy(copyIn, copyOut);
			copyIn.close();
			copyOut.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private static void copy(InputStream in, OutputStream out)
	{	
		try 
		{
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static boolean deleteDir(File dir) 
	{
	    if (dir.isDirectory()) 
	    {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) 
	            {
	            	System.out.println("problem with:" + new File(dir, children[i]).getName());
	                return false;                
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
}
