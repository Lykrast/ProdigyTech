package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.recipe.AtomicReshaperManager;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager.AtomicReshaperRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class AtomicReshaperCategory extends ProdigyCategory<AtomicReshaperWrapper> {
	public static final String UID = "ptreshaper";

	public AtomicReshaperCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 142, 142, 54), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AtomicReshaperWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		//Primordium
		guiItemStacks.init(0, true, 0, 36);
		//Input
		guiItemStacks.init(1, true, 36, 18);
		//Output
		guiItemStacks.init(2, false, 120, 18);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<AtomicReshaperWrapper> list = new ArrayList<>();
		
		List<AtomicReshaperRecipe> recipes = AtomicReshaperManager.INSTANCE.getAllRecipes();
		recipes.stream().sorted().forEachOrdered(recipe -> list.add(new AtomicReshaperWrapper(recipe, guiHelper)));
		
		registry.addRecipes(list, UID);
	}

}
