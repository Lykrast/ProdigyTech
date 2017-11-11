package lykrast.prodigytech.common.init;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceRecipe;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {
	public static void init()
	{
		initOreDict();
		initSmelting();
		ExplosionFurnaceRecipe.init();
	}
	
	private static void initOreDict()
	{
		//Ferramic
		OreDictionary.registerOre("blockFerramic", ModBlocks.ferramicBlock);
		OreDictionary.registerOre("ingotFerramic", ModItems.ferramicIngot);
		OreDictionary.registerOre("nuggetFerramic", ModItems.ferramicNugget);
	}
	
	private static void initSmelting()
	{
		
	}
}
