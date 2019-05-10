package lykrast.prodigytech.client.gui;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.glu.Project;

import com.google.common.collect.Lists;

import lykrast.prodigytech.common.gui.ContainerZorraAltar;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GuiZorraAltar extends GuiContainer {
	//Mostly copied from the Enchanting Table
	
    /** The ResourceLocation containing the Enchantment GUI texture location */
    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = ProdigyTech.resource("textures/gui/zorra_altar.png");
    /** The ResourceLocation containing the texture for the Book rendered above the enchantment table */
    private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
    /** The ModelBook instance used for rendering the book on the Enchantment table */
    private static final ModelBook MODEL_BOOK = new ModelBook();
    /** The player inventory currently bound to this GuiEnchantment instance. */
    private final InventoryPlayer playerInventory;
    /** A Random instance for use with the enchantment gui */
    private final Random random = new Random();
    private final ContainerZorraAltar container;
    public int ticks;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private ItemStack last = ItemStack.EMPTY;

    public GuiZorraAltar(InventoryPlayer inventory, World worldIn)
    {
        super(new ContainerZorraAltar(inventory, worldIn));
        this.playerInventory = inventory;
        this.container = (ContainerZorraAltar)this.inventorySlots;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(new TextComponentTranslation("container." + ProdigyTech.MODID + ".zorra_altar").getUnformattedText(), 12, 5, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
	public void updateScreen()
    {
        super.updateScreen();
        this.tickBook();
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        for (int k = 0; k < 3; ++k)
        {
            int l = mouseX - (i + 60);
            int i1 = mouseY - (j + 14 + 19 * k);

            if (l >= 0 && i1 >= 0 && l < 108 && i1 < 19 && this.container.enchantItem(this.mc.player, k))
            {
                this.mc.playerController.sendEnchantPacket(this.container.windowId, k);
            }
        }
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
        int midX = (width - xSize) / 2;
        int midY = (height - ySize) / 2;
        this.drawTexturedModalRect(midX, midY, 0, 0, xSize, ySize);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        GlStateManager.viewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
        GlStateManager.translate(-0.34F, 0.23F, 0.0F);
        Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0F, 3.3F, -16.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.scale(5.0F, 5.0F, 5.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
        GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
        float f2 = this.oOpen + (this.open - this.oOpen) * partialTicks;
        GlStateManager.translate((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
        GlStateManager.rotate(-(1.0F - f2) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        float f3 = this.oFlip + (this.flip - this.oFlip) * partialTicks + 0.25F;
        float f4 = this.oFlip + (this.flip - this.oFlip) * partialTicks + 0.75F;
        f3 = (f3 - (float)MathHelper.fastFloor((double)f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float)MathHelper.fastFloor((double)f4)) * 1.6F - 0.3F;

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f3 > 1.0F)
        {
            f3 = 1.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        GlStateManager.enableRescaleNormal();
        MODEL_BOOK.render((Entity)null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.matrixMode(5889);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        EnchantmentNameParts.getInstance().reseedRandomGenerator((long)this.container.xpSeed);
        int leaves = container.getLeafAmount();

        for (int row = 0; row < 3; ++row)
        {
            int i1 = midX + 60;
            int j1 = i1 + 20;
            zLevel = 0.0F;
            mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
            int cost = container.enchantCost[row];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            //Draw empty row
            if (cost == 0) drawTexturedModalRect(i1, midY + 14 + 19 * row, 0, 185, 108, 19);
            else
            {
            	//Glyphs + cost
                String displayCost = Integer.toString(cost);
                int glyphLength = 86 - fontRenderer.getStringWidth(displayCost);
                String glyph = EnchantmentNameParts.getInstance().generateNewRandomName(fontRenderer, glyphLength);
                FontRenderer fontrenderer = mc.standardGalacticFontRenderer;
                int shadowColor = 0x685E4A;
                
                int leafCost = row == 2 ? 3 : container.enchantLvl[row];
                //Icon 0 (left) for new enchants
                //Icon 1 (middle) for upgrading enchants
                //Icon 2 (right) for the mystery option
                int iconIndex = row == 2 ? 2 : (container.enchantLvl[row] == 1 ? 0 : 1);

                if (((leaves < leafCost || mc.player.experienceLevel < cost) && !mc.player.capabilities.isCreativeMode) || container.enchantId[row] == -1) // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
                {
                	//Disabled background
                    drawTexturedModalRect(i1, midY + 14 + 19 * row, 0, 185, 108, 19);
                    //Disabled icon
                    drawTexturedModalRect(i1 + 1, midY + 15 + 19 * row, 16 * iconIndex, 239, 16, 16);
                    fontrenderer.drawSplitString(glyph, j1, midY + 16 + 19 * row, glyphLength, (shadowColor & 16711422) >> 1);
                    shadowColor = 0x407F10;
                }
                else
                {
                    int j2 = mouseX - (midX + 60);
                    int k2 = mouseY - (midY + 14 + 19 * row);

                    if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19)
                    {
                    	//Selected background
                        drawTexturedModalRect(i1, midY + 14 + 19 * row, 0, 204, 108, 19);
                        shadowColor = 0xFFFF80;
                    }
                    else
                    {
                    	//Normal background
                        drawTexturedModalRect(i1, midY + 14 + 19 * row, 0, 166, 108, 19);
                    }

                    //Normal icon
                    drawTexturedModalRect(i1 + 1, midY + 15 + 19 * row, 16 * iconIndex, 223, 16, 16);
                    fontrenderer.drawSplitString(glyph, j1, midY + 16 + 19 * row, glyphLength, shadowColor);
                    shadowColor = 0x80FF20;
                }

                fontrenderer = this.mc.fontRenderer;
                fontrenderer.drawStringWithShadow(displayCost, (float)(j1 + 86 - fontrenderer.getStringWidth(displayCost)), (float)(midY + 16 + 19 * row + 7), shadowColor);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        partialTicks = this.mc.getTickLength();
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        boolean flag = this.mc.player.capabilities.isCreativeMode;
        int leaves = this.container.getLeafAmount();

        for (int j = 0; j < 3; ++j)
        {
            int cost = container.enchantCost[j];
            Enchantment enchantment = Enchantment.getEnchantmentByID(this.container.enchantId[j]);
            int lvl = container.enchantLvl[j];
            int leafCost = j == 2 ? 3 : container.enchantLvl[j];

            if (this.isPointInRegion(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && cost > 0)
            {
                List<String> list = Lists.<String>newArrayList();
                if(enchantment == null) Collections.addAll(list, "", TextFormatting.RED + I18n.format("forge.container.enchant.limitedEnchantability"));
                else
                {
                    if (enchantment != null)
                    {
                    	//Hide the 3rd
                    	if (j == 2) list.add("" + TextFormatting.WHITE + TextFormatting.ITALIC + I18n.format("container.prodigytech.zorra_altar.unknown"));
                    	else list.add(TextFormatting.WHITE + enchantment.getTranslatedName(lvl));
                    }
                    if (!flag)
                    {
                        list.add("");

                        if (this.mc.player.experienceLevel < cost)
                        {
                            list.add(TextFormatting.RED + I18n.format("container.enchant.level.requirement", this.container.enchantCost[j]));
                        }
                        else
                        {
                            String s;

                            if (leafCost == 1)
                            {
                                s = I18n.format("container.prodigytech.zorra_altar.leaves.one");
                            }
                            else
                            {
                                s = I18n.format("container.prodigytech.zorra_altar.leaves.many", leafCost);
                            }

                            TextFormatting textformatting = leaves >= leafCost ? TextFormatting.GRAY : TextFormatting.RED;
                            list.add(textformatting + "" + s);

                            if (container.enchantCost[j] == 1)
                            {
                                s = I18n.format("container.enchant.level.one");
                            }
                            else
                            {
                                s = I18n.format("container.enchant.level.many", container.enchantCost[j]);
                            }

                            list.add(TextFormatting.GRAY + "" + s);
                        }
                    }
                }

                this.drawHoveringText(list, mouseX, mouseY);
                break;
            }
        }
    }

    public void tickBook()
    {
        ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();

        if (!ItemStack.areItemStacksEqual(itemstack, this.last))
        {
            this.last = itemstack;

            while (true)
            {
                this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));

                if (this.flip > this.flipT + 1.0F || this.flip < this.flipT - 1.0F)
                {
                    break;
                }
            }
        }

        ++this.ticks;
        this.oFlip = this.flip;
        this.oOpen = this.open;
        boolean flag = false;

        for (int i = 0; i < 3; ++i)
        {
            if (this.container.enchantCost[i] != 0)
            {
                flag = true;
            }
        }

        if (flag)
        {
            this.open += 0.2F;
        }
        else
        {
            this.open -= 0.2F;
        }

        this.open = MathHelper.clamp(this.open, 0.0F, 1.0F);
        float f1 = (this.flipT - this.flip) * 0.4F;
        f1 = MathHelper.clamp(f1, -0.2F, 0.2F);
        this.flipA += (f1 - this.flipA) * 0.9F;
        this.flip += this.flipA;
    }
}
