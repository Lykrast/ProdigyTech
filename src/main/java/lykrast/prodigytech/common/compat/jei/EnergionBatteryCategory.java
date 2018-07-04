package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.Item;

public class EnergionBatteryCategory extends ProdigyCategory<EnergionBatteryWrapper> {
	public static final String UID = "ptbattery";

	public EnergionBatteryCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.drawableBuilder(ProdigyTechJEI.GUI, 0, 116, 82, 26).addPadding(0, 10, 11, 10).build(), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, EnergionBatteryWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 11, 4);
		guiItemStacks.init(1, false, 71, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<EnergionBatteryWrapper> list = new ArrayList<>();
		
		for (Item i : EnergionBatteryManager.getBatteryList())
		{
			list.add(new EnergionBatteryWrapper(i, guiHelper));
		}
		
		registry.addRecipes(list, UID);
	}

}
