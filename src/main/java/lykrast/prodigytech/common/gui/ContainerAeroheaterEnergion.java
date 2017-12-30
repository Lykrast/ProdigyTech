package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import lykrast.prodigytech.common.tileentity.TileAeroheaterEnergion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAeroheaterEnergion extends ContainerMachine<TileAeroheaterEnergion> {
    private int temperature;
    
	public ContainerAeroheaterEnergion(InventoryPlayer userInv, TileAeroheaterEnergion tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Battery 0-5				: 0-5
		//Player - Inventory 9-35			: 6-32
		//Player - Hotbar 0-8				: 33-41
		
		//Battery slots
		for (int i=0;i<2;i++)
		{
			for (int j=0;j<3;j++)
			{
		    	this.addSlotToContainer(new SlotEnergionBattery(tile, i*3 + j, 60 + j * 20, 33 + i * 20));
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
        }

        temperature = tile.getField(0);
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
            if (index < 6)
            {
                if (!this.mergeItemStack(itemstack1, 6, 42, true))
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
                    if (!this.mergeItemStack(itemstack1, 0, 6, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                else if (index >= 6 && index < 33)
                {
                    if (!this.mergeItemStack(itemstack1, 33, 42, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 33 && index < 42 && !this.mergeItemStack(itemstack1, 6, 33, false))
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
