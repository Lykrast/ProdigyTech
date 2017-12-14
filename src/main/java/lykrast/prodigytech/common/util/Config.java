package lykrast.prodigytech.common.util;

import java.util.logging.Level;

import lykrast.prodigytech.core.CommonProxy;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraftforge.common.config.Configuration;

public class Config {
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_MACHINES = "Machines";
	
	//General
	
	//Machines
	public static int incineratorProcessTime, blowerFurnaceProcessTime, rotaryGrinderProcessTime, soldererProcessTime, 
		crystalGrowthChamberProcessTime;
	public static float incineratorChance;
	public static int rotaryGrinderOreMultiplier;
	public static int soldererMaxGold;
	public static int crystalGrowthChamberMaxDesync;
	
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e) {
			ProdigyTech.logger.log(Level.WARNING, "Problem loading config file!", e);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}
	
	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
		cfg.addCustomCategoryComment(CATEGORY_MACHINES, "Machines configuration");

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
		//Blower Furnace
		blowerFurnaceProcessTime = cfg.getInt("blowerFurnaceProcessTime", CATEGORY_MACHINES, 300, 1, 3000, 
				"The base amount of time (in ticks) that the Blower Furnace takes to process 1 item");
		//Rotary Grinder
		rotaryGrinderProcessTime = cfg.getInt("rotaryGrinderProcessTime", CATEGORY_MACHINES, 300, 1, 3000, 
				"The base amount of time (in ticks) that the Rotary Grinder takes to process 1 item\n"
				+ "Several recipes have shorter or longer processing time, which are all derived from this value\n"
				+ "High values may result in weird behavior for slow recipes");
		rotaryGrinderOreMultiplier = cfg.getInt("rotaryGrinderOreMultiplier", CATEGORY_MACHINES, 2, 1, 10, 
				"By how much ore outputs are multiplied by when passing them through the Rotary Grinder");
		//Solderer
		soldererProcessTime = cfg.getInt("soldererProcessTime", CATEGORY_MACHINES, 400, 1, 3000, 
				"The base amount of time (in ticks) that the Solderer takes to make 1 Crude Circuit\n"
				+ "The time of all other recipes are derived from this value\n"
				+ "High values may result in weird behavior for slow recipes");
		soldererMaxGold = cfg.getInt("soldererMaxGold", CATEGORY_MACHINES, 81, 9, 20736, 
				"How much gold (in nuggets) can the Solderer hold in its internal buffer");
		//Crystal Growth Chamber
		crystalGrowthChamberProcessTime = cfg.getInt("crystalGrowthChamberProcessTime", CATEGORY_MACHINES, 120, 1, 3000, 
				"The base amount of time (in SECONDS) that the Crystal Growth Chamber takes to grow a Cluster\n"
				+ "Base speed is with a single set of Thermionic Oscillators");
		crystalGrowthChamberMaxDesync = cfg.getInt("crystalGrowthChamberMaxDesync", CATEGORY_MACHINES, 2000, 1, 30000, 
				"How much desynchronization can the Crystal Growth Chamber handle before exploding\n"
				+ "At base processing speed, this is in about 1/10 of a tick (or 1/200 of a second)\n"
				+ "Values below ~30 are highly discouraged since they basically mean no margin of error");
	}

}
