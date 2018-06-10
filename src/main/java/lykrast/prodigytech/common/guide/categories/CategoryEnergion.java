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

public class CategoryEnergion {
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(buildMap(), ProdigyTechGuide.prefix("category.energion"), new ItemStack(ModItems.energionDust));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.energion." + str);
	}
	
	private static Map<ResourceLocation, EntryAbstract> buildMap() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
		
		List<IPage> dust = new ArrayList<>();
		dust.add(GuideUtil.textPage(prefix("dust.content")));
		dust.add(GuideUtil.recipePage("materials/energion_dust"));
		entries.put(new ResourceLocation(GuideUtil.getName(ModItems.energionDust)), 
				new Entry(dust, GuideUtil.getName(ModItems.energionDust)));
		
		List<IPage> batteries = new ArrayList<>();
		batteries.add(GuideUtil.textPage(prefix("batteries.content1")));
		batteries.add(GuideUtil.textPage(prefix("batteries.content2")));
		batteries.add(GuideUtil.recipePage("materials/energion_battery_empty"));
		batteries.add(GuideUtil.recipePage("materials/energion_battery"));
		batteries.add(GuideUtil.recipePage("materials/energion_battery_double_empty"));
		batteries.add(GuideUtil.recipePage("materials/energion_battery_double"));
		batteries.add(GuideUtil.recipePage("materials/energion_battery_triple_empty"));
		batteries.add(GuideUtil.recipePage("materials/energion_battery_triple"));
		entries.put(new ResourceLocation(prefix("batteries")), 
				new Entry(batteries, prefix("batteries")));
		
		List<IPage> crystal = new ArrayList<>();
		crystal.add(GuideUtil.textPage(prefix("crystals.content1")));
		crystal.add(GuideUtil.textPage(prefix("crystals.content2")));
		crystal.add(GuideUtil.recipePage("tools/crystal_cutter"));
		entries.put(new ResourceLocation(prefix("crystals")), 
				new Entry(crystal, prefix("crystals")));
		
		List<IPage> aeroheater = new ArrayList<>();
		aeroheater.add(GuideUtil.textPage(prefix("aeroheater.content1")));
		aeroheater.add(GuideUtil.textPage(prefix("aeroheater.content2")));
		aeroheater.add(GuideUtil.recipePage("machine/energion_aeroheater"));
		entries.put(new ResourceLocation(GuideUtil.getName(ModBlocks.aeroheaterEnergion)), 
				new Entry(aeroheater, GuideUtil.getName(ModBlocks.aeroheaterEnergion)));
		
		return entries;
	}
}
