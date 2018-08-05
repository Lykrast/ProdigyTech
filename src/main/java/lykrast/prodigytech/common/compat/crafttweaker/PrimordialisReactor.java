package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.PrimordialisReactorManager;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.prodigytech.primordialisreactor")
public class PrimordialisReactor {
	//Add
	@ZenMethod
	public static void addInput(IItemStack in) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		CraftTweakerAPI.apply(new Add(CraftTweakerHelper.toItemStack(in)));
	}
	
	private static class Add implements IAction {
		private final ItemStack stack;
		
		public Add(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public void apply() {
			PrimordialisReactorManager.addInput(stack);
		}

		@Override
		public String describe() {
			return "Adding Primordialis Reactor input " + stack.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void addInput(IOreDictEntry in) {
		if (in == null) throw new IllegalArgumentException("Input cannot be null");
		CraftTweakerAPI.apply(new AddOre(in.getName()));
	}
	
	private static class AddOre implements IAction {
		private final String ore;
		
		public AddOre(String ore) {
			this.ore = ore;
		}

		@Override
		public void apply() {
			PrimordialisReactorManager.addInput(ore);
		}

		@Override
		public String describe() {
			return "Adding Primordialis Reactor input " + ore;
		}
	}
	
	//Remove
	@ZenMethod
	public static void removeInput(IItemStack in) {
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
			PrimordialisReactorManager.removeInput(stack);
		}

		@Override
		public String describe() {
			return "Removing Primordialis Reactor input " + stack.getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeInput(IOreDictEntry in) {
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
			PrimordialisReactorManager.removeInput(ore);
		}

		@Override
		public String describe() {
			return "Removing Primordialis Reactor input " + ore;
		}
	}
	
	@ZenMethod
	public static void removeAll() {
		CraftTweakerAPI.apply(new RemoveAll());
	}
	
	private static class RemoveAll implements IAction {
		@Override
		public void apply() {
			PrimordialisReactorManager.removeAll();
		}

		@Override
		public String describe() {
			return "Removing all Primordialis Reactor inputs";
		}
	}
}
