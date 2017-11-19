package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.recipe.SoldererManager;
import lykrast.prodigytech.common.recipe.SoldererManager.SoldererRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class SoldererCategory extends ProdigyCategory<SoldererWrapper> {
	public static final String UID = "ptsolderer";

	public SoldererCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 62, 118, 54), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SoldererWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		//Pattern
		guiItemStacks.init(0, true, 0, 0);
		//Gold
		guiItemStacks.init(1, true, 0, 36);
		//Additive
		guiItemStacks.init(2, true, 36, 0);
		//Plate
		guiItemStacks.init(3, true, 36, 36);
		//Output
		guiItemStacks.init(4, false, 96, 18);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<SoldererWrapper> list = new ArrayList<>();
		
		for (SoldererRecipe recipe : SoldererManager.RECIPES)
		{
			list.add(new SoldererWrapper(recipe, guiHelper));
		}
		
		registry.addRecipes(list, UID);
	}

}
