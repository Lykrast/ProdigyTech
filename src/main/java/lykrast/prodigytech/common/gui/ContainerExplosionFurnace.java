package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.tileentity.TileExplosionFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerExplosionFurnace extends Container {
	private TileExplosionFurnace tile;
	
	public ContainerExplosionFurnace(InventoryPlayer userInv, TileExplosionFurnace tile) {
		this.tile = tile;
		
		//Slot IDs
		//Tile - Explosives - Explosives 0	: 0
		//Tile - Explosives - Reactant 1	: 1
		//Tile - Input 2-4					: 2-4
		//Tile - Reagent 5					: 5
		//Tile - Input 6-8					: 6-8
		//Player - Inventory 9-35			: 9-35
		//Player - Hotbar 0-8				: 36-44
		
		//Explosion Furnace slots
		//Explosives
        for (int i = 0; i < 2; ++i)
        {
        	this.addSlotToContainer(new Slot(tile, i, 17, 26 + i * 18));
        }
		//Input
        for (int i = 0; i < 3; ++i)
        {
        	this.addSlotToContainer(new Slot(tile, i + 2, 53, 17 + i * 18));
        }
        //Reagent
        this.addSlotToContainer(new Slot(tile, 5, 89, 17));
		//Output
        for (int i = 0; i < 3; ++i)
        {
        	this.addSlotToContainer(new SlotOutput(userInv.player, tile, i + 6, 125, 17 + i * 18));
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

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.isUsableByPlayer(playerIn);
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

            //Item slots
            if (index < 9)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            //Inventory slots
            else if (!this.mergeItemStack(itemstack1, 0, 6, false))
            {
                return ItemStack.EMPTY;
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
