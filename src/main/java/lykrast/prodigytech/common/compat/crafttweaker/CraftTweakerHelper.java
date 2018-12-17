package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class CraftTweakerHelper {
	public static ItemStack toItemStack(IItemStack stack) {
		if (stack == null) return ItemStack.EMPTY;
		else return (ItemStack)stack.getInternal();
	}
	
	public static SimpleRecipe simpleRecipe(IItemStack in, IItemStack out, int time) {
		return new SimpleRecipe(toItemStack(in), toItemStack(out), time);
	}
	
	public static SimpleRecipe simpleRecipe(IOreDictEntry in, IItemStack out, int time) {
		return new SimpleRecipe(in.getName(), toItemStack(out), time);
	}
}
