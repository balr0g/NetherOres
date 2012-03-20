package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.IConnectionHandler;
import net.minecraft.src.forge.IPacketHandler;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.forge.MessageManager;
import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.powercrystals.netherores.NetherOresCore;

public class mod_NetherOres extends NetworkMod implements IConnectionHandler, IPacketHandler
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
	
	@Override
	public String getPriorities()
	{
		return NetherOresCore.priorities;
	}
	
	public static void causeFuseSoundAt(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fuse", 1.0F, 1.0F);
	}
	
	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] data)
	{
		int coords[] = new int[3];
		if(!channel.equals("mod_NetherOres"))
			return;
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
			ObjectInput input = new ObjectInputStream(inputStream);
			for(int i = 0; i < 3; i++) {
				coords[i] = input.readInt();
			}
			input.close();
			inputStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		causeFuseSoundAt(ModLoader.getMinecraftInstance().theWorld, coords[0], coords[1], coords[2]);
	}
	
	@Override
	public void OnLogin(NetworkManager network, Packet1Login login)
	{
		MessageManager.getInstance().registerChannel(network, this, "mod_NetherOres");
	}
	
	@Override
	public void OnDisconnect(NetworkManager network, String message, Object[] args)
	{
	}
	
	@Override
	public void OnConnect(NetworkManager network)
	{
	}
	
	@Override
	public boolean clientSideRequired()
	{
		return true;
	}
	
	@Override
	public boolean serverSideRequired()
	{
		return false;
	}
}
