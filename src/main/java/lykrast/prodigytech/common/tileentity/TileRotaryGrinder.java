package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.recipe.RotaryGrinderManager;

public class TileRotaryGrinder extends TileHotAirMachineSimpleManaged {
    public TileRotaryGrinder() {
		super(RotaryGrinderManager.INSTANCE, 0.8F);
	}

	@Override
	public String getName() {
		return super.getName() + "rotary_grinder";
	}
	
	@Override
	protected int getProcessSpeed()
	{
		return hotAir.getInAirTemperature() / 8;
	}

}
