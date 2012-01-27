package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.powercrystals.netherores.NetherOresCore;

public class mod_NetherOres extends BaseModMp
{
	public void load()
	{
		NetherOresCore.init(Minecraft.getMinecraftDir() + "/config/NetherOres.cfg", false);
		
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 0), "Nether Coal");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 1), "Nether Diamond");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 2), "Nether Gold Ore");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 3), "Nether Iron Ore");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 4), "Nether Lapis Lazuli");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 5), "Nether Redstone Ore");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 6), "Nether Copper Ore");
		ModLoader.AddName(new ItemStack(NetherOresCore.blockNetherOres, 1, 7), "Nether Tin Ore");
	}
	
	@Override
	public void GenerateNether(World world, Random random, int i, int j)
	{
		NetherOresCore.generateNether(world, random, i, j);
	}
	
	@Override
	public void ModsLoaded()
	{
		MinecraftForgeClient.preloadTexture(NetherOresCore.terrainTexture);
	}
	
	@Override
	public String getVersion() 
	{
		return NetherOresCore.version;
	}
	
}
