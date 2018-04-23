package lykrast.prodigytech.common.recipe;

import net.minecraft.item.ItemStack;

public class SimpleRecipeSecondaryOutput extends SimpleRecipe {
	protected ItemStack secondaryOutput;

	public SimpleRecipeSecondaryOutput(ItemStack input, ItemStack output, int time) {
		this(input, output, ItemStack.EMPTY, time);
	}
	
	public SimpleRecipeSecondaryOutput(String inputOre, ItemStack output, int time) {
		this(inputOre, output, ItemStack.EMPTY, time);
	}

	public SimpleRecipeSecondaryOutput(ItemStack input, ItemStack output, ItemStack secondaryOutput, int time) {
		super(input, output, time);
		this.secondaryOutput = secondaryOutput;
	}
	
	public SimpleRecipeSecondaryOutput(String inputOre, ItemStack output, ItemStack secondaryOutput, int time) {
		super(inputOre, output, time);
		this.secondaryOutput = secondaryOutput;
	}

	public ItemStack getSecondaryOutput() {
		if (secondaryOutput.isEmpty()) return ItemStack.EMPTY;
		return secondaryOutput.copy();
	}
	
	public boolean hasSecondaryOutput() {
		return !secondaryOutput.isEmpty();
	}

}
