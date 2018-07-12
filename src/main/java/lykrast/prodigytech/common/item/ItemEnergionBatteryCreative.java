package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnergionBatteryCreative extends Item implements IEnergionBattery {
	
	public ItemEnergionBatteryCreative() {
		super();
		setMaxStackSize(1);
		EnergionBatteryManager.register(this);
	}
	
	@Override
	public ItemStack getEmptyStack() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public int extract(ItemStack stack, int amount) {
		return amount;
	}

	@Override
	public boolean isDepleted(ItemStack stack) {
		return false;
	}

}
