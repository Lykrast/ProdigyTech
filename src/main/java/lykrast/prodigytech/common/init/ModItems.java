package lykrast.prodigytech.common.init;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.*;
import lykrast.prodigytech.common.util.Config;
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
		ash, flour, sawdust, meatGround, meatPatty,
		coalDust, ferramicDust, ferramicDustTiny, ironDust, ironDustTiny, goldDust, goldDustTiny, diamondDust, emeraldDust, quartzDust,
		carbonPlate, infernoFuel, infernoCrystal, sugarCube,
		circuitPlate, circuitCrude, circuitRefined, circuitPerfected, 
		patternCircuitCrude, patternCircuitRefined, patternCircuitPerfected,
		energionCrystalSeed, energionDust, 
		energionBatteryEmpty, energionBattery, energionBatteryDoubleEmpty, energionBatteryDouble, energionBatteryTripleEmpty, energionBatteryTriple,
		energionBatteryCreative,
		crystalCutter, wormholeLinker,
		primordium, aeternusCrystal, mysteryTreat,
		zorraLeaf, zorrasteelRaw, zorrasteelIngot,
		zorrasteelSword;
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
		infernoFuel = initItem(new ItemFuel(3200), "inferno_fuel");
		
		//Magnetic Reassembler
		infernoCrystal = initItem(new ItemFuel(4800), "inferno_crystal");
		sugarCube = initItem(new ItemSugarCube(1, 0.2F), "sugar_cube");
		
		//Solderer
		circuitPlate = initItem(new Item(), "circuit_plate");
		circuitCrude = initItem(new Item(), "circuit_crude");
		circuitRefined = initItem(new Item(), "circuit_refined");
		circuitPerfected = initItem(new Item(), "circuit_perfected");
		patternCircuitCrude = initItem(new Item().setMaxStackSize(1), "pattern_circuit_crude");
		patternCircuitRefined = initItem(new Item().setMaxStackSize(1), "pattern_circuit_refined");
		patternCircuitPerfected = initItem(new Item().setMaxStackSize(1), "pattern_circuit_perfected");

		//Energion
		energionCrystalSeed = initItem(new ItemEnergionCrystalSeed(), "energion_crystal_seed");
		energionDust = initItem(new Item(), "energion_dust");
		
		energionBatteryEmpty = initItem(new ItemEmptyBattery(), "energion_battery_empty");
		energionBattery = initItem(new ItemEnergionBattery(Config.energionBatteryDuration, energionBatteryEmpty), "energion_battery");
		((ItemEmptyBattery)energionBatteryEmpty).setFilledItem(energionBattery);
		
		energionBatteryDoubleEmpty = initItem(new ItemEmptyBattery(), "energion_battery_double_empty");
		energionBatteryDouble = initItem(new ItemEnergionBattery(Config.energionBatteryDuration*2, energionBatteryDoubleEmpty), "energion_battery_double");
		((ItemEmptyBattery)energionBatteryDoubleEmpty).setFilledItem(energionBatteryDouble);
		
		energionBatteryTripleEmpty = initItem(new ItemEmptyBattery(), "energion_battery_triple_empty");
		energionBatteryTriple = initItem(new ItemEnergionBattery(Config.energionBatteryDuration*3, energionBatteryTripleEmpty), "energion_battery_triple");
		((ItemEmptyBattery)energionBatteryTripleEmpty).setFilledItem(energionBatteryTriple);
		
		energionBatteryCreative = initItem(new ItemEnergionBatteryCreative(), "energion_battery_creative");
		
		//Tools
		crystalCutter = initItem(new ItemCrystalCutter(0, 50, 2.0F, 1), "crystal_cutter");
		wormholeLinker = initItem(new ItemWormholeLinker(), "wormhole_linker");
		//toolTest = initItem(new ItemEnergionUser(), "tool_test");
		
		//Atomic Reshaper
		primordium = initItem(new Item(), "primordium");
		aeternusCrystal = initItem(new ItemFuel(12800), "aeternus_crystal");
		//mysteryTreat = initItem(new ItemMysteryTreat(3, 0.8F), "mystery_treat", CreativeTabsMysteryTreats.INSTANCE);
		
		//Zorra
		zorraLeaf = initItem(new Item(), "zorra_leaf");
		zorrasteelRaw = initItem(new Item(), "zorrasteel_raw");
		zorrasteelIngot = initItem(new Item(), "zorrasteel_ingot");
		
		//Tools
		materialZorrasteel = EnumHelper.addToolMaterial("zorrasteel", 4, 0, 8, 3, 0);
		zorrasteelSword = initItem(new ItemZorrasteelSword(materialZorrasteel), "zorrasteel_sword");
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
		item.setUnlocalizedName(ProdigyTech.MODID + "." + name);
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
