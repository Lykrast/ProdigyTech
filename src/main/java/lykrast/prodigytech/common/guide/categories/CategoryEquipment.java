package lykrast.prodigytech.common.guide.categories;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import lykrast.prodigytech.common.guide.GuideUtil;
import lykrast.prodigytech.common.guide.ProdigyTechGuide;
import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryEquipment {
	private static final Map<ResourceLocation, EntryAbstract> ENTRIES = new LinkedHashMap<>();
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(ENTRIES, ProdigyTechGuide.prefix("category.equipment"), new ItemStack(ModItems.ferramicHandbow));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.equipment." + str);
	}
	
	public static void buildMap() {
		List<IPage> handbow = new ArrayList<>();
		handbow.add(GuideUtil.textPage(prefix("handbow.content")));
		handbow.add(GuideUtil.recipePage("tools/ferramic_handbow"));
		ENTRIES.put(new ResourceLocation(GuideUtil.getName(ModItems.ferramicHandbow)), 
				new Entry(handbow, GuideUtil.getName(ModItems.ferramicHandbow)));
	}
}
