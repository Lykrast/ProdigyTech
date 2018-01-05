package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import lykrast.prodigytech.common.tileentity.TileHoloFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHoloFactory extends ContainerMachine<TileHoloFactory> {
	public ContainerHoloFactory(InventoryPlayer userInv, TileHoloFactory tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Battery 0-2				: 0-2
		//Tile - Output 3-11				: 3-11
		//Player - Inventory 9-35			: 12-38
		//Player - Hotbar 0-8				: 39-47
		
		//Battery slots
		for (int i=0;i<3;i++)
		{
			this.addSlotToContainer(new SlotEnergionBattery(tile, i, 30, 17 + i * 18));
		}
		
		//Output slots
		for (int i=0;i<3;i++)
		{
			for (int j=0;j<3;j++)
			{
				this.addSlotToContainer(new SlotOutput(userInv.player, tile, 3 + i * 3 + j, 84 + j * 18, 17 + i * 18));
			}
		}

		//Player slots
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(userInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(userInv, k, 8 + k * 18, 142));
        }
	}

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tile);
    }

	/**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //Machine slots
            if (index < 12)
            {
                if (!this.mergeItemStack(itemstack1, 12, 48, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            //Inventory slots
            else
            {
            	//Fuel
            	if (EnergionBatteryManager.isBattery(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 3, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                else if (index >= 12 && index < 39)
                {
                    if (!this.mergeItemStack(itemstack1, 39, 48, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 39 && index < 48 && !this.mergeItemStack(itemstack1, 12, 39, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }
        
        return itemstack;
    }

}
