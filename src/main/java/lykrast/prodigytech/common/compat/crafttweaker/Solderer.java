package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import lykrast.prodigytech.common.recipe.SoldererManager;
import lykrast.prodigytech.common.recipe.SoldererManager.SoldererRecipe;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.solderer")
public class Solderer {
	//Helpers
	private static SoldererRecipe recipe(IItemStack pattern, IItemStack additive, IItemStack output, int gold, int time) {
		return new SoldererRecipe(CraftTweakerHelper.toItemStack(pattern), CraftTweakerHelper.toItemStack(additive), CraftTweakerHelper.toItemStack(output), gold, time);
	}
	private static SoldererRecipe recipe(IItemStack pattern, IItemStack output, int gold, int time) {
		return recipe(pattern, null, output, gold, time);
	}
	
	//Add
	@ZenMethod
	public static void addRecipe(IItemStack pattern, IItemStack additive, IItemStack output, int gold, @Optional int time) {
		if (pattern == null) throw new IllegalArgumentException("Pattern cannot be null");
		if (output == null) throw new IllegalArgumentException("Output cannot be null");
		if (gold <= 0) throw new IllegalArgumentException("Gold amount must be positive");
		else if (gold > Config.soldererMaxGold) throw new IllegalArgumentException("Recipe requires more Gold than the Solderer is configured to hold");
		if (time <= 0) time = Config.soldererProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(pattern, additive, output, gold, time)));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack pattern, IItemStack output, int gold, @Optional int time) {
		if (pattern == null) throw new IllegalArgumentException("Pattern cannot be null");
		if (output == null) throw new IllegalArgumentException("Output cannot be null");
		if (gold <= 0) throw new IllegalArgumentException("Gold amount must be positive");
		else if (gold > Config.soldererMaxGold) throw new IllegalArgumentException("Recipe requires more Gold than the Solderer is configured to hold");
		if (time <= 0) time = Config.soldererProcessTime;
		CraftTweakerAPI.apply(new Add(recipe(pattern, output, gold, time)));
	}
	
	private static class Add implements IAction {
		private final SoldererRecipe recipe;
		
		public Add(SoldererRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			SoldererManager.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding Solderer recipe for " + recipe.getOutput().getDisplayName();
		}
	}
	
	//Remove
	@ZenMethod
	public static void removeRecipe(IItemStack pattern, IItemStack additive) {
		if (pattern == null) throw new IllegalArgumentException("Pattern cannot be null");
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toItemStack(pattern), CraftTweakerHelper.toItemStack(additive)));
	}
	
	private static class Remove implements IAction {
		private final ItemStack pattern, additive;
		
		public Remove(ItemStack pattern, ItemStack additive) {
			this.pattern = pattern;
			this.additive = additive;
		}

		@Override
		public void apply() {
			SoldererManager.removeRecipe(pattern, additive, Integer.MAX_VALUE);
		}

		@Override
		public String describe() {
			return "Removing Solderer recipe with input " + pattern.getDisplayName() + " + " + additive.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			SoldererManager.removeAll();
		}

		@Override
		public String describe() {
			return "Removing all Solderer recipes";
		}
	}
}
