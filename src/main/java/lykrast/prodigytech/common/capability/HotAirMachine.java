package lykrast.prodigytech.common.capability;

import lykrast.prodigytech.common.tileentity.IProcessing;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HotAirMachine extends HotAirChangeable {
	protected IProcessing machine;
	protected int temperatureOut;
	protected float outputMultiplier;
	
	public HotAirMachine(IProcessing machine, float outputMultiplier) {
		super();
		temperatureOut = temperature;
		this.machine = machine;
		this.outputMultiplier = outputMultiplier;
	}
	
	public int getInAirTemperature() {
		return temperature;
	}

	@Override
	public int getOutAirTemperature() {
		return temperatureOut;
	}
	
	public void setOutAirTemperature(int value) {
		temperatureOut = value;
	}

	public void updateInTemperature(World world, BlockPos pos) {
		temperature = TemperatureHelper.getBlockTemp(world, pos.down());
	}

	public void updateOutTemperature() {
		if (machine.isProcessing()) temperatureOut = (int) (temperature * outputMultiplier);
		else temperatureOut = temperature;
	}

	@Override
	public NBTTagCompound serializeNBT() {
	    NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Temperature", temperature);
        compound.setInteger("TemperatureOut", temperatureOut);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		temperatureOut = nbt.getInteger("TemperatureOut");
	}

}
