package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.tileentity.TileCrystalGrowthChamber;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCrystalGrowthChamberInput extends Slot {
	private TileCrystalGrowthChamber tile;

	public SlotCrystalGrowthChamberInput(TileCrystalGrowthChamber inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		tile = inventoryIn;
	}

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack)
    {
        return tile.isItemValidForSlot(0, stack);
    }

}
