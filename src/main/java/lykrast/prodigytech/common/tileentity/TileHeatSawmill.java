package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.recipe.HeatSawmillManager;

public class TileHeatSawmill extends TileHotAirMachineSecondaryManaged {
    public TileHeatSawmill() {
		super(HeatSawmillManager.INSTANCE, 0.8F);
	}

	@Override
	public String getName() {
		return super.getName() + "heat_sawmill";
	}

	@Override
	protected int getProcessSpeed() {
		return hotAir.getInAirTemperature() / 8;
	}

}
