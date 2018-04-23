package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerHeatSawmill;
import lykrast.prodigytech.common.tileentity.TileHeatSawmill;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiHeatSawmill extends GuiHotAirMachineSimple {
	public static final ResourceLocation GUI = new ResourceLocation(ProdigyTech.MODID, "textures/gui/heat_sawmill.png");
	
	public GuiHeatSawmill(InventoryPlayer playerInv, TileHeatSawmill tile) {
		super(playerInv, tile, new ContainerHeatSawmill(playerInv, tile));
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
            i = Config.heatSawmillProcessTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

}
