package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.recipe.SimpleRecipe;
import lykrast.prodigytech.common.recipe.SimpleRecipeManager;
import net.minecraft.item.ItemStack;

/**
 * A hot air powered machine that uses a SimpleRecipeManager.
 * @author Lykrast
 */
public abstract class TileHotAirMachineSimpleManaged extends TileHotAirMachineSimple {
	protected final SimpleRecipeManager manager;
	
    public TileHotAirMachineSimpleManaged(SimpleRecipeManager manager) {
		super();
		this.manager = manager;
	}
    
    protected boolean isTooCool() {
    	return temperature < 80;
    }
    
    @Override
	protected boolean canProcess()
    {
    	if (getStackInSlot(0).isEmpty() || isTooCool()) return false;
    	
    	SimpleRecipe recipe = manager.findRecipe(getStackInSlot(0));
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
            		SimpleRecipe recipe = manager.findRecipe(getStackInSlot(0));
            		processTimeMax = recipe.getTimeProcessing();
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0)
            	{
            		smelt();
            		flag1 = true;
            		
            		if (canProcess())
            		{
            			SimpleRecipe recipe = manager.findRecipe(getStackInSlot(0));
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
                BlockMachineActiveable.setState(this.isProcessing(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	protected void smelt()
	{
		ItemStack itemstack = getStackInSlot(0);
        ItemStack itemstack1 = manager.findRecipe(itemstack).getOutput();
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
		if (index == 0) return manager.isValidInput(stack);
		else return false;
	}

}
