package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.Explosive;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExplosionFurnaceExplosiveWrapper implements IRecipeWrapper {
	private final List<ItemStack> explosive;
	private static final String POWER_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace_exp.generated";
	private final int power;
	
	public ExplosionFurnaceExplosiveWrapper(Explosive recipe)
	{
		explosive = recipe.getMatchingStacks();
		power = recipe.getPower();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> list = Collections.singletonList(explosive);
		ingredients.setInputLists(VanillaTypes.ITEM, list);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(I18n.format(POWER_DISPLAY, power), 24, 9 - (minecraft.fontRenderer.FONT_HEIGHT / 2), Color.gray.getRGB());
	}

}
