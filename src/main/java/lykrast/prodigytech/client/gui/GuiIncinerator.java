package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerIncinerator;
import lykrast.prodigytech.common.tileentity.TileIncinerator;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.MathHelper;

public class GuiIncinerator extends GuiHotAirMachineSimple {
	public GuiIncinerator(InventoryPlayer playerInv, TileIncinerator tile) {
		super(playerInv, tile, new ContainerIncinerator(playerInv, tile), 80);
	}

	@Override
	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.incineratorProcessTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

}
