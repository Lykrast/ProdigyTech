package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * A hot air powered machine that does simple recipes (1 input to 1 output)
 * @author Lykrast
 */
public abstract class TileHotAirMachineSimple extends TileMachineInventory implements IHotAir {
	/** The number of ticks that the machine needs to process */
	protected int processTime;
	/** The number of ticks that the current recipes needs in total */
	protected int processTimeMax;
	/** The current temperature of the machine */
	protected int temperature;
	/** The temperature that will come out of the machine */
	protected int temperatureOut;

	public TileHotAirMachineSimple() {
		super(2);
	}

	/**
	 * How much progress, in 1/10th of ticks, is made every tick
	 * @return progress per tick in 1/10th of ticks
	 */
	protected abstract int getProcessSpeed();

	protected abstract boolean canProcess();

	public boolean isProcessing()
    {
        return processTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }
	
	protected void process() {
		if (isProcessing())
		{
			if (canProcess()) processTime -= getProcessSpeed();
			else processTime = processTimeMax;
		}
	}

	protected void updateInTemperature() {
		temperature = TemperatureHelper.getBlockTemp(world, pos.down());
	}

	protected void updateOutTemperature() {
		if (isProcessing()) temperatureOut = (int) (temperature * 0.8F);
		else temperatureOut = temperature;
	}

	public int getField(int id) {
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

	public void setField(int id, int value) {
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
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 2, 0, 
			new boolean[]{true,false}, 
			new boolean[]{false,true});

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
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

}