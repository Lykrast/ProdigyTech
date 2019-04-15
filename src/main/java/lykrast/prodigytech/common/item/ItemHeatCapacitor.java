package lykrast.prodigytech.common.item;

import java.util.List;

import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHeatCapacitor extends ItemInfoShift implements IHeatCapacitor {
	private int temperature;
	
	public ItemHeatCapacitor(int temperature) {
		this.temperature = temperature;
		setMaxStackSize(1);
        setMaxDamage(Config.heatCapacitorDuration);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			items.add(new ItemStack(this, 1, getMaxDamage()));
			items.add(new ItemStack(this, 1, 0));
		}
	}

	@Override
	public boolean isFullyCharged(ItemStack stack) {
		return stack.getItemDamage() == 0;
	}

	@Override
	public boolean isDepleted(ItemStack stack) {
		return stack.getItemDamage() >= stack.getMaxDamage();
	}

	@Override
	public void charge(ItemStack stack, int ticks) {
		stack.setItemDamage(Math.max(0, stack.getItemDamage() - ticks));
	}

	@Override
	public void discharge(ItemStack stack, int ticks) {
		stack.setItemDamage(Math.min(stack.getMaxDamage(), stack.getItemDamage() + ticks));
	}

	@Override
	public int getTargetTemperature(ItemStack stack) {
		return temperature;
	}
	
	@Override
    public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - getDurabilityForDisplay(stack))) / 6.0F, 1.0F, 1.0F);
	}
	
	private static final String TOOLTIP_BASE = "item.prodigytech.heat_capacitor.tooltip",
			TOOLTIP_CHARGE = "item.prodigytech.heat_capacitor.tooltip.charge";

	@SideOnly(Side.CLIENT)
	@Override
	protected void addInfo(ItemStack stack, List<String> tooltip) {
		String tip = I18n.format(TOOLTIP_BASE);
		String[] lines = tip.split("\n");
		for (String s : lines) tooltip.add(TextFormatting.GRAY + s);
		TooltipUtil.addAeroheaterInfo(stack, tooltip, temperature);
		tooltip.add(I18n.format(TOOLTIP_CHARGE, 100 - (100 * stack.getItemDamage() / stack.getMaxDamage())));
	}

}
