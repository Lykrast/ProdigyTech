package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerEnergionTool extends Container {
	public ContainerEnergionTool(InventoryPlayer userInv, ItemStack tool) {
		this(userInv, tool.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
	}
	
	public ContainerEnergionTool(InventoryPlayer userInv, IItemHandler tool) {
		super();
		
		//Slot IDs
		//Tool - 0							: 0
		//Player - Inventory 9-35			: 1-27
		//Player - Hotbar 0-8				: 28-36
		
		//Fuel slot
    	this.addSlotToContainer(new SlotEnergionToolBattery(tool, 0, 80, 20));

		//Player slots
    	for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(userInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(userInv, i1, 8 + i1 * 18, 109));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
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

            //Tool Slots
            if (index < 1)
            {
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
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
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
            	else if (index >= 1 && index < 28)
                {
                    if (!this.mergeItemStack(itemstack1, 28, 37, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 1, 28, false))
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
