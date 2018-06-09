package lykrast.prodigytech.common.capability;

import net.minecraft.nbt.NBTTagCompound;

public abstract class HotAirAeroheater extends HotAirChangeable {
	protected int temperatureClock, maxTemperature;
	
	public HotAirAeroheater(int maxTemperature) {
		super();
		this.maxTemperature = maxTemperature;
	}
	
	public void raiseTemperature() {
		if (temperature >= maxTemperature) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature++;
			resetRaiseClock();
		}
	}
	
	public void lowerTemperature() {
		if (temperature <= 30) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature--;
			resetLowerClock();
		}
	}
	
	protected abstract void resetRaiseClock();
	protected abstract void resetLowerClock();

	@Override
	public NBTTagCompound serializeNBT() {
	    NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Temperature", temperature);
        compound.setInteger("TemperatureClock", temperatureClock);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		temperatureClock = nbt.getInteger("TemperatureClock");
	}

}
