package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnergionBattery extends Item {
	private final Item empty;
	
	public ItemEnergionBattery(int duration, Item empty)
	{
		super();
		setMaxStackSize(1);
		setMaxDamage(duration);
		this.empty = empty;
		EnergionBatteryManager.register(this);
	}
	
	public Item getEmptyForm()
	{
		return empty;
	}
	
	public ItemStack getEmptyStack()
	{
		return new ItemStack(empty);
	}

}
