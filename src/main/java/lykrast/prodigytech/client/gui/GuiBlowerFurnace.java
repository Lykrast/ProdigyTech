package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerBlowerFurnace;
import lykrast.prodigytech.common.tileentity.TileBlowerFurnace;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.MathHelper;

public class GuiBlowerFurnace extends GuiHotAirMachineSimple {
	public GuiBlowerFurnace(InventoryPlayer playerInv, TileBlowerFurnace tile) {
		super(playerInv, tile, new ContainerBlowerFurnace(playerInv, tile));
	}

	@Override
	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.blowerFurnaceProcessTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

}
