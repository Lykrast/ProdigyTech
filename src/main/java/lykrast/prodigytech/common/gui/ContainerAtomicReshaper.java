package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager;
import lykrast.prodigytech.common.tileentity.TileAtomicReshaper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAtomicReshaper extends ContainerMachine<TileAtomicReshaper> {
    private int temperature;
    private int temperatureOut;
    private int primordium;
    private int processTime;
    private int processTimeMax;
    
	public ContainerAtomicReshaper(InventoryPlayer userInv, TileAtomicReshaper tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Primordium 0				: 0
		//Tile - Input 1					: 1
		//Tile - Output 2					: 2
		//Player - Inventory 9-35			: 3-29
		//Player - Hotbar 0-8				: 30-38
		
		//Primordium
    	this.addSlotToContainer(new Slot(tile, 0, 8, 53) {
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == ModItems.primordium;
            }
    	});
		//Input
    	this.addSlotToContainer(new SlotManagerInput(AtomicReshaperManager.INSTANCE, tile, 1, 44, 35));
    	//Output
    	this.addSlotToContainer(new SlotOutput(userInv.player, tile, 2, 128, 35));

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

            if (primordium != tile.getField(4))
            {
                icontainerlistener.sendWindowProperty(this, 4, tile.getField(4));
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
        primordium = tile.getField(4);
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
            	//Primordium
            	if (itemstack1.getItem() == ModItems.primordium)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Input
            	if (AtomicReshaperManager.INSTANCE.isValidInput(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
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
