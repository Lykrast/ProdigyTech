package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.common.recipe.RotaryGrinderManager.RotaryGrinderRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RotaryGrinderWrapper implements IRecipeWrapper {
	private ItemStack out;
	private List<List<ItemStack>> in;
	
	public RotaryGrinderWrapper(RotaryGrinderRecipe recipe)
	{
		List<ItemStack> inputs = new ArrayList<>();
		
		int ore = recipe.getOreID();
		if (ore != -1)
		{
			List<ItemStack> items = OreDictionary.getOres(OreDictionary.getOreName(ore), false);
			inputs.addAll(items);
		}
		else inputs.add(recipe.getInput());
		
		in = Collections.singletonList(inputs);
		out = recipe.getOutput();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, in);
		ingredients.setOutput(ItemStack.class, out);
	}

}
