package lykrast.prodigytech.common.compat;

import java.util.function.Function;

import lykrast.prodigytech.common.tileentity.IProcessing;
import lykrast.prodigytech.common.tileentity.TileAeroheaterTartaric;
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
			//Tartaric gets special treatment
			if (te instanceof TileAeroheaterTartaric) {
				TileAeroheaterTartaric tartaric = (TileAeroheaterTartaric)te;
				if (tartaric.isBurningSomething()) {
					probeInfo.progress(tartaric.getBurnLeft(), tartaric.getBurnMax(), probeInfo.defaultProgressStyle().height(6).showText(false).filledColor(0xFFE19900).alternateFilledColor(0xFFE19900));
					probeInfo.progress(tartaric.getStokerLeft(), tartaric.getStokerMax(), probeInfo.defaultProgressStyle().height(6).showText(false).filledColor(0xFF78ACC8).alternateFilledColor(0xFF78ACC8));
				}
			}
			else if (te instanceof IProcessing) {
				IProcessing casted = (IProcessing)te;
				if (casted.isProcessing()) {
					int amount = casted.invertDisplay()
							? 100*casted.getProgressLeft() / casted.getMaxProgress()
							: 100*(casted.getMaxProgress() - casted.getProgressLeft()) / casted.getMaxProgress();
					probeInfo.progress(amount, 100,
							probeInfo.defaultProgressStyle().height(12).suffix("%"));
				}
			}
		}
		
	}

}
