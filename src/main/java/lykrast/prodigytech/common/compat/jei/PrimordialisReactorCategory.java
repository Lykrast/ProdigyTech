package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.client.gui.GuiPrimordialisReactor;
import lykrast.prodigytech.common.recipe.PrimordialisReactorManager;
import lykrast.prodigytech.common.util.Config;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class PrimordialisReactorCategory extends ProdigyCategory<PrimordialisReactorWrapper> {
	public static final String UID = "ptprimordialis";
	private final IDrawableAnimated bubbles;

	public PrimordialisReactorCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 196, 126, 29), UID);

		bubbles = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(GuiPrimordialisReactor.GUI, 176, 35, 12, 29), 
				Config.primordialisReactorCycleTime, IDrawableAnimated.StartDirection.BOTTOM, false);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft)
	{
		bubbles.draw(minecraft, 20, 0);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PrimordialisReactorWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 104, 4);
		
		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry)
	{
		List<PrimordialisReactorWrapper> list = new ArrayList<>();

		for (String ore : PrimordialisReactorManager.getAllOreEntries()) list.add(new PrimordialisReactorWrapper(ore));
		for (ItemStack stack : PrimordialisReactorManager.getAllEntries()) list.add(new PrimordialisReactorWrapper(stack));
		
		registry.addRecipes(list, UID);
	}

}
