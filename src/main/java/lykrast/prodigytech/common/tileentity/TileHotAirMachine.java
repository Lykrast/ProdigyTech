package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * A hot air powered machine that does simple recipes.
 * @author Lykrast
 */
public abstract class TileHotAirMachine extends TileMachineInventory implements ITickable, IProcessing {
	/** The number of ticks that the machine needs to process */
	protected int processTime;
	/** The number of ticks that the current recipes needs in total */
	protected int processTimeMax;
	protected HotAirMachine hotAir;

	public TileHotAirMachine(int slots, float outputMultiplier) {
		super(slots);
		hotAir = new HotAirMachine(this, outputMultiplier);
	}

	/**
	 * How much progress, in 1/10th of ticks, is made every tick
	 * @return progress per tick in 1/10th of ticks
	 */
	protected abstract int getProcessSpeed();

	protected abstract boolean canProcess();

	@Override
	public boolean isProcessing() {
        return processTime > 0;
    }
	
	@Override
	public int getProgressLeft() {
		return processTime;
	}
	
	@Override
	public int getMaxProgress() {
		return processTimeMax;
	}

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory) {
        return inventory.getField(0) > 0;
    }
	
	protected void process() {
		if (isProcessing()) {
			if (canProcess()) processTime -= getProcessSpeed();
			else processTime = processTimeMax;
		}
	}

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return processTime;
	        case 1:
	            return processTimeMax;
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
	            processTime = value;
	            break;
	        case 1:
	            processTimeMax = value;
	            break;
	        case 2:
	            hotAir.setTemperature(value);
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
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private IItemHandlerModifiable invHandler = createInventoryHandler();
	
	protected abstract IItemHandlerModifiable createInventoryHandler();

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return (T)invHandler;
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return (T)hotAir;
		return super.getCapability(capability, facing);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        processTime = compound.getInteger("ProcessTime");
        processTimeMax = compound.getInteger("ProcessTimeMax");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProcessTime", processTime);
        compound.setInteger("ProcessTimeMax", processTimeMax);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }

}