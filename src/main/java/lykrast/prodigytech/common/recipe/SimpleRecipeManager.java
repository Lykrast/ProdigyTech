package lykrast.prodigytech.common.recipe;

import net.minecraft.item.ItemStack;

//I may have gone too far in the abstraction...
/**
 * A SimpleRecipeManager for plain SimpleRecipes. Contains some utility methods for convenience.
 * @author Lykrast
 */
public abstract class SimpleRecipeManager extends SimpleRecipeManagerAbstract<SimpleRecipe> {
	/**
	 * Creates and register a recipe.
	 * @param in input
	 * @param out output
	 * @param time base processing time in ticks
	 * @return the created recipe
	 */
	public SimpleRecipe addRecipe(ItemStack in, ItemStack out, int time)
	{
		return addRecipe(new SimpleRecipe(in, out, time));
	}
	
	/**
	 * Creates and register a recipe with an Ore Dictionary input.
	 * @param inOre input Ore Dictionary tag
	 * @param out output
	 * @param time base processing time in ticks
	 * @return the created recipe
	 */
	public SimpleRecipe addRecipe(String inOre, ItemStack out, int time)
	{
		return addRecipe(new SimpleRecipe(inOre, out, time));
	}
}
