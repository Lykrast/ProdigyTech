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
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AtomicReshaperWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {
	protected static final String AMOUNT_UNLOCALIZED = "container.prodigytech.atomic_reshaper.primordium.amount";
	protected static final String CHANCE_UNLOCALIZED = "container.prodigytech.jei.ptreshaper.chance";
	protected static final String CHANCE_LOW_UNLOCALIZED = "container.prodigytech.jei.ptreshaper.chance.low";
	private final String amount;

	private List<ItemStack> in;
	private List<List<ItemStack>> out;
	private final IDrawableAnimated arrow;
	private final IDrawable primordiumGauge;
	private int primordiumAmount, primordiumScale, totalWeight;
	private int[] weights;
	
	public AtomicReshaperWrapper(AtomicReshaperRecipe recipe, IGuiHelper guiHelper)
	{
		in = new ArrayList<>();
		if (recipe.isOreRecipe())
		{
			List<ItemStack> items = OreDictionary.getOres(recipe.getOreInput(), false);
			in.addAll(items);
		}
		else in.add(recipe.getInput());
		
		List<ItemStack> outputs;
		if (!recipe.isSingleOutput()) outputs = recipe.getOutputList();
		else outputs = Collections.singletonList(recipe.getSingleOutput());
		out = Collections.singletonList(outputs);
		
		weights = recipe.getWeights();
		totalWeight = recipe.getTotalWeight();
		
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
		
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutputLists(VanillaTypes.ITEM, out);
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

	@Override
	public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
		if (slotIndex == 2 && weights.length > 1) {
			int chance = (weights[out.get(0).indexOf(ingredient)] * 100) / totalWeight;
			if (chance == 0) tooltip.add(I18n.format(CHANCE_LOW_UNLOCALIZED));
			else tooltip.add(I18n.format(CHANCE_UNLOCALIZED, chance));
		}
	}

}
