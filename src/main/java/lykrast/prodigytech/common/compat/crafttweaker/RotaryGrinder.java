package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.SimpleRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.rotarygrinder")
public class RotaryGrinder {
	@ZenMethod
	public static void addRecipe(IItemStack in, IItemStack out, int time) {
		if (in == null || out == null || time <= 0) return;
		
		SimpleRecipe recipe = new SimpleRecipe(CraftTweakerHelper.toItemStack(in), CraftTweakerHelper.toItemStack(out), time);
		CraftTweakerAPI.apply(new Add(recipe));
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
}
