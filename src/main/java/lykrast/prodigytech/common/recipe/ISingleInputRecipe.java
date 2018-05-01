package lykrast.prodigytech.common.recipe;

import net.minecraft.item.ItemStack;

public interface ISingleInputRecipe {
	/**
	 * Say if this recipe has an Ore Dictionary input.
	 * @return if this recipe has an Ore Dictionary input
	 */
	public boolean isOreRecipe();
	public ItemStack getInput();
	public String getOreInput();
	public boolean isValidInput(ItemStack in);
}
