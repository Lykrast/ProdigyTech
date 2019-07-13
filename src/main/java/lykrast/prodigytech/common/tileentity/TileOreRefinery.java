package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.recipe.OreRefineryManager;

public class TileOreRefinery extends TileHotAirMachineSecondaryManaged {
    public TileOreRefinery() {
		super(OreRefineryManager.INSTANCE, 0.75F);
	}
    
    @Override
    protected boolean isTooCool() {
    	return hotAir.getInAirTemperature() < 125;
    }

	@Override
	public String getName() {
		return super.getName() + "ore_refinery";
	}
	
	@Override
	protected int getProcessSpeed() {
		return (int) (hotAir.getInAirTemperature() / 12.5);
	}

}
