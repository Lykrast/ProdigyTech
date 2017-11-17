package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager.RotaryGrinderRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class TileRotaryGrinder extends TileHotAirMachineSimple implements ITickable, IHotAir {
    public TileRotaryGrinder() {
		super();
	}

	@Override
	public String getName() {
		return super.getName() + "rotary_grinder";
	}
    
    @Override
	protected boolean canProcess()
    {
    	if (getStackInSlot(0).isEmpty() || temperature < 80) return false;
    	
    	RotaryGrinderRecipe recipe = RotaryGrinderManager.findRecipe(getStackInSlot(0));
    	if (recipe == null) return false;
    	ItemStack itemstack = recipe.getOutput();
    	
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
            		RotaryGrinderRecipe recipe = RotaryGrinderManager.findRecipe(getStackInSlot(0));
            		processTimeMax = recipe.getTimeProcessing();
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0)
            	{
            		smelt();
            		flag1 = true;
            		
            		if (canProcess())
            		{
            			RotaryGrinderRecipe recipe = RotaryGrinderManager.findRecipe(getStackInSlot(0));
            			processTimeMax = recipe.getTimeProcessing();
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
        ItemStack itemstack1 = RotaryGrinderManager.findRecipe(itemstack).getOutput();
        ItemStack itemstack2 = getStackInSlot(1);

        if (itemstack2.isEmpty())
        {
        	setInventorySlotContents(1, itemstack1);
        }
        else if (itemstack2.getItem() == itemstack1.getItem())
        {
            itemstack2.grow(itemstack1.getCount());
        }

        itemstack.shrink(1);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return RotaryGrinderManager.isValidInput(stack);
		else return false;
	}

}
