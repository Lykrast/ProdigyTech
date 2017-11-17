package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager.RotaryGrinderRecipe;
import lykrast.prodigytech.common.util.Config;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;

public class RotaryGrinderCategory extends ProdigyCategory<RotaryGrinderWrapper> {
	public static final String UID = "ptgrinder";
	private final IDrawableAnimated arrow;

	public RotaryGrinderCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 36, 82, 26), UID);
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, Config.rotaryGrinderProcessTime, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft)
	{
		arrow.draw(minecraft, 24, 5);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RotaryGrinderWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		List<RotaryGrinderWrapper> list = new ArrayList<>();
		
		for (RotaryGrinderRecipe recipe : RotaryGrinderManager.RECIPES)
		{
			list.add(new RotaryGrinderWrapper(recipe));
		}
		
		registry.addRecipes(list, UID);
	}

}
