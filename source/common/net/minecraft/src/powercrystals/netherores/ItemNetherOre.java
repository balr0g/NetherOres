package net.minecraft.src.powercrystals.netherores;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemNetherOre extends ItemBlock
{
	public ItemNetherOre(int i)
	{
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	public int getPlacedBlockMetadata(int i)
	{
		return i;
	}
	
	public int getMetadata(int i)
	{
		return i;
	}
	
	public int getIconFromDamage(int i)
	{
		return Math.min(i, 7);
	}
	
	public String getItemNameIS(ItemStack itemstack)
	{
		int md = itemstack.getItemDamage();
		if(md == 0) return "itemNetherCoal";
		if(md == 1) return "itemNetherDiamond";
		if(md == 2) return "itemNetherGold";
		if(md == 3) return "itemNetherIron";
		if(md == 4) return "itemNetherLapis";
		if(md == 5) return "itemNetherRedstone";
		if(md == 6) return "itemNetherCopper";
		return "itemNetherTin";
	}
}
