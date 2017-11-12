package lykrast.prodigytech.common.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Heavily based on Immersive Engineering's IIEInventory interface (by BluSunrize)
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/util/inventory/IIEInventory.java
 */
public interface IProdigyInventory extends IInventory {
	/**
	 * Get the inventory as a List.
	 * @return the inventory
	 */
	NonNullList<ItemStack> getInventory();
	/**
	 * Say if the stack can be inserted into the given slot.
	 * @param stack ItemStack to insert
	 * @param slot slot to insert into
	 * @return if it can be inserted
	 */
	boolean isStackValid(ItemStack stack, int slot);
	/**
	 * Mostly a call to markDirty().
	 */
	void updateGraphics(int slot);
	
	/**
	 * How many items can fit in the given slot.
	 * @param slot slot to check
	 * @return max stack size
	 */
	default int getSlotLimit(int slot)
	{
		return getInventoryStackLimit();
	}
}
