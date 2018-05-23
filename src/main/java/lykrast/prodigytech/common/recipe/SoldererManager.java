package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SoldererManager {
	//A lot of this is hardcoded (like gold amounts or using Circuit Plates)
	//May change later if REALLY needed
	public static final List<SoldererRecipe> RECIPES = new ArrayList<>();
	private static int idGoldDust, idGoldTinyDust;
	
	public static SoldererRecipe addRecipe(ItemStack pattern, ItemStack additive, ItemStack output, int gold)
	{
		return addRecipe(new SoldererRecipe(pattern, additive, output, gold));
	}
	
	public static SoldererRecipe addRecipe(ItemStack pattern, ItemStack additive, ItemStack output, int gold, int time)
	{
		return addRecipe(new SoldererRecipe(pattern, additive, output, gold, time));
	}
	
	private static SoldererRecipe addRecipe(SoldererRecipe recipe)
	{
		RECIPES.add(recipe);
		return recipe;
	}
	
	public static SoldererRecipe findRecipe(ItemStack pattern, ItemStack additive, int gold)
	{
		for (SoldererRecipe recipe : RECIPES)
			if (recipe.isValidInput(pattern, additive, gold)) return recipe;
		
		return null;
	}
	
	public static SoldererRecipe removeRecipe(ItemStack pattern, ItemStack additive, int gold)
	{
		SoldererRecipe recipe = findRecipe(pattern, additive, gold);
		if (recipe != null) RECIPES.remove(recipe);
		
		return recipe;
	}
	
	public static boolean isValidPattern(ItemStack pattern)
	{
		for (SoldererRecipe recipe : RECIPES)
			if (recipe.isValidPattern(pattern)) return true;
		
		return false;
	}
	
	public static boolean isValidAdditive(ItemStack additive)
	{
		for (SoldererRecipe recipe : RECIPES)
			if (recipe.requiresAdditive() && recipe.isValidAdditive(additive)) return true;
		
		return false;
	}
	
	public static int getGoldAmount(ItemStack stack)
	{
		if (stack.isEmpty()) return 0;
		int[] oreIDs = OreDictionary.getOreIDs(stack);
		for (int i : oreIDs)
		{
			if (i == idGoldDust) return 9;
			else if (i == idGoldTinyDust) return 1;
		}
		return 0;
	}
	
	public static boolean isPlate(ItemStack stack)
	{
		return stack.getItem() == ModItems.circuitPlate;
	}
	
	public static void init()
	{
		idGoldDust = OreDictionary.getOreID("dustGold");
		idGoldTinyDust = OreDictionary.getOreID("dustTinyGold");
		
		addRecipe(new ItemStack(ModItems.patternCircuitCrude), ItemStack.EMPTY, 
				new ItemStack(ModItems.circuitCrude), 3);
		addRecipe(new ItemStack(ModItems.patternCircuitRefined), new ItemStack(Items.IRON_INGOT), 
				new ItemStack(ModItems.circuitRefined), 6, (int) (Config.soldererProcessTime * 1.5));
		addRecipe(new ItemStack(ModItems.patternCircuitPerfected), new ItemStack(Items.DIAMOND), 
				new ItemStack(ModItems.circuitPerfected), 9, Config.soldererProcessTime * 2);
	}
	
	public static class SoldererRecipe {
		private final ItemStack pattern;
		private final ItemStack additive;
		private final ItemStack output;
		private final int time, gold;
		
		public SoldererRecipe(ItemStack pattern, ItemStack additive, ItemStack output, int gold)
		{
			this(pattern, additive, output, gold, Config.soldererProcessTime);
		}
		
		public SoldererRecipe(ItemStack pattern, ItemStack additive, ItemStack output, int gold, int time)
		{
			this.pattern = pattern;
			pattern.setCount(1);
			this.additive = additive;
			this.output = output;
			this.gold = gold;
			this.time = time;
		}
		
		public ItemStack getPattern()
		{
			return pattern.copy();
		}
		
		public ItemStack getAdditive()
		{
			return additive.copy();
		}
		
		public ItemStack getOutput()
		{
			return output.copy();
		}
		
		public int getGoldAmount()
		{
			return gold;
		}
		
		public int getTimeTicks()
		{
			return time;
		}
		
		public int getTimeProcessing()
		{
			return time * 10;
		}
		
		public boolean isValidInput(ItemStack pattern, ItemStack additive, int gold)
		{
			if (!isValidPattern(pattern)) return false;
			if (!isValidAdditive(additive)) return false;
			
			return isEnoughGold(gold);
		}
		
		public boolean isValidPattern(ItemStack pattern)
		{
			return pattern.isItemEqual(this.pattern);
		}
		
		public boolean requiresAdditive()
		{
			return !additive.isEmpty();
		}
		
		public boolean isValidAdditive(ItemStack additive)
		{
			if (!requiresAdditive()) return true;
			return (additive.isItemEqual(this.additive) && additive.getCount() >= this.additive.getCount());
		}
		
		public boolean isEnoughGold(int gold)
		{
			return gold >= this.gold;
		}
	}

}
