package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockAeroheaterSolid;
import lykrast.prodigytech.common.block.BlockIncinerator;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileIncinerator extends TileMachineInventory implements ITickable, IHotAir {
    /** The number of ticks that the machine needs to process */
    private int processTime;
    /** The number of ticks that the current recipes needs in total */
    private int processTimeMax;
    /** The current temperature of the machine */
    private int temperature;
    /** The temperature that will come out of the machine */
    private int temperatureOut;

	public TileIncinerator() {
		super(2);
	}

	@Override
	public String getName() {
		return super.getName() + "incinerator";
	}
	
    public boolean isProcessing()
    {
        return processTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }
    
    private boolean canProcess()
    {
    	return (!getStackInSlot(0).isEmpty() && temperature >= 80);
    }
	
	private void process()
	{
		if (isProcessing())
		{
			if (canProcess()) processTime -= getProcessSpeed();
			else processTime = Math.max(processTimeMax, processTime + 5);
		}
	}
	
	private int getProcessSpeed()
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
                BlockIncinerator.setState(this.isProcessing(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private void updateInTemperature()
	{
		temperature = TemperatureHelper.getBlockTemp(world, pos.down());
	}
	
	private void updateOutTemperature()
	{
		if (isProcessing()) temperatureOut = (int) (temperature * 0.8F);
		else temperatureOut = temperature;
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

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.processTime = compound.getInteger("ProcessTime");
        this.processTimeMax = compound.getInteger("ProcessTimeMax");
        this.temperature = compound.getInteger("Temperature");
        this.temperatureOut = compound.getInteger("TemperatureOut");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProcessTime", (short)this.processTime);
        compound.setInteger("ProcessTimeMax", (short)this.processTimeMax);
        compound.setInteger("Temperature", (short)this.temperature);
        compound.setInteger("TemperatureOut", (short)this.temperatureOut);

        return compound;
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.processTime;
            case 1:
                return this.processTimeMax;
            case 2:
                return this.temperature;
            case 3:
                return this.temperatureOut;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.processTime = value;
                break;
            case 1:
                this.processTimeMax = value;
                break;
            case 2:
                this.temperature = value;
                break;
            case 3:
                this.temperatureOut = value;
                break;
        }
    }

    public int getFieldCount()
    {
        return 4;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return true;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 2, 0, 
			new boolean[]{true,false}, 
			new boolean[]{false,true});
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return (T)invHandler;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return (T)this;
		return super.getCapability(capability, facing);
	}

	@Override
	public int getOutAirTemperature() {
		return temperatureOut;
	}

}
