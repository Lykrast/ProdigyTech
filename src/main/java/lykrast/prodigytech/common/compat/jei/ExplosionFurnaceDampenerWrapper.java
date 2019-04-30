package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.Dampener;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExplosionFurnaceDampenerWrapper implements IRecipeWrapper {
	private final List<ItemStack> dampener;
	private static final String DAMPENING_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace_damp.dampened";
	private final int dampened;
	
	public ExplosionFurnaceDampenerWrapper(Dampener recipe)
	{
		dampener = recipe.getMatchingStacks();
		dampened = recipe.getDampening();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> list = Collections.singletonList(dampener);
		ingredients.setInputLists(VanillaTypes.ITEM, list);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(I18n.format(DAMPENING_DISPLAY, dampened), 24, 9 - (minecraft.fontRenderer.FONT_HEIGHT / 2), Color.gray.getRGB());
	}

}
