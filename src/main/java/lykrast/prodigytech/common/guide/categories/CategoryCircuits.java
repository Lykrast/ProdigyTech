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
	private static final Map<ResourceLocation, EntryAbstract> ENTRIES = new LinkedHashMap<>();
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(ENTRIES, ProdigyTechGuide.prefix("category.circuits"), new ItemStack(ModItems.circuitRefined));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.circuits." + str);
	}
	
	public static void buildMap() {
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
		ENTRIES.put(new ResourceLocation(GuideUtil.getName(ModBlocks.solderer)), 
				new Entry(solderer, GuideUtil.getName(ModBlocks.solderer)));
		
		List<IPage> magneticReassembler = new ArrayList<>();
		magneticReassembler.add(GuideUtil.textPage(prefix("reassembler.content")));
		magneticReassembler.add(GuideUtil.recipePage("machine/magnetic_reassembler"));
		ENTRIES.put(new ResourceLocation(GuideUtil.getName(ModBlocks.magneticReassembler)), 
				new Entry(magneticReassembler, GuideUtil.getName(ModBlocks.magneticReassembler)));
	}
}
