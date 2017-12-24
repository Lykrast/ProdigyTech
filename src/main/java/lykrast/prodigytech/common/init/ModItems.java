package lykrast.prodigytech.common.init;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.item.IItemCustomModel;
import lykrast.prodigytech.common.item.ItemCircuit;
import lykrast.prodigytech.common.item.ItemEnergionBattery;
import lykrast.prodigytech.common.item.ItemFuel;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModItems {
	public static Item ferramicIngot, ferramicNugget, ferramicGear,
		ash, flour, sawdust, meatGround, meatPatty,
		coalDust, ferramicDust, ferramicDustTiny, ironDust, ironDustTiny, goldDust, goldDustTiny, carbonPlate,
		circuitPlate, circuitPlateUnfired, circuit, patternCircuit,
		energionCrystal, energionCrystalSeed, energionDust, 
		energionBatteryEmpty, energionBattery, energionBatteryDoubleEmpty, energionBatteryDouble, energionBatteryTripleEmpty, energionBatteryTriple;
	private static List<Item> itemList = new ArrayList<>();
	static List<Item> itemBlockList = new ArrayList<>();
	
	static
	{
		//Materials
		ferramicIngot = initItem(new Item(), "ferramic_ingot");
		ferramicNugget = initItem(new Item(), "ferramic_nugget");
		ferramicGear = initItem(new Item(), "ferramic_gear");
		ash = initItem(new Item(), "ash");

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
		carbonPlate = initItem(new Item(), "carbon_plate");
		
		circuitPlate = initItem(new Item(), "circuit_plate");
		circuitPlateUnfired = initItem(new Item(), "circuit_plate_unfired");
		circuit = initItem(new ItemCircuit(), "circuit");
		patternCircuit = initItem(new ItemCircuit().setMaxStackSize(1), "pattern_circuit");

		energionCrystalSeed = initItem(new Item(), "energion_crystal_seed");
		energionCrystal = initItem(new Item(), "energion_crystal");
		energionDust = initItem(new Item(), "energion_dust");
		energionBatteryEmpty = initItem(new Item(), "energion_battery_empty");
		energionBattery = initItem(new ItemEnergionBattery(Config.energionBatteryDuration, energionBatteryEmpty), "energion_battery");
		energionBatteryDoubleEmpty = initItem(new Item(), "energion_battery_double_empty");
		energionBatteryDouble = initItem(new ItemEnergionBattery(Config.energionBatteryDuration*2, energionBatteryDoubleEmpty), "energion_battery_double");
		energionBatteryTripleEmpty = initItem(new Item(), "energion_battery_triple_empty");
		energionBatteryTriple = initItem(new ItemEnergionBattery(Config.energionBatteryDuration*3, energionBatteryTripleEmpty), "energion_battery_triple");
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
	}
	
	public static Item initItem(Item item, String name)
	{
		return initItem(item, name, CreativeTabsProdigyTech.instance);
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
