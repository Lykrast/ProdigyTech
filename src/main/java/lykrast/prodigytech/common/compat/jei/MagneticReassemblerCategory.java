package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.recipe.MagneticReassemblerManager;
import lykrast.prodigytech.common.recipe.MagneticReassemblerManager.MagneticReassemblerRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class MagneticReassemblerCategory extends ProdigyCategory<MagneticReassemblerWrapper> {
	public static final String UID = "ptreassembler";

	public MagneticReassemblerCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 36, 82, 26), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MagneticReassemblerWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<MagneticReassemblerWrapper> list = new ArrayList<>();
		
		for (MagneticReassemblerRecipe recipe : MagneticReassemblerManager.RECIPES)
		{
			list.add(new MagneticReassemblerWrapper(recipe, guiHelper));
		}
		
		registry.addRecipes(list, UID);
	}

}
