package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.powercrystals.netherores.NetherOresCore;

public class mod_NetherOres extends BaseModMp
{
	@Override
	public void load()
	{
		NetherOresCore.init(Minecraft.getMinecraftDir() + "/config/NetherOres.cfg", false);
		
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 0), "Nether Coal");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 1), "Nether Diamond");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 2), "Nether Gold Ore");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 3), "Nether Iron Ore");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 4), "Nether Lapis Lazuli");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 5), "Nether Redstone Ore");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 6), "Nether Copper Ore");
		ModLoader.addName(new ItemStack(NetherOresCore.blockNetherOres, 1, 7), "Nether Tin Ore");
	}
	
	@Override
	public void generateNether(World world, Random random, int i, int j)
	{
		NetherOresCore.generateNether(world, random, i, j);
	}
	
	@Override
	public void modsLoaded()
	{
		MinecraftForgeClient.preloadTexture(NetherOresCore.terrainTexture);
	}
	
	@Override
	public String getVersion() 
	{
		return NetherOresCore.version;
	}
	
	public static void causeFuseSoundAt(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fuse", 1.0F, 1.0F);
	}
	
	@Override
	public void handlePacket(Packet230ModLoader packet)
	{
		if (packet.packetType == 1)
		{
			causeFuseSoundAt(ModLoader.getMinecraftInstance().theWorld, packet.dataInt[0], packet.dataInt[1], packet.dataInt[2]);
		}
	}
}
