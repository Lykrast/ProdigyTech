package lykrast.prodigytech.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHotAir {
	@CapabilityInject(IHotAir.class)
	public static final Capability<IHotAir> HOT_AIR = null;
	
	public static void register()
	{
		//That's not gonna end well...
		CapabilityManager.INSTANCE.register(IHotAir.class, new IStorage<IHotAir>() {
			@Override
			public NBTBase writeNBT(Capability<IHotAir> capability, IHotAir instance, EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(Capability<IHotAir> capability, IHotAir instance, EnumFacing side, NBTBase nbt) {}
		}, HotAirNull::new);
	}
}
