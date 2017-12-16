package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.common.init.ModItems;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CrystalGrowthChamberWrapper implements IRecipeWrapper {
	private ItemStack in, out;
	
	public CrystalGrowthChamberWrapper()
	{		
		in = new ItemStack(ModItems.energionCrystalSeed);
		out = new ItemStack(ModItems.energionCrystal);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, in);
		ingredients.setOutput(ItemStack.class, out);
	}

}
