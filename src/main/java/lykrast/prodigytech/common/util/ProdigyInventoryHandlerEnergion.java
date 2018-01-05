package lykrast.prodigytech.common.util;

import java.util.Arrays;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.ItemStack;

public class ProdigyInventoryHandlerEnergion extends ProdigyInventoryHandler {
	private int batteryCount;
	
	public ProdigyInventoryHandlerEnergion(IProdigyInventory inventory, int slots, int offset, int batteryCount)
	{
		super(inventory, slots, offset, true, true);
		this.batteryCount = batteryCount;
	}
	
	public ProdigyInventoryHandlerEnergion(IProdigyInventory inventory, int slots, int offset, boolean insert, boolean extract, int batteryCount)
	{
		super(inventory, slots, offset, generateArray(slots, batteryCount, insert), generateArray(slots, batteryCount, extract));
		this.batteryCount = batteryCount;
	}
	
	public ProdigyInventoryHandlerEnergion(IProdigyInventory inventory, int slots, int offset, boolean[] insert, boolean[] extract, int batteryCount)
	{
		super(inventory, slots, offset, insert, extract);
		this.batteryCount = batteryCount;
	}
	
	private static boolean[] generateArray(int count, int batteryCount, boolean value)
	{
		boolean[] array = new boolean[count];
		Arrays.fill(array, value);
		
		if (!value) for (int i=0;i<batteryCount;i++) array[i] = true;
		
		return array;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		//Can't extract batteries
		if (slot < batteryCount && EnergionBatteryManager.isBattery(getStackInSlot(slot))) return ItemStack.EMPTY;
		else return super.extractItem(slot, amount, simulate);
	}

}
