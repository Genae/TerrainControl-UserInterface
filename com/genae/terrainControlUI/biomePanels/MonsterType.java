package com.genae.terrainControlUI.biomePanels;

public enum MonsterType 
{
	Monster("Monster"),
	Creature("Creature"),
	WaterCreature("WaterCreature");
	
	String string;
	
	MonsterType(String string)
	{
		this.string = string;
	}
	public static MonsterType get(String str)
	{
		if(str.equals("Monster")) return Monster;
		if(str.equals("Creature")) return Creature;
		if(str.equals("WaterCreature")) return WaterCreature;
		return null;
	}
}
