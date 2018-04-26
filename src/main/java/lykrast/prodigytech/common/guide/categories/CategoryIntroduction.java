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
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import lykrast.prodigytech.common.guide.ProdigyTechGuide;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryIntroduction {
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(buildMap(), ProdigyTechGuide.prefix("category.introduction"), new ItemStack(ModBlocks.explosionFurnace));
	}
	
	private static Map<ResourceLocation, EntryAbstract> buildMap() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
		
		List<IPage> gettingStarted = new ArrayList<>();
		gettingStarted.add(new PageText(prefix("gettingstarted.content")));
		entries.put(new ResourceLocation(prefix("gettingstarted")), new Entry(gettingStarted, prefix("gettingstarted")));
		
		List<IPage> explosionFurnace = new ArrayList<>();
		explosionFurnace.add(new PageText(prefix("explosionfurnace.content1")));
		explosionFurnace.add(new PageJsonRecipe(ProdigyTech.resource("machine/explosion_furnace")));
		explosionFurnace.add(new PageText(prefix("explosionfurnace.content2")));
		explosionFurnace.add(new PageText(prefix("explosionfurnace.content3")));
		entries.put(new ResourceLocation(prefix("explosionfurnace")), new Entry(explosionFurnace, prefix("explosionfurnace")));
		
		List<IPage> explosionFurnaceAdvanced = new ArrayList<>();
		explosionFurnaceAdvanced.add(new PageText(prefix("explosionfurnace.advanced.content1")));
		explosionFurnaceAdvanced.add(new PageText(prefix("explosionfurnace.advanced.content2")));
		explosionFurnaceAdvanced.add(new PageText(prefix("explosionfurnace.advanced.content3")));
		entries.put(new ResourceLocation(prefix("explosionfurnace.advanced")), new Entry(explosionFurnaceAdvanced, prefix("explosionfurnace.advanced")));
		
		
		return entries;
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.introduction." + str);
	}
}
