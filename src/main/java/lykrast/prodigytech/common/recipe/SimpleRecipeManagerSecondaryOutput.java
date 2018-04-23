package lykrast.prodigytech.common.recipe;

import net.minecraft.item.ItemStack;

public abstract class SimpleRecipeManagerSecondaryOutput extends SimpleRecipeManagerAbstract<SimpleRecipeSecondaryOutput> {
	/**
	 * Creates and register a recipe with no secondary output.
	 * @param in input
	 * @param out output
	 * @param time base processing time in ticks
	 * @return the created recipe
	 */
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, int time)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, time));
	}
	
	/**
	 * Creates and register a recipe with an Ore Dictionary input and no secondary output.
	 * @param inOre input Ore Dictionary tag
	 * @param out output
	 * @param time base processing time in ticks
	 * @return the created recipe
	 */
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, int time)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, time));
	}
	
	/**
	 * Creates and register a recipe with no secondary output.
	 * @param in input
	 * @param out output
	 * @param secondary secondary output
	 * @param time base processing time in ticks
	 * @return the created recipe
	 */
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, ItemStack secondary, int time)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, secondary, time));
	}
	
	/**
	 * Creates and register a recipe with an Ore Dictionary input and no secondary output.
	 * @param inOre input Ore Dictionary tag
	 * @param out output
	 * @param secondary secondary output
	 * @param time base processing time in ticks
	 * @return the created recipe
	 */
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, ItemStack secondary, int time)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, secondary, time));
	}
}
