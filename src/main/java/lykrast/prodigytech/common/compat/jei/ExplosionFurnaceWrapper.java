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

public class ExplosionFurnaceWrapper implements IRecipeWrapper {
	private ItemStack in, out, reag;
	private static final String POWER_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace.required";
	private final String power;
	
	public ExplosionFurnaceWrapper(ExplosionFurnaceManager.ExplosionFurnaceRecipe recipe)
	{
		in = recipe.getInput();
		out = recipe.getOutput();
		reag = recipe.getReagent();
		power = I18n.format(POWER_DISPLAY, recipe.getRequiredPower());
		
		if (recipe.needReagent())
		{
			int craftPerReagent = recipe.getCraftPerReagent();
			in.setCount(in.getCount() * craftPerReagent);
			out.setCount(out.getCount() * craftPerReagent);
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> list = ImmutableList.of(in, reag);
		ingredients.setInputs(ItemStack.class, list);
		ingredients.setOutput(ItemStack.class, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		int width = minecraft.fontRenderer.getStringWidth(power);
		int x = (recipeWidth - 4 - width)/2;
		int y = 45;
		minecraft.fontRenderer.drawString(power, x, y, Color.gray.getRGB());
	}

}
