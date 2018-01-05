package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.IEnergionBattery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnergionBatteryManager {
	public static final List<Item> BATTERIES = new ArrayList<>();
	public static final List<Item> BATTERIES_EMPTY = new ArrayList<>();
	
	public static void register(Item battery)
	{
		if (!(battery instanceof IEnergionBattery)) return;
		BATTERIES.add(battery);
		BATTERIES_EMPTY.add(((IEnergionBattery)battery).getEmptyForm());
	}
	
	public static void unregister(Item battery)
	{
		if (!(battery instanceof IEnergionBattery)) return;
		BATTERIES.remove(battery);
		BATTERIES_EMPTY.remove(((IEnergionBattery)battery).getEmptyForm());
	}
	
	public static boolean isBattery(Item i)
	{
		return BATTERIES.contains(i);
	}
	
	public static boolean isBattery(ItemStack stack)
	{
		return isBattery(stack.getItem());
	}
	
	public static boolean isEmptyBattery(Item i)
	{
		return BATTERIES_EMPTY.contains(i);
	}
	
	public static boolean isEmptyBattery(ItemStack stack)
	{
		return isEmptyBattery(stack.getItem());
	}
	
	public static List<Item> getBatteryList()
	{
		return BATTERIES;
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
		if (!isBattery(item) || !(item instanceof IEnergionBattery)) return 0;
		
		return ((IEnergionBattery)item).extract(battery, amount);
	}
	
	/**
	 * Check if the energion battery is depleted and returns a stack to replace it if it's the case
	 * @param battery energion battery to check
	 * @return stack to replace it with (if it's empty)
	 */
	public static ItemStack checkDepleted(ItemStack battery)
	{
		Item item = battery.getItem();
		if (!isBattery(item) || !(item instanceof IEnergionBattery)) return battery;
		IEnergionBattery casted = (IEnergionBattery)item;
		if (!casted.isDepleted(battery)) return battery;
		
		return casted.getEmptyStack();
	}
}
