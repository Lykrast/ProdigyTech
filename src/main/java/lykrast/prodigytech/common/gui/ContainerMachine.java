package lykrast.prodigytech.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public abstract class ContainerMachine<T extends IInventory> extends Container {
	protected T tile;

	public ContainerMachine(T tile) {
		super();
		this.tile = tile;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.isUsableByPlayer(playerIn);
	}
	
	protected void addPlayerSlots(InventoryPlayer userInv, int yOffset) {
    	for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(userInv, k + l * 9 + 9, 8 + k * 18, l * 18 + yOffset));
            }
        }
    	
    	yOffset += 58;

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(userInv, i1, 8 + i1 * 18, yOffset));
        }
	}
	
	protected void addPlayerSlotsDefault(InventoryPlayer userInv) {
		addPlayerSlots(userInv, 84);
	}
	
	protected void addPlayerSlotsHopper(InventoryPlayer userInv) {
		addPlayerSlots(userInv, 51);
	}

}