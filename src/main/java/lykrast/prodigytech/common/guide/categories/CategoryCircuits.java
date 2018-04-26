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

public class CategoryCircuits {
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(buildMap(), ProdigyTechGuide.prefix("category.circuits"), new ItemStack(ModItems.circuit, 1, 1));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.circuits." + str);
	}
	
	private static Map<ResourceLocation, EntryAbstract> buildMap() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
		
		List<IPage> solderer = new ArrayList<>();
		solderer.add(GuideUtil.textPage(prefix("solderer.content1")));
		solderer.add(GuideUtil.textPage(prefix("solderer.content2")));
		solderer.add(GuideUtil.textPage(prefix("solderer.content3")));
		solderer.add(GuideUtil.recipePage("machine/solderer"));
		solderer.add(GuideUtil.recipePage("materials/carbon_plate"));
		solderer.add(GuideUtil.recipePage("materials/circuit_plate"));
		solderer.add(GuideUtil.recipePage("placeholder/pattern_circuit_crude_ini"));
		solderer.add(GuideUtil.recipePage("placeholder/pattern_circuit_refined"));
		solderer.add(GuideUtil.recipePage("placeholder/pattern_circuit_perfected"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.solderer)), 
				new Entry(solderer, GuideUtil.getBlockName(ModBlocks.solderer)));
		
		List<IPage> magneticReassembler = new ArrayList<>();
		magneticReassembler.add(GuideUtil.textPage(prefix("reassembler.content")));
		magneticReassembler.add(GuideUtil.recipePage("machine/magnetic_reassembler"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.magneticReassembler)), 
				new Entry(magneticReassembler, GuideUtil.getBlockName(ModBlocks.magneticReassembler)));
		
		return entries;
	}
}
