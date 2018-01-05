package lykrast.prodigytech.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IEnergionBattery {
	
	/**
	 * Attempts to extract amount energy from battery
	 * @param stack energion battery to extract from
	 * @param amount energy to attempt to extract
	 * @return how much energy could actually be extracted
	 */
	public int extract(ItemStack stack, int amount);

	/**
	 * Check if the energion battery is depleted
	 * @param stack energion battery to check
	 * @return true if it's depleted and should be replaced
	 */
	public boolean isDepleted(ItemStack stack);

	/**
	 * Gives an Item form of the depleted battery
	 * @return Item form of the depleted battery
	 */
	public Item getEmptyForm();
	
	/**
	 * Gives an ItemStack form of the depleted battery
	 * @return ItemStack form of the depleted battery
	 */
	public ItemStack getEmptyStack();

}
