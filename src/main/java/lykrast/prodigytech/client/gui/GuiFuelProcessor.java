package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerFuelProcessor;
import lykrast.prodigytech.common.tileentity.TileFuelProcessor;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.MathHelper;

public class GuiFuelProcessor extends GuiHotAirMachineSimple {
	public GuiFuelProcessor(InventoryPlayer playerInv, TileFuelProcessor tile) {
		super(playerInv, tile, new ContainerFuelProcessor(playerInv, tile), 80);
	}

	@Override
	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.fuelProcessorBaseTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

}
