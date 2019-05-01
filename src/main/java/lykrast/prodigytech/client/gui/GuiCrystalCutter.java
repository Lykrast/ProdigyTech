package lykrast.prodigytech.client.gui;

import com.google.common.collect.ImmutableList;

import lykrast.prodigytech.common.gui.ContainerCrystalCutter;
import lykrast.prodigytech.common.tileentity.TileCrystalCutter;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.TooltipUtil;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiCrystalCutter extends GuiInventory {

	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/automatic_crystal_cutter.png");

	protected final IInventory playerInventory;
	protected final TileCrystalCutter tile;

	public GuiCrystalCutter(InventoryPlayer playerInv, TileCrystalCutter tile) {
		super(new ContainerCrystalCutter(playerInv, tile));
		
		playerInventory = playerInv;
		this.tile = tile;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.automaticCrystalCutterHarvestTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	
	    if (TileCrystalCutter.isProcessing(tile))
	    {
	        int k = getProcessLeftScaled(24);
	        this.drawTexturedModalRect(guiLeft + 59, guiTop + 35, 176, 0, k, 17);
	    }
	
	    //Heat
	    int l = getFieldScaled(2, 17, 30, 100);
	    this.drawTexturedModalRect(guiLeft + 62, guiTop + 52 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
	    //Heat output
	    l = getFieldScaled(3, 17, 30, 100);
	    this.drawTexturedModalRect(guiLeft + 62, guiTop + 16 + (17 - l), 176, 17 + (17 - l), 18, l + 1);
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
		if (x >= guiLeft + 62 && x < guiLeft + 80)
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