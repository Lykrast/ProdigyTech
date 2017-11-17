package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.common.recipe.RotaryGrinderManager.RotaryGrinderRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RotaryGrinderWrapper implements IRecipeWrapper {
	private ItemStack in, out;
	
	public RotaryGrinderWrapper(RotaryGrinderRecipe recipe)
	{
		in = recipe.getInput();
		out = recipe.getOutput();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, in);
		ingredients.setOutput(ItemStack.class, out);
	}

}
