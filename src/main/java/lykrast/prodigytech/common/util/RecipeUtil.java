package lykrast.prodigytech.common.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeUtil {
	private RecipeUtil() {}
	
	public static Pair<Item, Integer> stackToPair(ItemStack stack) {
		return new ImmutablePair<Item, Integer>(stack.getItem(), stack.getMetadata());
	}
	
	public static Pair<Item, Integer> stackToWildcardPair(ItemStack stack) {
		return new ImmutablePair<Item, Integer>(stack.getItem(), OreDictionary.WILDCARD_VALUE);
	}
	
	public static ItemStack pairToStack(Pair<Item, Integer> pair) {
		return new ItemStack(pair.getLeft(), 1, pair.getRight().intValue());
	}

}
