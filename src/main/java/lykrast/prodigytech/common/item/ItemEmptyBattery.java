package lykrast.prodigytech.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEmptyBattery extends Item implements IEnergionFillable {
	private Item filled;
	
	public void setFilledItem(Item filled) {
		this.filled = filled;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getFillableAmount(ItemStack stack, int amount) {
		//We're only using the default batteries so we can do that
		return Math.min(amount, filled.getMaxDamage());
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStack fill(ItemStack stack, int amount) {
		return new ItemStack(filled, 1, filled.getMaxDamage() - amount);
	}

	@Override
	public boolean isFull(ItemStack stack) {
		return false;
	}

}
