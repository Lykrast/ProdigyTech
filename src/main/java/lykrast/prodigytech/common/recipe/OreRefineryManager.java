package lykrast.prodigytech.common.recipe;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class OreRefineryManager extends SimpleRecipeManagerSecondaryOutput {
	public static final OreRefineryManager INSTANCE = new OreRefineryManager();
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, ItemStack secondary)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, secondary, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, ItemStack secondary)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, secondary, Config.oreRefineryProcessTime));
	}

	@Override
	public void init() {
		addRecipe("plankWood", new ItemStack(Items.STICK, 4), new ItemStack(ModItems.sawdust)).getTimeProcessing();
	}

}
