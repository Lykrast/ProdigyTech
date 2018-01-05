package lykrast.prodigytech.common.compat.jei;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class HoloFactoryCategory extends ProdigyCategory<HoloFactoryWrapper> {
	public static final String UID = "ptholofactory";

	public HoloFactoryCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 116, 82, 26), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, HoloFactoryWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		registry.addRecipes(ImmutableList.of(new HoloFactoryWrapper()), UID);
	}

}
