package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.tileentity.TileAeroheaterCapacitor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAeroheaterCapacitor extends ContainerMachine<TileAeroheaterCapacitor> {
    private int temperature;
    private int active;
    private int targetTemperature;
    
	public ContainerAeroheaterCapacitor(InventoryPlayer userInv, TileAeroheaterCapacitor tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Fuel 0						: 0
		//Player - Inventory 9-35			: 1-27
		//Player - Hotbar 0-8				: 28-36
		
		//Fuel slot
    	addSlotToContainer(new SlotMachine(tile, 0, 80, 35));

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

            if (temperature != tile.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, tile.getField(2));
            }

            if (active != tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, tile.getField(0));
            }

            if (targetTemperature != tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, tile.getField(1));
            }
        }

        temperature = tile.getField(2);
        active = tile.getField(0);
        targetTemperature = tile.getField(1);
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

            //Fuel slot
            if (index == 0)
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
            	if (tile.isItemValidForSlot(0, itemstack1))
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
                else if (index >= 28 && index < 37 && !this.mergeItemStack(itemstack1, 1, 28, false))
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
