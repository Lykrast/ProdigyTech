package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MagneticReassemblerManager {
	public static final List<MagneticReassemblerRecipe> RECIPES = new ArrayList<>();
	
	public static MagneticReassemblerRecipe addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(new MagneticReassemblerRecipe(in, out));
	}
	
	public static MagneticReassemblerRecipe addRecipe(ItemStack in, ItemStack out, int time)
	{
		return addRecipe(new MagneticReassemblerRecipe(in, out, time));
	}
	
	public static MagneticReassemblerRecipe addRecipe(String in, ItemStack out)
	{
		return addRecipe(new MagneticReassemblerRecipe(in, out));
	}
	
	public static MagneticReassemblerRecipe addRecipe(String in, ItemStack out, int time)
	{
		return addRecipe(new MagneticReassemblerRecipe(in, out, time));
	}
	
	private static MagneticReassemblerRecipe addRecipe(MagneticReassemblerRecipe recipe)
	{
		RECIPES.add(recipe);
		return recipe;
	}
	
	public static MagneticReassemblerRecipe findRecipe(ItemStack in)
	{
		for (MagneticReassemblerRecipe recipe : RECIPES)
			if (recipe.isValidInput(in)) return recipe;
		
		return null;
	}
	
	public static MagneticReassemblerRecipe findOreRecipe(String in)
	{
		int id = OreDictionary.getOreID(in);
		for (MagneticReassemblerRecipe recipe : RECIPES)
			if (recipe.getOreID() == id) return recipe;
		
		return null;
	}
	
	public static MagneticReassemblerRecipe removeRecipe(ItemStack in)
	{
		MagneticReassemblerRecipe recipe = findRecipe(in);
		if (recipe != null) RECIPES.remove(recipe);
		
		return recipe;
	}
	
	public static MagneticReassemblerRecipe removeOreRecipe(String in)
	{
		MagneticReassemblerRecipe recipe = findOreRecipe(in);
		if (recipe != null) RECIPES.remove(recipe);
		
		return recipe;
	}
	
	//Probably REALLY need to optimise those one day
	public static boolean isValidInput(ItemStack check)
	{
		for (MagneticReassemblerRecipe recipe : RECIPES)
			if (recipe.isValidInput(check)) return true;
		
		return false;
	}
	
	public static void init()
	{		
		addRecipe("cobblestone", new ItemStack(Blocks.STONE));
		addRecipe("gravel", new ItemStack(Blocks.COBBLESTONE));
		addRecipe("sand", new ItemStack(Blocks.GRAVEL));
		
		addRecipe("dustCoal", new ItemStack(Items.COAL));
		addRecipe("dustIron", new ItemStack(Items.IRON_INGOT));
		addRecipe("dustTinyIron", new ItemStack(Items.IRON_NUGGET), Config.magneticReassemblerProcessTime / 9);
		addRecipe("dustGold", new ItemStack(Items.GOLD_INGOT));
		addRecipe("dustTinyGold", new ItemStack(Items.GOLD_NUGGET), Config.magneticReassemblerProcessTime / 9);
		addRecipe("dustDiamond", new ItemStack(Items.DIAMOND));
		addRecipe("dustEmerald", new ItemStack(Items.EMERALD));
		addRecipe("dustQuartz", new ItemStack(Items.QUARTZ));
		
		addRecipe("dustFerramic", new ItemStack(ModItems.ferramicIngot));
		addRecipe("dustTinyFerramic", new ItemStack(ModItems.ferramicNugget), Config.magneticReassemblerProcessTime / 9);
		addRecipe("dustEnergion", new ItemStack(ModItems.energionCrystalSeed));
		
		addRecipe(new ItemStack(ModItems.infernoFuel), new ItemStack(ModItems.infernoCrystal), Config.magneticReassemblerProcessTime * 2);
		addRecipe(new ItemStack(Items.SUGAR), new ItemStack(ModItems.sugarCube));
	}
	
	public static class MagneticReassemblerRecipe {
		private final ItemStack input;
		private final ItemStack output;
		private final int time, oreInput;
		
		public MagneticReassemblerRecipe(ItemStack input, ItemStack output)
		{
			this(input, output, Config.magneticReassemblerProcessTime);
		}
		
		public MagneticReassemblerRecipe(ItemStack input, ItemStack output, int time)
		{
			this.input = input;
			//No support for recipes requiring multiple items for now
			input.setCount(1);
			this.output = output;
			this.time = time;
			oreInput = -1;
		}
		
		public MagneticReassemblerRecipe(String input, ItemStack output)
		{
			this(input, output, Config.magneticReassemblerProcessTime);
		}
		
		public MagneticReassemblerRecipe(String input, ItemStack output, int time)
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
