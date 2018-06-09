package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * A hot air powered machine that does very simple recipes (1 input to 1 output).
 * @author Lykrast
 */
public abstract class TileHotAirMachineSimple extends TileHotAirMachine {
	public TileHotAirMachineSimple(float outputMultiplier) {
		super(2, outputMultiplier);
	}
	
	@Override
	protected IItemHandlerModifiable createInventoryHandler() {
		return new ProdigyInventoryHandler(this, 2, 0, 
				new boolean[]{true,false}, 
				new boolean[]{false,true});
	}

}