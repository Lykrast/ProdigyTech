package lykrast.prodigytech.common.recipe;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.RecipeUtil;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RotaryGrinderManager extends SimpleRecipeManager {
	public static final RotaryGrinderManager INSTANCE = new RotaryGrinderManager();
	
	public SimpleRecipe addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(in, out, Config.rotaryGrinderProcessTime);
	}
	
	public SimpleRecipe addRecipe(String inOre, ItemStack out)
	{
		return addRecipe(inOre, out, Config.rotaryGrinderProcessTime);
	}
	
	public void init()
	{
		addRecipe("logWood", new ItemStack(ModItems.sawdust, 4));
		addRecipe("plankWood", new ItemStack(ModItems.sawdust));
		
		addRecipe("stone", new ItemStack(Blocks.COBBLESTONE));
		addRecipe("cobblestone", new ItemStack(Blocks.GRAVEL));
		addRecipe("gravel", new ItemStack(Blocks.SAND));
		addRecipe(new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.SAND, 2));
		addRecipe(new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.SAND, 2, 1));
		addRecipe(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4));
		addRecipe(new ItemStack(Blocks.CLAY), new ItemStack(Items.CLAY_BALL, 4), Config.rotaryGrinderProcessTime / 8);
		addRecipe(new ItemStack(Blocks.BRICK_BLOCK), new ItemStack(Items.BRICK, 4));
		addRecipe(new ItemStack(Blocks.NETHER_BRICK), new ItemStack(Items.NETHERBRICK, 4));
		addRecipe(new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.ROUGH_META), new ItemStack(Items.PRISMARINE_SHARD, 4));
		addRecipe(new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.BRICKS_META), new ItemStack(Items.PRISMARINE_SHARD, 9));
		addRecipe(new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.DARK_META), new ItemStack(Items.PRISMARINE_SHARD, 8));

		addRecipe(new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 4));
		addRecipe(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 6, 15));
		addRecipe("cropWheat", new ItemStack(ModItems.flour));
		addRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.BEEF), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.RABBIT), new ItemStack(ModItems.meatGround));
		addRecipe(new ItemStack(Items.MUTTON), new ItemStack(ModItems.meatGround, 2));

		addRecipe("oreCoal", new ItemStack(ModItems.coalDust, Config.rotaryGrinderOreMultiplier));
		if (!Config.autoOreRecipes) addRecipe("oreIron", new ItemStack(ModItems.ironDust, Config.rotaryGrinderOreMultiplier));
		if (!Config.autoOreRecipes) addRecipe("oreGold", new ItemStack(ModItems.goldDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreLapis", new ItemStack(Items.DYE, 6 * Config.rotaryGrinderOreMultiplier, 4));
		addRecipe("oreRedstone", new ItemStack(Items.REDSTONE, (int)(4.5 * Config.rotaryGrinderOreMultiplier)));
		if (!Config.autoOreRecipes) addRecipe("oreDiamond", new ItemStack(ModItems.diamondDust, Config.rotaryGrinderOreMultiplier));
		if (!Config.autoOreRecipes) addRecipe("oreEmerald", new ItemStack(ModItems.emeraldDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreQuartz", new ItemStack(ModItems.quartzDust, Config.rotaryGrinderOreMultiplier));
		
		addRecipe(new ItemStack(Items.COAL), new ItemStack(ModItems.coalDust));
		addRecipe("blockCoal", new ItemStack(ModItems.coalDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe("plateCarbon", new ItemStack(ModItems.coalDust, 8));

		if (!Config.autoOreRecipes)
		{
			addRecipe("ingotIron", new ItemStack(ModItems.ironDust));
			addRecipe("blockIron", new ItemStack(ModItems.ironDust, 9), Config.rotaryGrinderProcessTime * 9);
			addRecipe("nuggetIron", new ItemStack(ModItems.ironDustTiny), Config.rotaryGrinderProcessTime / 9);

			addRecipe("ingotGold", new ItemStack(ModItems.goldDust));
			addRecipe("blockGold", new ItemStack(ModItems.goldDust, 9), Config.rotaryGrinderProcessTime * 9);
			addRecipe("nuggetGold", new ItemStack(ModItems.goldDustTiny), Config.rotaryGrinderProcessTime / 9);
			
			addRecipe("gemDiamond", new ItemStack(ModItems.diamondDust));
			addRecipe("blockDiamond", new ItemStack(ModItems.diamondDust, 9), Config.rotaryGrinderProcessTime * 9);
			
			addRecipe("gemEmerald", new ItemStack(ModItems.emeraldDust));
			addRecipe("blockEmerald", new ItemStack(ModItems.emeraldDust, 9), Config.rotaryGrinderProcessTime * 9);
		}
		
		addRecipe("gemQuartz", new ItemStack(ModItems.quartzDust));
		addRecipe("blockQuartz", new ItemStack(ModItems.quartzDust, 4), Config.rotaryGrinderProcessTime * 4);
		addRecipe(new ItemStack(Blocks.QUARTZ_STAIRS), new ItemStack(ModItems.quartzDust, 6), (int) (Config.rotaryGrinderProcessTime * 6));
		addRecipe(new ItemStack(Blocks.STONE_SLAB, 1, Blocks.STONE_SLAB.getMetaFromState(Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.QUARTZ))), 
				new ItemStack(ModItems.quartzDust, 2), Config.rotaryGrinderProcessTime * 2);
		
		addRecipe("ingotFerramic", new ItemStack(ModItems.ferramicDust));
		addRecipe("blockFerramic", new ItemStack(ModItems.ferramicDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe("nuggetFerramic", new ItemStack(ModItems.ferramicDustTiny), Config.rotaryGrinderProcessTime / 9);
		addRecipe("gearFerramic", new ItemStack(ModItems.ferramicDustTiny, 32));
		addRecipe(new ItemStack(ModItems.energionBatteryEmpty), new ItemStack(ModItems.ferramicDustTiny, 16));
		addRecipe(new ItemStack(ModItems.energionBatteryDoubleEmpty), new ItemStack(ModItems.ferramicDustTiny, 33));
		addRecipe(new ItemStack(ModItems.energionBatteryTripleEmpty), new ItemStack(ModItems.ferramicDustTiny, 50));
		
		addRecipe("gemEnergion", new ItemStack(ModItems.energionDust));
//		addRecipe("gemBigEnergion", new ItemStack(ModItems.energionDust, 6), Config.rotaryGrinderProcessTime * 6);
//		addRecipe("blockEnergion", new ItemStack(ModItems.energionDust, 54), Config.rotaryGrinderProcessTime * 54);
		
		addRecipe(new ItemStack(ModItems.infernoCrystal), new ItemStack(ModItems.infernoFuel));
	}
	
	public void registerOres() {
		//NOTE: we also do Magnetic Reassembler stuff here to avoid iterating twice
		if (!Config.autoOreRecipes) return;
		
		//Borrowed from Immersive Engineering
		//https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/IERecipes.java
		for (String name : OreDictionary.getOreNames())
		{
			if (!RecipeUtil.oreExists(name)) continue;
			
			//ore -> Rotary Grinder processes into dust or gem
			if (name.startsWith("ore"))
			{
				String ore = name.substring("ore".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;
				
				if (RecipeUtil.oreExists("dust" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("dust" + ore);
					output.setCount(Config.rotaryGrinderOreMultiplier);
					addRecipe(name, output);
				}
				else if (RecipeUtil.oreExists("gem" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("gem" + ore);
					output.setCount(Config.rotaryGrinderOreMultiplier);
					addRecipe(name, output);
				}
			}
			//gem -> Rotary Grinder processes into dust
			else if (name.startsWith("gem"))
			{
				String ore = name.substring("gem".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("dust" + ore))
				{
					addRecipe(name, RecipeUtil.getPreferredOreStack("dust" + ore));
				}
			}
			//ingot -> Rotary Grinder processes into dust
			else if (name.startsWith("ingot"))
			{
				String ore = name.substring("ingot".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("dust" + ore))
				{
					addRecipe(name, RecipeUtil.getPreferredOreStack("dust" + ore));
				}
			}
			//nugget -> Rotary Grinder processes into dustTiny
			else if (name.startsWith("nugget"))
			{
				String ore = name.substring("nugget".length());
				if (RecipeUtil.isOreBlacklisted(ore)) continue;

				if (RecipeUtil.oreExists("dustTiny" + ore))
				{
					addRecipe(name, RecipeUtil.getPreferredOreStack("dustTiny" + ore), Config.rotaryGrinderProcessTime / 9);
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
					addRecipe(name, output, Config.rotaryGrinderProcessTime * 9);
				}
				else if (RecipeUtil.oreExists("gem" + ore))
				{
					ItemStack output = RecipeUtil.getPreferredOreStack("gem" + ore);
					output.setCount(9);
					addRecipe(name, output, Config.rotaryGrinderProcessTime * 9);
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

}
