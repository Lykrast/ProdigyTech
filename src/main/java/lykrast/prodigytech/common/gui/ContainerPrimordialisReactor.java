package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.PrimordialisReactorManager;
import lykrast.prodigytech.common.tileentity.TilePrimordialisReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPrimordialisReactor extends ContainerMachine<TilePrimordialisReactor> {
    private int temperature;
    private int temperatureOut;
    private int progressCycle;
    private int progressPrimordium;
    
	public ContainerPrimordialisReactor(InventoryPlayer userInv, TilePrimordialisReactor tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Input 0-8					: 0-8
		//Tile - Output 9					: 9
		//Player - Inventory 9-35			: 10-36
		//Player - Hotbar 0-8				: 37-45
		
		//Input
		for (int i=0;i<3;i++)
		{
			for (int j=0;j<3;j++)
			{
		    	this.addSlotToContainer(new SlotPrimordialisReactorInput(tile, i*3+j, 8 + 18 * j, 17 + 18 * i));
			}
		}
    	
    	//Output
    	this.addSlotToContainer(new SlotOutput(userInv.player, tile, 9, 148, 35));

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

            if (progressCycle != tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, tile.getField(0));
            }

            if (progressPrimordium != tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, tile.getField(1));
            }
        }

        temperature = tile.getField(2);
        temperatureOut = tile.getField(3);
        progressCycle = tile.getField(0);
        progressPrimordium = tile.getField(1);
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
            if (index <= 9)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            //Inventory slots
            else
            {
            	if (PrimordialisReactorManager.isValidInput(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 9, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                if (index >= 10 && index < 37)
                {
                    if (!this.mergeItemStack(itemstack1, 37, 46, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 37 && index < 46 && !this.mergeItemStack(itemstack1, 10, 37, false))
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
