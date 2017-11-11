package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExplosionFurnaceManager {
	public static final List<ExplosionFurnaceRecipe> RECIPES = new ArrayList<>();
	public static final List<ExplosionFurnaceExplosive> EXPLOSIVES = new ArrayList<>();
	
	public static ExplosionFurnaceRecipe addRecipe(ItemStack in, ItemStack out, int power)
	{
		return addRecipe(new ExplosionFurnaceRecipe(in, out, power));
	}
	
	public static ExplosionFurnaceRecipe addRecipe(ItemStack in, ItemStack out, int power, ItemStack reagent, int craftPerReagent)
	{
		return addRecipe(new ExplosionFurnaceRecipe(in, out, power, reagent, craftPerReagent));
	}
	
	private static ExplosionFurnaceRecipe addRecipe(ExplosionFurnaceRecipe recipe)
	{
		RECIPES.add(recipe);
		return recipe;
	}
	
	public static ExplosionFurnaceRecipe findRecipe(ItemStack in)
	{
		for (ExplosionFurnaceRecipe recipe : RECIPES)
		{
			if (recipe != null && recipe.isValidInput(in)) return recipe;
		}
		
		return null;
	}
	
	public static ExplosionFurnaceExplosive addExplosive(ItemStack explosive, ItemStack reactant, int amount)
	{
		ExplosionFurnaceExplosive recipe = new ExplosionFurnaceExplosive(explosive, reactant, amount);
		
		EXPLOSIVES.add(recipe);
		return recipe;
	}
	
	public static ExplosionFurnaceExplosive findExplosive(ItemStack explosive, ItemStack reactant)
	{
		for (ExplosionFurnaceExplosive recipe : EXPLOSIVES)
		{
			if (recipe != null && recipe.isValidExplosive(explosive) && recipe.isValidReactant(reactant)) return recipe;
		}
		
		return null;
	}
	
	public static void init()
	{
		addRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.ferramicIngot), 90, new ItemStack(Items.CLAY_BALL), 4);
		addExplosive(new ItemStack(Items.GUNPOWDER, 5), new ItemStack(Blocks.SAND, 4), 1440);
	}
	
	public static class ExplosionFurnaceRecipe {
		private final ItemStack input;
		private final ItemStack output;
		private final ItemStack reagent;
		private final int power;
		private final int craftPerReagent;
		
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power)
		{
			this(input, output, power, ItemStack.EMPTY, 0);
		}
		
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power, ItemStack reagent, int craftPerReagent)
		{
			this.input = input;
			this.output = output;
			this.power = power;
			this.reagent = reagent;
			this.reagent.setCount(1);
			this.craftPerReagent = craftPerReagent;
		}
		
		public ItemStack getInput()
		{
			return input.copy();
		}
		
		public ItemStack getOutput()
		{
			return output.copy();
		}
		
		public int getRequiredPower()
		{
			return power;
		}
		
		public boolean isValidInput(ItemStack in)
		{
			return (!in.isEmpty() && in.isItemEqual(input) && in.getCount() >= input.getCount());
		}
		
		public ItemStack getReagent()
		{
			if (reagent.isEmpty()) return ItemStack.EMPTY;
			return reagent.copy();
		}
		
		public int getCraftPerReagent()
		{
			return craftPerReagent;
		}
		
		public boolean needReagent()
		{
			return (!reagent.isEmpty() && craftPerReagent > 0);
		}
		
		public boolean isValidReagent(ItemStack reag)
		{
			return reag.isItemEqual(reagent);
		}
	}
	
	public static class ExplosionFurnaceExplosive {
		private final ItemStack explosive;
		private final ItemStack reactant;
		private final int power;
		
		public ExplosionFurnaceExplosive(ItemStack explosive, ItemStack reactant, int power)
		{
			this.explosive = explosive;
			this.reactant = reactant;
			this.power = power;
		}
		
		public ItemStack getExplosive()
		{
			return explosive.copy();
		}
		
		public ItemStack getReactant()
		{
			return reactant.copy();
		}
		
		public int getOptimalPower()
		{
			return power;
		}
		
		public boolean isValidExplosive(ItemStack in)
		{
			return (!in.isEmpty() && in.isItemEqual(explosive));
		}
		
		public boolean isValidReactant(ItemStack in)
		{
			return (!in.isEmpty() && in.isItemEqual(reactant));
		}
		
		public float getEfficiency(ItemStack exp, ItemStack react)
		{
			float expAmount = exp.getCount() * reactant.getCount();
			float reactAmount = react.getCount() * explosive.getCount();
			
			if (expAmount > reactAmount) return reactAmount / expAmount;
			else return expAmount / reactAmount;
		}
		
		public int getPower(ItemStack exp)
		{
			float mult = (float)exp.getCount() / (float)explosive.getCount();
			return (int) (power * mult);
		}
	}

}
