package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.HeatSawmillManager;
import lykrast.prodigytech.common.recipe.SimpleRecipeSecondaryOutput;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.heatsawmill")
public class HeatSawmill {
	//Helpers
	private static SimpleRecipeSecondaryOutput recipe(IItemStack in, IItemStack out, int time) {
		return new SimpleRecipeSecondaryOutput(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), time);
	}
	private static SimpleRecipeSecondaryOutput recipe(IItemStack in, IItemStack out, IItemStack sec, int time) {
		return new SimpleRecipeSecondaryOutput(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), CraftTweakerHelper.toItemStack(sec), time);
	}
	private static SimpleRecipeSecondaryOutput recipe(IOreDictEntry in, IItemStack out, int time) {
		return new SimpleRecipeSecondaryOutput(in.getName(), CraftTweakerHelper.toItemStack(out), time);
	}
	private static SimpleRecipeSecondaryOutput recipe(IOreDictEntry in, IItemStack out, IItemStack sec, int time) {
		return new SimpleRecipeSecondaryOutput(in.getName(), CraftTweakerHelper.toItemStack(out), CraftTweakerHelper.toItemStack(sec), time);
	}
	
	//Add
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, @Optional int time) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (time <= 0) time = Config.heatSawmillProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, out, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, IItemStack sec, @Optional int time) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (time <= 0) time = Config.heatSawmillProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, out, sec, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IOreDictEntry in, IItemStack out, @Optional int time) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (time <= 0) time = Config.heatSawmillProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, out, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IOreDictEntry in, IItemStack out, IItemStack sec, @Optional int time) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (time <= 0) time = Config.heatSawmillProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, out, sec, time)));
	}
	
	private static class Add implements IAction {
		private final SimpleRecipeSecondaryOutput recipe;
		
		public Add(SimpleRecipeSecondaryOutput recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			HeatSawmillManager.INSTANCE.addRecipe(recipe);
		}

		@Override
		public String describe() {
			String message = "Adding Heat Sawmill recipe for " + recipe.getOutput().getDisplayName();
			if (recipe.hasSecondaryOutput()) message += " + " + recipe.getSecondaryOutput().getDisplayName();
			
			return message;
		}
	}
	
	//Remove
	@ZenMethod
	public static void removeRecipe(IItemStack in) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toItemStack(in)));
	}
	
	private static class Remove implements IAction {
		private final ItemStack stack;
		
		public Remove(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public void apply() {
			HeatSawmillManager.INSTANCE.removeRecipe(stack);
		}

		@Override
		public String describe() {
			return "Removing Heat Sawmill recipe with input " + stack.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IOreDictEntry in) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		CraftTweakerAPI.apply(new RemoveOre(in.getName()));
	}
	
	private static class RemoveOre implements IAction {
		private final String ore;
		
		public RemoveOre(String ore) {
			this.ore = ore;
		}

		@Override
		public void apply() {
			HeatSawmillManager.INSTANCE.removeOreRecipe(ore);
		}

		@Override
		public String describe() {
			return "Removing Heat Sawmill recipe with input " + ore;
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			HeatSawmillManager.INSTANCE.removeAll();
		}

		@Override
		public String describe() {
			return "Removing all Heat Sawmill recipes";
		}
	}
}
