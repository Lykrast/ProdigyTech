package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.client.gui.GuiExplosionFurnace;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class ExplosionFurnaceExplosiveCategory extends ProdigyCategory<ExplosionFurnaceExplosiveWrapper> {
	public static final String UID = "ptexplosionfurnace_exp";

	public ExplosionFurnaceExplosiveCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.drawableBuilder(GuiExplosionFurnace.GUI, 16, 25, 18, 36).addPadding(0, 0, 0, 107).build(), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ExplosionFurnaceExplosiveWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, true, 0, 18);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		List<ExplosionFurnaceExplosiveWrapper> list = new ArrayList<>();
		
		for (ExplosionFurnaceManager.ExplosionFurnaceExplosive recipe : ExplosionFurnaceManager.EXPLOSIVES)
		{
			list.add(new ExplosionFurnaceExplosiveWrapper(recipe));
		}
		
		registry.addRecipes(list, UID);
	}

}
