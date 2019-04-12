package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lykrast.prodigytech.client.gui.GuiBatteryReplenisher;
import lykrast.prodigytech.common.item.IEnergionBattery;
import lykrast.prodigytech.common.util.Config;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BatteryReplenisherWrapper implements IRecipeWrapper {
	protected static final String AMOUNT_UNLOCALIZED = "container.prodigytech.battery_replenisher.energion.amount";
	private final String amount;

	private List<List<ItemStack>> in;
	private ItemStack out;
	private final IDrawable energionGauge;
	private int energionAmount, energionScale;
	
	public BatteryReplenisherWrapper(ItemStack in, ItemStack out, IGuiHelper guiHelper)
	{
		this.out = out;
		
		energionAmount = ((IEnergionBattery)out.getItem()).getTotalLifetime(out);
		
		int count = (int) Math.ceil(energionAmount / (double)Config.energionDuration);
		List<ItemStack> dust = new ArrayList<>();
		List<ItemStack> ores = OreDictionary.getOres("dustEnergion", false);
		for (ItemStack i : ores) {
			ItemStack j = i.copy();
			j.setCount(count);
			dust.add(j);
		}
		
		this.in = new ArrayList<>();
		this.in.add(dust);
		this.in.add(Collections.singletonList(in));
		
		energionScale = Math.min(52, energionAmount * 52 / (Config.batteryReplenisherMaxEnergion * Config.energionDuration));
		energionGauge = guiHelper.createDrawable(GuiBatteryReplenisher.GUI, 176, 18 + (52 - energionScale), 4, energionScale);
		
		amount = I18n.format(AMOUNT_UNLOCALIZED, "%d");
	}

	@Override
	public void getIngredients(IIngredients ingredients) {		
		ingredients.setInputLists(VanillaTypes.ITEM, in);
		ingredients.setOutput(VanillaTypes.ITEM, out);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		energionGauge.draw(minecraft, 30, 1 + (52 - energionScale));
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		if (mouseX >= 30 && mouseX <= 34)
		{
			List<String> list = new ArrayList<>();
			list.add(String.format(amount, energionAmount));
			return list;
		}
		else return Collections.emptyList();
	}

}
