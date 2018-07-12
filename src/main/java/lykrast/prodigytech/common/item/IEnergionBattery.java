package lykrast.prodigytech.common.item;

import net.minecraft.item.ItemStack;

/**
 * Interface for making an Energion Battery from which power can be extracted.
 * @author Lykrast
 */
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
	 * Gives an ItemStack form of the depleted battery
	 * @return ItemStack form of the depleted battery
	 */
	public ItemStack getEmptyStack();
	
	/**
	 * Gives the total expected lifetime of the battery in ticks if 
	 * 1 energy is extracted per tick
	 * @param stack battery to check
	 * @return expected lifetime in ticks, or a negative number if it can vary or is not defined
	 */
	default public int getTotalLifetime(ItemStack stack) {
		return -1;
	}

}
