package lykrast.prodigytech.common.init;

import lykrast.prodigytech.common.recipe.AtomicReshaperManager;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import lykrast.prodigytech.common.recipe.HeatSawmillManager;
import lykrast.prodigytech.common.recipe.MagneticReassemblerManager;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.SoldererManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class ModRecipes {
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		initSmelting();
		ExplosionFurnaceManager.init();
		RotaryGrinderManager.INSTANCE.init();
		SoldererManager.init();
		MagneticReassemblerManager.INSTANCE.init();
		HeatSawmillManager.INSTANCE.init();
		AtomicReshaperManager.INSTANCE.init();
	}
	
	public static void initOreDict()
	{
		//Ferramic
		OreDictionary.registerOre("blockFerramic", ModBlocks.ferramicBlock);
		OreDictionary.registerOre("ingotFerramic", ModItems.ferramicIngot);
		OreDictionary.registerOre("nuggetFerramic", ModItems.ferramicNugget);
		OreDictionary.registerOre("gearFerramic", ModItems.ferramicGear);
		
		//Dust
		OreDictionary.registerOre("dustCoal", ModItems.coalDust);
		OreDictionary.registerOre("dustFerramic", ModItems.ferramicDust);
		OreDictionary.registerOre("dustTinyFerramic", ModItems.ferramicDustTiny);
		OreDictionary.registerOre("dustIron", ModItems.ironDust);
		OreDictionary.registerOre("dustTinyIron", ModItems.ironDustTiny);
		OreDictionary.registerOre("dustGold", ModItems.goldDust);
		OreDictionary.registerOre("dustTinyGold", ModItems.goldDustTiny);
		OreDictionary.registerOre("dustDiamond", ModItems.diamondDust);
		OreDictionary.registerOre("dustEmerald", ModItems.emeraldDust);
		OreDictionary.registerOre("dustQuartz", ModItems.quartzDust);
		
		//Energion
		OreDictionary.registerOre("gemEnergion", ModItems.energionCrystalSeed);
		OreDictionary.registerOre("dustEnergion", ModItems.energionDust);
		
		//Other
		OreDictionary.registerOre("dustAsh", ModItems.ash);
		OreDictionary.registerOre("dustWood", ModItems.sawdust);
		OreDictionary.registerOre("foodFlour", ModItems.flour);
		OreDictionary.registerOre("plateCarbon", ModItems.carbonPlate);
		OreDictionary.registerOre("blockPlateCarbon", ModBlocks.carbonPlateBlock);
		OreDictionary.registerOre("plankWood", ModBlocks.particleBoard);
		OreDictionary.registerOre("plankWood", ModBlocks.particleBoardPlanks);
	}
	
	public static void initSmelting()
	{
		GameRegistry.addSmelting(ModItems.flour, new ItemStack(Items.BREAD), 0.35F);
		GameRegistry.addSmelting(ModItems.meatGround, new ItemStack(ModItems.meatPatty), 0.35F);
		
		GameRegistry.addSmelting(ModItems.ferramicDust, new ItemStack(ModItems.ferramicIngot), 0.1F);
		GameRegistry.addSmelting(ModItems.ferramicDustTiny, new ItemStack(ModItems.ferramicNugget), 0);
		GameRegistry.addSmelting(ModItems.ironDust, new ItemStack(Items.IRON_INGOT), 0.1F);
		GameRegistry.addSmelting(ModItems.ironDustTiny, new ItemStack(Items.IRON_NUGGET), 0);
		GameRegistry.addSmelting(ModItems.goldDust, new ItemStack(Items.GOLD_INGOT), 0.1F);
		GameRegistry.addSmelting(ModItems.goldDustTiny, new ItemStack(Items.GOLD_NUGGET), 0);
	}
}
