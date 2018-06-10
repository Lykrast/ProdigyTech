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
import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryPrimordium {
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(buildMap(), ProdigyTechGuide.prefix("category.primordium"), new ItemStack(ModItems.primordium));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.primordium." + str);
	}
	
	private static Map<ResourceLocation, EntryAbstract> buildMap() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
		
		List<IPage> reactor = new ArrayList<>();
		reactor.add(GuideUtil.textPage(prefix("primordialis_reactor.content1")));
		reactor.add(GuideUtil.textPage(prefix("primordialis_reactor.content2")));
		reactor.add(GuideUtil.recipePage("machine/primordialis_reactor"));
		entries.put(new ResourceLocation(GuideUtil.getName(ModBlocks.primordialisReactor)), 
				new Entry(reactor, GuideUtil.getName(ModBlocks.primordialisReactor)));
		
		List<IPage> reshaper = new ArrayList<>();
		reshaper.add(GuideUtil.textPage(prefix("atomic_reshaper.content")));
		reshaper.add(GuideUtil.recipePage("machine/atomic_reshaper"));
		entries.put(new ResourceLocation(GuideUtil.getName(ModBlocks.atomicReshaper)), 
				new Entry(reshaper, GuideUtil.getName(ModBlocks.atomicReshaper)));
		
		List<IPage> zorra = new ArrayList<>();
		zorra.add(GuideUtil.textPage(prefix("zorra.content1")));
		zorra.add(GuideUtil.textPage(prefix("zorra.content2")));
		zorra.add(GuideUtil.recipePage("materials/zorrasteel_raw"));
		entries.put(new ResourceLocation(prefix("zorra")), 
				new Entry(zorra, prefix("zorra")));
		
		return entries;
	}
}
