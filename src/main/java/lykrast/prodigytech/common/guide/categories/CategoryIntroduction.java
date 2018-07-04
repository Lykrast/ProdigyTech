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

public class CategoryIntroduction {
	private static final Map<ResourceLocation, EntryAbstract> ENTRIES = new LinkedHashMap<>();
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(ENTRIES, ProdigyTechGuide.prefix("category.introduction"), new ItemStack(ModBlocks.explosionFurnace));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.introduction." + str);
	}

	public static void buildMap() {		
		List<IPage> gettingStarted = new ArrayList<>();
		gettingStarted.add(GuideUtil.textPage(prefix("gettingstarted.content")));
		ENTRIES.put(new ResourceLocation(prefix("gettingstarted")), 
				new Entry(gettingStarted, prefix("gettingstarted")));
		
		List<IPage> explosionFurnace = new ArrayList<>();
		explosionFurnace.add(GuideUtil.textPage(prefix("explosionfurnace.content1")));
		explosionFurnace.add(GuideUtil.recipePage("machine/explosion_furnace"));
		explosionFurnace.add(GuideUtil.textPage(prefix("explosionfurnace.content2")));
		explosionFurnace.add(GuideUtil.textPage(prefix("explosionfurnace.content3")));
		explosionFurnace.add(GuideUtil.textPage(prefix("explosionfurnace.content4")));
		ENTRIES.put(new ResourceLocation(GuideUtil.getName(ModBlocks.explosionFurnace)), 
				new Entry(explosionFurnace, GuideUtil.getName(ModBlocks.explosionFurnace)));
		
		List<IPage> explosionFurnaceAdvanced = new ArrayList<>();
		explosionFurnaceAdvanced.add(GuideUtil.textPage(prefix("explosionfurnace.advanced.content1")));
		explosionFurnaceAdvanced.add(GuideUtil.textPage(prefix("explosionfurnace.advanced.content2")));
		explosionFurnaceAdvanced.add(GuideUtil.textPage(prefix("explosionfurnace.advanced.content3")));
		ENTRIES.put(new ResourceLocation(prefix("explosionfurnace.advanced")), 
				new Entry(explosionFurnaceAdvanced, prefix("explosionfurnace.advanced")));
	}
}
