package lykrast.prodigytech.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public final class TheOneProbeRenderer {
	private TheOneProbeRenderer() {}

	//Code from the TOP internals to avoid depending on it
	//https://github.com/McJtyMods/TheOneProbe/blob/1.12/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java
	public static void renderText(String txt, int x, int y, Minecraft mc) {
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 0.0F, 32.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableLighting();
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();

		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		mc.fontRenderer.drawStringWithShadow(txt, x, y, 16777215);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.enableBlend();

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
	}
	
	public static void renderText(String txt, int x, int y) {
		renderText(txt, x, y, Minecraft.getMinecraft());
	}
	
	public static void renderTextFormatted(String txt, int value, int x, int y) {
		renderText(I18n.format(txt, value), x, y, Minecraft.getMinecraft());
	}
	
	public static int getWidth(String text) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }
	
	public static int getWidthFormatted(String txt, int value) {
        return getWidth(I18n.format(txt, value));
    }
}
