package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirMachine;
import lykrast.prodigytech.common.item.IEnergionFillable;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.IProdigyInventory;
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
import net.minecraftforge.oredict.OreDictionary;

public class TileBatteryReplenisher extends TileMachineInventory implements ITickable, IProcessing {
	private boolean processing;
	private HotAirMachine hotAir;
	/** The amount of energion in the machine */
	private int energion;

	//Slots :
	//0 Primordium
	//1 Battery
	public TileBatteryReplenisher() {
		super(2);
		hotAir = new HotAirMachine(this, 0.8F);
		processing = false;
	}

	@Override
	public String getName() {
		return super.getName() + "battery_replenisher";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			int id = OreDictionary.getOreID("dustEnergion");
			int[] ids = OreDictionary.getOreIDs(stack);
			for (int i : ids) if (i == id) return true;
			return false;
		}
		else if (index == 1) {
			if (!(stack.getItem() instanceof IEnergionFillable)) return false;
			return !((IEnergionFillable)stack.getItem()).isFull(stack);
		}
		else return false;
	}
	
	private int canFillEnergion()
	{
		if (getStackInSlot(0).isEmpty() || energion > ((Config.batteryReplenisherMaxEnergion - 1) * Config.energionDuration)) return 0;
		else return Config.energionDuration;
	}
    
	private boolean canProcess()
    {
    	ItemStack stack = getStackInSlot(1);
    	if (stack.isEmpty() || hotAir.getInAirTemperature() < 80) return false;
    	
    	return energion > 0 && !((IEnergionFillable)stack.getItem()).isFull(stack);
    }

	@Override
	public void update() {
        boolean flag = this.isProcessing();
        boolean flag1 = false;
        
        if (!this.world.isRemote)
        {
        	hotAir.updateInTemperature(world, pos);

    		int energionAmount = canFillEnergion();
    		if (energionAmount > 0)
    		{
    			energion += energionAmount;
    			getStackInSlot(0).shrink(1);
    		}
    		
        	if (canProcess())
        	{
        		processing = true;
        		ItemStack stack = getStackInSlot(1);
            	IEnergionFillable fillable = (IEnergionFillable)stack.getItem();
            	
            	int fill = Math.min(energion, (int)(Config.batteryReplenisherSpeed * hotAir.getInAirTemperature() / 80.0F));
            	fill = fillable.getFillableAmount(stack, fill);
            	energion -= fill;
            	setInventorySlotContents(1, fillable.fill(stack, fill));
        	}
        	else processing = false;
        	
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

	@Override
	public boolean isProcessing()
    {
        return processing;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return hotAir.getInAirTemperature();
	        case 1:
	            return hotAir.getOutAirTemperature();
	        case 2:
	            return energion;
	        default:
	            return 0;
	    }
	}

	public void setField(int id, int value) {
	    switch (id)
	    {
	        case 0:
	        	hotAir.setTemperature(value);
	            break;
	        case 1:
	        	hotAir.setOutAirTemperature(value);
	            break;
	        case 2:
	            energion = value;
	            break;
	    }
	}

	public int getFieldCount() {
	    return 3;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return true;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new TileBatteryReplenisher.InventoryHandler(this);

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
        processing = compound.getBoolean("Processing");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
        energion = compound.getInteger("Energion");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("Processing", processing);
        compound.setTag("HotAir", hotAir.serializeNBT());
        compound.setInteger("Energion", energion);

        return compound;
    }
    
    private static class InventoryHandler extends ProdigyInventoryHandler {

		public InventoryHandler(IProdigyInventory inventory) {
			super(inventory, 2, 0, new boolean[]{true, true}, new boolean[]{false, true});
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (slot == 1) {
				ItemStack stack = getStackInSlot(1);
				
				//This should always happen in base Prodigy Tech, but dunno what others may do when filling their items
				if (stack.getItem() instanceof IEnergionFillable) {
					//Don't extract batteries that aren't full
					if (!((IEnergionFillable)stack.getItem()).isFull(stack)) return ItemStack.EMPTY;
				}
			}
			return super.extractItem(slot, amount, simulate);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			if (slot == 1) return 1;
			else return 64;
		}
    	
    }

}
