package lykrast.prodigytech.common.util;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TooltipUtil {
	private TooltipUtil() {}

	/**
	 * Adds the item's tooltip to the itemstack, following localization.
	 * <br>
	 * This is intended to be called in addInformation.
	 * @param stack stack to put the tooltip on
	 * @param tooltip other parameter from addInfo
	 */
	@SideOnly(Side.CLIENT)
	public static void addTooltip(ItemStack stack, List<String> tooltip)
	{
		String tip = I18n.format(stack.getTranslationKey() + ".tooltip");
		String[] lines = tip.split("\n");
		for (String s : lines) tooltip.add(TextFormatting.GRAY + s);
	}
	
	public static final String SHIFT = "tooltip.prodigytech.shift";
	
	/**
	 * Adds a tooltip stating to hold SHIFT for extended information.
	 * <br>
	 * Returns true if the key is hold and more tooltips should be added.
	 * <br>
	 * This is intended to be called in addInformation.
	 * @param tooltip tooltip list
	 * @return true if shift is held and more text should be added, false otherwise
	 */
	@SideOnly(Side.CLIENT)
	public static boolean addShiftTooltip(List<String> tooltip)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) return true;
		else
		{
			tooltip.add(I18n.format(SHIFT));
			return false;
		}
	}
	
	
	public static final String AEROHEATER_HEAT_MAXIMUM = "tooltip.prodigytech.temperature.max";
	
	@SideOnly(Side.CLIENT)
	public static void addAeroheaterInfo(ItemStack stack, List<String> tooltip, int maxHeat)
	{
		tooltip.add(I18n.format(AEROHEATER_HEAT_MAXIMUM, maxHeat));
	}
	
	public static final String HEAT_MINIMUM = "tooltip.prodigytech.temperature.min";
	public static final String HEAT_TRANSFER = "tooltip.prodigytech.temperature.transfer";
	
	@SideOnly(Side.CLIENT)
	public static void addAirMachineInfo(ItemStack stack, List<String> tooltip, int minHeat, int transfer)
	{
		tooltip.add(I18n.format(HEAT_MINIMUM, minHeat));
		if (transfer > 0) tooltip.add(I18n.format(HEAT_TRANSFER, transfer));
	}
	
	public static final String ENERGION_BATTERIES_REQUIRED_SINGLE = "tooltip.prodigytech.energion.required.single";
	public static final String ENERGION_BATTERIES_REQUIRED_INTERVAL = "tooltip.prodigytech.energion.required.interval";
	public static final String ENERGION_ENERGY_CONSUMPTION = "tooltip.prodigytech.energion.consumption";
	
	@SideOnly(Side.CLIENT)
	public static void addEnergionInfo(ItemStack stack, List<String> tooltip, int minBatteries, int maxBatteries, int consumption)
	{
		if (maxBatteries > minBatteries) tooltip.add(I18n.format(ENERGION_BATTERIES_REQUIRED_INTERVAL, minBatteries, maxBatteries));
		else tooltip.add(I18n.format(ENERGION_BATTERIES_REQUIRED_SINGLE, minBatteries));
		tooltip.add(I18n.format(ENERGION_ENERGY_CONSUMPTION, consumption));
	}
}
