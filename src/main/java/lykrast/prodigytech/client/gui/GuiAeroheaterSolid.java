package lykrast.prodigytech.client.gui;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.gui.ContainerAeroheaterSolid;
import lykrast.prodigytech.common.tileentity.TileAeroheaterSolid;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiAeroheaterSolid extends GuiInventory {
	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/solid_fuel_aeroheater.png");
    private final IInventory playerInventory;
    private final TileAeroheaterSolid tile;
    private static final String TEMPERATURE_OUT_UNLOCALIZED = "container.prodigytech.temperature.output";
    private final String temperature;

	public GuiAeroheaterSolid(InventoryPlayer playerInv, TileAeroheaterSolid tile) {
		super(new ContainerAeroheaterSolid(playerInv, tile));
		
		playerInventory = playerInv;
		this.tile = tile;
		
		this.xSize = 176;
		this.ySize = 166;
		temperature = I18n.format(TEMPERATURE_OUT_UNLOCALIZED, "%d");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (TileAeroheaterSolid.isBurning(tile))
        {
            int k = getBurnLeftScaled(13);
            this.drawTexturedModalRect(guiLeft + 81, guiTop + 37 + (13 - k), 176, (13 - k), 14, k + 1);
        }

        int l = getTemperatureScaled(17, 30, 100);
        this.drawTexturedModalRect(guiLeft + 79, guiTop + 16 + (17 - l), 176, 14 + (17 - l), 18, l + 1);
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tile.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    private int getTemperatureScaled(int pixels, int min, int max)
    {
        int temp = MathHelper.clamp(tile.getField(2), min, max) - min;
        int interval = max - min;
        return temp != 0 && interval != 0 ? temp * pixels / interval : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return tile.getField(0) * pixels / i;
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderTemperatureToolTip(mouseX, mouseY);
	}

    private void renderTemperatureToolTip(int x, int y)
    {
        if (x >= guiLeft + 79 && x <= guiLeft + 97 && y >= guiTop + 16 && y <= guiTop + 34)
        {
        	String tooltip = String.format(temperature, tile.getField(2));
            this.drawHoveringText(ImmutableList.of(tooltip), x, y, fontRenderer);
        }
    }

}
