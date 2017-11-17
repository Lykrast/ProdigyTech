package lykrast.prodigytech.common.util;

import java.util.logging.Level;

import lykrast.prodigytech.core.CommonProxy;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraftforge.common.config.Configuration;

public class Config {
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_MACHINES = "Machines";
	
	//General
	public static boolean test;
	
	//Machines
	public static int incineratorProcessTime, blowerFurnaceProcessTime, rotaryGrinderProcessTime;
	public static float incineratorChance;
	
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
		test = cfg.getBoolean("test", CATEGORY_GENERAL, true, "Testing that stuff out");
		
		//-----------
		//Machines
		//-----------
		incineratorProcessTime = cfg.getInt("incineratorProcessTime", CATEGORY_MACHINES, 200, 0, 30000, 
				"The base amount of time (in ticks) that the Incinerator takes to process 1 item");
		incineratorChance = cfg.getFloat("incineratorChance", CATEGORY_MACHINES, 1.0F, 0, 1.0F, 
				"The chance that an item burned in the Incinerator gives Ash");
		blowerFurnaceProcessTime = cfg.getInt("blowerFurnaceProcessTime", CATEGORY_MACHINES, 300, 0, 30000, 
				"The base amount of time (in ticks) that the Blower Furnace takes to process 1 item");
		rotaryGrinderProcessTime = cfg.getInt("rotaryGrinderProcessTime", CATEGORY_MACHINES, 300, 0, 30000, 
				"The base amount of time (in ticks) that the Rotary Grinder takes to process 1 item\n"
				+ "Several recipes have shorter or longer processing time, which are all derived from this value");
	}

}
