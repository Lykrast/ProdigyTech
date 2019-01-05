package lykrast.prodigytech.common.compat.jei;

import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PrimordialisReactorWrapper implements IRecipeWrapper {
	private ItemStack out;
	private List<List<ItemStack>> in;
	
	public PrimordialisReactorWrapper(ItemStack input) {
		this(Collections.singletonList(input));
	}
	
	public PrimordialisReactorWrapper(String ore) {
		this(OreDictionary.getOres(ore, false));
	}
	
	private PrimordialisReactorWrapper(List<ItemStack> inputs) {
		in = Collections.singletonList(inputs);
		out = new ItemStack(ModItems.primordium);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, in);
		ingredients.setOutput(VanillaTypes.ITEM, out);
	}

}
