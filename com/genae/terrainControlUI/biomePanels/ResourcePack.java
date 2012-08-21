package com.genae.terrainControlUI.biomePanels;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.genae.terrainControlUI.main.Main;
import com.khorn.terraincontrol.configuration.Resource;
import com.khorn.terraincontrol.generator.resourcegens.ResourceType;

public class ResourcePack 
{
	HashMap<String, Resource> resPack;
	HashMap<String, Boolean> resActive;
	static String biome = "";
	int id;
	int data;
	ResourceType type;
	boolean copy = false;
	
	public ResourcePack(Resource res, String biome)
	{
		resPack = new HashMap<String, Resource>();
		resActive = new HashMap<String, Boolean>();
		this.id = res.BlockId;
		this.type = res.Type;
		this.data = res.BlockData;
		add(res, biome);
	}
	public boolean fit(Resource res)
	{
		if(res != null)
		return res.BlockId == id && res.Type == type && res.BlockData == data;
		return false;
	}
	public void add(Resource res, String biome)
	{
		if(!biome.equals(""))
		{
		resPack.put(biome, res);
		resActive.put(biome, true);
		}
	}
	public void setCopy(boolean b)
	{
		copy = b;
		if(b && !resPack.containsKey("all"))
		{
			resPack.put("all", resPack.get(biome));
		}
	}
	public Resource getRes(String biome)
	{
		if(copy) return resPack.get("all");
		return(resPack.get(biome));
	}
	public boolean isUsed(String biome)
	{
		return(resActive.get(biome));
	}
	public void setUsed(String biome, boolean b, InputResources input)
	{
		if(!resPack.containsKey(biome))
		{
			resPack.put(biome, new Resource(type));
			resPack.get(biome).BlockId = id;
		}
		resActive.put(biome, b);
		boolean usedOnce = false;
		for(String k:resActive.keySet().toArray(new String[]{}))
		{
			if(resActive.get(k)) usedOnce = true;
		}
		if(!usedOnce) remove(input);
	}
	public boolean compareRes(Resource res1, Resource res2)
	{
		return res1.BlockId == res2.BlockId && res1.Type == res2.Type && res1.BlockData == res2.BlockData;
	}
	public boolean contains(String biome)
	{
		return resPack.containsKey(biome);
	}
	public void remove(InputResources input)
	{
		int answer = JOptionPane.showConfirmDialog(Main.main.mainFrame, "The Resource isn't used anymore. Delete?");
		if(answer == JOptionPane.OK_OPTION)
		{
			input.removeRes(this);
		}
		if(answer == JOptionPane.CANCEL_OPTION)
		{
			resActive.put(biome, true);
			input.useHere.setSelected(true);
		}
	}
}
