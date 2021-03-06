package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExplosionFurnaceWrapper implements IRecipeWrapper {
	private List<ItemStack> in, reag;
	private ItemStack out;
	private static final String POWER_DISPLAY = "container.prodigytech.jei.ptexplosionfurnace.required";
	private final int power;
	
	public ExplosionFurnaceWrapper(ExplosionFurnaceManager.ExplosionFurnaceRecipe recipe) {
		in = recipe.getInputs();
		reag = recipe.getReagents();
		power = recipe.needReagent() ? recipe.getRequiredPower() * recipe.getCraftPerReagent() : recipe.getRequiredPower();
		out = recipe.getOutput();
		if (recipe.needReagent()) out.setCount(out.getCount() * recipe.getCraftPerReagent());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(in, reag));
		ingredients.setOutput(VanillaTypes.ITEM, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String s = I18n.format(POWER_DISPLAY, power);
		int width = minecraft.fontRenderer.getStringWidth(s);
		minecraft.fontRenderer.drawString(s, (recipeWidth - width)/2, 45, Color.gray.getRGB());
	}

}
