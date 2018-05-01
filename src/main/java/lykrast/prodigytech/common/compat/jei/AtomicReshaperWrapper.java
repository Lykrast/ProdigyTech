package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.client.gui.GuiAtomicReshaper;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager.AtomicReshaperRecipe;
import lykrast.prodigytech.common.util.Config;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AtomicReshaperWrapper implements IRecipeWrapper {
	protected static final String AMOUNT_UNLOCALIZED = "container.prodigytech.atomic_reshaper.primordium.amount";
	private final String amount;

	private List<ItemStack> in;
	private List<List<ItemStack>> out;
	private final IDrawableAnimated arrow;
	private final IDrawable primordiumGauge;
	private int primordiumAmount, primordiumScale;
	
	public AtomicReshaperWrapper(AtomicReshaperRecipe recipe, IGuiHelper guiHelper)
	{
		in = new ArrayList<>();
		if (recipe.isOreRecipe())
		{
			List<ItemStack> items = OreDictionary.getOres(recipe.getOreInput(), false);
			in.addAll(items);
		}
		else in.add(recipe.getInput());
		
		List<ItemStack> outputs = new ArrayList<>();
		if (!recipe.isSingleOutput())
		{
			recipe.getWeightedOutputs().stream().forEach(p -> outputs.add(p.getLeft().copy()));
		}
		else outputs.add(recipe.getSingleOutput());
		out = Collections.singletonList(outputs);
		
		primordiumAmount = recipe.getPrimordiumAmount();
		
		arrow = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(GuiAtomicReshaper.GUI, 176, 0, 48, 17), recipe.getTimeTicks(), IDrawableAnimated.StartDirection.LEFT, false);
		
		primordiumScale = primordiumAmount * 52 / (Config.atomicReshaperMaxPrimordium * 100);
		primordiumGauge = guiHelper.createDrawable(GuiAtomicReshaper.GUI, 176, 35 + (52 - primordiumScale), 4, primordiumScale);
		
		amount = I18n.format(AMOUNT_UNLOCALIZED, "%d");
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		
		int primordiumCount = primordiumAmount % 100 == 0 ? primordiumAmount / 100 : (primordiumAmount / 100) + 1;
		inputs.add(Collections.singletonList(new ItemStack(ModItems.primordium, primordiumCount)));
		
		inputs.add(in);
		
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputLists(ItemStack.class, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		arrow.draw(minecraft, 60, 19);
		primordiumGauge.draw(minecraft, 30, 1 + (52 - primordiumScale));
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		if (mouseX >= 30 && mouseX <= 34)
		{
			List<String> list = new ArrayList<>();
			list.add(String.format(amount, primordiumAmount));
			return list;
		}
		else return Collections.emptyList();
	}

}
