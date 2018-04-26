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

public class CategoryHotAir {
	
	public static CategoryAbstract build() {
		return new CategoryItemStack(buildMap(), ProdigyTechGuide.prefix("category.hotair"), new ItemStack(ModBlocks.aeroheaterSolid));
	}
	
	private static String prefix(String str) {
		return ProdigyTechGuide.prefix("entry.hotair." + str);
	}
	
	private static Map<ResourceLocation, EntryAbstract> buildMap() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
		
		List<IPage> mechanics = new ArrayList<>();
		mechanics.add(GuideUtil.textPage(prefix("mechanics.content1")));
		mechanics.add(GuideUtil.textPage(prefix("mechanics.content2")));
		mechanics.add(GuideUtil.textPage(prefix("mechanics.content3")));
		mechanics.add(GuideUtil.textPage(prefix("mechanics.content4")));
		mechanics.add(GuideUtil.recipePage("machine/air_funnel"));
		entries.put(new ResourceLocation(prefix("mechanics")), 
				new Entry(mechanics, prefix("mechanics")));
		
		List<IPage> aeroheaters = new ArrayList<>();
		aeroheaters.add(GuideUtil.textPage(prefix("aeroheaters.content")));
		entries.put(new ResourceLocation(prefix("aeroheaters")), 
				new Entry(aeroheaters, prefix("aeroheaters")));
		
		List<IPage> aeroheatersMagmatic = new ArrayList<>();
		aeroheatersMagmatic.add(GuideUtil.textPage(prefix("aeroheaters.magmatic.content")));
		aeroheatersMagmatic.add(GuideUtil.recipePage("machine/magmatic_aeroheater"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.aeroheaterMagmatic)), 
				new Entry(aeroheatersMagmatic, GuideUtil.getBlockName(ModBlocks.aeroheaterMagmatic)));
		
		List<IPage> aeroheatersSolid = new ArrayList<>();
		aeroheatersSolid.add(GuideUtil.textPage(prefix("aeroheaters.solid.content")));
		aeroheatersSolid.add(GuideUtil.recipePage("machine/solid_fuel_aeroheater"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.aeroheaterSolid)), 
				new Entry(aeroheatersSolid, GuideUtil.getBlockName(ModBlocks.aeroheaterSolid)));
		
		List<IPage> machinesRotaryGrinder = new ArrayList<>();
		machinesRotaryGrinder.add(GuideUtil.textPage(prefix("machines.grinder.content")));
		machinesRotaryGrinder.add(GuideUtil.recipePage("machine/rotary_grinder"));
		machinesRotaryGrinder.add(GuideUtil.recipePage("materials/ferramic_gear"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.rotaryGrinder)), 
				new Entry(machinesRotaryGrinder, GuideUtil.getBlockName(ModBlocks.rotaryGrinder)));
		
		List<IPage> machinesBlowerFurnace = new ArrayList<>();
		machinesBlowerFurnace.add(GuideUtil.textPage(prefix("machines.furnace.content")));
		machinesBlowerFurnace.add(GuideUtil.recipePage("machine/blower_furnace"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.blowerFurnace)), 
				new Entry(machinesBlowerFurnace, GuideUtil.getBlockName(ModBlocks.blowerFurnace)));
		
		List<IPage> machinesHeatSawmill = new ArrayList<>();
		machinesHeatSawmill.add(GuideUtil.textPage(prefix("machines.sawmill.content")));
		machinesHeatSawmill.add(GuideUtil.recipePage("machine/heat_sawmill"));
		machinesHeatSawmill.add(GuideUtil.recipePage("materials/ferramic_gear"));
		machinesHeatSawmill.add(GuideUtil.recipePage("decorative/particle_board"));
		machinesHeatSawmill.add(GuideUtil.recipePage("decorative/particle_board_planks"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.heatSawmill)), 
				new Entry(machinesHeatSawmill, GuideUtil.getBlockName(ModBlocks.heatSawmill)));
		
		List<IPage> machinesIncinerator = new ArrayList<>();
		machinesIncinerator.add(GuideUtil.textPage(prefix("machines.incinerator.content")));
		machinesIncinerator.add(GuideUtil.recipePage("machine/incinerator"));
		machinesIncinerator.add(GuideUtil.recipePage("materials/inferno_fuel"));
		machinesIncinerator.add(GuideUtil.recipePage("decorative/ash_bricks"));
		entries.put(new ResourceLocation(GuideUtil.getBlockName(ModBlocks.incinerator)), 
				new Entry(machinesIncinerator, GuideUtil.getBlockName(ModBlocks.incinerator)));
		
		return entries;
	}
}
