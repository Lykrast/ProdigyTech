package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.ZorraAltarManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemZorrasteelSword extends ItemSword implements IZorrasteelEquipment {

	public ItemZorrasteelSword(ToolMaterial material) {
		super(material);
	}

	@Override
	public ZorraAltarManager getManager() {
		return ZorraAltarManager.SWORD;
	}

	//Zorrasteel tools are only enchantable on the Zorra Altar
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
}
