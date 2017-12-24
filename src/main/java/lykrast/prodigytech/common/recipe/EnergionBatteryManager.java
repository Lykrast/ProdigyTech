package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.ItemEnergionBattery;
import net.minecraft.item.Item;

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
	
	public static boolean isEmptyBattery(Item i)
	{
		return batteriesEmpty.contains(i);
	}
	
	public List<ItemEnergionBattery> getBatteryList()
	{
		return batteries;
	}

}
