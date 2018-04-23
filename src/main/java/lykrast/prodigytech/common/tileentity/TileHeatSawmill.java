package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.recipe.HeatSawmillManager;
import lykrast.prodigytech.common.recipe.SimpleRecipe;
import lykrast.prodigytech.common.recipe.SimpleRecipeSecondaryOutput;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileHeatSawmill extends TileHotAirMachine {
    public TileHeatSawmill() {
		super(3);
	}

	@Override
	public String getName() {
		return super.getName() + "heat_sawmill";
	}

	@Override
	protected int getProcessSpeed() {
		return temperature / 8;
	}

	@Override
	protected IItemHandlerModifiable createInventoryHandler() {
		return new ProdigyInventoryHandler(this, 3, 0, 
				new boolean[]{true,false,false}, 
				new boolean[]{false,true,true});
	}
    
    @Override
	protected boolean canProcess()
    {
    	if (getStackInSlot(0).isEmpty() || temperature < 80) return false;
    	
    	SimpleRecipeSecondaryOutput recipe = HeatSawmillManager.INSTANCE.findRecipe(getStackInSlot(0));
    	if (recipe == null) return false;
    	
    	if (recipe.hasSecondaryOutput() && !getStackInSlot(2).isEmpty())
    	{
        	ItemStack secondaryOutput = recipe.getSecondaryOutput();
    	    ItemStack outputSlot = getStackInSlot(2);
    	    
    	    //Secondary output contains an item that does not match current recipe
    	    if (!outputSlot.isItemEqual(secondaryOutput)) return false;
    	    //Secondary output doesn't have enough room
    	    else if (outputSlot.getCount() + secondaryOutput.getCount() > this.getInventoryStackLimit()) return false;
    	    else if (outputSlot.getCount() + secondaryOutput.getCount() > outputSlot.getMaxStackSize()) return false;
    	}
    	
    	ItemStack itemstack = recipe.getOutput();
	    ItemStack itemstack1 = getStackInSlot(1);
		
	    //Output empty
        if (itemstack1.isEmpty())
        {
            return true;
        }
        //Output contains an item that does not match current recipe
        else if (!itemstack1.isItemEqual(itemstack))
        {
            return false;
        }
        //Output has enough room for the upcoming item
        else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
        {
            return true;
        }
        //I have no clue what this is doing here but it's not my code so may break stuff
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
            		SimpleRecipe recipe = HeatSawmillManager.INSTANCE.findRecipe(getStackInSlot(0));
            		processTimeMax = recipe.getTimeProcessing();
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0)
            	{
            		smelt();
            		flag1 = true;
            		
            		if (canProcess())
            		{
            			SimpleRecipe recipe = HeatSawmillManager.INSTANCE.findRecipe(getStackInSlot(0));
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
		SimpleRecipeSecondaryOutput recipe = HeatSawmillManager.INSTANCE.findRecipe(itemstack);
        //Primary output
        ItemStack itemstack1 = recipe.getOutput();
        ItemStack itemstack2 = getStackInSlot(1);

        if (itemstack2.isEmpty())
        {
        	setInventorySlotContents(1, itemstack1);
        }
        else if (itemstack2.getItem() == itemstack1.getItem())
        {
            itemstack2.grow(itemstack1.getCount());
        }
        
        //Secondary output
        itemstack1 = recipe.getSecondaryOutput();
        itemstack2 = getStackInSlot(2);

        if (itemstack2.isEmpty())
        {
        	setInventorySlotContents(2, itemstack1);
        }
        else if (itemstack2.getItem() == itemstack1.getItem())
        {
            itemstack2.grow(itemstack1.getCount());
        }
        
        itemstack.shrink(1);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return HeatSawmillManager.INSTANCE.isValidInput(stack);
		else return false;
	}

}
