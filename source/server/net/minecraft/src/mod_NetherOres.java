package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.forge.IConnectionHandler;
import net.minecraft.src.forge.IPacketHandler;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.powercrystals.netherores.NetherOresCore;

public class mod_NetherOres extends NetworkMod
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
	
	@Override
	public String getPriorities()
	{
		return NetherOresCore.priorities;
	}

	public static void causeFuseSoundAt(World world, int i, int j, int k)
	{
		byte[] arry = null;

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutput output = new ObjectOutputStream(outputStream);
			int[] coords = {i, j, k};
			for(int a = 0; a < 3; a++) {
				output.writeInt(coords[a]);
			}
			arry = outputStream.toByteArray();
			output.close();
			outputStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		if(arry == null || arry.length == 0) return;
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "mod_NetherOres";
		packet.data = arry;
		packet.length = arry.length;
		
		List<EntityPlayerMP> playerList = world.playerEntities;
		for(EntityPlayerMP e : playerList) {
			e.playerNetServerHandler.sendPacket(packet);
		}
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
