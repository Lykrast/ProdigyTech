package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.ItemEnergionBattery;
import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class EnergionBatteryCategory extends ProdigyCategory<EnergionBatteryWrapper> {
	public static final String UID = "ptbattery";

	public EnergionBatteryCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 116, 82, 26, 0, 10, 0, 0), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, EnergionBatteryWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<EnergionBatteryWrapper> list = new ArrayList<>();
		
		for (ItemEnergionBattery i : EnergionBatteryManager.getBatteryList())
		{
			list.add(new EnergionBatteryWrapper(i, guiHelper));
		}
		
		registry.addRecipes(list, UID);
	}

}
