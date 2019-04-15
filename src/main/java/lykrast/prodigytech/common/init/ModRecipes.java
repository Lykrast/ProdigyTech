package lykrast.prodigytech.common.init;

import lykrast.prodigytech.common.recipe.AtomicReshaperManager;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import lykrast.prodigytech.common.recipe.HeatSawmillManager;
import lykrast.prodigytech.common.recipe.MagneticReassemblerManager;
import lykrast.prodigytech.common.recipe.OreRefineryManager;
import lykrast.prodigytech.common.recipe.PrimordialisReactorManager;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.SoldererManager;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.RecipeUtil;
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
		PrimordialisReactorManager.init();
		AtomicReshaperManager.INSTANCE.init();
		OreRefineryManager.INSTANCE.init();
		
		makeOreRecipes();
	}
	
	public static void makeOreRecipes() {
		//Create automatic recipes for Rotary Grinder, Magnetic Reassembler and Ore Refinery
		if (!Config.autoOreRecipes) return;
		
		//Borrowed from Immersive Engineering
		//https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/IERecipes.java
		for (String name : OreDictionary.getOreNames())
		{
			if (!RecipeUtil.oreExists(name)) continue;
			
			//ore -> Rotary Grinder and Ore Refinery process into dust or gem
			if (name.startsWith("ore"))
			{
				String ore = name.substring("ore".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;
				
				if (RecipeUtil.oreExists("dust" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("dust" + ore);
					output.setCount(Config.rotaryGrinderOreMultiplier);
					RotaryGrinderManager.INSTANCE.addRecipe(name, output);
					
					OreRefineryManager.INSTANCE.addOreRecipe(ore, name, "dust" + ore);
				}
				else if (RecipeUtil.oreExists("gem" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("gem" + ore);
					output.setCount(Config.rotaryGrinderOreMultiplier);
					RotaryGrinderManager.INSTANCE.addRecipe(name, output);
					
					OreRefineryManager.INSTANCE.addOreRecipe(ore, name, "gem" + ore);
				}
			}
			//gem -> Rotary Grinder processes into dust
			else if (name.startsWith("gem"))
			{
				String ore = name.substring("gem".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("dust" + ore))
				{
					RotaryGrinderManager.INSTANCE.addRecipe(name, RecipeUtil.getPreferredOreStack("dust" + ore));
				}
			}
			//ingot -> Rotary Grinder processes into dust
			else if (name.startsWith("ingot"))
			{
				String ore = name.substring("ingot".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("dust" + ore))
				{
					RotaryGrinderManager.INSTANCE.addRecipe(name, RecipeUtil.getPreferredOreStack("dust" + ore));
				}
			}
			//nugget -> Rotary Grinder processes into dustTiny
			else if (name.startsWith("nugget"))
			{
				String ore = name.substring("nugget".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("dustTiny" + ore))
				{
					RotaryGrinderManager.INSTANCE.addRecipe(name, RecipeUtil.getPreferredOreStack("dustTiny" + ore), Config.rotaryGrinderProcessTime / 9);
				}
			}
			//block -> Rotary Grinder processes into 9 dust
			else if (name.startsWith("block"))
			{
				String ore = name.substring("block".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;
				
				if (RecipeUtil.oreExists("dust" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("dust" + ore);
					output.setCount(9);
					RotaryGrinderManager.INSTANCE.addRecipe(name, output, Config.rotaryGrinderProcessTime * 9);
				}
				else if (RecipeUtil.oreExists("gem" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("gem" + ore);
					output.setCount(9);
					RotaryGrinderManager.INSTANCE.addRecipe(name, output, Config.rotaryGrinderProcessTime * 9);
				}
			}
			//dustTiny -> Magnetic Reassembler processes into nugget
			else if (name.startsWith("dustTiny"))
			{
				String ore = name.substring("dustTiny".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("nugget" + ore))
				{
					MagneticReassemblerManager.INSTANCE.addRecipe(name, 
							RecipeUtil.getPreferredOreStack("nugget" + ore), Config.magneticReassemblerProcessTime / 9);
				}
			}
			//dust -> Magnetic Reassembler processes into ingot or gem
			else if (name.startsWith("dust"))
			{
				String ore = name.substring("dust".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("ingot" + ore))
				{
					MagneticReassemblerManager.INSTANCE.addRecipe(name, RecipeUtil.getPreferredOreStack("ingot" + ore));
				}
				else if (RecipeUtil.oreExists("gem" + ore))
				{
					MagneticReassemblerManager.INSTANCE.addRecipe(name, RecipeUtil.getPreferredOreStack("gem" + ore));
				}
			}
		}
	}
	
	public static void initOreDict() {
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
		OreDictionary.registerOre("dustNetherQuartz", ModItems.quartzDust);
		
		//Energion
		OreDictionary.registerOre("gemEnergion", ModItems.energionCrystalSeed);
		OreDictionary.registerOre("dustEnergion", ModItems.energionDust);
		
		//Zorra
		OreDictionary.registerOre("leafZorra", ModItems.zorraLeaf);
		OreDictionary.registerOre("ingotRawZorrasteel", ModItems.zorrasteelRaw);
		OreDictionary.registerOre("ingotZorrasteel", ModItems.zorrasteelIngot);
		OreDictionary.registerOre("blockZorrasteel", ModBlocks.zorrasteelBlock);
		
		//Other
		OreDictionary.registerOre("dustAsh", ModItems.ash);
		OreDictionary.registerOre("dustWood", ModItems.sawdust);
		OreDictionary.registerOre("foodFlour", ModItems.flour);
		OreDictionary.registerOre("plateCarbon", ModItems.carbonPlate);
		OreDictionary.registerOre("blockPlateCarbon", ModBlocks.carbonPlateBlock);
		OreDictionary.registerOre("plankWood", ModBlocks.particleBoard);
		OreDictionary.registerOre("plankWood", ModBlocks.particleBoardPlanks);
		OreDictionary.registerOre("logWood", ModBlocks.zorraLog);
		OreDictionary.registerOre("plankWood", ModBlocks.zorraPlanks);
		OreDictionary.registerOre("treeLeaves", ModBlocks.zorraLeaves);
		OreDictionary.registerOre("treeSapling", ModBlocks.zorraSapling);
		OreDictionary.registerOre("cobblestoneCharred", ModBlocks.charredCobblestone);
		OreDictionary.registerOre("stoneCharred", ModBlocks.charredStone);
	}
	
	public static void initSmelting() {
		GameRegistry.addSmelting(ModBlocks.charredCobblestone, new ItemStack(ModBlocks.charredStone), 0.1F);
		
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
