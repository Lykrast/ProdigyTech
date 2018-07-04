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
import lykrast.prodigytech.common.init.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryAutomation {
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(buildMap(), ProdigyTechGuide.prefix("category.automation"), new ItemStack(ModBlocks.linearExtractor));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.automation." + str);
	}
	
	private static Map<ResourceLocation, EntryAbstract> buildMap() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
		
		List<IPage> linear = new ArrayList<>();
		linear.add(GuideUtil.textPage(prefix("linear.content")));
		linear.add(GuideUtil.recipePage("machine/linear_extractor"));
		entries.put(new ResourceLocation(GuideUtil.getName(ModBlocks.linearExtractor)), 
				new Entry(linear, GuideUtil.getName(ModBlocks.linearExtractor)));
		
		List<IPage> funneling = new ArrayList<>();
		funneling.add(GuideUtil.textPage(prefix("funnelling.content")));
		funneling.add(GuideUtil.recipePage("machine/funnelling_extractor"));
		funneling.add(GuideUtil.recipePage("materials/ferramic_gear"));
		entries.put(new ResourceLocation(GuideUtil.getName(ModBlocks.funnellingExtractor)), 
				new Entry(funneling, GuideUtil.getName(ModBlocks.funnellingExtractor)));
		
		return entries;
	}
}
