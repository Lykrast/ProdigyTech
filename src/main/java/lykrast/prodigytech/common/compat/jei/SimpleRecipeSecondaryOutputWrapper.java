package lykrast.prodigytech.common.compat.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.recipe.SimpleRecipeSecondaryOutput;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleRecipeSecondaryOutputWrapper implements IRecipeWrapper {
	private List<List<ItemStack>> in;
	private List<ItemStack> out;
	private final IDrawableAnimated arrow;
	private int chance;
	
	public SimpleRecipeSecondaryOutputWrapper(SimpleRecipeSecondaryOutput recipe, IGuiHelper guiHelper)
	{
		List<ItemStack> inputs = new ArrayList<>();
		
		if (recipe.isOreRecipe())
		{
			List<ItemStack> items = OreDictionary.getOres(recipe.getOreInput(), false);
			inputs.addAll(items);
		}
		else inputs.add(recipe.getInput());
		
		in = Collections.singletonList(inputs);
		out = ImmutableList.of(recipe.getOutput(), recipe.getSecondaryOutput());
		chance = recipe.hasSecondaryOutput() ? (int)(recipe.getSecondaryChance() * 100) : 100;
		
		arrow = guiHelper.createAnimatedDrawable(ProdigyTechJEI.getDefaultProcessArrow(guiHelper), recipe.getTimeTicks(), IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, in);
		ingredients.setOutputs(VanillaTypes.ITEM, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		arrow.draw(minecraft, 24, 5);
		
		if (chance < 100)
		{
			String chanceString;
			if (chance < 1) chanceString = "< 1%";
			else chanceString = String.format("%d%%", chance);

			int width = minecraft.fontRenderer.getStringWidth(chanceString);
			minecraft.fontRenderer.drawString(chanceString, 95 - width/2, 28, Color.gray.getRGB());
		}
	}

}
