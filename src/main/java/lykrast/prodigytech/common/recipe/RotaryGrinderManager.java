package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RotaryGrinderManager {
	public static final List<RotaryGrinderRecipe> RECIPES = new ArrayList<>();
	
	public static RotaryGrinderRecipe addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(new RotaryGrinderRecipe(in, out));
	}
	
	public static RotaryGrinderRecipe addRecipe(ItemStack in, ItemStack out, int time)
	{
		return addRecipe(new RotaryGrinderRecipe(in, out, time));
	}
	
	public static RotaryGrinderRecipe addRecipe(String in, ItemStack out)
	{
		return addRecipe(new RotaryGrinderRecipe(in, out));
	}
	
	public static RotaryGrinderRecipe addRecipe(String in, ItemStack out, int time)
	{
		return addRecipe(new RotaryGrinderRecipe(in, out, time));
	}
	
	private static RotaryGrinderRecipe addRecipe(RotaryGrinderRecipe recipe)
	{
		RECIPES.add(recipe);
		return recipe;
	}
	
	public static RotaryGrinderRecipe findRecipe(ItemStack in)
	{
		for (RotaryGrinderRecipe recipe : RECIPES)
			if (recipe.isValidInput(in)) return recipe;
		
		return null;
	}
	
	//Probably REALLY need to optimise those one day
	public static boolean isValidInput(ItemStack check)
	{
		for (RotaryGrinderRecipe recipe : RECIPES)
			if (recipe.isValidInput(check)) return true;
		
		return false;
	}
	
	public static void init()
	{
		addRecipe("logWood", new ItemStack(ModItems.sawdust, 4));
		addRecipe("plankWood", new ItemStack(ModItems.sawdust));
		
		addRecipe("stone", new ItemStack(Blocks.COBBLESTONE));
		addRecipe("cobblestone", new ItemStack(Blocks.GRAVEL));
		addRecipe("gravel", new ItemStack(Blocks.SAND));
		addRecipe(new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.SAND, 2));
		addRecipe(new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.SAND, 2, 1));
		addRecipe(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4));

		addRecipe(new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 4));
		addRecipe(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 6, 15));
		addRecipe("cropWheat", new ItemStack(ModItems.flour));
		addRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.BEEF), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.RABBIT), new ItemStack(ModItems.meatGround));
		addRecipe(new ItemStack(Items.MUTTON), new ItemStack(ModItems.meatGround, 2));

		addRecipe("oreCoal", new ItemStack(ModItems.coalDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreIron", new ItemStack(ModItems.ironDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreGold", new ItemStack(ModItems.goldDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreLapis", new ItemStack(Items.DYE, 6 * Config.rotaryGrinderOreMultiplier, 4));
		addRecipe("oreRedstone", new ItemStack(Items.REDSTONE, (int)(4.5 * Config.rotaryGrinderOreMultiplier)));
		addRecipe("oreDiamond", new ItemStack(ModItems.diamondDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreEmerald", new ItemStack(ModItems.emeraldDust, Config.rotaryGrinderOreMultiplier));
		addRecipe("oreQuartz", new ItemStack(ModItems.quartzDust, Config.rotaryGrinderOreMultiplier));
		
		addRecipe(new ItemStack(Items.COAL), new ItemStack(ModItems.coalDust));
		addRecipe("blockCoal", new ItemStack(ModItems.coalDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe(new ItemStack(ModItems.carbonPlate), new ItemStack(ModItems.coalDust, 8));

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
		
		addRecipe("gemQuartz", new ItemStack(ModItems.quartzDust));
		addRecipe("blockQuartz", new ItemStack(ModItems.quartzDust, 4), Config.rotaryGrinderProcessTime * 4);
		addRecipe(new ItemStack(Blocks.QUARTZ_STAIRS), new ItemStack(ModItems.quartzDust, 6), (int) (Config.rotaryGrinderProcessTime * 6));
		//I hate this
		addRecipe(new ItemStack(Blocks.STONE_SLAB, 1, Blocks.STONE_SLAB.getMetaFromState(Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.QUARTZ))), 
				new ItemStack(ModItems.quartzDust, 2), Config.rotaryGrinderProcessTime * 2);
		
		addRecipe("ingotFerramic", new ItemStack(ModItems.ferramicDust));
		addRecipe("blockFerramic", new ItemStack(ModItems.ferramicDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe("nuggetFerramic", new ItemStack(ModItems.ferramicDustTiny), Config.rotaryGrinderProcessTime / 9);
		addRecipe("gearFerramic", new ItemStack(ModItems.ferramicDustTiny, 15));
		addRecipe(new ItemStack(ModItems.energionBatteryEmpty), new ItemStack(ModItems.ferramicDustTiny, 16));
		addRecipe(new ItemStack(ModItems.energionBatteryDoubleEmpty), new ItemStack(ModItems.ferramicDustTiny, 33));
		addRecipe(new ItemStack(ModItems.energionBatteryTripleEmpty), new ItemStack(ModItems.ferramicDustTiny, 50));
		
		addRecipe("gemEnergion", new ItemStack(ModItems.energionDust));
		addRecipe("gemBigEnergion", new ItemStack(ModItems.energionDust, 6), Config.rotaryGrinderProcessTime * 6);
		addRecipe("blockEnergion", new ItemStack(ModItems.energionDust, 54), Config.rotaryGrinderProcessTime * 54);
	}
	
	public static class RotaryGrinderRecipe {
		private final ItemStack input;
		private final ItemStack output;
		private final int time, oreInput;
		
		public RotaryGrinderRecipe(ItemStack input, ItemStack output)
		{
			this(input, output, Config.rotaryGrinderProcessTime);
		}
		
		public RotaryGrinderRecipe(ItemStack input, ItemStack output, int time)
		{
			this.input = input;
			//No support for recipes requiring multiple items for now
			input.setCount(1);
			this.output = output;
			this.time = time;
			oreInput = -1;
		}
		
		public RotaryGrinderRecipe(String input, ItemStack output)
		{
			this(input, output, Config.rotaryGrinderProcessTime);
		}
		
		public RotaryGrinderRecipe(String input, ItemStack output, int time)
		{
			oreInput = OreDictionary.getOreID(input);
			this.input = ItemStack.EMPTY;
			this.output = output;
			this.time = time;
		}
		
		public ItemStack getInput()
		{
			return input.copy();
		}
		
		public int getOreID()
		{
			return oreInput;
		}
		
		public ItemStack getOutput()
		{
			return output.copy();
		}
		
		public int getTimeTicks()
		{
			return time;
		}
		
		public int getTimeProcessing()
		{
			return time * 10;
		}
		
		public boolean isValidInput(ItemStack in)
		{
			if (in.isEmpty()) return false;
			if (oreInput != -1)
			{
				int[] oreIDs = OreDictionary.getOreIDs(in);
				for (int i : oreIDs)
				{
					if (i == oreInput) return true;
				}
				return false;
			}
			
			return (in.isItemEqual(input) && in.getCount() >= input.getCount());
		}
	}

}
