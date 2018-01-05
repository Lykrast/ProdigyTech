package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HoloFactoryWrapper implements IRecipeWrapper {
	private ItemStack output;
	
	public HoloFactoryWrapper()
	{
		output = new ItemStack(ModBlocks.hologram);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> items = new ArrayList<>();
		
		for (Item i : EnergionBatteryManager.BATTERIES)
		{
			items.add(new ItemStack(i));
		}
		ingredients.setInputLists(ItemStack.class, Collections.singletonList(items));
		ingredients.setOutput(ItemStack.class, output);
	}

}
