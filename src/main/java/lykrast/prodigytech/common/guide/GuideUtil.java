package lykrast.prodigytech.common.guide;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.Block;

public class GuideUtil {
	private GuideUtil() {}
	
	public static String getBlockName(Block block) {
		return block.getUnlocalizedName() + ".name";
	}
	
	public static IPage textPage(String content) {
		return new PageText(content);
	}
	
	public static IPage recipePage(String path) {
		return new PageJsonRecipe(ProdigyTech.resource(path));
	}
}
