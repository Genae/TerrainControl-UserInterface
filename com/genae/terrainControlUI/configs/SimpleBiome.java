package com.genae.terrainControlUI.configs;

import com.khorn.terraincontrol.DefaultBiome;
import com.khorn.terraincontrol.LocalBiome;
import com.khorn.terraincontrol.configuration.BiomeConfig;

public class SimpleBiome implements LocalBiome
{

    private String Name;
    private int ID;
    private boolean isCustom = false;
    private int custom_id;

    public SimpleBiome(DefaultBiome biome)
    {
        this.Name = biome.Name;
        this.ID = biome.Id;
    }

    public SimpleBiome(String _name, int _id)
    {
        this.Name = _name;
        this.ID = _id;
        this.isCustom = true;
    }

    public void setCustomID( int id)
    {
        this.custom_id = id;
    }

    public boolean isCustom()
    {
        return this.isCustom;
    }

    public void setCustom(BiomeConfig config)
    {
        
    }

    public String getName()
    {
        return this.Name;
    }

    public int getId()
    {
        return this.ID;
    }

    public int getCustomId()
    {
        return this.custom_id;
    }

    public float getTemperature()
    {
    	
    	float value = 0.0f;
    	if(Name.equals("Ocean"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("Plains"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("Desert"))
    	{
    		value = 1.0f;
    	}
    	else if(Name.equals("Extreme Hills"))
    	{
    		value = 0.2f;
    	}
    	else if(Name.equals("Forest"))
    	{
    		value = 0.7f;
    	}
    	else if(Name.equals("Taiga"))
    	{
    		value = 0.05f;
    	}
    	else if(Name.equals("Swampland"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("River"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("Hell"))
    	{
    		value = 1.0f;
    	}
    	else if(Name.equals("Sky"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("FrozenOcean"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("FrozenRiver"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("Ice Plains"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("Ice Mountains"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("MushroomIsland"))
    	{
    		value = 0.9f;
    	}
    	//TODO
    	else if(Name.equals("MushroomIslandShore"))
    	{
    		value = 0.9f;
    	}
    	else if(Name.equals("Beach"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("DesertHills"))
    	{
    		value = 1.0f;
    	}
    	else if(Name.equals("ForestHills"))
    	{
    		value = 0.7f;
    	}
    	else if(Name.equals("TaigaHills"))
    	{
    		value = 0.05f;
    	}
    	else if(Name.equals("Extreme Hills Edge"))
    	{
    		value = 0.2f;
    	}
    	else if(Name.equals("Jungle"))
    	{
    		value = 1.0f;
    	}
    	else if(Name.equals("JungleHills"))
    	{
    		value = 1.0f;
    	} 	
    	else return 0;
        return value;  
    }

    public float getWetness()
    {
    	float value = 0.0f;
    	if(Name.equals("Ocean"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("Plains"))
    	{
    		value = 0.4f;
    	}
    	else if(Name.equals("Desert"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("Extreme Hills"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Forest"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("Taiga"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("Swampland"))
    	{
    		value = 0.9f;
    	}
    	else if(Name.equals("River"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("Hell"))
    	{
    		value = 0;
    	}
    	else if(Name.equals("Sky"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("FrozenOcean"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("FrozenRiver"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("Ice Plains"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("Ice Mountains"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("MushroomIsland"))
    	{
    		value = 1.0f;
    	}
    	//TODO
    	else if(Name.equals("MushroomIslandShore"))
    	{
    		value = 1.0f;
    	}
    	else if(Name.equals("Beach"))
    	{
    		value = 0.4f;
    	}
    	else if(Name.equals("DesertHills"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("ForestHills"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("TaigaHills"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("Extreme Hills Edge"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Jungle"))
    	{
    		value = 0.9f;
    	}
    	else if(Name.equals("JungleHills"))
    	{
    		value = 0.9f;
    	} 	
    	else return 0;
        return value;  
    }

    public float getSurfaceHeight()
    {
    	float value = 0.0f;
    	if(Name.equals("Ocean"))
    	{
    		value = -1.0f;
    	}
    	else if(Name.equals("Plains"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Desert"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Extreme Hills"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Forest"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Taiga"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Swampland"))
    	{
    		value = -0.2f;
    	}
    	else if(Name.equals("River"))
    	{
    		value = -0.5f;
    	}
    	else if(Name.equals("Hell"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Sky"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("FrozenOcean"))
    	{
    		value = -1.0f;
    	}
    	else if(Name.equals("FrozenRiver"))
    	{
    		value = -0.5f;
    	}
    	else if(Name.equals("Ice Plains"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Ice Mountains"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("MushroomIsland"))
    	{
    		value = 0.2f;
    	}
    	//TODO
    	else if(Name.equals("MushroomIslandShore"))
    	{
    		value = -1.0f;
    	}
    	else if(Name.equals("Beach"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("DesertHills"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("ForestHills"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("TaigaHills"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Extreme Hills Edge"))
    	{
    		value = 0.2f;
    	}
    	else if(Name.equals("Jungle"))
    	{
    		value = 0.2f;
    	}
    	else if(Name.equals("JungleHills"))
    	{
    		value = 1.8f;
    	} 	
    	else return 0;
    	return value;
    }

    public float getSurfaceVolatility()
    {
    	float value = 0.0f;
    	if(Name.equals("Ocean"))
    	{
    		value = 0.4f;
    	}
    	else if(Name.equals("Plains"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Desert"))
    	{
    		value = 0.2f;
    	}
    	else if(Name.equals("Extreme Hills"))
    	{
    		value = 1.5f;
    	}
    	else if(Name.equals("Forest"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Taiga"))
    	{
    		value = 0.4f;
    	}
    	else if(Name.equals("Swampland"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("River"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("Hell"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Sky"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("FrozenOcean"))
    	{
    		value = 0.5f;
    	}
    	else if(Name.equals("FrozenRiver"))
    	{
    		value = 0.0f;
    	}
    	else if(Name.equals("Ice Plains"))
    	{
    		value = 0.3f;
    	}
    	else if(Name.equals("Ice Mountains"))
    	{
    		value = 1.3f;
    	}
    	else if(Name.equals("MushroomIsland"))
    	{
    		value = 1.0f;
    	}
    	//TODO
    	else if(Name.equals("MushroomIslandShore"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("Beach"))
    	{
    		value = 0.1f;
    	}
    	else if(Name.equals("DesertHills"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("ForestHills"))
    	{
    		value = 0.7f;
    	}
    	else if(Name.equals("TaigaHills"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("Extreme Hills Edge"))
    	{
    		value = 0.8f;
    	}
    	else if(Name.equals("Jungle"))
    	{
    		value = 0.4f;
    	}
    	else if(Name.equals("JungleHills"))
    	{
    		value = 0.5f;
    	} 	
    	else return 0;
        return value;  
    }

    public byte getSurfaceBlock()
    {
    	byte value = 0;
    	if(Name.equals("Ocean"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Plains"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Desert"))
    	{
    		value = 12;
    	}
    	else if(Name.equals("Extreme Hills"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Forest"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Taiga"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Swampland"))
    	{
    		value = 2;
    	}
    	//TODO
    	else if(Name.equals("River"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Hell"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Sky"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("FrozenOcean"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("FrozenRiver"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Ice Plains"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Ice Mountains"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("MushroomIsland"))
    	{
    		value = 110;
    	}
    	else if(Name.equals("MushroomIslandShore"))
    	{
    		value = 110;
    	}
    	else if(Name.equals("Beach"))
    	{
    		value = 12;
    	}
    	else if(Name.equals("DesertHills"))
    	{
    		value = 12;
    	}
    	else if(Name.equals("ForestHills"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("TaigaHills"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Extreme Hills Edge"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("Jungle"))
    	{
    		value = 2;
    	}
    	else if(Name.equals("JungleHills"))
    	{
    		value = 2;
    	} 	
    	else return 0;
        return value;  
    }

    public byte getGroundBlock()
    {
    	byte value = 0;
    	if(Name.equals("Ocean"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Plains"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Desert"))
    	{
    		value = 12;
    	}
    	else if(Name.equals("Extreme Hills"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Forest"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Taiga"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Swampland"))
    	{
    		value = 3;
    	}
    	//TODO
    	else if(Name.equals("River"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Hell"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Sky"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("FrozenOcean"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("FrozenRiver"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Ice Plains"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Ice Mountains"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("MushroomIsland"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("MushroomIslandShore"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Beach"))
    	{
    		value = 12;
    	}
    	else if(Name.equals("DesertHills"))
    	{
    		value = 12;
    	}
    	else if(Name.equals("ForestHills"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("TaigaHills"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Extreme Hills Edge"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("Jungle"))
    	{
    		value = 3;
    	}
    	else if(Name.equals("JungleHills"))
    	{
    		value = 3;
    	} 	
    	else return 0;
        return value;   
    }
}
