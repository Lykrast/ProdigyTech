package lykrast.prodigytech.common.util;

import lykrast.prodigytech.core.CommonProxy;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraftforge.common.config.Configuration;

public class Config {
	//private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_MACHINES = "Machines";
	private static final String CATEGORY_ENERGION = "Energion";
	private static final String CATEGORY_AUTOMATION = "Automation";
	private static final String CATEGORY_ALTAR = "Zorra Altar";
	
	//General
	
	//Machines
	public static int incineratorProcessTime, blowerFurnaceProcessTime, rotaryGrinderProcessTime, heatSawmillProcessTime,
		soldererProcessTime, 
		magneticReassemblerProcessTime, oreRefineryProcessTime, automaticCrystalCutterHarvestTime, automaticCrystalCutterIdleTime,
		batteryReplenisherSpeed,
		primordialisReactorCycleTime, atomicReshaperProcessTime;
	public static float incineratorChance, oreRefineryChance;
	public static int rotaryGrinderOreMultiplier, oreRefineryOreMultiplier;
	public static boolean autoOreRecipes;
	public static float heatSawmillPlankMultiplier, heatSawmillStickMultiplier;
	public static boolean heatSawmillAutoPlankRecipes;
	public static int soldererMaxGold;
	public static int batteryReplenisherMaxEnergion;
	public static int primordialisReactorRequiredInput;
	public static int atomicReshaperMaxPrimordium;
	public static int tartaricStokerTime;
	public static boolean incineratorJEI;
	
	//Energion
	public static int energionBatteryDuration;
	
	//Automation
	public static int extractorDelay, extractorMaxStack;
	
