package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.client.gui.GuiExplosionFurnace;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;

public class ExplosionFurnaceExplosiveCategory extends ProdigyCategory<ExplosionFurnaceExplosiveWrapper> {
	public static final String UID = "ptexplosionfurnace_exp";

	public ExplosionFurnaceExplosiveCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(GuiExplosionFurnace.GUI, 16, 25, 18, 36, 0, 0, 0, 80), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ExplosionFurnaceExplosiveWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, true, 0, 18);

		guiItemStacks.set(ingredients);
	}

}
