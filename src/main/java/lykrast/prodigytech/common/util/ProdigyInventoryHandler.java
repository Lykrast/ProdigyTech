package lykrast.prodigytech.common.util;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * VERY heavily based on Immersive Engineering's IEInventoryHandler implementation (by BluSunrize)
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/util/inventory/IEInventoryHandler.java
 */
public class ProdigyInventoryHandler implements IItemHandlerModifiable {
	private IProdigyInventory inventory;
	private int slots, offset;
	private boolean[] insert, extract;
	
	public ProdigyInventoryHandler(IProdigyInventory inventory, int slots)
	{
		this(inventory, slots, 0, true, true);
	}
	
	public ProdigyInventoryHandler(IProdigyInventory inventory, int slots, int offset, boolean insert, boolean extract)
	{
		this(inventory, slots, offset, new boolean[slots], new boolean[slots]);
		Arrays.fill(this.insert, insert);
		Arrays.fill(this.extract, extract);
	}
	
	public ProdigyInventoryHandler(IProdigyInventory inventory, int slots, int offset, boolean[] insert, boolean[] extract)
	{
		this.inventory = inventory;
		this.slots = slots;
		this.offset = offset;
		this.insert = insert;
		this.extract = extract;
	}

	@Override
	public int getSlots() {
		return slots;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getInventory().get(slot+offset);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!insert[slot] || stack.isEmpty()) return ItemStack.EMPTY;
		stack = stack.copy();

		if(!inventory.isItemValidForSlot(slot+offset, stack)) return stack;
		
		int realSlot = slot+offset;
		ItemStack current = inventory.getInventory().get(realSlot);
		
		if (current.isEmpty())
		{
			int accepted = Math.min(stack.getMaxStackSize(), inventory.getSlotLimit(realSlot));
			if(accepted < stack.getCount())
			{
				if(!simulate)
				{
					inventory.getInventory().set(realSlot, stack.splitStack(accepted));
					inventory.updateGraphics(realSlot);
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
					inventory.getInventory().set(realSlot, stack);
					inventory.updateGraphics(realSlot);
				}
				return ItemStack.EMPTY;
			}
		}
		else
		{
			if(!ItemHandlerHelper.canItemStacksStack(stack, current))
				return stack;

			int accepted = Math.min(stack.getMaxStackSize(), inventory.getSlotLimit(realSlot)) - current.getCount();
			if(accepted < stack.getCount())
			{
				if(!simulate)
				{
					ItemStack newStack = stack.splitStack(accepted);
					newStack.grow(current.getCount());
					inventory.getInventory().set(realSlot, newStack);
					inventory.updateGraphics(realSlot);
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
					newStack.grow(current.getCount());
					inventory.getInventory().set(realSlot, newStack);
					inventory.updateGraphics(realSlot);
				}
				return ItemStack.EMPTY;
			}
		}
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!extract[slot] || amount == 0) return ItemStack.EMPTY;
        
		int realSlot = slot+offset;
		ItemStack stack = inventory.getInventory().get(realSlot);
		
		if (stack.isEmpty()) return ItemStack.EMPTY;
		
		int extracted = Math.min(stack.getCount(), amount);

		ItemStack copy = stack.copy();
		copy.setCount(extracted);
		
		if(!simulate)
		{
			if(extracted < stack.getCount()) stack.shrink(extracted);
			else stack = ItemStack.EMPTY;
			inventory.getInventory().set(realSlot, stack);
			inventory.updateGraphics(realSlot);
		}
		return copy;
	}

	@Override
	public int getSlotLimit(int slot) {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		inventory.getInventory().set(slot+offset, stack);
		inventory.updateGraphics(slot+offset);
	}

}
