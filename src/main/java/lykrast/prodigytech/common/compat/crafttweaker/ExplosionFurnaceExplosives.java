package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.explosionfurnace.explosives")
@ZenRegister
public class ExplosionFurnaceExplosives {	
	//Add
	@ZenMethod
	public static void add(IItemStack explosive, int amount) {
		if (explosive == null) throw new IllegalArgumentException("Explosive cannot be null");
		if (amount <= 0) throw new IllegalArgumentException("EP amount must be positive");
		CraftTweakerAPI.apply(new Add(CraftTweakerHelper.toItemStack(explosive), amount));
	}
	
	private static class Add implements IAction {
		private final ItemStack explosive;
		private final int amount;
		
		public Add(ItemStack explosive, int amount) {
			this.explosive = explosive;
			this.amount = amount;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.addExplosive(explosive, amount);
		}

		@Override
		public String describe() {
			return "Adding Explosion Furnace explosive " + explosive.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void add(IOreDictEntry explosive, int amount) {
		if (explosive == null) throw new IllegalArgumentException("Explosive cannot be null");
		if (amount <= 0) throw new IllegalArgumentException("EP amount must be positive");
		CraftTweakerAPI.apply(new AddOre(explosive.getName(), amount));
	}
	
	private static class AddOre implements IAction {
		private final String explosive;
		private final int amount;
		
		public AddOre(String explosive, int amount) {
			this.explosive = explosive;
			this.amount = amount;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.addExplosive(explosive, amount);
		}

		@Override
		public String describe() {
			return "Adding Explosion Furnace explosive " + explosive;
		}
	}
	
	//Remove
	@ZenMethod
	public static void remove(IItemStack explosive) {
		if (explosive == null) throw new IllegalArgumentException("Explosive cannot be null");
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toItemStack(explosive)));
	}
	
	private static class Remove implements IAction {
		private final ItemStack explosive;
		
		public Remove(ItemStack explosive) {
			this.explosive = explosive;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.removeExplosive(explosive);
		}

		@Override
		public String describe() {
			return "Removing Explosion Furnace explosive " + explosive.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void remove(IOreDictEntry explosive) {
		if (explosive == null) throw new IllegalArgumentException("Explosive cannot be null");
		CraftTweakerAPI.apply(new RemoveOre(explosive.getName()));
	}
	
	private static class RemoveOre implements IAction {
		private final String explosive;
		
		public RemoveOre(String explosive) {
			this.explosive = explosive;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.removeExplosive(explosive);
		}

		@Override
		public String describe() {
			return "Removing Explosion Furnace explosive " + explosive;
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			ExplosionFurnaceManager.removeAllExplosives();
		}

		@Override
		public String describe() {
			return "Removing all Explosion Furnace explosives";
		}
	}
}
