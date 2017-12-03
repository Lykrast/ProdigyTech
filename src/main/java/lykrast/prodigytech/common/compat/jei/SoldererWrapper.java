package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.client.gui.GuiSolderer;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.SoldererManager.SoldererRecipe;
import lykrast.prodigytech.common.util.Config;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SoldererWrapper implements IRecipeWrapper {
	private static final String BLOCKS_UNLOCALIZED = "container.prodigytech.solderer.gold.blocks";
	private static final String INGOTS_UNLOCALIZED = "container.prodigytech.solderer.gold.ingots";
	private static final String NUGGETS_UNLOCALIZED = "container.prodigytech.solderer.gold.nuggets";
	private final String blocks, ingots, nuggets;
	
	private ItemStack pattern, additive, output;
	private final IDrawableAnimated arrow;
	private final IDrawable goldGauge;
	private int goldAmount, goldScale;
	
	public SoldererWrapper(SoldererRecipe recipe, IGuiHelper guiHelper)
	{
		pattern = recipe.getPattern();
		additive = recipe.getAdditive();
		output = recipe.getOutput();
		goldAmount = recipe.getGoldAmount();
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, recipe.getTimeTicks(), IDrawableAnimated.StartDirection.LEFT, false);
		
		goldScale = goldAmount * 52 / Config.soldererMaxGold;
		goldGauge = guiHelper.createDrawable(GuiSolderer.GUI, 176, 35 + (52 - goldScale), 4, goldScale);
		
		blocks = I18n.format(BLOCKS_UNLOCALIZED, "%d");
		ingots = I18n.format(INGOTS_UNLOCALIZED, "%d");
		nuggets = I18n.format(NUGGETS_UNLOCALIZED, "%d");
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		inputs.add(Collections.singletonList(pattern));
		
		List<ItemStack> oreDust = OreDictionary.getOres("dustTinyGold", false);
		List<ItemStack> dust = new ArrayList<>();
		for (ItemStack i : oreDust)
		{
			ItemStack n = i.copy();
			n.setCount(goldAmount);
			dust.add(n);
		}
		inputs.add(dust);
		
		inputs.add(Collections.singletonList(additive));
		inputs.add(Collections.singletonList(new ItemStack(ModItems.circuitPlate)));
		
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		arrow.draw(minecraft, 60, 19);
		goldGauge.draw(minecraft, 30, 1 + (52 - goldScale));
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		if (mouseX >= 30 && mouseX <= 34)
		{
			int tmp = goldAmount;
			List<String> list = new ArrayList<>();
			
			int blocks = tmp / 81;
			if (blocks > 0)
			{
				list.add(String.format(this.blocks, blocks));
				tmp %= 81;
			}
			
			int ingots = tmp / 9;
			if (ingots > 0)
			{
				list.add(String.format(this.ingots, ingots));
				tmp %= 9;
			}
			
			if (tmp > 0)
			{
				list.add(String.format(nuggets, tmp));
			}
			
			return list;
		}
		else return Collections.emptyList();
	}

}
