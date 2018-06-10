package lykrast.prodigytech.client.gui;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.tileentity.TileHotAirMachine;
import lykrast.prodigytech.common.tileentity.TileHotAirMachineSimple;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public abstract class GuiHotAirMachineSimple extends GuiInventory {
	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/hot_air_simple_machine.png");

	protected abstract int getProcessLeftScaled(int pixels);

	protected final IInventory playerInventory;
	protected final TileHotAirMachine tile;
	protected static final String TEMPERATURE_UNLOCALIZED = "container.prodigytech.temperature";
	protected static final String TEMPERATURE_OUT_UNLOCALIZED = "container.prodigytech.temperature.output";
	protected final String temperature;
	protected final String temperatureOut;
	
	protected ResourceLocation getGUI() {
		return GUI;
	}

	public GuiHotAirMachineSimple(InventoryPlayer playerInv, TileHotAirMachine tile, Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		playerInventory = playerInv;
		this.tile = tile;
		
		this.xSize = 176;
		this.ySize = 166;
		temperature = I18n.format(TEMPERATURE_UNLOCALIZED, "%d");
		temperatureOut = I18n.format(TEMPERATURE_OUT_UNLOCALIZED, "%d");
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
	
	    int l = getTemperatureScaled(false, 17, 30, 80);
	    this.drawTexturedModalRect(guiLeft + 55, guiTop + 52 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	
	    int m = getTemperatureScaled(true, 17, 30, 80);
	    this.drawTexturedModalRect(guiLeft + 55, guiTop + 16 + (17 - m), 176, 17 + (17 - m), 18, m + 1);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = tile.getDisplayName().getUnformattedText();
	    this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	    this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	protected int getTemperatureScaled(boolean isOut, int pixels, int min, int max) {
		int index = 2;
		if (isOut) index = 3;
		
	    int temp = MathHelper.clamp(tile.getField(index), min, max) - min;
	    int interval = max - min;
	    return temp != 0 && interval != 0 ? temp * pixels / interval : 0;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderTemperatureToolTip(mouseX, mouseY);
	}

	private void renderTemperatureToolTip(int x, int y) {
		if (x >= guiLeft + 55 && x <= guiLeft + 73)
		{
	        if (y >= guiTop + 52 && y <= guiTop + 70)
	        {
	        	String tooltip = String.format(temperature, tile.getField(2));
	            this.drawHoveringText(ImmutableList.of(tooltip), x, y, fontRenderer);
	        }
	        else if (y >= guiTop + 16 && y <= guiTop + 34)
	        {
	        	String tooltip = String.format(temperatureOut, tile.getField(3));
	            this.drawHoveringText(ImmutableList.of(tooltip), x, y, fontRenderer);
	        }
		}
	}

}