package lykrast.prodigytech.common.recipe;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public abstract class SimpleRecipeManagerAbstract<T extends ISingleInputRecipe> {
	protected final ItemMap<T> recipes = new ItemMap<>();
	
	/**
	 * Only a single Manager for each machine is supposed to exist at a given time.
	 */
	protected SimpleRecipeManagerAbstract() {}
	
	/**
	 * Builds and returns a List of all registered recipes.
	 * @return a List of all registered recipes
	 */
	public List<T> getAllRecipes() {
		return recipes.getAllContent();
	}
	
	/**
	 * Register a recipe.
	 * @param recipe recipe to register
	 * @return registered recipe
	 */
	public T addRecipe(T recipe) {
		if (recipe.isOreRecipe()) recipes.add(recipe.getOreInput(), recipe);
		else recipes.add(recipe.getInput(), recipe);
		return recipe;
	}
	
	/**
	 * Find a recipe whose input matches the given ItemStack.
	 * This also checks for any Ore Dictionary Tag the stack may have.
	 * @param in ItemStack to find a recipe for
	 * @return found recipe, or null if none is found
	 */
	@Nullable
	public T findRecipe(ItemStack in) {
		return recipes.find(in);
	}
	
	/**
	 * Attempts to remove a recipe whose input matches the given ItemStack.
	 * @param in ItemStack to find and remove a recipe for
	 * @return found and removed recipe, or null if none is found
	 */
	@Nullable
	public T removeRecipe(ItemStack in) {
		return recipes.remove(in);
	}
	
	/**
	 * Attempts to remove a recipe whose input matches the given Ore Dictionary tag.
	 * @param in Ore Dictionary tag to find and remove a recipe for
	 * @return found and removed recipe, or null if none is found
	 */
	@Nullable
	public T removeOreRecipe(String in) {
		return recipes.remove(in);
	}
	
	/**
	 * Removes all registered recipes.
	 */
	public void removeAll() {
		recipes.clear();
	}
	
	/**
	 * Check if a given ItemStack is a valid input for this Manager.
	 * Both the item itself and its Ore Dictionary tags are checked.
	 * @param check ItemStack to check
	 * @return whether or not a recipe could be found
	 */
	public boolean isValidInput(ItemStack check) {
		return recipes.isValid(check);
	}
	
	/**
	 * Register all base recipes in this manager.
	 */
	public abstract void init();

}
