package lykrast.prodigytech.common.guide;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageText;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GuideUtil {
	private GuideUtil() {}
	
	public static String getName(Block block) {
		return block.getUnlocalizedName() + ".name";
	}
	public static String getName(Item item) {
		return item.getUnlocalizedName() + ".name";
	}
	
	public static IPage textPage(String content) {
		return new PageText(content);
	}
	
	public static IPage recipePage(String path) {
		return new PageIRecipe(ForgeRegistries.RECIPES.getValue(ProdigyTech.resource(path)));
	}
}
