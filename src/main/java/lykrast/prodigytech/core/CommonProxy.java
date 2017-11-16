package lykrast.prodigytech.core;

import java.io.File;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.util.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public static Configuration config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "prodigy_tech.cfg"));
        Config.readConfig();
        
        NetworkRegistry.INSTANCE.registerGuiHandler(ProdigyTech.instance, new ProdigyTechGuiHandler());
        CapabilityHotAir.register();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}

}
