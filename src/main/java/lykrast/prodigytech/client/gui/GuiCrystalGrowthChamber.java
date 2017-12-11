package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerCrystalGrowthChamber;
import lykrast.prodigytech.common.tileentity.TileCrystalGrowthChamber;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiCrystalGrowthChamber extends GuiInventory {

	public static final ResourceLocation GUI = new ResourceLocation(ProdigyTech.MODID, "textures/gui/crystal_growth_chamber.png");

	protected final IInventory playerInventory;
	protected final TileCrystalGrowthChamber tile;
	protected static final String NORTH_UNLOCALIZED = "container.prodigytech.crystal_growth_chamber.north";
	protected static final String SOUTH_UNLOCALIZED = "container.prodigytech.crystal_growth_chamber.south";
	protected static final String EAST_UNLOCALIZED = "container.prodigytech.crystal_growth_chamber.east";
	protected static final String WEST_UNLOCALIZED = "container.prodigytech.crystal_growth_chamber.west";
	protected static final String DYELLOW_UNLOCALIZED = "container.prodigytech.crystal_growth_chamber.danger.yellow";
	protected static final String DRED_UNLOCALIZED = "container.prodigytech.crystal_growth_chamber.danger.red";
	protected final String north, south, east, west, dangerYellow, dangerRed;

	public GuiCrystalGrowthChamber(InventoryPlayer playerInv, TileCrystalGrowthChamber tile) {
		super(new ContainerCrystalGrowthChamber(playerInv, tile));
		
		playerInventory = playerInv;
		this.tile = tile;
		
		this.xSize = 176;
		this.ySize = 187;
		north = I18n.format(NORTH_UNLOCALIZED);
		south = I18n.format(SOUTH_UNLOCALIZED);
		east = I18n.format(EAST_UNLOCALIZED);
		west = I18n.format(WEST_UNLOCALIZED);
		dangerYellow = I18n.format(DYELLOW_UNLOCALIZED);
		dangerRed = I18n.format(DRED_UNLOCALIZED);
	}

	protected int getProcessLeftScaled(int pixels)
    {
        int i = tile.getField(1);

        if (i == 0)
        {
            i = Config.soldererProcessTime * 10;
        }
        
        int j = MathHelper.clamp(i - tile.getField(0), 0, i);

        return j * pixels / i;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		//Oscillations
	    //North
	    int l = getFieldScaled(2, 18, 0, 20);
	    this.drawTexturedModalRect(guiLeft + 79, guiTop + 16, 176, 0, 18, l);
	    //South
	    l = getFieldScaled(3, 18, 0, 20);
	    this.drawTexturedModalRect(guiLeft + 79, guiTop + 52 + (18 - l), 194, (18 - l), 18, l);
	    //East
	    l = getFieldScaled(4, 18, 0, 20);
	    this.drawTexturedModalRect(guiLeft + 97 + (18 - l), guiTop + 34, 194 + (18 - l), 18, l, 18);
	    //West
	    l = getFieldScaled(5, 18, 0, 20);
	    this.drawTexturedModalRect(guiLeft + 61, guiTop + 34, 176, 18, l, 18);
	
//	    if (TileCrystalGrowthChamber.isProcessing(tile))
//	    {
//	        int k = getProcessLeftScaled(24);
//	        this.drawTexturedModalRect(guiLeft + 79, guiTop + 35, 176, 0, k, 17);
//	    }
//	
//	
//	    //Gold
//	    int m = getFieldScaled(3, 52, 0, Config.soldererMaxGold);
//	    this.drawTexturedModalRect(guiLeft + 49, guiTop + 17 + (52 - m), 176, 35 + (52 - m), 4, m);
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
//		if (x >= guiLeft + 82 && x <= guiLeft + 100 && y >= guiTop + 52 && y <= guiTop + 70)
//		{
//        	String tooltip = String.format(temperature, tile.getField(2));
//            this.drawHoveringText(ImmutableList.of(tooltip), x, y, fontRenderer);
//		}
//        else if (x >= guiLeft + 49 && x <= guiLeft + 53 && y >= guiTop + 19 && y <= guiTop + 70)
//        {
//            this.drawHoveringText(getGoldString(tile.getField(3)), x, y, fontRenderer);
//        }
	}

}