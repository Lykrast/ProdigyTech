package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.SimpleRecipeManagerAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMachineHotAirSecondary<T extends IInventory> extends ContainerMachine<T> {
    private int temperature;
    private int temperatureOut;
    private int processTime;
    private int processTimeMax;
    
	protected SimpleRecipeManagerAbstract<?> manager;
    
	public ContainerMachineHotAirSecondary(InventoryPlayer userInv, T tile, SimpleRecipeManagerAbstract<?> manager) {
		super(tile);
		this.manager = manager;
		
		//Slot IDs
		//Tile - Input 0					: 0
		//Tile - Output 1					: 1
		//Tile - Secondary Output 2			: 2
		//Player - Inventory 9-35			: 3-29
		//Player - Hotbar 0-8				: 30-38
		
		//Fuel slot
    	this.addSlotToContainer(new SlotManagerInput(manager, tile, 0, 56, 35));
    	this.addSlotToContainer(new SlotOutput(userInv.player, tile, 1, 116, 35));
    	this.addSlotToContainer(new SlotOutput(userInv.player, tile, 2, 142, 35));

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
            if (index <= 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            //Inventory slots
            else
            {
            	if (manager.isValidInput(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
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
