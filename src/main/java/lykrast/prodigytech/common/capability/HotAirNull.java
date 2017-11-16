package lykrast.prodigytech.common.capability;

import net.minecraft.tileentity.TileEntity;

/**
 * A default implementation that always returns 0.
 * @author Lykrast
 */
public class HotAirNull implements IHotAir {

	@Override
	public int getOutAirTemperature() {
		return 0;
	}

}
