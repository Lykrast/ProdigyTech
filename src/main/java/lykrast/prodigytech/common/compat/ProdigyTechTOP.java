package lykrast.prodigytech.common.compat;

import java.util.function.Function;

import lykrast.prodigytech.common.tileentity.IProcessing;
import lykrast.prodigytech.core.ProdigyTech;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ProdigyTechTOP implements Function<ITheOneProbe, Void> {

	@Override
	public Void apply(ITheOneProbe probe) {
		probe.registerProvider(new ProgressInfoProvider());
		return null;
	}
	
	public static class ProgressInfoProvider implements IProbeInfoProvider {

		@Override
		public String getID() {
			return ProdigyTech.MODID + ":progress";
		}

		@Override
		public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
			TileEntity te = world.getTileEntity(data.getPos());
			if (te instanceof IProcessing) {
				IProcessing casted = (IProcessing)te;
				if (casted.isProcessing()) probeInfo.progress(100*(casted.getMaxProgress() - casted.getProgressLeft()) / casted.getMaxProgress(), 100,
						probeInfo.defaultProgressStyle().suffix("%"));
			}
		}
		
	}

}
