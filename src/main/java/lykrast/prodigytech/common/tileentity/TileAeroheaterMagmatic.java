package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockAeroheaterMagmatic;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

public class TileAeroheaterMagmatic extends TileEntity implements ITickable, IHotAir {
    /** The current temperature of the heater */
    private int temperature;
    /** A clock to change the speed at which temperature raises */
    private int temperatureClock;
    private boolean active;
    private boolean checkNextTick = false;

	public TileAeroheaterMagmatic() {
		temperature = 30;
		active = false;
		checkNextTick = true;
	}
	
	public void checkActive()
	{
		Block below = world.getBlockState(pos.down()).getBlock();
		if (below == Blocks.LAVA || below == Blocks.FLOWING_LAVA) active = true;
		else active = false;
	}

	@Override
	public void update() {
        boolean flag = world.getBlockState(pos).getValue(BlockAeroheaterMagmatic.ACTIVE);
        boolean flag1 = false;
        
        if (!this.world.isRemote)
        {
        	if (checkNextTick)
        	{
        		checkNextTick = false;
        		checkActive();
        	}
        	
            if (active) raiseTemperature();
            else lowerTemperature();
        	
            if (flag != active)
            {
                flag1 = true;
                BlockAeroheaterMagmatic.setState(active, this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private void raiseTemperature()
	{
		if (temperature >= 80) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature++;
			
			//10 seconds to reach 80 °C (when Draft Furnace starts working)
			temperatureClock = 4;
		}
	}
	
	private void lowerTemperature()
	{
		if (temperature <= 30) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature--;
			
			//20 seconds to cool down fully
			temperatureClock = 8;
		}
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.temperature = compound.getInteger("Temperature");
        this.temperatureClock = compound.getInteger("TemperatureClock");
        checkActive();
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Temperature", (short)this.temperature);
        compound.setInteger("TemperatureClock", (short)this.temperatureClock);

        return compound;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return (T)this;
		return super.getCapability(capability, facing);
	}

	@Override
	public int getOutAirTemperature() {
		return temperature;
	}

}
