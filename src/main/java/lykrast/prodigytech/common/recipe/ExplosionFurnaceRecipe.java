package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExplosionFurnaceRecipe {
	public static final List<ExplosionFurnaceRecipe> RECIPES = new ArrayList<>();
	
	private final ItemStack input;
	private final ItemStack output;
	
	public ExplosionFurnaceRecipe(ItemStack input, ItemStack output)
	{
		this.input = input;
		this.output = output;
	}
	
	public ItemStack getInput()
	{
		return input.copy();
	}
	
	public ItemStack getOutput()
	{
		return output.copy();
	}
	
	public boolean isValidInput(ItemStack in)
	{
		return (!in.isEmpty() && in.getItem() == input.getItem() && in.getCount() >= input.getCount());
	}
	
	public static ExplosionFurnaceRecipe addRecipe(ItemStack in, ItemStack out)
	{
		ExplosionFurnaceRecipe recipe = new ExplosionFurnaceRecipe(in, out);
		
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
	
	public static void init()
	{
		addRecipe(new ItemStack(Items.IRON_INGOT, 2), new ItemStack(ModItems.ferramicIngot));
	}

}
