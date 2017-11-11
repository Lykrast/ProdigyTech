package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExplosionFurnaceWrapper implements IRecipeWrapper {
	private ItemStack in, out, reag;
	private static final String POWER_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace.required";
	private static final String CRAFT_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace.craft";
	private int power;
	
	public ExplosionFurnaceWrapper(ExplosionFurnaceManager.ExplosionFurnaceRecipe recipe)
	{
		in = recipe.getInput();
		out = recipe.getOutput();
		power = recipe.getRequiredPower();
		
		if (recipe.needReagent())
		{
			reag = recipe.getReagent();
			
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
		String localized = I18n.format(POWER_DISPLAY) + power;
		int width = minecraft.fontRenderer.getStringWidth(localized);
		int x = (recipeWidth - 4 - width)/2;
		int y = 45;
		minecraft.fontRenderer.drawString(localized, x, y, Color.gray.getRGB());
	}

	public static void registerRecipes(IModRegistry registry)
	{
		List<ExplosionFurnaceWrapper> list = new ArrayList<>();
		
		for (ExplosionFurnaceManager.ExplosionFurnaceRecipe recipe : ExplosionFurnaceManager.RECIPES)
		{
			list.add(new ExplosionFurnaceWrapper(recipe));
		}
		
		registry.addRecipes(list, ExplosionFurnaceCategory.UID);
	}

}
