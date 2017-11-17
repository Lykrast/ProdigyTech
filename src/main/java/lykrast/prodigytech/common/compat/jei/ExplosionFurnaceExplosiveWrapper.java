package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExplosionFurnaceExplosiveWrapper implements IRecipeWrapper {
	private final ItemStack explosive, reactant;
	private static final String POWER_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace_exp.generated";
	private final String power;
	
	public ExplosionFurnaceExplosiveWrapper(ExplosionFurnaceManager.ExplosionFurnaceExplosive recipe)
	{
		explosive = recipe.getExplosive();
		reactant = recipe.getReactant();
		power = I18n.format(POWER_DISPLAY, recipe.getOptimalPower());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> list = ImmutableList.of(explosive, reactant);
		ingredients.setInputs(ItemStack.class, list);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(power, 24, 13, Color.gray.getRGB());
	}

}
