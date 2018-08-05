package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import lykrast.prodigytech.common.recipe.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class CraftTweakerHelper {
	public static void preInit() {
		CraftTweakerAPI.registerClass(ExplosionFurnace.class);
		CraftTweakerAPI.registerClass(ExplosionFurnaceExplosives.class);
		CraftTweakerAPI.registerClass(RotaryGrinder.class);
		CraftTweakerAPI.registerClass(Solderer.class);
		CraftTweakerAPI.registerClass(MagneticReassembler.class);
		CraftTweakerAPI.registerClass(HeatSawmill.class);
		CraftTweakerAPI.registerClass(PrimordialisReactor.class);
		CraftTweakerAPI.registerClass(AtomicReshaper.class);
	}
	
	public static ItemStack toItemStack(IItemStack stack) {
		if (stack == null) return ItemStack.EMPTY;
		else return (ItemStack)stack.getInternal();
	}
	
	public static SimpleRecipe simpleRecipe(IItemStack in, IItemStack out, int time) {
		return new SimpleRecipe(toItemStack(in), toItemStack(out), time);
	}
	
	public static SimpleRecipe simpleRecipe(IOreDictEntry in, IItemStack out, int time) {
		return new SimpleRecipe(in.getName(), toItemStack(out), time);
	}
}
