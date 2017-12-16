package lykrast.prodigytech.common.compat.jei;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.client.gui.GuiCrystalGrowthChamber;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

public class CrystalGrowthChamberCategory extends ProdigyCategory<CrystalGrowthChamberWrapper> {
	public static final String UID = "ptcrystalgrowthchamber";
	private final IDrawableAnimated north, south, east, west;

	public CrystalGrowthChamberCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(GuiCrystalGrowthChamber.GUI, 61, 16, 89, 54), UID);
		
		IDrawableStatic northS = guiHelper.createDrawable(GuiCrystalGrowthChamber.GUI, 176, 0, 18, 18);
		IDrawableStatic southS = guiHelper.createDrawable(GuiCrystalGrowthChamber.GUI, 194, 0, 18, 18);
		IDrawableStatic eastS = guiHelper.createDrawable(GuiCrystalGrowthChamber.GUI, 194, 18, 18, 18);
		IDrawableStatic westS = guiHelper.createDrawable(GuiCrystalGrowthChamber.GUI, 176, 18, 18, 18);
		north = guiHelper.createAnimatedDrawable(northS, 20, IDrawableAnimated.StartDirection.TOP, false);
		south = guiHelper.createAnimatedDrawable(southS, 20, IDrawableAnimated.StartDirection.BOTTOM, false);
		east = guiHelper.createAnimatedDrawable(eastS, 20, IDrawableAnimated.StartDirection.RIGHT, false);
		west = guiHelper.createAnimatedDrawable(westS, 20, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft)
	{
		north.draw(minecraft, 18, 0);
		south.draw(minecraft, 18, 36);
		east.draw(minecraft, 36, 18);
		west.draw(minecraft, 0, 18);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrystalGrowthChamberWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 18, 18);
		guiItemStacks.init(1, false, 67, 18);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		registry.addRecipes(ImmutableList.of(new CrystalGrowthChamberWrapper()), UID);
	}

}
