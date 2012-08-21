package com.genae.terrainControlUI.biomePanels;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.genae.terrainControlUI.main.Main;
import com.khorn.terraincontrol.configuration.WeightedMobSpawnGroup;

public class MonsterGroupPack 
{
	HashMap<String, WeightedMobSpawnGroup> monsterPack;
	HashMap<String, Boolean> monsterActive;
	static String biome = "";
	String name;
	MonsterType type;
	boolean copy = false;
	
	public MonsterGroupPack(WeightedMobSpawnGroup monster, String biome, MonsterType type)
	{
		monsterPack = new HashMap<String, WeightedMobSpawnGroup>();
		monsterActive = new HashMap<String, Boolean>();
		this.name = monster.getMobName();
		this.type = type;
		add(monster, biome);
	}
	public boolean fit(WeightedMobSpawnGroup monster, MonsterType type)
	{
		if(monster != null)
		return monster.getMobName().equalsIgnoreCase(name) && type == this.type;
		return false;
	}
	public void add(WeightedMobSpawnGroup monster, String biome)
	{
		if(!biome.equals(""))
		{
		monsterPack.put(biome, monster);
		monsterActive.put(biome, true);
		}
	}
	public void setCopy(boolean b)
	{
		copy = b;
		if(b && !monsterPack.containsKey("all"))
		{
			monsterPack.put("all", monsterPack.get(biome));
		}
	}
	public WeightedMobSpawnGroup getMonster(String biome)
	{
		if(copy) return monsterPack.get("all");
		return(monsterPack.get(biome));
	}
	public boolean isUsed(String biome)
	{
		return(monsterActive.get(biome));
	}
	public void setUsed(String biome, boolean b, InputMonster input)
	{
		if(!monsterPack.containsKey(biome))
		{
			WeightedMobSpawnGroup monster = monsterPack.get(monsterPack.keySet().toArray()[0]);
			monsterPack.put(biome, new WeightedMobSpawnGroup(monster.getMobName(), monster.getWeight(), monster.getMin(), monster.getMax()));
		}
		monsterActive.put(biome, b);
		boolean usedOnce = false;
		for(String k:monsterActive.keySet().toArray(new String[]{}))
		{
			if(monsterActive.get(k)) usedOnce = true;
		}
		if(!usedOnce) remove(input);
	}
	public boolean compareRes(WeightedMobSpawnGroup m1, WeightedMobSpawnGroup m2)
	{
		return m1.getDefaultMobType() == m2.getDefaultMobType() && m1.getWeight() == m2.getWeight() && m1.getMax() == m2.getMax() && m1.getMin() == m2.getMin();
	}
	public boolean contains(String biome)
	{
		return monsterPack.containsKey(biome);
	}
	public void remove(InputMonster input)
	{
		int answer = JOptionPane.showConfirmDialog(Main.main.mainFrame, "The Resource isn't used anymore. Delete?");
		if(answer == JOptionPane.OK_OPTION)
		{
			input.removeMonster(this);
		}
		if(answer == JOptionPane.CANCEL_OPTION)
		{
			monsterActive.put(biome, true);
			input.useHere.setSelected(true);
		}
	}
}
