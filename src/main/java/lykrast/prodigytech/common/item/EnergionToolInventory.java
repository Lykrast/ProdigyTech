package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class EnergionToolInventory implements IItemHandlerModifiable {
	private ItemStack battery;
	
	public EnergionToolInventory() {
		battery = ItemStack.EMPTY;
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot == 0 ? battery : ItemStack.EMPTY;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot != 0 || stack.isEmpty() || !EnergionBatteryManager.isBattery(stack)) return stack;
        
		stack = stack.copy();
		
		if (battery.isEmpty())
		{
			int accepted = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
			if(accepted < stack.getCount())
			{
				if(!simulate)
				{
					setStackInSlot(slot, stack.splitStack(accepted));
					return stack;
				}
				else
				{
					stack.shrink(accepted);
					return stack;
				}
			}
			else
			{
				if(!simulate)
				{
					setStackInSlot(slot, stack);
				}
				return ItemStack.EMPTY;
			}
		}
		else
		{
			if(!ItemHandlerHelper.canItemStacksStack(stack, battery))
				return stack;

			int accepted = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - battery.getCount();
			if(accepted < stack.getCount())
			{
				if(!simulate)
				{
					ItemStack newStack = stack.splitStack(accepted);
					newStack.grow(battery.getCount());
					setStackInSlot(slot, newStack);
					return stack;
				}
				else
				{
					stack.shrink(accepted);
					return stack;
				}
			}
			else
			{
				if(!simulate)
				{
					ItemStack newStack = stack.copy();
					newStack.grow(battery.getCount());
					setStackInSlot(slot, newStack);
				}
				return ItemStack.EMPTY;
			}
		}
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot != 0 || amount == 0 || battery.isEmpty()) return ItemStack.EMPTY;
		
		int extracted = Math.min(battery.getCount(), amount);

		ItemStack copy = battery.copy();
		copy.setCount(extracted);
		
		if(!simulate)
		{
			if(extracted < battery.getCount()) battery.shrink(extracted);
			else battery = ItemStack.EMPTY;
		}
		return copy;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (slot == 0) battery = stack;
	}

}
