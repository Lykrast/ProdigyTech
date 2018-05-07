package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager.AtomicReshaperRecipe;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AtomicReshaperManager extends SimpleRecipeManagerAbstract<AtomicReshaperRecipe> {
	public static final AtomicReshaperManager INSTANCE = new AtomicReshaperManager();
	private AtomicReshaperManager() {}

	public AtomicReshaperRecipe addRecipe(ItemStack input, int time, int primordium, Object... outputs)
	{
		return addRecipe(new AtomicReshaperRecipe(input, time, primordium, outputs));
	}
	
	public AtomicReshaperRecipe addRecipe(String input, int time, int primordium, Object... outputs)
	{
		return addRecipe(new AtomicReshaperRecipe(input, time, primordium, outputs));
	}
	
	@Override
	public void init() {
		addRecipe("stone", Config.atomicReshaperProcessTime * 3, 20, new ItemStack(Blocks.COAL_ORE), 64, 
				new ItemStack(Blocks.IRON_ORE), 48, 
				new ItemStack(Blocks.REDSTONE_ORE), 33, 
				new ItemStack(Blocks.LAPIS_ORE), 22, 
				new ItemStack(Blocks.GOLD_ORE), 20, 
				new ItemStack(Blocks.DIAMOND_ORE), 14, 
				new ItemStack(Blocks.EMERALD_ORE), 10);
		
		addRecipe("sand", Config.atomicReshaperProcessTime, 1, new ItemStack(Blocks.DIRT));
		addRecipe("dirt", Config.atomicReshaperProcessTime, 3, new ItemStack(Blocks.CLAY));
		addRecipe("paper", Config.atomicReshaperProcessTime, 2, new ItemStack(ModItems.circuitPlate));
		addRecipe(new ItemStack(ModItems.infernoCrystal), Config.atomicReshaperProcessTime, 5, new ItemStack(ModItems.aeternusCrystal));
	}
	
	public static class AtomicReshaperRecipe implements ISingleInputRecipe, Comparable<AtomicReshaperRecipe> {
		private static int NEXTID = 0;
		private int id;
		
		protected final ItemStack input;
		protected final String oreInput;
		protected final List<Pair<ItemStack, Integer>> outputs;
		protected final int time, primordium;
		private int totalWeight;
		
		public AtomicReshaperRecipe(ItemStack input, int time, int primordium, Object... outputs) {
			this.input = input;
			oreInput = null;
			this.time = time;
			this.primordium = primordium;
			this.outputs = new ArrayList<>();
			processOutputs(outputs);
			id = NEXTID++;
		}
		
		public AtomicReshaperRecipe(String input, int time, int primordium, Object... outputs) {
			this.input = ItemStack.EMPTY;
			oreInput = input;
			this.time = time;
			this.primordium = primordium;
			this.outputs = new ArrayList<>();
			processOutputs(outputs);
			id = NEXTID++;
		}
		
		private void processOutputs(Object[] args) {
			if (args.length == 1)
			{
				if (!(args[0] instanceof ItemStack)) throw new IllegalArgumentException("Lists with single outputs need to be ItemStack");
				outputs.add(new ImmutablePair<ItemStack, Integer>((ItemStack)args[0], 1));
				totalWeight = 1;
			}
			else
			{
				if (args.length <= 0) throw new IllegalArgumentException("Must have at least one output");
				if (args.length % 2 != 0) throw new IllegalArgumentException("Must have 1 or an even number of output arguments");
				
				totalWeight = 0;
				for (int i=0; i<args.length; i+=2)
				{
					if (!(args[i] instanceof ItemStack) || !(args[i+1] instanceof Integer)) throw new IllegalArgumentException("Output arguments must be alternating between ItemStacks and integers, in that order");
					Integer w = (Integer)args[i+1];
					outputs.add(new ImmutablePair<ItemStack, Integer>((ItemStack)args[i], w));
					totalWeight += w;
				}
			}
		}
		
		public boolean isOreRecipe() {
			return oreInput != null && input.isEmpty();
		}

		public ItemStack getInput() {
			return input.copy();
		}

		public String getOreInput() {
			return oreInput;
		}

		public int getTimeTicks() {
			return time;
		}

		public int getTimeProcessing() {
			return time * 10;
		}
		
		public int getPrimordiumAmount() {
			return primordium;
		}
		
		public boolean isSingleOutput() {
			return outputs.size() == 1;
		}
		
		public ItemStack getSingleOutput() {
			return outputs.get(0).getLeft().copy();
		}

		public List<Pair<ItemStack, Integer>> getWeightedOutputs() {
			return outputs;
		}
		
		public int getTotalWeight() {
			return totalWeight;
		}
		
		public ItemStack getRandomOutput(Random rand) {
			int index = rand.nextInt(totalWeight);
			
			for (Pair<ItemStack, Integer> p : outputs)
			{
				if (index < p.getRight().intValue()) return p.getLeft().copy();
				else index -= p.getRight().intValue();
			}
			
			//We shouldn't end up here
			return ItemStack.EMPTY;
		}

		public boolean isValidInput(ItemStack in) {
			if (in.isEmpty())
				return false;
			if (oreInput != null) {
				int[] oreIDs = OreDictionary.getOreIDs(in);
				for (int i : oreIDs) {
					if (OreDictionary.getOreName(i).equals(oreInput))
						return true;
				}
				return false;
			}

			return (in.isItemEqual(input) && in.getCount() >= input.getCount());
		}

		@Override
		public int compareTo(AtomicReshaperRecipe other) {
			return Integer.compareUnsigned(id, other.id);
		}
	}
}
