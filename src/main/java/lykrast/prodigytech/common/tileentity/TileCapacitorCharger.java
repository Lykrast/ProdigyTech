package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.capability.HotAirMachine;
import lykrast.prodigytech.common.item.IHeatCapacitor;
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

public class TileCapacitorCharger extends TileMachineInventory implements ITickable, IProcessing {
	private int progressCycle;
	private int targetTemperature;
	private HotAirMachine hotAir;
	
	public TileCapacitorCharger() {
		super(1);
		hotAir = new HotAirMachine(this, 0);
	}

	@Override
	public String getName() {
		return super.getName() + "capacitor_charger";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return stack.getItem() instanceof IHeatCapacitor && ((IHeatCapacitor)stack.getItem()).isChargeable(stack);
		else return false;
	}
    
	private boolean canProcess() {
    	ItemStack capacitor = getStackInSlot(0);
    	if (capacitor.isEmpty()) return false;

		IHeatCapacitor casted = (IHeatCapacitor)capacitor.getItem();
		if (casted.isFullyCharged(capacitor)) return false;
		
		targetTemperature = casted.getTargetTemperature(capacitor);
    	return hotAir.getInAirTemperature() >= targetTemperature;
    }

	@Override
	public void update() {
        boolean wasProcessing = isProcessing();
        boolean shouldDirty = false;
        
        process();
        
        if (!world.isRemote) {
        	hotAir.updateInTemperature(world, pos);
    		
        	if (canProcess()) {
        		//Cycle hasn't started, start it
            	if (progressCycle <= 0) progressCycle = 1;
            	//Cycle has finished
            	else if (progressCycle > Config.capacitorChargerChargeTime * 10) {
                	ItemStack capacitor = getStackInSlot(0);
					((IHeatCapacitor)capacitor.getItem()).charge(capacitor, 20);
            		shouldDirty = true;
            		
            		//Start the next one if possible
            		if (canProcess()) progressCycle = 1;
            	}
        	}
        	//Can't process, stop the current cycle
        	else if (progressCycle > 0) progressCycle = 0;
        	
        	hotAir.updateOutTemperature();
        	
            if (wasProcessing != isProcessing()) {
                shouldDirty = true;
                BlockMachineActiveable.setState(isProcessing(), world, pos);
            }
        }

        if (shouldDirty) markDirty();
	}
	
	private int getProcessSpeed() {
		if (targetTemperature <= 30) return 10;
		//Prevent capacitors being charged faster than they can last
		int cap = Config.capacitorChargerChargeTime / 2;
		return Math.min(cap, 10 + ((hotAir.getInAirTemperature() - targetTemperature) * 2) / targetTemperature);
	}

	@Override
	public boolean isProcessing() {
		return progressCycle > 0;
	}
	
	@Override
	public int getProgressLeft() {
		ItemStack stack = getStackInSlot(0);
		if (stack.isEmpty()) return 0;
		IHeatCapacitor capacitor = ((IHeatCapacitor)stack.getItem());
		return capacitor.getMaxCharge(stack) - capacitor.getChargeLeft(stack);
	}
	
	@Override
	public int getMaxProgress() {
		ItemStack stack = getStackInSlot(0);
		return stack.isEmpty() ? 0 : ((IHeatCapacitor)stack.getItem()).getMaxCharge(stack);
	}

    @SideOnly(Side.CLIENT)
	public static boolean isProcessing(IInventory inventory) {
		return inventory.getField(0) > 0;
	}
	
	private void process() {
		if (isProcessing()) {
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
	            return targetTemperature;
	        case 2:
	            return hotAir.getInAirTemperature();
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
	        	targetTemperature = value;
	            break;
	        case 2:
	            hotAir.setTemperature(value);
	            break;
	    }
	}

	public int getFieldCount() {
	    return 3;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.DOWN)
			return true;
		return super.hasCapability(capability, facing);
	}

	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 1, 0, true, true) {
		@Override
		public boolean canExtract(int slot) {
			ItemStack stack = getStackInSlot(slot);
			if (stack.isEmpty()) return true;
			return super.canExtract(slot) && ((IHeatCapacitor)stack.getItem()).isFullyCharged(stack);
		}
	};

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.DOWN)
			return (T)invHandler;
		return super.getCapability(capability, facing);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        progressCycle = compound.getInteger("ProgressCycle");
        targetTemperature = compound.getInteger("TargetTemperature");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProgressCycle", progressCycle);
        compound.setInteger("TargetTemperature", targetTemperature);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }

}
