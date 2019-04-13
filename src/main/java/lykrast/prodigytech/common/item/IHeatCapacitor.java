package lykrast.prodigytech.common.item;

import net.minecraft.item.ItemStack;

public interface IHeatCapacitor {
	boolean isFullyCharged(ItemStack stack);
	boolean isDepleted(ItemStack stack);
	/**
	 * Can this heat capacitor be charged in the Heat Accumulator?
	 * @param stack ItemStack to check
	 * @return true if the Heat Accumulator should accept this item and charge it
	 */
	default boolean isChargeable(ItemStack stack) {
		return true;
	}
	void charge(ItemStack stack, int ticks);
	void discharge(ItemStack stack, int ticks);
	int getTargetTemperature(ItemStack stack);

}
