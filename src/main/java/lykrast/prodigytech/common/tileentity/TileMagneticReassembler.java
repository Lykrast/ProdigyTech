package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.recipe.MagneticReassemblerManager;

public class TileMagneticReassembler extends TileHotAirMachineSimpleManaged {
    public TileMagneticReassembler() {
		super(MagneticReassemblerManager.INSTANCE);
	}
    
    @Override
    protected boolean isTooCool() {
    	return temperature < 125;
    }

	@Override
	public String getName() {
		return super.getName() + "magnetic_reassembler";
	}
	
	@Override
	protected int getProcessSpeed()
	{
		return (int) (temperature / 12.5);
	}

	@Override
	protected void updateOutTemperature() {
		if (isProcessing()) temperatureOut = (int) (temperature * 0.75F);
		else temperatureOut = temperature;
	}

}
