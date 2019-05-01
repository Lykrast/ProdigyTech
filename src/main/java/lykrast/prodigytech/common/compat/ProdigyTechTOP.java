package lykrast.prodigytech.common.compat;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import lykrast.prodigytech.client.gui.TheOneProbeRenderer;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirMachine;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.tileentity.IProcessing;
import lykrast.prodigytech.common.tileentity.TileAeroheaterTartaric;
import lykrast.prodigytech.common.tileentity.TileWormholeFunnel;
import lykrast.prodigytech.common.util.TooltipUtil;
import lykrast.prodigytech.core.ProdigyTech;
import mcjty.theoneprobe.api.IElement;
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
	private static int elementid;
	public static final String
			WORMHOLE_LINKED = "status.prodigytech.wormhole_linker.linked",
			WORMHOLE_UNLINKED = "status.prodigytech.wormhole_linker.unlinked";

	@Override
	public Void apply(ITheOneProbe probe) {
		elementid = probe.registerElementFactory(TextFormatInt::new);
		probe.registerProvider(new HotAirInfoProvider());
		probe.registerProvider(new ProgressInfoProvider());
		return null;
	}
	
	public static class HotAirInfoProvider implements IProbeInfoProvider {

		@Override
		public String getID() {
			return ProdigyTech.MODID + ":hotair";
		}

		@Override
		public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
			TileEntity te = world.getTileEntity(data.getPos());
			//Special case Wormhole Funnels to show input temp
			if (te instanceof TileWormholeFunnel) {
				TileWormholeFunnel funnel = (TileWormholeFunnel)te;
				//Do linking info while we're at it, even though another provider would make more sense
				probeInfo.text(IProbeInfo.STARTLOC + (funnel.isLinked() ? WORMHOLE_LINKED : WORMHOLE_UNLINKED) + IProbeInfo.ENDLOC);	
				
				if (funnel.isInput()) {
					//For input funnels this is actually their input temp
					if (funnel.getOutAirTemperature() > 0) probeInfo.element(new TextFormatInt(TooltipUtil.TEMPERATURE_INPUT, funnel.getOutAirTemperature()));
				}
				else {
					if (funnel.getOutAirTemperature() > 0) probeInfo.element(new TextFormatInt(TooltipUtil.TEMPERATURE_OUT, funnel.getOutAirTemperature()));
				}
			}
			else if (te != null && te.hasCapability(CapabilityHotAir.HOT_AIR, null)) {
				IHotAir hotair = te.getCapability(CapabilityHotAir.HOT_AIR, null);
				//Check if machine for input
				if (hotair instanceof HotAirMachine) {
					HotAirMachine casted = (HotAirMachine)hotair;
					if (casted.getInAirTemperature() > 0) probeInfo.element(new TextFormatInt(TooltipUtil.TEMPERATURE_INPUT, casted.getInAirTemperature()));
				}
				if (hotair.getOutAirTemperature() > 0) probeInfo.element(new TextFormatInt(TooltipUtil.TEMPERATURE_OUT, hotair.getOutAirTemperature()));
			}
		}
		
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
	
	public static class TextFormatInt implements IElement {
		private String text;
		private int value;
		
		public TextFormatInt(String text, int value) {
	        this.text = text;
	        this.value = value;
	    }

	    public TextFormatInt(ByteBuf buf) {
	    	value = buf.readInt();
	        text = readStringUTF8(buf);
	    }

		@Override
		public void render(int x, int y) {
			TheOneProbeRenderer.renderTextFormatted(text, value, x, y);
		}

		@Override
		public int getWidth() {
			return TheOneProbeRenderer.getWidthFormatted(text, value);
		}

		@Override
		public int getHeight() {
			return 10;
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(value);
			writeStringUTF8(buf, text);
		}

		@Override
		public int getID() {
			return elementid;
		}
	}
	
	//From TOP internals
	//https://github.com/McJtyMods/TheOneProbe/blob/1.12/src/main/java/mcjty/theoneprobe/network/NetworkTools.java
	public static String readStringUTF8(ByteBuf dataIn) {
        int s = dataIn.readInt();
        if (s == -1) {
            return null;
        }
        if (s == 0) {
            return "";
        }
        byte[] dst = new byte[s];
        dataIn.readBytes(dst);
        return new String(dst, java.nio.charset.StandardCharsets.UTF_8);
    }

    public static void writeStringUTF8(ByteBuf dataOut, String str) {
        if (str == null) {
            dataOut.writeInt(-1);
            return;
        }
        byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        dataOut.writeInt(bytes.length);
        if (bytes.length > 0) {
            dataOut.writeBytes(bytes);
        }
    }

}
