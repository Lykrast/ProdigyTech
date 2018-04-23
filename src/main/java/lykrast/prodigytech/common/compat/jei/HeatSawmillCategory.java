package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.recipe.HeatSawmillManager;
import lykrast.prodigytech.common.recipe.SimpleRecipeSecondaryOutput;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class HeatSawmillCategory extends ProdigyCategory<SimpleRecipeSecondaryOutputWrapper> {
	public static final String UID = "ptsawmill";

	public HeatSawmillCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 36, 104, 26), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SimpleRecipeSecondaryOutputWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);
		guiItemStacks.init(2, false, 86, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<SimpleRecipeSecondaryOutputWrapper> list = new ArrayList<>();
		
		List<SimpleRecipeSecondaryOutput> recipes = HeatSawmillManager.INSTANCE.getAllRecipes();
		recipes.stream().sorted().forEachOrdered(recipe -> list.add(new SimpleRecipeSecondaryOutputWrapper(recipe, guiHelper)));
		
		registry.addRecipes(list, UID);
	}

}
