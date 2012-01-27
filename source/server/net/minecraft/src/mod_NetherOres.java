package net.minecraft.src;

import java.util.Random;

import net.minecraft.src.powercrystals.netherores.NetherOresCore;

public class mod_NetherOres extends BaseModMp
{
	public mod_NetherOres()
	{
		NetherOresCore.init("config/NetherOres.cfg", true);
	}
	
	@Override
	public void GenerateNether(World world, Random random, int i, int j)
	{
		NetherOresCore.generateNether(world, random, i, j);
	}
	
	@Override
	public String getVersion() 
	{
		return NetherOresCore.version;
	}
	
	@Override
	public void load()
	{
	}
	
}
