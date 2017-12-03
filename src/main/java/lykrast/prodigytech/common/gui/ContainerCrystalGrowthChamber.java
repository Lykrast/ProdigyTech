package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.tileentity.TileCrystalGrowthChamber;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrystalGrowthChamber extends ContainerMachine<TileCrystalGrowthChamber> {
	private int processTime;
	private int danger;
	private int expectedN, expectedS, expectedE, expectedW;
	
	public ContainerCrystalGrowthChamber(InventoryPlayer userInv, TileCrystalGrowthChamber tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Input 0					: 0
		//Tile - Output 1					: 1
		//Player - Inventory 9-35			: 2-28
		//Player - Hotbar 0-8				: 29-37
		
		//Input
    	this.addSlotToContainer(new SlotCrystalGrowthChamberInput(tile, 0, 80, 35));
    	//Output
    	this.addSlotToContainer(new SlotOutput(userInv.player, tile, 1, 129, 35));

		//Player slots
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(userInv, j + i * 9 + 9, 8 + j * 18, 105 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(userInv, k, 8 + k * 18, 163));
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

            if (processTime != tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, tile.getField(0));
            }

            if (danger != tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, tile.getField(1));
            }

            if (expectedN != tile.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, tile.getField(2));
            }

            if (expectedS != tile.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, tile.getField(3));
            }

            if (expectedE != tile.getField(4))
            {
                icontainerlistener.sendWindowProperty(this, 4, tile.getField(4));
            }

            if (expectedW != tile.getField(5))
            {
                icontainerlistener.sendWindowProperty(this, 5, tile.getField(5));
            }
        }
        
        processTime = tile.getField(0);
        danger = tile.getField(1);
        expectedN = tile.getField(2);
        expectedS = tile.getField(3);
        expectedE = tile.getField(4);
        expectedW = tile.getField(5);
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
            else if (!this.mergeItemStack(itemstack1, 0, 1, false))
            {
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
