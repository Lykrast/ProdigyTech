package lykrast.prodigytech.common.compat.crafttweaker;

import java.util.Arrays;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager.AtomicReshaperRecipe;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.atomicreshaper")
public class AtomicReshaper {
	//Helpers
	private static AtomicReshaperRecipe recipe(IItemStack in, IItemStack out, int primordium, int time) {
		return new AtomicReshaperRecipe(CraftTweakerHelper.toItemStack(in), time, primordium, CraftTweakerHelper.toItemStack(out));
	}
	private static AtomicReshaperRecipe recipe(IOreDictEntry in, IItemStack out, int primordium, int time) {
		return new AtomicReshaperRecipe(in.getName(), time, primordium, CraftTweakerHelper.toItemStack(out));
	}
	private static AtomicReshaperRecipe recipe(IItemStack in, int primordium, int time, IItemStack[] outputs, int[] weights) {
		Object[] args = new Object[outputs.length * 2];
		for (int i=0; i<outputs.length; i++) {
			args[2*i] = CraftTweakerHelper.toItemStack(outputs[i]);
			args[2*i+1] = weights[i];
		}
		return new AtomicReshaperRecipe(CraftTweakerHelper.toItemStack(in), time, primordium, args);
	}
	
	//Add
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, int primordium, @Optional int time) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (primordium <= 0) throw new IllegalArgumentException("Primordium unit amount must be positive");
		else if (primordium > Config.atomicReshaperMaxPrimordium * 100) throw new IllegalArgumentException("Recipe requires more Primordium units than the Atomic Reshaper is configured to hold");
		else if (primordium > (Config.atomicReshaperMaxPrimordium - 1) * 100) CraftTweakerAPI.logWarning("Recipe requires too many Primordium units to be reliably made, consider reducing it to " + ((Config.atomicReshaperMaxPrimordium - 1) * 100) + " or less or increase the Atomic Reshaper's Primordium capacity");
		if (time <= 0) time = Config.atomicReshaperProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, out, primordium, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IOreDictEntry in, IItemStack out, int primordium, @Optional int time) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (primordium <= 0) throw new IllegalArgumentException("Primordium unit amount must be positive");
		else if (primordium > Config.atomicReshaperMaxPrimordium * 100) throw new IllegalArgumentException("Recipe requires more Primordium units than the Atomic Reshaper is configured to hold");
		else if (primordium > (Config.atomicReshaperMaxPrimordium - 1) * 100) CraftTweakerAPI.logWarning("Recipe requires too many Primordium units to be reliably made, consider reducing it to " + ((Config.atomicReshaperMaxPrimordium - 1) * 100) + " or less or increase the Atomic Reshaper's Primordium capacity");
		if (time <= 0) time = Config.atomicReshaperProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, out, primordium, time)));
	}
	
	@ZenMethod
	public static void addRecipeMulti(IItemStack in, int primordium, int time, IItemStack[] outputs, @Optional int[] weights) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (outputs == null) throw new IllegalArgumentException("Output array cannot be null");
		if (weights == null) {
			weights = new int[outputs.length];
			Arrays.fill(weights, 1);
		}
		else if (weights.length != outputs.length) throw new IllegalArgumentException("Output array and weight array must have the same length");
		if (primordium <= 0) throw new IllegalArgumentException("Primordium unit amount must be positive");
		else if (primordium > Config.atomicReshaperMaxPrimordium * 100) throw new IllegalArgumentException("Recipe requires more Primordium units than the Atomic Reshaper is configured to hold");
		else if (primordium > (Config.atomicReshaperMaxPrimordium - 1) * 100) CraftTweakerAPI.logWarning("Recipe requires too many Primordium units to be reliably made, consider reducing it to " + ((Config.atomicReshaperMaxPrimordium - 1) * 100) + " or less or increase the Atomic Reshaper's Primordium capacity");
		if (time <= 0) time = Config.atomicReshaperProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(in, primordium, time, outputs, weights)));
	}
	
	private static class Add implements IAction {
		private final AtomicReshaperRecipe recipe;
		
		public Add(AtomicReshaperRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			AtomicReshaperManager.INSTANCE.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding Atomic Reshaper recipe with input " + recipe.getInput().getDisplayName();
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
			AtomicReshaperManager.INSTANCE.removeRecipe(stack);
		}

		@Override
		public String describe() {
			return "Removing Atomic Reshaper recipe with input " + stack.getDisplayName();
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
			AtomicReshaperManager.INSTANCE.removeOreRecipe(ore);
		}

		@Override
		public String describe() {
			return "Removing Atomic Reshaper recipe with input " + ore;
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			AtomicReshaperManager.INSTANCE.removeAll();
		}

		@Override
		public String describe() {
			return "Removing all Atomic Reshaper recipes";
		}
	}
}
