package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.SimpleRecipeManagerAbstract;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotManagerInput extends Slot {
	private SimpleRecipeManagerAbstract<?> manager;

	public SlotManagerInput(SimpleRecipeManagerAbstract<?> manager, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.manager = manager;
	}

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return manager.isValidInput(stack);
    }

}
