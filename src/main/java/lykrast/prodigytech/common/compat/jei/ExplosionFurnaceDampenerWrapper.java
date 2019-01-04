package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.Dampener;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExplosionFurnaceDampenerWrapper implements IRecipeWrapper {
	private final List<ItemStack> dampener;
	private static final String DAMPENING_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace_damp.dampened";
	private final String dampened;
	
	public ExplosionFurnaceDampenerWrapper(Dampener recipe)
	{
		dampener = recipe.getMatchingStacks();
		dampened = I18n.format(DAMPENING_DISPLAY, recipe.getDampening());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> list = ImmutableList.of(dampener);
		ingredients.setInputLists(ItemStack.class, list);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(dampened, 24, 9 - (minecraft.fontRenderer.FONT_HEIGHT / 2), Color.gray.getRGB());
	}

}
