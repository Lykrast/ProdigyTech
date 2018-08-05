package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import lykrast.prodigytech.common.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PrimordialisReactorManager {
	private PrimordialisReactorManager() {}
	
	private static Set<Pair<Item, Integer>> inputs = new HashSet<>();
	private static Set<String> inputsOre = new HashSet<>();

	public static void addInput(Item item) {
		inputs.add(RecipeUtil.stackToPair(new ItemStack(item)));
	}
	public static void addInput(Block block) {
		inputs.add(RecipeUtil.stackToPair(new ItemStack(block)));
	}
	public static void addInput(ItemStack stack) {
		inputs.add(RecipeUtil.stackToPair(stack));
	}
	public static void addInput(String ore) {
		inputsOre.add(ore);
	}
	public static void removeInput(ItemStack stack) {
		inputs.remove(RecipeUtil.stackToPair(stack));
	}
	public static void removeInput(String ore) {
		inputsOre.remove(ore);
	}
	public static void removeAll() {
		inputs.clear();
		inputsOre.clear();
	}
	
	public static boolean isValidInput(ItemStack in) {
		if (in.isEmpty()) return false;
		if (inputs.contains(RecipeUtil.stackToPair(in)) || inputs.contains(RecipeUtil.stackToWildcardPair(in))) return true;
		
		int[] ores = OreDictionary.getOreIDs(in);
		for (int i : ores) if (inputsOre.contains(OreDictionary.getOreName(i))) return true;
		
		return false;
	}
	
	public static List<ItemStack> getAllEntries() {
		List<ItemStack> list = new ArrayList<>();
		inputs.forEach(pair -> list.add(RecipeUtil.pairToStack(pair)));
		return list;
	}
	
	public static List<String> getAllOreEntries() {
		List<String> list = new ArrayList<>(inputsOre);
		return list;
	}
	
	public static void init() {
		addInput("treeSapling");
		addInput(Items.APPLE);
		addInput("vine");
		
		addInput(Blocks.RED_MUSHROOM);
		addInput(Blocks.BROWN_MUSHROOM);
		
		addInput("cropWheat");
		addInput(Items.WHEAT_SEEDS);
		addInput("cropPotato");
		addInput("cropCarrot");
		addInput(Items.BEETROOT);
		addInput(Items.BEETROOT_SEEDS);
		addInput("cropNetherWart");
		addInput("sugarcane");
		addInput("blockCactus");
		addInput(Items.MELON);
		addInput(Items.MELON_SEEDS);
		addInput(Blocks.PUMPKIN);
		addInput(Items.PUMPKIN_SEEDS);
		addInput(new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()));
	}
}
