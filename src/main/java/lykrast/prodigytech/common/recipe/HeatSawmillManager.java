package lykrast.prodigytech.common.recipe;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class HeatSawmillManager extends SimpleRecipeManagerSecondaryOutput {
	public static final HeatSawmillManager INSTANCE = new HeatSawmillManager();
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, Config.heatSawmillProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, Config.heatSawmillProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, ItemStack secondary)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, secondary, Config.heatSawmillProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, ItemStack secondary)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, secondary, Config.heatSawmillProcessTime));
	}

	@Override
	public void init() {
		addRecipe("plankWood", new ItemStack(Items.STICK, (int)(2 * Config.heatSawmillStickMultiplier)), new ItemStack(ModItems.sawdust));
	}

}
