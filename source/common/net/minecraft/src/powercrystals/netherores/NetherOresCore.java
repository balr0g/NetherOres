package net.minecraft.src.powercrystals.netherores;

import java.io.File;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.IOreHandler;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.Property;
import net.minecraft.src.ic2.api.Ic2Recipes;
import net.minecraft.src.ic2.api.Items;

public class NetherOresCore
{
	public static String terrainTexture = "/NetherOresSprites/block0.png";
	
	public static Block blockNetherOres;
	
	public static Property netherOreBlockId;
	public static Property explosionPower;
	public static Property explosionChances;
	public static Property enableStandardFurnaceRecipes;
	public static Property enableMaceratorRecipes;
	
	public static boolean foundTin;
	public static boolean foundCopper;
	
	public static String version = "1.2.3R1.2.0";
	public static String priorities = "after:mod_IC2;after:mod_RedPowerCore";
	
	public static void init(String configPath, boolean onServer)
	{
		loadConfig(configPath);
		
		blockNetherOres = new BlockNetherOres(Integer.parseInt(netherOreBlockId.value), 0);
		
		ModLoader.registerBlock(blockNetherOres, ItemNetherOre.class);
		
		MinecraftForge.registerOreHandler(new NetherOresCore().new OreHandler());
		
		MinecraftForge.setBlockHarvestLevel(blockNetherOres, 0, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(blockNetherOres, 1, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockNetherOres, 2, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockNetherOres, 3, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(blockNetherOres, 4, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockNetherOres, 5, "pickaxe", 2);
		
		MinecraftForge.registerOre("oreNetherCoal", new ItemStack(blockNetherOres, 1, 0));
		MinecraftForge.registerOre("oreNetherDiamond", new ItemStack(blockNetherOres, 1, 1));
		MinecraftForge.registerOre("oreNetherGold", new ItemStack(blockNetherOres, 1, 2));
		MinecraftForge.registerOre("oreNetherIron", new ItemStack(blockNetherOres, 1, 3));
		MinecraftForge.registerOre("oreNetherLapis", new ItemStack(blockNetherOres, 1, 4));
		MinecraftForge.registerOre("oreNetherRedstone", new ItemStack(blockNetherOres, 1, 5));
		MinecraftForge.registerOre("oreNetherCopper", new ItemStack(blockNetherOres, 1, 6));
		MinecraftForge.registerOre("oreNetherTin", new ItemStack(blockNetherOres, 1, 7));
		
		if (Boolean.parseBoolean(enableMaceratorRecipes.value) == true && ModLoader.isModLoaded("mod_IC2")) {
			Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 0), new ItemStack(Item.coal, 2));
			Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 1), new ItemStack(Item.diamond, 2));
			Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 2), new ItemStack(Items.getItem("goldDust").itemID, 4, 0));
			Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 3), new ItemStack(Items.getItem("ironDust").itemID, 4, 0));
			Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 4), new ItemStack(Item.dyePowder, 8, 4));
			Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 5), new ItemStack(Item.redstone, 6));

			System.out.println("NetherOres: loaded Macerator Recipes");
		}
		
		if(Boolean.parseBoolean(enableStandardFurnaceRecipes.value) == true)
		{
			FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 0, new ItemStack(Item.coal));
			FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 1, new ItemStack(Item.diamond));
			FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 2, new ItemStack(Block.oreGold));
			FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 3, new ItemStack(Block.oreIron));
			FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 4, new ItemStack(Item.dyePowder, 8, 4));
			FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 5, new ItemStack(Item.redstone, 6));
		}
	}
	
	private static void loadConfig(String path)
	{
		File f = new File(path);
		Configuration c = new Configuration(f);
		c.load();
		
		netherOreBlockId = c.getOrCreateBlockIdProperty("ID.NetherOreBlock", 140);
		explosionPower = c.getOrCreateIntProperty("ExplosionPower", Configuration.GENERAL_PROPERTY, 2);
		explosionPower.comment = "How powerful an explosion will be. Creepers are 3, TNT is 4, electrified creepers are 6. This affects both the ability of the explosion to punch through blocks as well as the blast radius.";
		explosionChances = c.getOrCreateIntProperty("ExplosionChances", Configuration.GENERAL_PROPERTY, 3);
		explosionChances.comment = "The number of chances a nether ore has to find another one to detonate. When a nether ore block is mined, it searches a random adjacent block. If that block is a nether ore, it becomes armed. This number controls how many times it searches. It will not stop at arming only one if it finds more than one.";
		enableStandardFurnaceRecipes = c.getOrCreateBooleanProperty("EnableStandardFurnaceRecipes", Configuration.GENERAL_PROPERTY, true);
		enableStandardFurnaceRecipes.comment = "Set this to false to remove the standard furnace recipes (ie, nether iron ore -> normal iron ore). Provided for compatibility with Metallurgy. If you set this to false and no other mod connects to this mod's ores, they will be useless.";
		enableMaceratorRecipes = c.getOrCreateBooleanProperty("EnableMaceratorRecipes", Configuration.GENERAL_PROPERTY, true);
		enableMaceratorRecipes.comment = "Set this to false to disable use of these ores in the IC2 Macerator.";
		
		c.save();
	}
	
	public static void generateNether(World world, Random random, int chunkX, int chunkZ)
	{
		for(int i = 0; i < 24; i++)
		{
			new WorldGenNetherOres(blockNetherOres.blockID, 0, 12).generate(world, random, chunkX + random.nextInt(16),
					random.nextInt(128), chunkZ + random.nextInt(16));
		}
		for(int i = 0; i < 3; i++)
		{
			new WorldGenNetherOres(blockNetherOres.blockID, 1, 8).generate(world, random, chunkX + random.nextInt(16),
					random.nextInt(32), chunkZ + random.nextInt(16));
		}
		for(int i = 0; i < 4; i++)
		{
			new WorldGenNetherOres(blockNetherOres.blockID, 2, 10).generate(world, random, chunkX + random.nextInt(16),
					random.nextInt(96), chunkZ + random.nextInt(16));
		}
		for(int i = 0; i < 8; i++)
		{
			new WorldGenNetherOres(blockNetherOres.blockID, 3, 14).generate(world, random, chunkX + random.nextInt(16),
					random.nextInt(96), chunkZ + random.nextInt(16));
		}
		for(int i = 0; i < 6; i++)
		{
			new WorldGenNetherOres(blockNetherOres.blockID, 4, 3).generate(world, random, chunkX + random.nextInt(16),
					random.nextInt(128), chunkZ + random.nextInt(16));
		}
		for(int i = 0; i < 8; i++)
		{
			new WorldGenNetherOres(blockNetherOres.blockID, 5, 9).generate(world, random, chunkX + random.nextInt(16),
					random.nextInt(96), chunkZ + random.nextInt(16));
		}
		if(foundCopper)
		{
			for(int i = 0; i < 8; i++)
			{
				new WorldGenNetherOres(blockNetherOres.blockID, 6, 14).generate(world, random, chunkX + random.nextInt(16),
						random.nextInt(96), chunkZ + random.nextInt(16));
			}
		}
		if(foundTin)
		{
			for(int i = 0; i < 8; i++)
			{
				new WorldGenNetherOres(blockNetherOres.blockID, 7, 14).generate(world, random, chunkX + random.nextInt(16),
						random.nextInt(96), chunkZ + random.nextInt(16));
			}
		}
	}
	
	public class OreHandler implements IOreHandler
	{

		@Override
		public void registerOre(String oreClass, ItemStack ore)
		{
			if(oreClass == "oreCopper" && !foundCopper)
			{
				foundCopper = true;
				ItemStack smeltedOre = ore.copy();
				smeltedOre.stackSize = 1;
				MinecraftForge.setBlockHarvestLevel(blockNetherOres, 6, "pickaxe", 1);
				if(Boolean.parseBoolean(enableStandardFurnaceRecipes.value) == true)
				{
					FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 6, smeltedOre);
				}
				if (Boolean.parseBoolean(enableMaceratorRecipes.value) == true && ModLoader.isModLoaded("mod_IC2")) {
					Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 6), new ItemStack(smeltedOre.getItem(), 2));
				}
			}
			if(oreClass == "oreTin" && !foundTin)
			{
				foundTin = true;
				ItemStack smeltedOre = ore.copy();
				smeltedOre.stackSize = 1;
				FurnaceRecipes.smelting().addSmelting(blockNetherOres.blockID, 7, smeltedOre);
				if(Boolean.parseBoolean(enableStandardFurnaceRecipes.value) == true)
				{
					MinecraftForge.setBlockHarvestLevel(blockNetherOres, 7, "pickaxe", 1);
				}
				if (Boolean.parseBoolean(enableMaceratorRecipes.value) == true && ModLoader.isModLoaded("mod_IC2")) {
					Ic2Recipes.addMaceratorRecipe(new ItemStack(blockNetherOres.blockID, 1, 7), new ItemStack(smeltedOre.getItem(), 2));
				}

			}
		}
	}
}
