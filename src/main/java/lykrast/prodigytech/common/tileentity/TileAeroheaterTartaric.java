package lykrast.prodigytech.common.tileentity;

import java.util.Arrays;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirAeroheater;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileAeroheaterTartaric extends TileMachineInventory implements ITickable {
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int currentItemBurnTime;
    /** The number of ticks that the stoker will keep burning */
    private int stokerBurnTime;
    private TileAeroheaterTartaric.HotAir hotAir;

	public TileAeroheaterTartaric() {
		super(2);
		hotAir = new HotAir();
	}

	@Override
	public String getName() {
		return super.getName() + "tartaric_aeroheater";
	}
	
    public boolean isBurning() {
        return furnaceBurnTime > 0 && stokerBurnTime > 0;
    }

    public static boolean isBurningFuel(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    public static boolean isBurningStoker(IInventory inventory) {
        return inventory.getField(3) > 0;
    }

	@Override
	public void update() {
        boolean wasBurning = isBurning();
        boolean shouldDirty = false;

        if (furnaceBurnTime > 0)
        {
        	furnaceBurnTime -= hotAir.getFuelSpeed();
        	if (furnaceBurnTime < 0) furnaceBurnTime = 0;
        }
        if (stokerBurnTime > 0)
        {
        	stokerBurnTime -= hotAir.getFuelSpeed();
        	if (stokerBurnTime < 0) stokerBurnTime = 0;
        }
        
        if (!world.isRemote)
        {
			if (!isBurning() && !world.isBlockPowered(pos)) {
	        	ItemStack fuel = getStackInSlot(0);
	        	ItemStack stoker = getStackInSlot(1);
	        	
				//Only use fuel and/or stoker if both would burn at the end
				boolean canFuel = furnaceBurnTime == 0 && !fuel.isEmpty();
				boolean canStoker = stokerBurnTime == 0 && !stoker.isEmpty();
				boolean shouldFuel = canFuel && (stokerBurnTime > 0 || canStoker);
				boolean shouldStoker = canStoker && (furnaceBurnTime > 0 || canFuel);
				
				if (shouldFuel) {
					//Update burn time
					furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
					currentItemBurnTime = furnaceBurnTime;
					
					//Remove fuel, add the container item if needed
					shouldDirty = true;
					Item item = fuel.getItem();
					fuel.shrink(1);

					if (fuel.isEmpty()) {
						ItemStack item1 = item.getContainerItem(fuel);
						setInventorySlotContents(0, item1);
					}
				}
				if (shouldStoker) {
					//Update burn time
					stokerBurnTime = Config.tartaricStokerTime;
					
					//Remove item
					shouldDirty = true;
					stoker.shrink(1);
					if (stoker.isEmpty()) setInventorySlotContents(1, ItemStack.EMPTY);
				}
			}

            if (isBurning()) hotAir.raiseTemperature();
            else hotAir.lowerTemperature();
        	
            if (wasBurning != isBurning())
            {
                shouldDirty = true;
                BlockHotAirMachine.setState(isBurning(), world, pos);
            }
        }

        if (shouldDirty) markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return TileEntityFurnace.isItemFuel(stack);
		else if (index == 1) return stack.getItem() == ModItems.tartaricStoker;
		else return false;
	}
	
	public boolean isBurningSomething() {
        return furnaceBurnTime > 0 || stokerBurnTime > 0;
	}
	
	public int getBurnLeft() {
		return furnaceBurnTime;
	}
	
	public int getBurnMax() {
		return currentItemBurnTime;
	}
	
	public int getStokerLeft() {
		return stokerBurnTime;
	}
	
	public int getStokerMax() {
		return Config.tartaricStokerTime;
	}

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        furnaceBurnTime = compound.getInteger("BurnTime");
        currentItemBurnTime = compound.getInteger("MaxBurnTime");
        stokerBurnTime = compound.getInteger("StokerTime");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", furnaceBurnTime);
        compound.setInteger("MaxBurnTime", currentItemBurnTime);
        compound.setInteger("StokerTime", stokerBurnTime);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }

    public int getField(int id) {
        switch (id)
        {
            case 0:
                return furnaceBurnTime;
            case 1:
                return currentItemBurnTime;
            case 2:
                return hotAir.getOutAirTemperature();
            case 3:
                return stokerBurnTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
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
            case 3:
                stokerBurnTime = value;
                break;
        }
    }

    public int getFieldCount() {
        return 4;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP)
			return true;
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 2, 0, true, false);
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP)
			return (T)invHandler;
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return (T)hotAir;
		return super.getCapability(capability, facing);
	}
	
	private static class HotAir extends HotAirAeroheater {
		private static final int[] THRESHOLDS =	{80, 100, 125, 160, 200, 250, 320, 400, 500, 600, 750, 1000};
		private static final int[] SPEEDS =		{1,  2,   3,   4,   5,   6,   8,   10,  13,  16,  20,  25};
		private int index;
		
		public HotAir() {
			super(1000);
		}
		
		private int getFuelSpeed() {
			return SPEEDS[index];
		}
		
		private void updateIndex() {
			index = Arrays.binarySearch(THRESHOLDS, temperature);
			//If current temperature isn't an exact match, get the next biggest
			if (index < 0) index = -(index+1);
			if (index >= THRESHOLDS.length) index = THRESHOLDS.length-1;
		}

		@Override
		protected void resetRaiseClock() {
			//Heats much faster than the other aeroheaters
			//Good luck maintaining the whole time though
			temperatureClock = 2;
			
			updateIndex();
		}

		@Override
		protected void resetLowerClock() {
			//Heat dissipates fast
			temperatureClock = 1;
			
			updateIndex();
		}
		
	}

}
