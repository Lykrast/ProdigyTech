package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.recipe.OreRefineryManager;
import lykrast.prodigytech.common.tileentity.TileOreRefinery;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerOreRefinery extends ContainerMachineHotAirSecondary<TileOreRefinery> {    
	public ContainerOreRefinery(InventoryPlayer userInv, TileOreRefinery tile) {
		super(userInv, tile, OreRefineryManager.INSTANCE);
	}

}
