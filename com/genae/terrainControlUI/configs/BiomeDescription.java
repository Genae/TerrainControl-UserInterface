package com.genae.terrainControlUI.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class BiomeDescription 
{
	String description;
	String name;
	ArrayList<File> images;
	public static HashMap<String, BiomeDescription> descriptions;
	
	public BiomeDescription(String name)
	{
		description = "";
		this.name = name;
		images = new ArrayList<File>();
		BiomeDescription.descriptions.put(name, this);
	}
	public void appendDescription(String s)
	{
		description = description + System.getProperty("line.seperator") + s;
	}
	public String[] getDescription()
	{
		return description.split(System.getProperty("line.separator"));
	}
	public ArrayList<File> getImages()
	{
		return images;
	}
	public void addImage(File img)
	{
		images.add(img);
	}
	static
	{
		descriptions = new HashMap<String, BiomeDescription>();
	}
}
