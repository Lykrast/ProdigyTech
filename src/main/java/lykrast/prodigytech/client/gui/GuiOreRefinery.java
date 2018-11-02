package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerOreRefinery;
import lykrast.prodigytech.common.tileentity.TileOreRefinery;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiOreRefinery extends GuiHotAirMachineSimple {
	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/hot_air_secondary_output.png");
	
	public GuiOreRefinery(InventoryPlayer playerInv, TileOreRefinery tile) {
		super(playerInv, tile, new ContainerOreRefinery(playerInv, tile), 125);
	}
	
	@Override
	protected ResourceLocation getGUI() {
		return GUI;
	}

	@Override
	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.oreRefineryProcessTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

}
