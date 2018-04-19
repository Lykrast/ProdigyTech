package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class SimpleRecipeManager {
	/**
	 * The map containing all recipes that check Item and metadata. Will have to be redone in 1.13.
	 */
	protected final HashMap<Pair<Item, Integer>, SimpleRecipe> recipes = new HashMap<>();
	/**
	 * The map containing all recipes that check Ore Dictionary tags.
	 */
	protected final HashMap<String, SimpleRecipe> recipesOre = new HashMap<>();
	
	/**
	 * Only a single Manager for each machine is supposed to exist at a given time.
	 */
	protected SimpleRecipeManager() {}
	
	/**
	 * Builds and returns a List of all registered recipes.
	 * @return a List of all registered recipes
	 */
	public List<SimpleRecipe> getAllRecipes() {
		List<SimpleRecipe> list = new ArrayList<>();
		list.addAll(recipes.values());
		list.addAll(recipesOre.values());
		
		return list;
	}
	
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
	
	protected Pair<Item, Integer> stackToPair(ItemStack stack) {
		return new ImmutablePair<Item, Integer>(stack.getItem(), stack.getMetadata());
	}
	
	protected Pair<Item, Integer> stackToWildcardPair(ItemStack stack) {
		return new ImmutablePair<Item, Integer>(stack.getItem(), OreDictionary.WILDCARD_VALUE);
	}
	
	/**
	 * Register a recipe.
	 * @param recipe recipe to register
	 * @return registered recipe
	 */
	protected SimpleRecipe addRecipe(SimpleRecipe recipe)
	{
		if (recipe.isOreRecipe()) recipesOre.put(recipe.getOreInput(), recipe);
		else recipes.put(stackToPair(recipe.getInput()), recipe);
		return recipe;
	}
	
	/**
	 * Find a recipe whose input matches the given ItemStack.
	 * This also checks for any Ore Dictionary Tag the stack may have.
	 * @param in ItemStack to find a recipe for
	 * @return found recipe, or null if none is found
	 */
	@Nullable
	public SimpleRecipe findRecipe(ItemStack in)
	{
		SimpleRecipe recipe = recipes.get(stackToPair(in));
		if (recipe != null) return recipe;
		recipe = recipes.get(stackToWildcardPair(in));
		if (recipe != null) return recipe;
		
		//Check for ore
		int[] ores = OreDictionary.getOreIDs(in);
		for (int i : ores)
		{
			recipe = findOreRecipe(OreDictionary.getOreName(i));
			if (recipe != null) return recipe;
		}
		
		return null;
	}
	
	/**
	 * Find a recipe whose input matches the given Ore Dictionary tag.
	 * @param in Ore Dictionary tag to find a recipe for
	 * @return found recipe, or null if none is found
	 */
	@Nullable
	public SimpleRecipe findOreRecipe(String in)
	{
		return recipesOre.get(in);
	}
	
	/**
	 * Attempts to remove a recipe whose input matches the given ItemStack.
	 * @param in ItemStack to find and remove a recipe for
	 * @return found and removed recipe, or null if none is found
	 */
	@Nullable
	public SimpleRecipe removeRecipe(ItemStack in)
	{
		SimpleRecipe removed = recipes.remove(stackToPair(in));
		if (removed != null) return removed;
		
		return recipes.remove(stackToWildcardPair(in));
	}
	
	/**
	 * Attempts to remove a recipe whose input matches the given Ore Dictionary tag.
	 * @param in Ore Dictionary tag to find and remove a recipe for
	 * @return found and removed recipe, or null if none is found
	 */
	@Nullable
	public SimpleRecipe removeOreRecipe(String in)
	{
		return recipesOre.remove(in);
	}
	
	/**
	 * Check if a given ItemStack is a valid input for this Manager.
	 * Both the item itself and its Ore Dictionary tags are checked.
	 * @param check ItemStack to check
	 * @return whether or not a recipe could be found
	 */
	public boolean isValidInput(ItemStack check)
	{
		return findRecipe(check) != null;
	}
	
	/**
	 * Register all base recipes in this manager.
	 */
	public abstract void init();

}
