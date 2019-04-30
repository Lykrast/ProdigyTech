package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirChangeable;
import lykrast.prodigytech.common.item.IHeatCapacitor;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileAeroheaterCapacitor extends TileMachineInventory implements ITickable, IProcessing {
    private int targetTemperature;
    private boolean active;
    private TileAeroheaterCapacitor.HotAir hotAir;

	public TileAeroheaterCapacitor() {
		super(1);
		hotAir = new HotAir();
	}

	@Override
	public String getName() {
		return super.getName() + "capacitor_aeroheater";
	}

    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

	@Override
	public void update() {
        boolean wasActive = active;
        boolean shouldDirty = false;
        
        if (!world.isRemote) {
        	ItemStack fuel = getStackInSlot(0);
        	
			if (!fuel.isEmpty() && !world.isBlockPowered(pos)) {
				IHeatCapacitor capacitor = (IHeatCapacitor)fuel.getItem();
				if (!capacitor.isDepleted(fuel)) {
					targetTemperature = capacitor.getTargetTemperature(fuel);
					capacitor.discharge(fuel, 1);
					active = true;
					shouldDirty = true;
				}
				else active = false;
			}
			else active = false;

            if (active) hotAir.raiseTemperature(targetTemperature);
            else hotAir.lowerTemperature();
        	
            if (wasActive != active) {
                shouldDirty = true;
                BlockHotAirMachine.setState(active, world, pos);
            }
        }

        if (shouldDirty) markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return stack.getItem() instanceof IHeatCapacitor;
		else return false;
	}

	@Override
	public boolean isProcessing() {
		return active;
	}
	
	@Override
	public int getProgressLeft() {
		ItemStack stack = getStackInSlot(0);
		return stack.isEmpty() ? 0 : ((IHeatCapacitor)stack.getItem()).getChargeLeft(stack);
	}
	
	@Override
	public int getMaxProgress() {
		ItemStack stack = getStackInSlot(0);
		return stack.isEmpty() ? 0 : ((IHeatCapacitor)stack.getItem()).getMaxCharge(stack);
	}
	
	@Override
	public boolean invertDisplay() {
		return true;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        active = compound.getBoolean("Active");
        targetTemperature = compound.getInteger("TargetTemperature");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("Active", active);
        compound.setInteger("TargetTemperature", targetTemperature);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return active ? 1 : 0;
            case 1:
                return targetTemperature;
            case 2:
                return hotAir.getOutAirTemperature();
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                active = value != 0;
                break;
            case 1:
            	targetTemperature = value;
                break;
            case 2:
            	hotAir.setTemperature(value);
                break;
        }
    }

    public int getFieldCount()
    {
        return 3;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP)
			return true;
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 1, 0, true, true) {
		@Override
		public boolean canExtract(int slot) {
			ItemStack stack = getStackInSlot(slot);
			if (stack.isEmpty()) return true;
			return super.canExtract(slot) && ((IHeatCapacitor)stack.getItem()).isDepleted(stack);
		}
	};
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP)
			return (T)invHandler;
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return (T)hotAir;
		return super.getCapability(capability, facing);
	}
	
	private static class HotAir extends HotAirChangeable {
		public void raiseTemperature(int target) {
			if (temperature > target) temperature = Math.max(target, temperature - 10);
			else temperature = Math.min(target, temperature + 10);
		}

		public void lowerTemperature() {
			temperature = Math.max(30, temperature - 10);
		}
		
	}

}
