package lykrast.prodigytech.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SimpleRecipe implements Comparable<SimpleRecipe> {
	private static int NEXTID = 0;
	/**
	 * A simple int used to sort the unordered recipes by order of creation for JEI. It's nicer.
	 */
	private int id;
	
	protected final ItemStack input;
	protected final ItemStack output;
	protected final int time;
	protected final String oreInput;

	public SimpleRecipe(ItemStack input, ItemStack output, int time) {
		this.input = input;
		// No support for recipes requiring multiple items for now
		input.setCount(1);
		this.output = output;
		this.time = time;
		oreInput = null;
		
		id = NEXTID++;
	}

	public SimpleRecipe(String inputOre, ItemStack output, int time) {
		oreInput = inputOre;
		this.input = ItemStack.EMPTY;
		this.output = output;
		this.time = time;
		
		id = NEXTID++;
	}
	
	/**
	 * Say if this recipe has an Ore Dictionary input.
	 * @return if this recipe has an Ore Dictionary input
	 */
	public boolean isOreRecipe() {
		return oreInput != null && input.isEmpty();
	}

	public ItemStack getInput() {
		return input.copy();
	}

	public String getOreInput() {
		return oreInput;
	}

	public ItemStack getOutput() {
		return output.copy();
	}

	public int getTimeTicks() {
		return time;
	}

	public int getTimeProcessing() {
		return time * 10;
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
	public int compareTo(SimpleRecipe other) {
		return Integer.compareUnsigned(id, other.id);
	}
}
