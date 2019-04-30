package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirAeroheater;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileAeroheaterSolid extends TileMachineInventory implements ITickable, IProcessing {
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int currentItemBurnTime;
    private HotAirAeroheater hotAir;

	public TileAeroheaterSolid() {
		super(1);
		hotAir = new HotAir();
	}

	@Override
	public String getName() {
		return super.getName() + "solid_fuel_aeroheater";
	}

    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

	@Override
	public void update() {
        boolean wasBurning = isProcessing();
        boolean shouldDirty = false;

        if (isProcessing()) --furnaceBurnTime;
        
        if (!world.isRemote) {
        	ItemStack fuel = getStackInSlot(0);
        	
			if (!isProcessing() && !fuel.isEmpty() && !world.isBlockPowered(pos)) {
				furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
				currentItemBurnTime = furnaceBurnTime;

				if (isProcessing()) {
					shouldDirty = true;

					if (!fuel.isEmpty()) {
						fuel.shrink(1);

						if (fuel.isEmpty()) setInventorySlotContents(0, fuel.getItem().getContainerItem(fuel));
					}
				}
			}

            if (isProcessing()) hotAir.raiseTemperature();
            else hotAir.lowerTemperature();
        	
            if (wasBurning != isProcessing()) {
                shouldDirty = true;
                BlockHotAirMachine.setState(isProcessing(), world, pos);
            }
        }

        if (shouldDirty) markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return TileEntityFurnace.isItemFuel(stack);
		else return false;
	}

	@Override
	public boolean isProcessing() {
		return furnaceBurnTime > 0;
	}

	@Override
	public int getProgressLeft() {
		return furnaceBurnTime;
	}

	@Override
	public int getMaxProgress() {
		return currentItemBurnTime;
	}
	
	@Override
	public boolean invertDisplay() {
		return true;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        furnaceBurnTime = compound.getInteger("BurnTime");
        currentItemBurnTime = compound.getInteger("MaxBurnTime");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", furnaceBurnTime);
        compound.setInteger("MaxBurnTime", currentItemBurnTime);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return furnaceBurnTime;
            case 1:
                return currentItemBurnTime;
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
                furnaceBurnTime = value;
                break;
            case 1:
                currentItemBurnTime = value;
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
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 1, 0, true, false);
	
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
	
	private static class HotAir extends HotAirAeroheater {
		public HotAir() {
			super(200);
		}

		@Override
		protected void resetRaiseClock() {
			//5 seconds to reach 80 °C (when Blower Furnace starts working)
			if (temperature < 80) temperatureClock = 2;
			//10 more seconds to reach 100 °C (Blower Furnace reaches Furnace speed and 2 can get fueled at once)
			else if (temperature < 100) temperatureClock = 10;
			//30 more seconds to reach 125 °C (3 Blower Furnaces at once)
			else if (temperature < 125) temperatureClock = 24;
			//70 more seconds to reach 160 °C (4 Blower Furnaces at once)
			else if (temperature < 160) temperatureClock = 40;
			//120 more seconds to reach 200 °C (5 Blower Furnaces at once)
			else temperatureClock = 60;
		}

		@Override
		protected void resetLowerClock() {
			//Stays at 4+ furnaces (200-160) for 4 seconds
			if (temperature > 160) temperatureClock = 2;
			//Stays at 3+ furnaces (160-125) for 7 seconds
			else if (temperature > 125) temperatureClock = 4;
			//Stays at 2+ furnaces (125-100) for 10 seconds
			else if (temperature > 100) temperatureClock = 8;
			//Stays at 1+ furnaces (100-80) for 15 seconds
			else if (temperature > 80) temperatureClock = 15;
			//Fully cools (80-30) in 50 seconds
			else temperatureClock = 20;
		}
		
	}

}
