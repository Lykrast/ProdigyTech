package lykrast.prodigytech.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.gui.ContainerAtomicReshaper;
import lykrast.prodigytech.common.tileentity.TileAtomicReshaper;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiAtomicReshaper extends GuiInventory {

	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/atomic_reshaper.png");

	protected final IInventory playerInventory;
	protected final TileAtomicReshaper tile;
	protected static final String TEMPERATURE_UNLOCALIZED = "container.prodigytech.temperature";
	protected static final String TEMPERATURE_OUT_UNLOCALIZED = "container.prodigytech.temperature.output";
	protected static final String AMOUNT_UNLOCALIZED = "container.prodigytech.atomic_reshaper.primordium.amount";
	protected static final String EMPTY_UNLOCALIZED = "container.prodigytech.atomic_reshaper.primordium.empty";
	protected final String temperature, temperatureOut, amount, empty;

	public GuiAtomicReshaper(InventoryPlayer playerInv, TileAtomicReshaper tile) {
		super(new ContainerAtomicReshaper(playerInv, tile));
		
		playerInventory = playerInv;
		this.tile = tile;
		
		this.xSize = 176;
		this.ySize = 166;
		temperature = I18n.format(TEMPERATURE_UNLOCALIZED, "%d");
		temperatureOut = I18n.format(TEMPERATURE_OUT_UNLOCALIZED, "%d");
		amount = I18n.format(AMOUNT_UNLOCALIZED, "%d");
		empty = I18n.format(EMPTY_UNLOCALIZED);
	}

	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = 2000;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	
	    if (TileAtomicReshaper.isProcessing(tile))
	    {
	        int k = getProcessLeftScaled(48);
	        this.drawTexturedModalRect(guiLeft + 67, guiTop + 35, 176, 0, k, 17);
	    }
	
	    //Heat
	    int l = getFieldScaled(2, 17, 30, 250);
	    this.drawTexturedModalRect(guiLeft + 43, guiTop + 52 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	    //Heat output
	    l = getFieldScaled(3, 17, 30, 250);
	    this.drawTexturedModalRect(guiLeft + 43, guiTop + 16 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	
	    //Primordium
	    l = getFieldScaled(4, 52, 0, Config.atomicReshaperMaxPrimordium * 100);
	    this.drawTexturedModalRect(guiLeft + 37, guiTop + 17 + (52 - l), 176, 35 + (52 - l), 4, l);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = tile.getDisplayName().getUnformattedText();
	    this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	    this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	private int getFieldScaled(int index, int pixels, int min, int max) {
	    int temp = MathHelper.clamp(tile.getField(index), min, max) - min;
	    int interval = max - min;
	    return temp != 0 && interval != 0 ? temp * pixels / interval : 0;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderToolTips(mouseX, mouseY);
	}

	private void renderToolTips(int x, int y) {
		if (x >= guiLeft + 43 && x <= guiLeft + 61)
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
        else if (x >= guiLeft + 37 && x <= guiLeft + 41 && y >= guiTop + 19 && y <= guiTop + 70)
        {
            this.drawHoveringText(getPrimordiumString(tile.getField(4)), x, y, fontRenderer);
        }
	}
	
	private List<String> getPrimordiumString(int amount)
	{
		List<String> list = new ArrayList<>();
		
		if (amount == 0) list.add(empty);
		else list.add(String.format(this.amount, amount));
		
		return list;
	}

}