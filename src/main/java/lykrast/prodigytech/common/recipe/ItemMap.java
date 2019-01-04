package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import lykrast.prodigytech.common.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMap<T> {
	private final HashMap<Pair<Item, Integer>, T> normal;
	private final HashMap<String, T> ore;
	
	public ItemMap() {
		normal = new HashMap<>();
		ore = new HashMap<>();
	}
	
	public T add(ItemStack stack, T thing) {
		normal.put(RecipeUtil.stackToPair(stack), thing);
		return thing;
	}
	
	public T add(String ore, T thing) {
		this.ore.put(ore, thing);
		return thing;
	}
	
	public List<T> getAllContent() {
		List<T> list = new ArrayList<>();
		list.addAll(normal.values());
		list.addAll(ore.values());
		
		return list;
	}
	
	@Nullable
	public T find(ItemStack in) {
		if (in.isEmpty()) return null;
		T recipe = normal.get(RecipeUtil.stackToPair(in));
		if (recipe != null) return recipe;
		recipe = normal.get(RecipeUtil.stackToWildcardPair(in));
		if (recipe != null) return recipe;
		
		//Check for ore
		int[] ores = OreDictionary.getOreIDs(in);
		for (int i : ores)
		{
			recipe = findOre(OreDictionary.getOreName(i));
			if (recipe != null) return recipe;
		}
		
		return null;
	}
	
	public boolean isValid(ItemStack check) {
		return find(check) != null;
	}
	
	@Nullable
	public T findOre(String in) {
		return ore.get(in);
	}
	
	@Nullable
	public T remove(ItemStack in) {
		T removed = normal.remove(RecipeUtil.stackToPair(in));
		if (removed != null) return removed;
		
		return normal.remove(RecipeUtil.stackToWildcardPair(in));
	}
	
	@Nullable
	public T remove(String in) {
		return ore.remove(in);
	}
	
	/**
	 * Removes all registered normal.
	 */
	public void clear() {
		normal.clear();
		ore.clear();
	}

}
