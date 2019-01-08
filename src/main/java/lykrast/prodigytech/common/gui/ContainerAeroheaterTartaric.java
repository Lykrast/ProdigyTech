package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.tileentity.TileAeroheaterTartaric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerAeroheaterTartaric extends ContainerMachine<TileAeroheaterTartaric> {
    private int temperature;
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int stokerBurnTime;
    
	public ContainerAeroheaterTartaric(InventoryPlayer userInv, TileAeroheaterTartaric tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Fuel 0						: 0
		//Tile - Stoker 1					: 1
		//Player - Inventory 9-35			: 2-28
		//Player - Hotbar 0-8				: 29-37
		
		//Fuel slot
    	this.addSlotToContainer(new SlotFurnaceFuel(tile, 0, 71, 53));
    	this.addSlotToContainer(new Slot(tile, 1, 89, 53) {
            public boolean isItemValid(ItemStack stack) {
            	return stack.getItem() == ModItems.tartaricStoker;
            }
    	});

		//Player slots
		addPlayerSlotsDefault(userInv);
	}

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tile);
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (temperature != tile.getField(2)) icontainerlistener.sendWindowProperty(this, 2, tile.getField(2));
            if (furnaceBurnTime != tile.getField(0)) icontainerlistener.sendWindowProperty(this, 0, tile.getField(0));
            if (currentItemBurnTime != tile.getField(1)) icontainerlistener.sendWindowProperty(this, 1, tile.getField(1));
            if (stokerBurnTime != tile.getField(3)) icontainerlistener.sendWindowProperty(this, 3, tile.getField(3));
        }

        temperature = tile.getField(2);
        furnaceBurnTime = tile.getField(0);
        currentItemBurnTime = tile.getField(1);
        stokerBurnTime = tile.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        tile.setField(id, data);
    }

	/**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //Fuel slot
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
            	//Fuel
            	if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Stoker
            	else if (itemstack1.getItem() == ModItems.tartaricStoker)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                else if (index >= 2 && index < 29)
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