	//Zorra Altar
	public static int altarDeviationMin, altarBonusLvl;
	public static float altarDeviationMult, altarCostMult, altarUnknownMult;
	
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e) {
			ProdigyTech.logger.warn("Problem loading config file!", e);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}
	
	private static void initGeneralConfig(Configuration cfg) {
		//cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
		cfg.addCustomCategoryComment(CATEGORY_MACHINES, "Machines configuration");
		cfg.addCustomCategoryComment(CATEGORY_ENERGION, "Energion configuration");
		cfg.addCustomCategoryComment(CATEGORY_AUTOMATION, "Automation configuration");
		cfg.addCustomCategoryComment(CATEGORY_ALTAR, "Zorra Altar configuration");

		//-----------
		// General
		//-----------
		//test = cfg.getBoolean("test", CATEGORY_GENERAL, true, "Testing that stuff out");
		
		//-----------
		//Machines
		//-----------
		//Incinerator
		incineratorProcessTime = cfg.getInt("incineratorProcessTime", CATEGORY_MACHINES, 200, 1, 3000, 
				"The base amount of time (in ticks) that the Incinerator takes to process 1 item");
		incineratorChance = cfg.getFloat("incineratorChance", CATEGORY_MACHINES, 1.0F, 0, 1.0F, 
				"The chance that an item burned in the Incinerator gives Ash");
		incineratorJEI = cfg.getBoolean("incineratorJEI", CATEGORY_MACHINES, true, 
				"Show the Incinerator recipe for Ash in JEI");
		//Blower Furnace
		blowerFurnaceProcessTime = cfg.getInt("blowerFurnaceProcessTime", CATEGORY_MACHINES, 300, 1, 3000, 
				"The base amount of time (in ticks) that the Blower Furnace takes to process 1 item");
		//Rotary Grinder
		rotaryGrinderProcessTime = cfg.getInt("rotaryGrinderProcessTime", CATEGORY_MACHINES, 300, 1, 3000, 
				"The base amount of time (in ticks) that the Rotary Grinder takes to process 1 item\n"
				+ "Several recipes have shorter or longer processing time, which are all derived from this value");
		rotaryGrinderOreMultiplier = cfg.getInt("rotaryGrinderOreMultiplier", CATEGORY_MACHINES, 2, 1, 10, 
				"By how much ore outputs are multiplied by when passing them through the Rotary Grinder");
		autoOreRecipes = cfg.getBoolean("autoOreRecipes", CATEGORY_MACHINES, true, 
				"Automatically generate Rotary Grinder, Magnetic Reassembler and Ore Refinery recipes to process ores?\n"
				+ "If false, only recipes for vanilla ores and Prodigy Tech ingots will be registered");
		//HeatSawmill
		heatSawmillProcessTime = cfg.getInt("heatSawmillProcessTime", CATEGORY_MACHINES, 200, 1, 3000, 
				"The base amount of time (in ticks) that the Heat Sawmill takes to process 1 item");
		heatSawmillPlankMultiplier = cfg.getFloat("heatSawmillPlankMultiplier", CATEGORY_MACHINES, 1.5F, 1.0F, 10.0F, 
				"Multiplier to the amount of planks the Heat Sawmil can extract from a single log (compared to manual crafting)");
		heatSawmillStickMultiplier = cfg.getFloat("heatSawmillStickMultiplier", CATEGORY_MACHINES, 2.0F, 1.0F, 10.0F, 
				"Multiplier to the amount of planks the Heat Sawmil can extract from a single log (compared to manual crafting)");
		heatSawmillAutoPlankRecipes = cfg.getBoolean("heatSawmillAutoPlankRecipes", CATEGORY_MACHINES, true, 
				"Automatically generate Heat Sawmill recipes to cut wood into planks\n"
				+ "If false, only recipes for vanilla logs and zorra will be registered");
		
		//Solderer
		soldererProcessTime = cfg.getInt("soldererProcessTime", CATEGORY_MACHINES, 400, 1, 3000, 
				"The base amount of time (in ticks) that the Solderer takes to make 1 Crude Circuit\n"
				+ "The time of all other recipes are derived from this value");
		soldererMaxGold = cfg.getInt("soldererMaxGold", CATEGORY_MACHINES, 81, 9, 20736, 
				"How much gold (in nuggets) can the Solderer hold in its internal buffer");
		
		//Magnetic Reassembler
		magneticReassemblerProcessTime = cfg.getInt("magneticReassemblerProcessTime", CATEGORY_MACHINES, 300, 1, 3000, 
				"The base amount of time (in ticks) that the Magnetic Reassembler takes to process 1 item\n"
				+ "Several recipes have shorter or longer processing time, which are all derived from this value");
		//Ore Refinery
		oreRefineryProcessTime = cfg.getInt("oreRefineryProcessTime", CATEGORY_MACHINES, 100, 1, 3000, 
				"The base amount of time (in ticks) that the Ore Refinery takes to process 1 item");
		oreRefineryOreMultiplier = cfg.getInt("oreRefineryOreMultiplier", CATEGORY_MACHINES, 2, 1, 10, 
				"By how much ore outputs are multiplied by when passing them through the Ore Refinery");
		oreRefineryChance = cfg.getFloat("oreRefineryChance", CATEGORY_MACHINES, 0.15F, 0, 1.0F, 
				"The chance the Ore Refinery produces a secondary ore");
		//Automatic Crystal Cutter
		automaticCrystalCutterHarvestTime = cfg.getInt("automaticCrystalCutterHarvestTime", CATEGORY_MACHINES, 100, 1, 3000, 
				"The base amount of time (in ticks) that the Automatic Crystal Cutter takes to harvest 1 stage");
		automaticCrystalCutterIdleTime = cfg.getInt("automaticCrystalCutterIdleTime", CATEGORY_MACHINES, 60, 1, 200, 
				"The time (in ticks) between 2 checks of the Automatic Crystal Cutter\n"
				+ "1 means every tick, 20 means once every second and so on\n"
				+ "Lower value will make them more reactive to crystal growing, but will make them sligtly laggier when idle");
		//Battery Replenisher
		batteryReplenisherSpeed = cfg.getInt("batteryReplenisherSpeed", CATEGORY_MACHINES, 10, 1, 1000000, 
				"How much energion ticks can the Battery Replenisher fill per tick");
		batteryReplenisherMaxEnergion = cfg.getInt("batteryReplenisherMaxEnergion", CATEGORY_MACHINES, 3, 1, 64, 
				"How many Energion Dust equivalent can the Battery Replenisher hold in its internal buffer");
		
		//Primordialis Reactor
		primordialisReactorCycleTime = cfg.getInt("primordialisReactorCycleTime", CATEGORY_MACHINES, 60, 1, 3000, 
				"The base amount of time (in ticks) that the Primordialis Reactor takes to make 1 cycle");
		primordialisReactorRequiredInput = cfg.getInt("primordialisReactorRequiredInput", CATEGORY_MACHINES, 576, 9, 5760, 
				"How many the Primordialis Reactor needs to consume to make 1 Primordium\n"
				+ "Note that this can be divided by up to 9 by putting different items\n"
				+ "This also means the number of cycles required ranges from this number to 1/81");
		//Atomic Reshaper
		atomicReshaperProcessTime = cfg.getInt("atomicReshaperProcessTime", CATEGORY_MACHINES, 200, 1, 3000, 
				"The base amount of time (in ticks) that the Atomic Reassembler takes to process 1 item\n"
				+ "Several recipes have shorter or longer processing time, which are all derived from this value");
		atomicReshaperMaxPrimordium = cfg.getInt("atomicReshaperMaxPrimordium", CATEGORY_MACHINES, 4, 1, 64, 
				"How many Primordium items can the Atomic Reshaper hold in its internal buffer");
		
		//Tartaric Aeroheater
		tartaricStokerTime = cfg.getInt("tartaricStokerTime", CATEGORY_MACHINES, 1600, 1, Short.MAX_VALUE, 
				"The base amount of time (in ticks) that 1 Tartaric Stoker lasts in the Tartaric Aeroheater");
		
		//-----------
		//Energion
		//-----------
		energionBatteryDuration = cfg.getInt("energionBatteryDuration", CATEGORY_ENERGION, 12000, 20, 1728000, 
				"The time (in ticks) a simple Energion Battery will last, other values are derived from this one");
		
		//-----------
		//Automation
		//-----------
		extractorDelay = cfg.getInt("extractorDelay", CATEGORY_AUTOMATION, 10, 1, 200, 
				"The time (in ticks) between 2 push/pulls of an Extractor\n"
				+ "1 means every tick, 20 means once every second and so on");
		extractorMaxStack = cfg.getInt("extractorMaxStack", CATEGORY_AUTOMATION, 64, 1, 64, 
				"How many items from a stack an Extractor can push/pull at once");
		
		//-----------
		//Zorra Altar
		//-----------
		altarCostMult = cfg.getFloat("altarCostMult", CATEGORY_ALTAR, 1.0F, 0.0F, 10.0F, 
				"A multiplier applied to all enchantment costs on the Zorra Altar\n"
				+ "For example 1.5 means that everything costs 50% more than the base amount");
		altarUnknownMult = cfg.getFloat("altarUnknownMult", CATEGORY_ALTAR, 0.5F, 0.0F, 10.0F, 
				"Cost multiplier for the unknown option\n"
				+ "For example 0.5 means that the unknown option only costs 50% of the normal cost");
		altarDeviationMin = cfg.getInt("altarDeviationMin", CATEGORY_ALTAR, 2, 0, 100, 
				"Minimum random level deviation for enchantment cost on the Zorra Altar\n"
				+ "2 means that the cost can range from -2 to +2 at least");
		altarDeviationMult = cfg.getFloat("altarDeviationMult", CATEGORY_ALTAR, 0.1F, 0.0F, 2.0F, 
				"Maximum random level deviation for enchantment cost on the Zorra Altar, multiplied by the base cost\n"
				+ "0.10 means that the cost can range from -10% to +10% (unless it's smaller than the min value)");
		altarBonusLvl = cfg.getInt("altarBonusLvl", CATEGORY_ALTAR, 3, 0, 100, 
				"How many levels beyond the normal maximum can the Zorra Altar apply enchantments\n"
				+ "Some enchantments don't take account of this limit");
	}

}
