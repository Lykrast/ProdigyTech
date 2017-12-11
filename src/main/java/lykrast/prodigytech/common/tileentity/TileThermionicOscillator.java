package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.CapabilityThermionicOscillation;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.capability.IThermionicOscillation;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

public class TileThermionicOscillator extends TileEntity implements IHotAir, IThermionicOscillation, ITickable {
	private EnumFacing facing;
	/** The temperature that will come out of the machine */
	private int temperatureOut;
	/** The current emitted power */
	private int power;
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return true;
		else if(capability==CapabilityThermionicOscillation.THERMIONIC_OSCILLATION && facing == this.facing && !world.isBlockPowered(pos))
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && facing == EnumFacing.UP)
			return (T)this;
		else if(capability==CapabilityThermionicOscillation.THERMIONIC_OSCILLATION && facing == this.facing && !world.isBlockPowered(pos))
			return (T)this;
		return super.getCapability(capability, facing);
	}

	@Override
	public int getOutAirTemperature() {
		return temperatureOut;
	}

	@Override
	public int getOscillationPower() {
		return power;
	}

	@Override
	public void update() {
		int temperature = TemperatureHelper.getBlockTemp(world, pos.down());
		boolean previous = power > 0;
		boolean next = !world.isBlockPowered(pos) && temperature > 125;
		if (next)
		{
			temperatureOut = (int) (temperature * 0.75F);
			power = (int) (temperature / 12.5);
		}
		else
		{
			temperatureOut = temperature;
			power = 0;
		}
		
		if (next != previous)
		{
            BlockHotAirMachine.setState(next, this.world, this.pos);
		}
	}
	
	public void setFacing(EnumFacing face)
	{
		facing = face;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        facing = EnumFacing.VALUES[compound.getInteger("Facing")];
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Facing", (short)facing.ordinal());

        return compound;
    }

}
