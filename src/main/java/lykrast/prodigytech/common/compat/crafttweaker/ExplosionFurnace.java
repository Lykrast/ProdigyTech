package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.ExplosionFurnaceRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.explosionfurnace.recipes")
@ZenRegister
public class ExplosionFurnace {
	//Helpers
	private static ExplosionFurnaceRecipe recipe(IItemStack in, IItemStack out, int ep) {
		return new ExplosionFurnaceRecipe(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), ep);
	}
	private static ExplosionFurnaceRecipe recipe(IItemStack in, IItemStack out, int ep, IItemStack reagent, int craftPerReagent) {
		return new ExplosionFurnaceRecipe(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), ep, CraftTweakerHelper.toItemStack(reagent), craftPerReagent);
	}
	
	//Add
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, int ep) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (ep <= 0) throw new IllegalArgumentException("EP amount must be positive");
		CraftTweakerAPI.apply(new Add(recipe(in, out, ep)));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, int ep, IItemStack reagent, int craftPerReagent) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (reagent == null) throw new IllegalArgumentException("Reagent cannot be null with the extra reagent arguments");
		if (ep <= 0) throw new IllegalArgumentException("EP amount must be positive");
		if (craftPerReagent <= 0) throw new IllegalArgumentException("Craft per reagent must be positive");
		CraftTweakerAPI.apply(new Add(recipe(in, out, ep, reagent, craftPerReagent)));
	}
	
	private static class Add implements IAction {
		private final ExplosionFurnaceRecipe recipe;
		
		public Add(ExplosionFurnaceRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding Explosion Furnace recipe for " + recipe.getOutput().getDisplayName();
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
			//ExplosionFurnaceManager.removeRecipe(stack);
		}

		@Override
		public String describe() {
			return "Removing Explosion Furnace recipe with input " + stack.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			ExplosionFurnaceManager.removeAllRecipes();
		}

		@Override
		public String describe() {
			return "Removing all Explosion Furnace recipes";
		}
	}
}
