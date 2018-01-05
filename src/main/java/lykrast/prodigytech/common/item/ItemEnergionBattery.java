package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnergionBattery extends Item implements IEnergionBattery {
	private final Item empty;
	
	public ItemEnergionBattery(int duration, Item empty)
	{
		super();
		setMaxStackSize(1);
		setMaxDamage(duration);
		this.empty = empty;
		EnergionBatteryManager.register(this);
	}

	@Override
	public Item getEmptyForm()
	{
		return empty;
	}
	
	@Override
	public ItemStack getEmptyStack()
	{
		return new ItemStack(empty);
	}
	
	@Override
	public int extract(ItemStack stack, int amount)
	{
		int extracted = Math.min(amount, stack.getMaxDamage() - stack.getItemDamage());
		stack.setItemDamage(stack.getItemDamage() + extracted);
		
		return extracted;
	}

	@Override
	public boolean isDepleted(ItemStack stack)
	{
		return stack.getItemDamage() >= stack.getMaxDamage();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getLifetime()
	{
		return getMaxDamage();
	}

}
