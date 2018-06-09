package lykrast.prodigytech.common.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class HotAirChangeable implements IHotAir, INBTSerializable<NBTTagCompound> {
	protected int temperature;
	
	public HotAirChangeable() {
		temperature = 30;
	}
	
	public void setTemperature(int value) {
		temperature = value;
	}

	@Override
	public int getOutAirTemperature() {
		return temperature;
	}

	@Override
	public NBTTagCompound serializeNBT() {
	    NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Temperature", temperature);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
        temperature = nbt.getInteger("Temperature");
	}

}
