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

@ZenClass("mods.prodigytech.explosionfurnace.dampeners")
@ZenRegister
public class ExplosionFurnaceDampeners {	
	//Add
	@ZenMethod
	public static void add(IItemStack dampener, int amount) {
		if (dampener == null) throw new IllegalArgumentException("Dampener cannot be null");
		if (amount <= 0) throw new IllegalArgumentException("EP amount must be positive");
		CraftTweakerAPI.apply(new Add(CraftTweakerHelper.toItemStack(dampener), amount));
	}
	
	private static class Add implements IAction {
		private final ItemStack dampener;
		private final int amount;
		
		public Add(ItemStack dampener, int amount) {
			this.dampener = dampener;
			this.amount = amount;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.addDampener(dampener, amount);
		}

		@Override
		public String describe() {
			return "Adding Explosion Furnace dampener " + dampener.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void add(IOreDictEntry dampener, int amount) {
		if (dampener == null) throw new IllegalArgumentException("Dampener cannot be null");
		if (amount <= 0) throw new IllegalArgumentException("EP amount must be positive");
		CraftTweakerAPI.apply(new AddOre(dampener.getName(), amount));
	}
	
	private static class AddOre implements IAction {
		private final String dampener;
		private final int amount;
		
		public AddOre(String dampener, int amount) {
			this.dampener = dampener;
			this.amount = amount;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.addDampener(dampener, amount);
		}

		@Override
		public String describe() {
			return "Adding Explosion Furnace dampener " + dampener;
		}
	}
	
	//Remove
	@ZenMethod
	public static void remove(IItemStack dampener) {
		if (dampener == null) throw new IllegalArgumentException("Dampener cannot be null");
		CraftTweakerAPI.apply(new Remove(CraftTweakerHelper.toItemStack(dampener)));
	}
	
	private static class Remove implements IAction {
		private final ItemStack dampener;
		
		public Remove(ItemStack dampener) {
			this.dampener = dampener;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.removeDampener(dampener);
		}

		@Override
		public String describe() {
			return "Removing Explosion Furnace dampener " + dampener.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void remove(IOreDictEntry dampener) {
		if (dampener == null) throw new IllegalArgumentException("Dampener cannot be null");
		CraftTweakerAPI.apply(new RemoveOre(dampener.getName()));
	}
	
	private static class RemoveOre implements IAction {
		private final String dampener;
		
		public RemoveOre(String dampener) {
			this.dampener = dampener;
		}

		@Override
		public void apply() {
			ExplosionFurnaceManager.removeDampener(dampener);
		}

		@Override
		public String describe() {
			return "Removing Explosion Furnace dampener " + dampener;
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			ExplosionFurnaceManager.removeAllDampeners();
		}

		@Override
		public String describe() {
			return "Removing all Explosion Furnace dampeners";
		}
	}
}
