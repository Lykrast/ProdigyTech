package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileAirFunnel extends TileEntity implements IHotAir {
	
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
		if (world.isBlockPowered(pos)) return 30;
		else return TemperatureHelper.getBlockTemp(world, pos.down());
	}

}
