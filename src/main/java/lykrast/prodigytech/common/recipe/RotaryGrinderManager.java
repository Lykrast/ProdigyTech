package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
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
		
		addRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE));
		addRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		addRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));
		
		addRecipe(new ItemStack(Items.WHEAT), new ItemStack(ModItems.flour));
		addRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.BEEF), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ModItems.meatGround, 2));
		addRecipe(new ItemStack(Items.RABBIT), new ItemStack(ModItems.meatGround));
		addRecipe(new ItemStack(Items.MUTTON), new ItemStack(ModItems.meatGround, 2));
		
		addRecipe(new ItemStack(Items.COAL), new ItemStack(ModItems.coalDust));
		addRecipe(new ItemStack(Blocks.COAL_BLOCK), new ItemStack(ModItems.coalDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe(new ItemStack(ModItems.carbonPlate), new ItemStack(ModItems.coalDust, 8));
		
		addRecipe("ingotFerramic", new ItemStack(ModItems.ferramicDust));
		addRecipe("blockFerramic", new ItemStack(ModItems.ferramicDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe("nuggetFerramic", new ItemStack(ModItems.ferramicDustTiny), Config.rotaryGrinderProcessTime / 9);
		addRecipe("gearFerramic", new ItemStack(ModItems.ferramicDustTiny, 15));

		addRecipe("oreIron", new ItemStack(ModItems.ironDust, 2));
		addRecipe("ingotIron", new ItemStack(ModItems.ironDust));
		addRecipe("blockIron", new ItemStack(ModItems.ironDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe("nuggetIron", new ItemStack(ModItems.ironDustTiny), Config.rotaryGrinderProcessTime / 9);

		addRecipe("oreGold", new ItemStack(ModItems.goldDust), 2);
		addRecipe("ingotGold", new ItemStack(ModItems.goldDust));
		addRecipe("blockGold", new ItemStack(ModItems.goldDust, 9), Config.rotaryGrinderProcessTime * 9);
		addRecipe("nuggetGold", new ItemStack(ModItems.goldDustTiny), Config.rotaryGrinderProcessTime / 9);
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
