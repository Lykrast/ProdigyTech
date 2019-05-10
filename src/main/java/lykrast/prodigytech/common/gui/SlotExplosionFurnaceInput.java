package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotExplosionFurnaceInput extends Slot {

	public SlotExplosionFurnaceInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    @Override
	public boolean isItemValid(ItemStack stack)
    {
        return ExplosionFurnaceManager.isValidInput(stack);
    }

}
