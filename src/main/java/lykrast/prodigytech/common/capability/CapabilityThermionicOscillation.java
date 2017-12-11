package lykrast.prodigytech.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityThermionicOscillation {
	@CapabilityInject(IThermionicOscillation.class)
	public static final Capability<IThermionicOscillation> THERMIONIC_OSCILLATION = null;
	
	public static void register()
	{
		//That's not gonna end well...
		CapabilityManager.INSTANCE.register(IThermionicOscillation.class, new IStorage<IThermionicOscillation>() {
			@Override
			public NBTBase writeNBT(Capability<IThermionicOscillation> capability, IThermionicOscillation instance, EnumFacing side) {
				return null;
			}

			@Override
			public void readNBT(Capability<IThermionicOscillation> capability, IThermionicOscillation instance, EnumFacing side, NBTBase nbt) {}
		}, ThermionicOscillationNull.class);
	}
}
