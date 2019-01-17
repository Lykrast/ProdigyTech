package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.ExplosionFurnaceRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.explosionfurnace.recipes")
@ZenRegister
public class ExplosionFurnace {
	//Helpers to make the recipes out of the ct objects
	//Normal input, no reagent
	private static ExplosionFurnaceRecipe recipe(IItemStack in, IItemStack out, int ep) {
		return new ExplosionFurnaceRecipe(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), ep);
	}
	//Oredict input, no reagent
	private static ExplosionFurnaceRecipe recipe(IOreDictEntry in, IItemStack out, int ep) {
		return new ExplosionFurnaceRecipe(in.getName(), in.getAmount(), CraftTweakerHelper.toItemStack(out), ep);
	}
	//Normal input, normal reagent
	private static ExplosionFurnaceRecipe recipe(IItemStack in, IItemStack out, int ep, IItemStack reagent, int craftPerReagent) {
		return new ExplosionFurnaceRecipe(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), ep, CraftTweakerHelper.toItemStack(reagent), craftPerReagent);
	}
	//Oredict input, normal reagent
	private static ExplosionFurnaceRecipe recipe(IOreDictEntry in, IItemStack out, int ep, IItemStack reagent, int craftPerReagent) {
		return new ExplosionFurnaceRecipe(in.getName(), in.getAmount(), CraftTweakerHelper.toItemStack(out), ep, CraftTweakerHelper.toItemStack(reagent), craftPerReagent);
	}
	//Normal input, oredict reagent
	private static ExplosionFurnaceRecipe recipe(IItemStack in, IItemStack out, int ep, IOreDictEntry reagent, int craftPerReagent) {
		return new ExplosionFurnaceRecipe(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), ep, reagent.getName(), craftPerReagent);
	}
	//Oredict input, oredict reagent
	private static ExplosionFurnaceRecipe recipe(IOreDictEntry in, IItemStack out, int ep, IOreDictEntry reagent, int craftPerReagent) {
		return new ExplosionFurnaceRecipe(in.getName(), in.getAmount(), CraftTweakerHelper.toItemStack(out), ep, reagent.getName(), craftPerReagent);
	}
	
	//Add
	//No reagent
	@ZenMethod
	public static void addRecipe(IIngredient in, IItemStack out, int ep) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (ep <= 0) throw new IllegalArgumentException("EP amount must be positive and non null");
		
		if (in instanceof IItemStack)
			CraftTweakerAPI.apply(new Add(recipe((IItemStack)in, out, ep)));
		else if (in instanceof IOreDictEntry)
			CraftTweakerAPI.apply(new Add(recipe((IOreDictEntry)in, out, ep)));
		else if (in instanceof IOreDictEntry)
			throw new IllegalArgumentException("Input must an ItemStack or an OreDictEntry");
	}

	//Normal reagent
	@ZenMethod
	public static void addRecipe(IIngredient in, IItemStack out, int ep, IItemStack reagent, int craftPerReagent) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (reagent == null) throw new IllegalArgumentException("Reagent cannot be null with the extra reagent arguments");
		if (ep <= 0) throw new IllegalArgumentException("EP amount must be positive and non null");
		if (craftPerReagent <= 0) throw new IllegalArgumentException("Craft per reagent must be positive and non null");
		
		if (in instanceof IItemStack)
			CraftTweakerAPI.apply(new Add(recipe((IItemStack)in, out, ep, reagent, craftPerReagent)));
		else if (in instanceof IOreDictEntry)
			CraftTweakerAPI.apply(new Add(recipe((IOreDictEntry)in, out, ep, reagent, craftPerReagent)));
		else if (in instanceof IOreDictEntry)
			throw new IllegalArgumentException("Input must an ItemStack or an OreDictEntry");
	}

	//Oredict reagent
	@ZenMethod
	public static void addRecipe(IIngredient in, IItemStack out, int ep, IOreDictEntry reagent, int craftPerReagent) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		if (out == null) throw new IllegalArgumentException("Output cannot be null");
		if (reagent == null) throw new IllegalArgumentException("Reagent cannot be null with the extra reagent arguments");
		if (ep <= 0) throw new IllegalArgumentException("EP amount must be positive and non null");
		if (craftPerReagent <= 0) throw new IllegalArgumentException("Craft per reagent must be positive and non null");
		
		if (in instanceof IItemStack)
			CraftTweakerAPI.apply(new Add(recipe((IItemStack)in, out, ep, reagent, craftPerReagent)));
		else if (in instanceof IOreDictEntry)
			CraftTweakerAPI.apply(new Add(recipe((IOreDictEntry)in, out, ep, reagent, craftPerReagent)));
		else if (in instanceof IOreDictEntry)
			throw new IllegalArgumentException("Input must an ItemStack or an OreDictEntry");
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
