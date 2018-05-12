package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotEnergionToolBattery extends SlotItemHandler {

	public SlotEnergionToolBattery(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return EnergionBatteryManager.isBattery(stack);
    }

}
