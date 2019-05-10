package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.SoldererManager;
import lykrast.prodigytech.common.tileentity.TileSolderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSolderer extends ContainerMachine<TileSolderer> {
    private int temperature, temperatureOut;
    private int gold;
    private int processTime;
    private int processTimeMax;
    
	public ContainerSolderer(InventoryPlayer userInv, TileSolderer tile) {
		super(tile);
		
		//Slot IDs
		//Tile - Pattern 0					: 0
		//Tile - Gold 1						: 1
		//Tile - Additive 2					: 2
		//Tile - Plates 3					: 3
		//Tile - Output 4					: 4
		//Player - Inventory 9-35			: 5-31
		//Player - Hotbar 0-8				: 32-40
		
		//Pattern
    	this.addSlotToContainer(new Slot(tile, 0, 20, 17) {
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                return SoldererManager.isValidPattern(stack);
            }
    	});
		//Gold
    	this.addSlotToContainer(new Slot(tile, 1, 20, 53) {
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                return SoldererManager.getGoldAmount(stack) > 0;
            }
    	});
		//Additive
    	this.addSlotToContainer(new Slot(tile, 2, 56, 17) {
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                return SoldererManager.isValidAdditive(stack);
            }
    	});
		//Plates
    	this.addSlotToContainer(new Slot(tile, 3, 56, 53) {
            @Override
			public boolean isItemValid(ItemStack stack)
            {
                return SoldererManager.isPlate(stack);
            }
    	});
    	//Output
    	this.addSlotToContainer(new SlotOutput(userInv.player, tile, 4, 116, 35));

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

            if (gold != tile.getField(4))
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
        gold = tile.getField(4);
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
            if (index <= 4)
            {
                if (!this.mergeItemStack(itemstack1, 5, 41, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            //Inventory slots
            else
            {
            	//Pattern
            	if (SoldererManager.isValidPattern(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Gold
            	if (SoldererManager.getGoldAmount(itemstack1) > 0)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Additive
            	if (SoldererManager.isValidAdditive(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 2, 3, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Plate
            	if (SoldererManager.isPlate(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 3, 4, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            	//Player inventory
                if (index >= 5 && index < 32)
                {
                    if (!this.mergeItemStack(itemstack1, 32, 41, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 32 && index < 41 && !this.mergeItemStack(itemstack1, 5, 32, false))
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
