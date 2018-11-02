package lykrast.prodigytech.common.recipe;

import java.util.Random;

import net.minecraft.item.ItemStack;

public class SimpleRecipeSecondaryOutput extends SimpleRecipe {
	protected ItemStack secondaryOutput;
	private float secondaryChance;

	public SimpleRecipeSecondaryOutput(ItemStack input, ItemStack output, int time) {
		this(input, output, ItemStack.EMPTY, time);
	}
	
	public SimpleRecipeSecondaryOutput(String inputOre, ItemStack output, int time) {
		this(inputOre, output, ItemStack.EMPTY, time);
	}

	public SimpleRecipeSecondaryOutput(ItemStack input, ItemStack output, ItemStack secondaryOutput, int time) {
		this(input, output, secondaryOutput, time, 1);
	}
	
	public SimpleRecipeSecondaryOutput(String inputOre, ItemStack output, ItemStack secondaryOutput, int time) {
		this(inputOre, output, secondaryOutput, time, 1);
	}

	public SimpleRecipeSecondaryOutput(ItemStack input, ItemStack output, ItemStack secondaryOutput, int time, float secondaryChance) {
		super(input, output, time);
		this.secondaryOutput = secondaryOutput;
		this.secondaryChance = secondaryChance;
	}
	
	public SimpleRecipeSecondaryOutput(String inputOre, ItemStack output, ItemStack secondaryOutput, int time, float secondaryChance) {
		super(inputOre, output, time);
		this.secondaryOutput = secondaryOutput;
		this.secondaryChance = secondaryChance;
	}

	public ItemStack getSecondaryOutput() {
		if (secondaryOutput.isEmpty()) return ItemStack.EMPTY;
		return secondaryOutput.copy();
	}
	
	public boolean hasSecondaryOutput() {
		return !secondaryOutput.isEmpty();
	}
	
	public float getSecondaryChance() {
		return secondaryChance;
	}
	
	public boolean shouldSecondaryOutput(Random rand) {
		if (secondaryOutput.isEmpty()) return false;
		if (secondaryChance >= 1) return true;
		
		return rand.nextFloat() < secondaryChance;
	}

}
