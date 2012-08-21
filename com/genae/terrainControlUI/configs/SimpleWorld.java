package com.genae.terrainControlUI.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.khorn.terraincontrol.DefaultBiome;
import com.khorn.terraincontrol.DefaultMaterial;
import com.khorn.terraincontrol.LocalBiome;
import com.khorn.terraincontrol.LocalWorld;
import com.khorn.terraincontrol.configuration.WorldConfig;
import com.khorn.terraincontrol.generator.resourcegens.TreeType;

public class SimpleWorld implements LocalWorld
{
    public long Seed;
    private String Name;
    private int NextBiomeId = DefaultBiome.values().length;

    private int maxBiomeCount = 256;
    private LocalBiome[] Biomes = new LocalBiome[maxBiomeCount];
    private HashMap<String, LocalBiome> BiomeNames = new HashMap<String, LocalBiome>();
    private static ArrayList<LocalBiome> DefaultBiomes = new ArrayList<LocalBiome>();


    private int worldHeight = 256;
    private int heightBits = 8;
    private int CustomBiomesCount = 21;

    public WorldConfig settings;

    public SimpleWorld(long _seed, String _name)
    {
        this.Seed = _seed;
        this.Name = _name;
        for (DefaultBiome biome : DefaultBiome.values())
        {
            Biomes[biome.Id] = new SimpleBiome(biome);
            DefaultBiomes.add(Biomes[biome.Id]);
            BiomeNames.put(Biomes[biome.Id].getName(), Biomes[biome.Id]);
        }
    }

    public LocalBiome AddBiome(String name, int id)
    {
        SimpleBiome biome = new SimpleBiome(name, id);
        biome.setCustomID(CustomBiomesCount++);
        Biomes[biome.getId()] = biome;
        this.BiomeNames.put(biome.getName(), biome);
        return biome;
    }
    public void RemoveBiome(String name, int id)
    {
        SimpleBiome biome = (SimpleBiome) Biomes[id];

        Biomes[id] = null;
        BiomeNames.remove(name);

        CustomBiomesCount--;

        for (LocalBiome localBiome : Biomes)
        {
            if (localBiome == null || !localBiome.isCustom())
                continue;
            SimpleBiome biome2 = (SimpleBiome) localBiome;

            if (biome2.getCustomId() > biome.getCustomId())
                biome2.setCustomID(biome2.getCustomId() - 1);

        }

    }
    public LocalBiome getNullBiome(String name)
    {
        return null;
    }

    public int getMaxBiomesCount()
    {
        return maxBiomeCount;
    }

    public int getFreeBiomeId()
    {
        return NextBiomeId++;
    }

    public LocalBiome getBiomeById(int id)
    {
        return Biomes[id];
    }

    public int getBiomeIdByName(String name)
    {
        return this.BiomeNames.get(name).getId();
    }

    public ArrayList<LocalBiome> getDefaultBiomes()
    {
        return DefaultBiomes;
    }

    public int[] getBiomesUnZoomed(int[] biomeArray, int x, int z, int x_size, int z_size)
    {
        return new int[0];
    }

    public float[] getTemperatures(int x, int z, int x_size, int z_size)
    {
        return new float[0];
    }

    public int[] getBiomes(int[] biomeArray, int x, int z, int x_size, int z_size)
    {
        return new int[0];
    }

    public int getBiome(int x, int z)
    {
        return 0;
    }

    public LocalBiome getLocalBiome(int x, int z)
    {
        return null;
    }

    public double getBiomeFactorForOldBM(int index)
    {
        return 0;
    }

    public void PrepareTerrainObjects(int x, int z, byte[] chunkArray, boolean dry)
    {

    }

    public void PlaceDungeons(Random rand, int x, int y, int z)
    {

    }

    public void PlaceTree(TreeType type, Random rand, int x, int y, int z)
    {

    }

    public void PlacePonds(int BlockId, Random rand, int x, int y, int z)
    {

    }

    public void PlaceIce(int x, int z)
    {

    }

    public boolean PlaceTerrainObjects(Random rand, int chunk_x, int chunk_z)
    {
        return false;
    }

    public void DoBlockReplace()
    {

    }

    public void DoBiomeReplace()
    {

    }

    public int getTypeId(int x, int y, int z)
    {
        return 0;
    }

    public boolean isEmpty(int x, int y, int z)
    {
        return false;
    }

    public void setBlock(int x, int y, int z, int typeId, int data, boolean updateLight, boolean applyPhysics, boolean notifyPlayers)
    {

    }

    public void setBlock(int x, int y, int z, int typeId, int data)
    {

    }

    public int getLiquidHeight(int x, int z)
    {
        return 0;
    }

    public int getHighestBlockYAt(int x, int z)
    {
        return 0;
    }

    public DefaultMaterial getMaterial(int x, int y, int z)
    {
        return null;
    }

    public void setChunksCreations(boolean createNew)
    {

    }

    public int getLightLevel(int x, int y, int z)
    {
        return 0;
    }

    public boolean isLoaded(int x, int y, int z)
    {
        return false;
    }

    public WorldConfig getSettings()
    {
        return this.settings;
    }

    public void setSettings(WorldConfig _settings)
    {
        this.settings = _settings;
    }

    public String getName()
    {
        return this.Name;
    }

    public long getSeed()
    {
        return this.Seed;
    }

    public int getHeight()
    {
        return this.worldHeight;
    }

    public int getHeightBits()
    {
        return this.heightBits;
    }

    public void setHeightBits(int heightBits)
    {

    }
}
