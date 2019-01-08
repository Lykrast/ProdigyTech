package lykrast.prodigytech.client.gui;

import lykrast.prodigytech.common.gui.ContainerEnergionTool;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiEnergionTool extends GuiInventory {
	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/energion_tool.png");
    private final IInventory playerInventory;
    private final ItemStack tool;

	public GuiEnergionTool(InventoryPlayer playerInv, ItemStack tool) {
		super(new ContainerEnergionTool(playerInv, tool));
		
		playerInventory = playerInv;
		this.tool = tool;
		
		this.xSize = 176;
		this.ySize = 133;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = tool.getDisplayName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

}
