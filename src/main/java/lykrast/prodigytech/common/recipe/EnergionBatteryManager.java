package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.ItemEnergionBattery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnergionBatteryManager {
	private static List<ItemEnergionBattery> batteries = new ArrayList<>();
	private static List<Item> batteriesEmpty = new ArrayList<>();
	
	public static void register(ItemEnergionBattery battery)
	{
		batteries.add(battery);
		batteriesEmpty.add(battery.getEmptyForm());
	}
	
	public static boolean isBattery(Item i)
	{
		return batteries.contains(i);
	}
	
	public static boolean isBattery(ItemStack stack)
	{
		return isBattery(stack.getItem());
	}
	
	public static boolean isEmptyBattery(Item i)
	{
		return batteriesEmpty.contains(i);
	}
	
	public static boolean isEmptyBattery(ItemStack stack)
	{
		return isEmptyBattery(stack.getItem());
	}
	
	public static List<ItemEnergionBattery> getBatteryList()
	{
		return batteries;
	}
	
	/**
	 * Attempts to extract amount energy from battery
	 * @param battery energion battery to extract from
	 * @param amount energy to attempt to extract
	 * @return how much energy could actually be extracted
	 */
	public static int extract(ItemStack battery, int amount)
	{
		Item item = battery.getItem();
		if (!isBattery(item) || !(item instanceof ItemEnergionBattery)) return 0;
		
		int extracted = Math.min(amount, battery.getMaxDamage() - battery.getItemDamage());
		battery.setItemDamage(battery.getItemDamage() + extracted);
		
		return extracted;
	}
	
	/**
	 * Check if the energion battery is depleted and returns a stack to replace it if it's the case
	 * @param battery energion battery to check
	 * @return stack to replace it with (if it's empty)
	 */
	public static ItemStack checkDepleted(ItemStack battery)
	{
		Item item = battery.getItem();
		if (!isBattery(item) || !(item instanceof ItemEnergionBattery)) return battery;
		if (battery.getItemDamage() < battery.getMaxDamage()) return battery;
		
		return ((ItemEnergionBattery)item).getEmptyStack();
	}
}
