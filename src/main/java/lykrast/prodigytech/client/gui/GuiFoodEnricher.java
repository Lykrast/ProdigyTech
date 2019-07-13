package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerFoodEnricher;
import lykrast.prodigytech.common.tileentity.TileFoodEnricher;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.MathHelper;

public class GuiFoodEnricher extends GuiHotAirMachineSimple {
	public GuiFoodEnricher(InventoryPlayer playerInv, TileFoodEnricher tile) {
		super(playerInv, tile, new ContainerFoodEnricher(playerInv, tile), 125);
	}

	@Override
	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0) i = Config.foodEnricherBaseTime * 10;
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

}
