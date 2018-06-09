package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirMachine;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.PrimordialisReactorManager;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TilePrimordialisReactor extends TileMachineInventory implements ITickable, IProcessing {
	/** Progress for the current reactor cycle */
	private int progressCycle;
	/** Progress for the creation of Primordium */
	private int progressPrimordium;
	private HotAirMachine hotAir;

	//Slots :
	//0-8 Input
	//9 Output
	public TilePrimordialisReactor() {
		super(10);
		hotAir = new HotAirMachine(this, 0.5F);
	}

	@Override
	public String getName() {
		return super.getName() + "primordialis_reactor";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index < 9) return PrimordialisReactorManager.isValidInput(stack);
		else return false;
	}
    
	private boolean canProcess()
    {
    	if (hotAir.getInAirTemperature() < 250) return false;
    	
    	boolean content = false;
    	for (int i=0;i<9;i++)
    	{
    		if (!getStackInSlot(i).isEmpty())
    		{
    			content = true;
    			break;
    		}
    	}
    	
    	if (!content) return false;
    	
    	//Merging checks	
		ItemStack output = getStackInSlot(9);

		if (output.isEmpty())
			return true;
		else return (output.getCount() < this.getInventoryStackLimit() && output.getCount() < output.getMaxStackSize());
    }

	@Override
	public void update() {
        boolean flag = this.isProcessing();
        boolean flag1 = false;
        
        process();
        
        if (!this.world.isRemote)
        {
        	hotAir.updateInTemperature(world, pos);
    		
        	if (canProcess())
        	{
        		//Cycle hasn't started, start it
            	if (progressCycle <= 0)
            	{
            		progressCycle = 1;
            	}
            	//Cycle has finished
            	else if (progressCycle > Config.primordialisReactorCycleTime * 10)
            	{
            		cycle();
            		if (progressPrimordium >= Config.primordialisReactorRequiredInput) makePrimordium();
            		flag1 = true;
            		
            		//Start the next one if possible
            		if (canProcess()) progressCycle = 1;
            	}
        	}
        	//Can't process, stop the current cycle
        	else if (progressCycle > 0) progressCycle = 0;
        	
        	hotAir.updateOutTemperature();
        	
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
	
	private void cycle() {
		ItemStack[] collected = new ItemStack[9];
		int amount = 0;
		
		collect:
    	for (int i=0;i<9;i++)
    	{
    		ItemStack stack = getStackInSlot(i);
    		if (stack.isEmpty()) continue;
    		
    		//check for uniqueness
    		for (int j=0;j<amount;j++) if (collected[j].isItemEqual(stack)) continue collect;
    		
    		collected[amount] = stack.splitStack(1);
    		amount++;
    	}
		
		progressPrimordium += amount * amount;
	}
	
	private void makePrimordium() {
		ItemStack existing = getStackInSlot(9);

		if (existing.isEmpty()) setInventorySlotContents(9, new ItemStack(ModItems.primordium));
		else if (existing.getItem() == ModItems.primordium) existing.grow(1);
        progressPrimordium = 0;
	}
	
	private int getProcessSpeed()
	{
		return hotAir.getInAirTemperature() / 25;
	}

	@Override
	public boolean isProcessing()
    {
        return progressCycle > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }
	
	private void process() {
		if (isProcessing())
		{
			if (canProcess()) progressCycle += getProcessSpeed();
			else progressCycle = 0;
		}
	}

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return progressCycle;
	        case 1:
	            return progressPrimordium;
	        case 2:
	            return hotAir.getInAirTemperature();
	        case 3:
	            return hotAir.getOutAirTemperature();
	        default:
	            return 0;
	    }
	}

	public void setField(int id, int value) {
	    switch (id)
	    {
	        case 0:
	            progressCycle = value;
	            break;
	        case 1:
	            progressPrimordium = value;
	            break;
	        case 2:
	            hotAir.setTemperature(value);;
	            break;
	        case 3:
	        	hotAir.setOutAirTemperature(value);
	            break;
	    }
	}

	public int getFieldCount() {
	    return 4;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return true;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private IItemHandlerModifiable invHandler = new ProdigyInventoryHandler(this, 10, 0, 
			new boolean[]{true,true,true,true,true,true,true,true,true,false}, 
			new boolean[]{false,false,false,false,false,false,false,false,false,true});

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return (T)invHandler;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return (T)hotAir;
		return super.getCapability(capability, facing);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        progressCycle = compound.getInteger("ProgressCycle");
        progressPrimordium = compound.getInteger("ProgressPrimordium");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProgressCycle", progressCycle);
        compound.setInteger("ProgressPrimordium", progressPrimordium);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }

}
