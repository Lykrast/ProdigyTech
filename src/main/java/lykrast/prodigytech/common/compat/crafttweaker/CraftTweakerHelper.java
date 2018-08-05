package lykrast.prodigytech.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;

public class CraftTweakerHelper {
	public static void preInit() {
		CraftTweakerAPI.registerClass(RotaryGrinder.class);
	}
	
	public static ItemStack toItemStack(IItemStack stack) {
		if (stack == null) return ItemStack.EMPTY;
		else return (ItemStack)stack.getInternal();
	}
}
