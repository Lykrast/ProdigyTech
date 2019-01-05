package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.common.recipe.SimpleRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleRecipeWrapper implements IRecipeWrapper {
	private ItemStack out;
	private List<List<ItemStack>> in;
	private final IDrawableAnimated arrow;
	
	public SimpleRecipeWrapper(SimpleRecipe recipe, IGuiHelper guiHelper)
	{
		List<ItemStack> inputs = new ArrayList<>();
		
		if (recipe.isOreRecipe())
		{
			List<ItemStack> items = OreDictionary.getOres(recipe.getOreInput(), false);
			inputs.addAll(items);
		}
		else inputs.add(recipe.getInput());
		
		in = Collections.singletonList(inputs);
		out = recipe.getOutput();
		
		arrow = guiHelper.createAnimatedDrawable(ProdigyTechJEI.getDefaultProcessArrow(guiHelper), recipe.getTimeTicks(), IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, in);
		ingredients.setOutput(VanillaTypes.ITEM, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		arrow.draw(minecraft, 24, 5);
	}

}
