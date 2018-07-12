package lykrast.prodigytech.common.item;

import net.minecraft.item.ItemStack;

/**
 * Interface for making an item fillable in the Battery Replenisher.
 * @author Lykrast
 */
public interface IEnergionFillable {
	
	/**
	 * Simulate inserting amount energy in the item,
	 * this should not modify the stack, use {@link #fill(ItemStack, int) fill} for that
	 * @param stack item to insert into
	 * @param amount energy to attempt to insert
	 * @return how much energy can actually be inserted
	 */
	public int getFillableAmount(ItemStack stack, int amount);
	
	/**
	 * Fills the item with amount energy,
	 * this can assume the amount can always fit according to {@link #getFillableAmount(ItemStack, int) getFillAmount}
	 * @param stack item to fill
	 * @param amount amount to fill
	 * @return ItemStack containing the filled item
	 */
	public ItemStack fill(ItemStack stack, int amount);
	
	/**
	 * Says if the item is full and thus can't be filled anymore
	 * @param stack item to check
	 * @return if the item is full
	 */
	public boolean isFull(ItemStack stack);

}
