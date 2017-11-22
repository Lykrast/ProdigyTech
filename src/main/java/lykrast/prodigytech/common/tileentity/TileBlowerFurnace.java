package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileBlowerFurnace extends TileHotAirMachineSimple implements ITickable, IHotAir {
    public TileBlowerFurnace() {
		super();
	}

	@Override
	public String getName() {
		return super.getName() + "blower_furnace";
	}
    
    @Override
	protected boolean canProcess()
    {
    	if (getStackInSlot(0).isEmpty() || temperature < 80) return false;
    	
    	ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0));
    	if (itemstack.isEmpty()) return false;
    	
	    ItemStack itemstack1 = getStackInSlot(1);
		
        if (itemstack1.isEmpty())
        {
            return true;
        }
        else if (!itemstack1.isItemEqual(itemstack))
        {
            return false;
        }
        else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
        {
            return true;
        }
        else
        {
            return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
        }
    }
	
	@Override
	protected int getProcessSpeed()
	{
		return temperature / 10;
	}

	@Override
	public void update() {
        boolean flag = this.isProcessing();
        boolean flag1 = false;
        
        process();
        
        if (!this.world.isRemote)
        {
        	updateInTemperature();
        	
        	if (canProcess())
        	{
            	if (processTimeMax <= 0)
            	{
            		processTimeMax = Config.blowerFurnaceProcessTime * 10;
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0)
            	{
            		smelt();
            		flag1 = true;
            		
            		if (canProcess())
            		{
            			processTimeMax = Config.blowerFurnaceProcessTime * 10;
                		processTime = processTimeMax;
            		}
            		else
            		{
            			processTimeMax = 0;
            			processTime = 0;
            		}
            	}
        	}
        	else if (processTime >= processTimeMax)
    		{
    			processTimeMax = 0;
    			processTime = 0;
    		}
        	
        	updateOutTemperature();
        	
            if (flag != this.isProcessing())
            {
                flag1 = true;
                BlockHotAirMachine.setState(this.isProcessing(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private void smelt()
	{
		ItemStack itemstack = getStackInSlot(0);
        ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
        ItemStack itemstack2 = getStackInSlot(1);

        if (itemstack2.isEmpty())
        {
        	setInventorySlotContents(1, itemstack1.copy());
        }
        else if (itemstack2.getItem() == itemstack1.getItem())
        {
            itemstack2.grow(itemstack1.getCount());
        }

        itemstack.shrink(1);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty();
		else return false;
	}

}