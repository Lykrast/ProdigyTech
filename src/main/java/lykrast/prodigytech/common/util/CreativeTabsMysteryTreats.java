package lykrast.prodigytech.common.util;

import lykrast.prodigytech.common.item.ItemMysteryTreat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class CreativeTabsMysteryTreats extends CreativeTabs {
	
	//public static final CreativeTabs INSTANCE = new CreativeTabsMysteryTreats(CreativeTabs.getNextID(), "ptmysterytreats");

	public CreativeTabsMysteryTreats(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return ItemMysteryTreat.createWithEffect(new PotionEffect(MobEffects.SPEED), new PotionEffect(MobEffects.RESISTANCE));
	}

}
