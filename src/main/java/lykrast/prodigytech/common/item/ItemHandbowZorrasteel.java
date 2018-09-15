package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.recipe.ZorraAltarManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class ItemHandbowZorrasteel extends ItemHandbow implements IZorrasteelEquipment {

	public ItemHandbowZorrasteel() {
		super(0);
	}

	@Override
	public ZorraAltarManager getManager() {
		return ZorraAltarManager.BOW;
	}

	//Zorrasteel tools are only enchantable on the Zorra Altar
	
	@Override
    public int getItemEnchantability() {
        return 1;
    }
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
}
