package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnergionBattery extends Item implements IEnergionBattery, IEnergionFillable {
	private final Item empty;
	
	public ItemEnergionBattery(int duration, Item empty) {
		super();
		setMaxStackSize(1);
		setMaxDamage(duration);
		this.empty = empty;
		EnergionBatteryManager.register(this);
	}
	
	@Override
	public ItemStack getEmptyStack() {
		return new ItemStack(empty);
	}
	
	@Override
	public int extract(ItemStack stack, int amount) {
		int extracted = Math.min(amount, stack.getMaxDamage() - stack.getItemDamage());
		stack.setItemDamage(stack.getItemDamage() + extracted);
		
		return extracted;
	}

	@Override
	public boolean isDepleted(ItemStack stack) {
		return stack.getItemDamage() >= stack.getMaxDamage();
	}
	
	@Override
	public int getTotalLifetime(ItemStack stack) {
		return stack.getMaxDamage();
	}

	@Override
	public int getFillableAmount(ItemStack stack, int amount) {
		return Math.min(amount, stack.getItemDamage());
	}

	@Override
	public ItemStack fill(ItemStack stack, int amount) {
		stack.setItemDamage(stack.getItemDamage() - amount);
		return stack;
	}

	@Override
	public boolean isFull(ItemStack stack) {
		return stack.getItemDamage() <= 0;
	}

}
