package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.tileentity.TileFuelProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFuelProcessor extends ContainerMachine<TileFuelProcessor> {
    private int temperature;
    private int temperatureOut;
    private int processTime;
    private int processTimeMax;
    
	public ContainerFuelProcessor(InventoryPlayer userInv, TileFuelProcessor tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Input 0					: 0
		//Tile - Output 1					: 1
		//Player - Inventory 9-35			: 2-28
		//Player - Hotbar 0-8				: 29-37
		
		//Fuel slot
    	addSlotToContainer(new Slot(tile, 0, 56, 35) {
            @Override
			public boolean isItemValid(ItemStack stack) {
                return TileFuelProcessor.isValidInput(stack);
            }
    	});
    	addSlotToContainer(new SlotOutput(userInv.player, tile, 1, 116, 35));

		//Player slots
    	addPlayerSlotsDefault(userInv);
	}

    @Override
	public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tile);
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
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

            if (temperatureOut != tile.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, tile.getField(3));
            }

            if (processTime != tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, tile.getField(0));
            }

            if (processTimeMax != tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, tile.getField(1));
            }
        }

        temperature = tile.getField(2);
        temperatureOut = tile.getField(3);
        processTime = tile.getField(0);
        processTimeMax = tile.getField(1);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        tile.setField(id, data);
    }

	/**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
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
            	if (TileFuelProcessor.isValidInput(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
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

}
