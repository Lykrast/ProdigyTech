package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.SimpleRecipe;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.rotarygrinder")
public class RotaryGrinder {
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, int time) {
		if (in == null || out == null || time <= 0) return;
		CraftTweakerAPI.apply(new Add(CraftTweakerHelper.simpleRecipe(in, out, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IOreDictEntry in, IItemStack out, int time) {
		if (in == null || out == null || time <= 0) return;
		CraftTweakerAPI.apply(new Add(CraftTweakerHelper.simpleRecipe(in, out, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out) {
		addRecipe(in, out, Config.rotaryGrinderProcessTime);
	}
	
	@ZenMethod
	public static void addRecipe(IOreDictEntry in, IItemStack out) {
		addRecipe(in, out, Config.rotaryGrinderProcessTime);
	}
	
	private static class Add implements IAction {
		private final SimpleRecipe recipe;
		
		public Add(SimpleRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			RotaryGrinderManager.INSTANCE.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding Rotary Grinder recipe for " + recipe.getOutput().getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack in) {
		if (in == null) return;
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toItemStack(in)));
	}
	
	private static class Remove implements IAction {
		private final ItemStack stack;
		
		public Remove(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public void apply() {
			RotaryGrinderManager.INSTANCE.removeRecipe(stack);
		}

		@Override
		public String describe() {
			return "Removing Rotary Grinder recipe with input " + stack.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IOreDictEntry in) {
		if (in == null) return;
		CraftTweakerAPI.apply(new RemoveOre(in.getName()));
	}
	
	private static class RemoveOre implements IAction {
		private final String ore;
		
		public RemoveOre(String ore) {
			this.ore = ore;
		}

		@Override
		public void apply() {
			RotaryGrinderManager.INSTANCE.removeOreRecipe(ore);
		}

		@Override
		public String describe() {
			return "Removing Rotary Grinder recipe with input " + ore;
		}
	}
}
