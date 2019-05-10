package lykrast.prodigytech.client.gui;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.gui.ContainerPrimordialisReactor;
import lykrast.prodigytech.common.tileentity.TilePrimordialisReactor;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.TooltipUtil;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiPrimordialisReactor extends GuiInventory {

	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/primordialis_reactor.png");

	protected final IInventory playerInventory;
	protected final TilePrimordialisReactor tile;

	public GuiPrimordialisReactor(InventoryPlayer playerInv, TilePrimordialisReactor tile) {
		super(new ContainerPrimordialisReactor(playerInv, tile));
		
		playerInventory = playerInv;
		this.tile = tile;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	
		//Cycle
	    if (TilePrimordialisReactor.isProcessing(tile))
	    {
	        int k = getFieldScaled(0, 28, 0, Config.primordialisReactorCycleTime * 10);
		    this.drawTexturedModalRect(guiLeft + 63, guiTop + 30 + (28 - k), 176, 35 + (28 - k), 12, k + 1);
	    }
	
	    //Input air
	    int l = getFieldScaled(2, 17, 30, 250);
	    this.drawTexturedModalRect(guiLeft + 92, guiTop + 52 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	    //Output air
	    l = getFieldScaled(3, 17, 30, 250);
	    this.drawTexturedModalRect(guiLeft + 92, guiTop + 16 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	
	    //Primordium
	    int m = getFieldScaled(1, 62, 0, Config.primordialisReactorRequiredInput);
	    this.drawTexturedModalRect(guiLeft + 77, guiTop + 35, 176, 0, m, 17);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
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
		renderTemperatureToolTip(mouseX, mouseY);
	}

	private void renderTemperatureToolTip(int x, int y) {
		if (x >= guiLeft + 92 && x < guiLeft + 110)
		{
	        if (y >= guiTop + 52 && y < guiTop + 70)
	        {
	        	String tooltip = I18n.format(TooltipUtil.TEMPERATURE_INPUT, tile.getField(2));
	            this.drawHoveringText(ImmutableList.of(tooltip), x, y, fontRenderer);
	        }
	        else if (y >= guiTop + 16 && y < guiTop + 34)
	        {
	        	String tooltip = I18n.format(TooltipUtil.TEMPERATURE_OUT, tile.getField(3));
	            this.drawHoveringText(ImmutableList.of(tooltip), x, y, fontRenderer);
	        }
		}
	}

}