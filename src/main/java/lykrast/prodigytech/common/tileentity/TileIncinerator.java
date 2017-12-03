package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class TileIncinerator extends TileHotAirMachineSimple implements ITickable, IHotAir {
    public TileIncinerator() {
		super();
	}

	@Override
	public String getName() {
		return super.getName() + "incinerator";
	}
    
    @Override
	protected boolean canProcess()
    {
    	return (!getStackInSlot(0).isEmpty() && temperature >= 80);
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
            		processTimeMax = Config.incineratorProcessTime * 10;
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0)
            	{
            		incinerate();
            		flag1 = true;
            		
            		if (canProcess())
            		{
            			processTimeMax = Config.incineratorProcessTime * 10;
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
	
	private void incinerate()
	{
		getStackInSlot(0).shrink(1);
		
		if (world.rand.nextFloat() < Config.incineratorChance)
		{
			ItemStack result = new ItemStack(ModItems.ash);
			ItemStack output = getStackInSlot(1);
			
			if (output.isEmpty())
			{
				setInventorySlotContents(1, result);
			}
			else if (output.isItemEqual(result))
			{
				output.grow(result.getCount());
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return true;
		else return false;
	}

}
