package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerMagneticReassembler;
import lykrast.prodigytech.common.tileentity.TileHotAirMachineSimple;
import lykrast.prodigytech.common.tileentity.TileMagneticReassembler;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.MathHelper;

public class GuiMagneticReassembler extends GuiHotAirMachineSimple {
	public GuiMagneticReassembler(InventoryPlayer playerInv, TileMagneticReassembler tile) {
		super(playerInv, tile, new ContainerMagneticReassembler(playerInv, tile));
	}

	@Override
	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.magneticReassemblerProcessTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(getGUI());
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	
	    if (TileHotAirMachineSimple.isProcessing(tile))
	    {
	        int k = getProcessLeftScaled(24);
	        this.drawTexturedModalRect(guiLeft + 79, guiTop + 35, 176, 0, k, 17);
	    }
	
	    int l = getTemperatureScaled(false, 17, 30, 125);
	    this.drawTexturedModalRect(guiLeft + 55, guiTop + 52 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	
	    int m = getTemperatureScaled(true, 17, 30, 125);
	    this.drawTexturedModalRect(guiLeft + 55, guiTop + 16 + (17 - m), 176, 17 + (17 - m), 18, m + 1);
	}

}
