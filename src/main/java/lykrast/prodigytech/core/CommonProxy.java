package lykrast.prodigytech.core;

import java.io.File;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.init.ModRecipes;
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
        config = new Configuration(new File(directory.getPath(), "defiled_lands.cfg"));
        Config.readConfig();
        
        ModBlocks.init();
        ModItems.init();
        ModRecipes.init();
        
        NetworkRegistry.INSTANCE.registerGuiHandler(ProdigyTech.instance, new ProdigyTechGuiHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}

}
