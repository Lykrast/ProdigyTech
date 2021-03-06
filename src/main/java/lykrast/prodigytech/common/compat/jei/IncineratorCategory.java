package lykrast.prodigytech.common.compat.jei;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.util.Config;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

public class IncineratorCategory extends ProdigyCategory<IncineratorWrapper> {
	public static final String UID = "ptincinerator";
	private final IDrawableAnimated arrow;

	public IncineratorCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.drawableBuilder(ProdigyTechJEI.GUI, 0, 36, 82, 26)
				.addPadding(0, (int)(Config.incineratorChance * 100) >= 100 ? 0 : 10 , 0, 0).build(), UID);
		
		this.arrow = guiHelper.createAnimatedDrawable(ProdigyTechJEI.getDefaultProcessArrow(guiHelper), Config.incineratorProcessTime, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft)
	{
		arrow.draw(minecraft, 24, 5);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IncineratorWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		registry.addRecipes(ImmutableList.of(new IncineratorWrapper()), UID);
	}

}
