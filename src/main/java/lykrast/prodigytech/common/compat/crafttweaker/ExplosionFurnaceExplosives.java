package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.explosionfurnace.explosives")
@ZenRegister
public class ExplosionFurnaceExplosives {	
	//Add
	@ZenMethod
	public static void addPair(IItemStack explosive, IItemStack reactant, int amount) {
		if (explosive == null) throw new IllegalArgumentException("Explosive cannot be null");
		if (reactant == null) throw new IllegalArgumentException("Reactant cannot be null");
		if (amount <= 0) throw new IllegalArgumentException("EP amount must be positive");
		CraftTweakerAPI.apply(new Add(CraftTweakerHelper.toItemStack(explosive), CraftTweakerHelper.toItemStack(reactant), amount));
	}
	
	private static class Add implements IAction {
		private final ItemStack explosive, reactant;
		private final int amount;
		
		public Add(ItemStack explosive, ItemStack reactant, int amount) {
			this.explosive = explosive;
			this.reactant = reactant;
			this.amount = amount;
		}

		@Override
		public void apply() {
			//ExplosionFurnaceManager.addExplosive(explosive, reactant, amount);
		}

		@Override
		public String describe() {
			return "Adding Explosion Furnace explosive pair " + explosive.getDisplayName() + " + " + reactant.getDisplayName();
		}
	}
	
	//Remove
	@ZenMethod
	public static void removePair(IItemStack explosive, IItemStack reactant) {
		if (explosive == null) throw new IllegalArgumentException("Explosive cannot be null");
		if (reactant == null) throw new IllegalArgumentException("Reactant cannot be null");
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toItemStack(explosive), CraftTweakerHelper.toItemStack(reactant)));
	}
	
	private static class Remove implements IAction {
		private final ItemStack explosive, reactant;
		
		public Remove(ItemStack explosive, ItemStack reactant) {
			this.explosive = explosive;
			this.reactant = reactant;
		}

		@Override
		public void apply() {
			//ExplosionFurnaceManager.removeExplosive(explosive, reactant);
		}

		@Override
		public String describe() {
			return "Removing Explosion Furnace explosive pair " + explosive.getDisplayName() + " + " + reactant.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			//ExplosionFurnaceManager.removeAllExplosives();
		}

		@Override
		public String describe() {
			return "Removing all Explosion Furnace explosive pairs";
		}
	}
}
