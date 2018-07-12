package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.item.IEnergionFillable;
import lykrast.prodigytech.common.tileentity.TileBatteryReplenisher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerBatteryReplenisher extends ContainerMachine<TileBatteryReplenisher> {
    private int temperature;
    private int temperatureOut;
    private int energion;
    private Slot slotEnergion, slotFill;
    
	public ContainerBatteryReplenisher(InventoryPlayer userInv, TileBatteryReplenisher tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Energion 0					: 0
		//Tile - Input						: 1
		//Player - Inventory 9-35			: 2-28
		//Player - Hotbar 0-8				: 29-37
		
		//Energion
		slotEnergion = new SlotEnergion(tile, 0, 44, 53);
    	addSlotToContainer(slotEnergion);
		//Input
    	slotFill = new SlotFill(tile, 1, 80, 35);
    	addSlotToContainer(slotFill);

		//Player slots
		addPlayerSlotsDefault(userInv);
	}

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tile);
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (temperature != tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, tile.getField(0));
            }

            if (temperatureOut != tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, tile.getField(1));
            }

            if (energion != tile.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, tile.getField(2));
            }
        }

        temperature = tile.getField(0);
        temperatureOut = tile.getField(1);
        energion = tile.getField(2);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        tile.setField(id, data);
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

            //Tile Slots
            if (index <= 1)
            {
                if (!this.mergeItemStack(itemstack1, 2, 38, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            //Inventory slots
            else
            {
            	//Energion
            	if (slotEnergion.isItemValid(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Input
            	if (slotFill.isItemValid(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                if (index >= 2 && index < 29)
                {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
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
    
    private static class SlotEnergion extends Slot {
        public SlotEnergion(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		public boolean isItemValid(ItemStack stack) {
			int id = OreDictionary.getOreID("dustEnergion");
			int[] ids = OreDictionary.getOreIDs(stack);
			for (int i : ids) if (i == id) return true;
			return false;
        }
    }
    
    private static class SlotFill extends Slot {
        public SlotFill(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		public boolean isItemValid(ItemStack stack) {
			if (!(stack.getItem() instanceof IEnergionFillable)) return false;
			return !((IEnergionFillable)stack.getItem()).isFull(stack);
        }
		
	    public int getSlotStackLimit() {
	        return 1;
	    }
    }

}
