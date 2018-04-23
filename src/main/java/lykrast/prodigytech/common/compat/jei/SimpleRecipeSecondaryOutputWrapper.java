package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.recipe.SimpleRecipeSecondaryOutput;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleRecipeSecondaryOutputWrapper implements IRecipeWrapper {
	private List<List<ItemStack>> in;
	private List<ItemStack> out;
	private final IDrawableAnimated arrow;
	
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
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, recipe.getTimeTicks(), IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, in);
		ingredients.setOutputs(ItemStack.class, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		arrow.draw(minecraft, 24, 5);
	}

}
