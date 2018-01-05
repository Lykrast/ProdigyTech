package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import lykrast.prodigytech.common.util.ProdigyInventoryHandlerEnergion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileAeroheaterEnergion extends TileMachineInventory implements ITickable, IHotAir {
	private static final int[] MAX_TEMP = {0, 80, 100, 125, 160, 200, 250};
	
    /** The current temperature of the heater */
    private int temperature;
    /** A clock to change the speed at which temperature raises */
    private int temperatureClock;

	public TileAeroheaterEnergion() {
		super(6);
		temperature = 30;
	}

	@Override
	public String getName() {
		return super.getName() + "energion_aeroheater";
	}

	@Override
	public void update() {
        boolean flag = world.getBlockState(pos).getValue(BlockHotAirMachine.ACTIVE);
        boolean flag1 = false;
        
        if (!this.world.isRemote)
        {
        	int energy = 0;
        	
        	if (!world.isBlockPowered(pos))
        	{
            	for (int i=0;i<6;i++)
            	{
                	ItemStack battery = getStackInSlot(i);
                	if (!battery.isEmpty() && EnergionBatteryManager.isBattery(battery))
                	{
                		energy += EnergionBatteryManager.extract(battery, 1);
                		setInventorySlotContents(i, EnergionBatteryManager.checkDepleted(battery));
                	}
            	}
        	}
        	
        	boolean active = energy > 0;

            if (active) raiseTemperature(energy);
            else lowerTemperature();
        	
            if (flag != active)
            {
                flag1 = true;
                BlockHotAirMachine.setState(active, this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private void raiseTemperature(int energy)
	{
		if (temperature == MAX_TEMP[energy]) return;
		else if (temperature > MAX_TEMP[energy])
		{
			lowerTemperature();
			return;
		}
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature++;
			
			//2.5 seconds to reach 80 °C
			if (temperature < 80) temperatureClock = 1;
			//5 more seconds to reach 100 °C
			else if (temperature < 100) temperatureClock = 5;
			//10 more seconds to reach 125 °C
			else if (temperature < 125) temperatureClock = 8;
			//21 more seconds to reach 160 °C
			else if (temperature < 160) temperatureClock = 12;
			//32 more seconds to reach 200 °C
			else if (temperature < 200) temperatureClock = 16;
			//60 more seconds to reach 250 °C
			else temperatureClock = 24;
		}
	}
	
	private void lowerTemperature()
	{
		if (temperature <= 30) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature--;

			//Stays at 5+ furnaces (250-200) for 2.5 seconds
			if (temperature > 200) temperatureClock = 1;
			//Stays at 4+ furnaces (200-160) for 4 seconds
			if (temperature > 160) temperatureClock = 2;
			//Stays at 3+ furnaces (160-125) for 7 seconds
			else if (temperature > 125) temperatureClock = 4;
			//Stays at 2+ furnaces (125-100) for 10 seconds
			else if (temperature > 100) temperatureClock = 8;
			//Stays at 1+ furnaces (100-80) for 12 seconds
			else if (temperature > 80) temperatureClock = 12;
			//Fully cools (80-30) in 35 seconds
			else temperatureClock = 14;
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index <= 5) return EnergionBatteryManager.isBattery(stack);
		else return false;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.temperature = compound.getInteger("Temperature");
        this.temperatureClock = compound.getInteger("TemperatureClock");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Temperature", (short)this.temperature);
        compound.setInteger("TemperatureClock", (short)this.temperatureClock);

        return compound;
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.temperature;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.temperature = value;
                break;
        }
    }

    public int getFieldCount()
    {
        return 1;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP)
			return true;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandlerEnergion(this, 6, 0);
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.UP)
			return (T)invHandler;
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return (T)this;
		return super.getCapability(capability, facing);
	}

	@Override
	public int getOutAirTemperature() {
		return temperature;
	}

}
