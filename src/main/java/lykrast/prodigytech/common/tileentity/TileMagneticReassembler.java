package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.recipe.MagneticReassemblerManager;

public class TileMagneticReassembler extends TileHotAirMachineSimpleManaged {
    public TileMagneticReassembler() {
		super(MagneticReassemblerManager.INSTANCE, 0.75F);
	}
    
    @Override
    protected boolean isTooCool() {
    	return hotAir.getInAirTemperature() < 125;
    }

	@Override
	public String getName() {
		return super.getName() + "magnetic_reassembler";
	}
	
	@Override
	protected int getProcessSpeed()
	{
		return (int) (hotAir.getInAirTemperature() / 12.5);
	}

}
