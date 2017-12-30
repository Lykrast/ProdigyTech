package lykrast.prodigytech.common.util;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.ItemStack;

public class InventoryHandlerAeroheaterEnergion extends ProdigyInventoryHandler {
	
	public InventoryHandlerAeroheaterEnergion(IProdigyInventory inventory, int slots, int offsets)
	{
		super(inventory, slots, offsets, true, true);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		//Can't extract batteries
		if (EnergionBatteryManager.isBattery(getStackInSlot(slot))) return ItemStack.EMPTY;
		else return super.extractItem(slot, amount, simulate);
	}

}
