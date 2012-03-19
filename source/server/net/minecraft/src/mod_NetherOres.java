package net.minecraft.src;

import java.util.Random;

import net.minecraft.src.powercrystals.netherores.NetherOresCore;

public class mod_NetherOres extends BaseModMp
{
	private static mod_NetherOres instance;
	
	public mod_NetherOres()
	{
		NetherOresCore.init("config/NetherOres.cfg", true);
		instance = this;
	}
	
	@Override
	public void generateNether(World world, Random random, int i, int j)
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

	public static void causeFuseSoundAt(World world, int i, int j, int k)
	{
		Packet230ModLoader packet = new Packet230ModLoader();
		int[] coords = {i, j, k};
		packet.dataInt = coords;
		packet.packetType = 1;
		ModLoaderMp.sendPacketToAll(instance, packet);
	}
}
