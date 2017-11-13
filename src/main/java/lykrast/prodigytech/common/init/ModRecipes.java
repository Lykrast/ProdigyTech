package lykrast.prodigytech.common.init;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class ModRecipes {
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		initSmelting();
		ExplosionFurnaceManager.init();
	}
	
	public static void initOreDict()
	{
		//Ferramic
		OreDictionary.registerOre("blockFerramic", ModBlocks.ferramicBlock);
		OreDictionary.registerOre("ingotFerramic", ModItems.ferramicIngot);
		OreDictionary.registerOre("nuggetFerramic", ModItems.ferramicNugget);
	}
	
	public static void initSmelting()
	{
		
	}
}
