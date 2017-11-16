package lykrast.prodigytech.common.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class IncineratorCategory extends ProdigyCategory<IncineratorWrapper> {
	public static final String UID = "ptincinerator";

	public IncineratorCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 36, 82, 26, 0, 20, 0, 0), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IncineratorWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

}
