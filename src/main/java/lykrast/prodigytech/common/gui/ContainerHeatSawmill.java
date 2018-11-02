package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.HeatSawmillManager;
import lykrast.prodigytech.common.tileentity.TileHeatSawmill;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerHeatSawmill extends ContainerMachineHotAirSecondary<TileHeatSawmill> {    
	public ContainerHeatSawmill(InventoryPlayer userInv, TileHeatSawmill tile) {
		super(userInv, tile, HeatSawmillManager.INSTANCE);
	}

}
