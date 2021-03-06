package lykrast.prodigytech.common.init;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.*;
import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModItems {
	public static Item ferramicIngot, ferramicNugget, ferramicGear,
		ash, purifiedFood, flour, sawdust, meatGround, meatPatty,
		coalDust, ferramicDust, ferramicDustTiny, ironDust, ironDustTiny, goldDust, goldDustTiny, diamondDust, emeraldDust, quartzDust,
		carbonPlate, enrichedFuel, infernoFuel, infernoCrystal, sugarCube,
		circuitPlate, circuitCrude, circuitRefined, circuitPerfected, 
		patternCircuitCrude, patternCircuitRefined, patternCircuitPerfected,
		heatCapacitor125, heatCapacitor250, heatCapacitor500, heatCapacitor1000,
		fuelPellet1, fuelPellet4, fuelPellet16, fuelPellet64,
		energionCrystalSeed, energionDust,
		crystalCutter, wormholeLinker,
		ferramicHandbow,
		primordium, aeternusCrystal, mysteryTreat,
		zorraLeaf, zorrasteelRaw, zorrasteelIngot, tartaricStoker,
		zorrasteelSword, zorrasteelHandbow;
	public static ToolMaterial materialZorrasteel;
	private static List<Item> itemList = new ArrayList<>();
	static List<Item> itemBlockList = new ArrayList<>();
	
	static
	{
		//Materials
		ferramicIngot = initItem(new Item(), "ferramic_ingot");
		ferramicNugget = initItem(new Item(), "ferramic_nugget");
		ferramicGear = initItem(new Item(), "ferramic_gear");
		ash = initItem(new Item(), "ash");
		//ItemFood sets the creative tab in constructor so have to override here
		purifiedFood = initItem(new ItemFoodPurified(), "purified_food", null).setCreativeTab(null);

		//Grinder
		flour = initItem(new Item(), "flour");
		sawdust = initItem(new Item(), "sawdust");
		meatGround = initItem(new ItemFood(2, 0.3F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F), "meat_ground");
		meatPatty = initItem(new ItemFood(8, 1.0F, true), "meat_patty");
		coalDust = initItem(new ItemFuel(1600), "coal_dust");
		ferramicDust = initItem(new Item(), "ferramic_dust");
		ferramicDustTiny = initItem(new Item(), "ferramic_dust_tiny");
		ironDust = initItem(new Item(), "iron_dust");
		ironDustTiny = initItem(new Item(), "iron_dust_tiny");
		goldDust = initItem(new Item(), "gold_dust");
		goldDustTiny = initItem(new Item(), "gold_dust_tiny");
		diamondDust = initItem(new Item(), "diamond_dust");
		emeraldDust = initItem(new Item(), "emerald_dust");
		quartzDust = initItem(new Item(), "quartz_dust");
		
		carbonPlate = initItem(new Item(), "carbon_plate");
		enrichedFuel = initItem(new ItemFuel(1600), "enriched_fuel");
		infernoFuel = initItem(new ItemFuel(3600), "inferno_fuel");
		
		//Magnetic Reassembler
		infernoCrystal = initItem(new ItemFuel(5400), "inferno_crystal");
		sugarCube = initItem(new ItemSugarCube(1, 0.2F), "sugar_cube");
		
		//Solderer
		circuitPlate = initItem(new Item(), "circuit_plate");
		circuitCrude = initItem(new Item(), "circuit_crude");
		circuitRefined = initItem(new Item(), "circuit_refined");
		circuitPerfected = initItem(new Item(), "circuit_perfected");
		patternCircuitCrude = initItem(new Item().setMaxStackSize(1), "pattern_circuit_crude");
		patternCircuitRefined = initItem(new Item().setMaxStackSize(1), "pattern_circuit_refined");
		patternCircuitPerfected = initItem(new Item().setMaxStackSize(1), "pattern_circuit_perfected");
		
		//Heat Capacitors
		heatCapacitor125 = initItem(new ItemHeatCapacitor(125), "heat_capacitor_0");
		heatCapacitor250 = initItem(new ItemHeatCapacitor(250), "heat_capacitor_1");
		heatCapacitor500 = initItem(new ItemHeatCapacitor(500), "heat_capacitor_2");
		heatCapacitor1000 = initItem(new ItemHeatCapacitor(1000), "heat_capacitor_3");
		
		//Fuel Pellets
		fuelPellet1 = initItem(new ItemFuel(200), "fuel_pellet_1");
		fuelPellet4 = initItem(new ItemFuel(800), "fuel_pellet_4");
		fuelPellet16 = initItem(new ItemFuel(3200), "fuel_pellet_16");
		fuelPellet64 = initItem(new ItemFuel(12800), "fuel_pellet_64");

		//Energion
		energionCrystalSeed = initItem(new ItemEnergionCrystalSeed(), "energion_crystal_seed");
		energionDust = initItem(new Item(), "energion_dust");
		
		//Tools
		crystalCutter = initItem(new ItemCrystalCutter(0, 191, 2.0F, 1), "crystal_cutter");
		wormholeLinker = initItem(new ItemWormholeLinker(), "wormhole_linker");
		ferramicHandbow = initItem(new ItemHandbow(864), "ferramic_handbow");
		
		//Atomic Reshaper
		primordium = initItem(new Item(), "primordium");
		aeternusCrystal = initItem(new ItemFuel(12800), "aeternus_crystal");
		//mysteryTreat = initItem(new ItemMysteryTreat(3, 0.8F), "mystery_treat", CreativeTabsMysteryTreats.INSTANCE);
		
		//Zorra
		zorraLeaf = initItem(new Item(), "zorra_leaf");
		zorrasteelRaw = initItem(new Item(), "zorrasteel_raw");
		zorrasteelIngot = initItem(new Item(), "zorrasteel_ingot");
		tartaricStoker = initItem(new ItemInfoShift(), "tartaric_stoker");
		
		//Tools
		materialZorrasteel = EnumHelper.addToolMaterial("zorrasteel", 4, 0, 8, 3, 0);
		zorrasteelSword = initItem(new ItemSwordZorrasteel(materialZorrasteel), "zorrasteel_sword");
		zorrasteelHandbow = initItem(new ItemHandbowZorrasteel(), "zorrasteel_handbow");
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		//Just making sure item blocks get registered before items
		for (Item i : itemBlockList) event.getRegistry().register(i);
		for (Item i : itemList) event.getRegistry().register(i);
		ModRecipes.initOreDict();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent evt)
	{
		for (Item i : itemBlockList) initModel(i);
		for (Item i : itemList) initModel(i);
		
		//We no longer use them afterwards
		itemBlockList = null;
		itemList = null;
	}
	
	public static Item initItem(Item item, String name)
	{
		return initItem(item, name, CreativeTabsProdigyTech.INSTANCE);
	}
	
	public static Item initItem(Item item, String name, CreativeTabs tab)
	{
		item.setRegistryName(name);
		item.setTranslationKey(ProdigyTech.MODID + "." + name);
		if (tab != null) item.setCreativeTab(tab);
		
		itemList.add(item);
		
		return item;
	}

	@SideOnly(Side.CLIENT)
	private static void initModel(Item i)
	{
		if (i instanceof IItemCustomModel) ((IItemCustomModel)i).initModel();
		else ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}
}
