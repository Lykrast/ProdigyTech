package lykrast.prodigytech.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
	
	//Oredict preference stuff
	//From Immersive Engineering
	//https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/api/IEApi.java
	//https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/api/ApiUtils.java
	
	//From what mod to take oredict items in priority
	public static final List<String> MOD_PREFERENCE = new ArrayList<>();
	//Ores to not generate recipes for as they don't behave like the others
	public static final List<String> ORE_BLACKLIST = new ArrayList<>();
	static {
		MOD_PREFERENCE.add("minecraft");
		MOD_PREFERENCE.add("basemetals");
		MOD_PREFERENCE.add("baseminerals");
		MOD_PREFERENCE.add("modernmetals");
		MOD_PREFERENCE.add("nethermetals");
		MOD_PREFERENCE.add("endmetals");
		MOD_PREFERENCE.add("thermalfoundation");
		MOD_PREFERENCE.add("immersiveengineering");
		MOD_PREFERENCE.add("techreborn");
		MOD_PREFERENCE.add("ic2");
		MOD_PREFERENCE.add("mekanism");
		MOD_PREFERENCE.add("magneticraft");
		MOD_PREFERENCE.add("prodigytech");
		
		ORE_BLACKLIST.add("Coal");
		ORE_BLACKLIST.add("Lapis");
		ORE_BLACKLIST.add("Redstone");
		ORE_BLACKLIST.add("Quartz");
		ORE_BLACKLIST.add("NetherQuartz");
		ORE_BLACKLIST.add("Prismarine");
		ORE_BLACKLIST.add("Glowstone");
		ORE_BLACKLIST.add("Ferramic");
		ORE_BLACKLIST.add("Energion");
		ORE_BLACKLIST.add("Salt");
	}
	
	public static boolean oreExists(String name) {
		return OreDictionary.doesOreNameExist(name) && !OreDictionary.getOres(name, false).isEmpty();
	}
	
	public static boolean isOreBlacklisted(String reducedName) {
		return ORE_BLACKLIST.contains(reducedName);
	}
	
	private static final Map<String, ItemStack> PREFERRED_STACKS = new HashMap<>();
	
	public static ItemStack getPreferredOreStack(String ore) {
		ItemStack stack;
		if (!PREFERRED_STACKS.containsKey(ore))
		{
			stack = oreExists(ore) ? getPreferredStack(OreDictionary.getOres(ore)) : ItemStack.EMPTY;
			PREFERRED_STACKS.put(ore, stack);
		}
		else stack = PREFERRED_STACKS.get(ore);
		return stack == null ? ItemStack.EMPTY : stack.copy();
	}
	
	public static ItemStack getPreferredStack(Collection<ItemStack> candidates) {
		ItemStack preferred = ItemStack.EMPTY;
		int rank = -1;
		
		for (ItemStack stack : candidates)
		{
			if (stack.isEmpty()) continue;
			//0 is highest priority so no point searching further, probably will never be useful though
			if (rank == 0) break;
			
			ResourceLocation resource = Item.REGISTRY.getNameForObject(stack.getItem());
			if (resource == null) continue;
			
			String modid = resource.getResourceDomain();
			int curRank = modid == null || modid.isEmpty() ? -1 : MOD_PREFERENCE.indexOf(modid);
			if (preferred.isEmpty() || (curRank >= 0 && (rank < 0 || curRank < rank)))
			{
				preferred = stack;
				rank = curRank;
			}
		}
		
		return preferred.copy();
	}
}
