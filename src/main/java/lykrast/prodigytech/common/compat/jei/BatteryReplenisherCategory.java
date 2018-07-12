package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class BatteryReplenisherCategory extends ProdigyCategory<BatteryReplenisherWrapper> {
	public static final String UID = "ptreplenisher";

	public BatteryReplenisherCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 138, 0, 118, 54), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BatteryReplenisherWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		//Energion
		guiItemStacks.init(0, true, 0, 36);
		//Input
		guiItemStacks.init(1, true, 36, 18);
		//Output
		guiItemStacks.init(2, false, 96, 18);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<BatteryReplenisherWrapper> list = new ArrayList<>();
		
		list.add(new BatteryReplenisherWrapper(new ItemStack(ModItems.energionBatteryEmpty), 
				new ItemStack(ModItems.energionBattery), guiHelper));
		list.add(new BatteryReplenisherWrapper(new ItemStack(ModItems.energionBatteryDoubleEmpty), 
				new ItemStack(ModItems.energionBatteryDouble), guiHelper));
		list.add(new BatteryReplenisherWrapper(new ItemStack(ModItems.energionBatteryTripleEmpty), 
				new ItemStack(ModItems.energionBatteryTriple), guiHelper));
		
		registry.addRecipes(list, UID);
	}

}
