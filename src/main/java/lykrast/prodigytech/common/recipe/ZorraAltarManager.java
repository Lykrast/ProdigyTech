package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ZorraAltarManager {
	public static final ZorraAltarManager SWORD = new ZorraAltarManager();
	
	public static void init() {
		SWORD.addEnchant(Enchantments.SHARPNESS, 8);
		SWORD.addEnchant(Enchantments.SMITE, 8);
		SWORD.addEnchant(Enchantments.BANE_OF_ARTHROPODS, 8);
		SWORD.addEnchant(Enchantments.FIRE_ASPECT, 5);
		SWORD.addEnchant(Enchantments.KNOCKBACK, 5);
		SWORD.addEnchant(Enchantments.LOOTING, 6);
		SWORD.addEnchant(Enchantments.SWEEPING, 6);
	}
	
	private List<EnchantmentData> enchants;
	
	public ZorraAltarManager() {
		enchants = new ArrayList<>();
	}
	
	public void addEnchant(EnchantmentData enchant) {
		enchants.add(enchant);
	}
	public void addEnchant(Enchantment enchant, int maxLvl) {
		enchants.add(new EnchantmentData(enchant, maxLvl));
	}
	
	public int getLevelCost(EnchantmentData data) {
		int lvl = data.enchantmentLevel;
		if (lvl <= 1) return data.enchantment.getMinEnchantability(1);
		else return data.enchantment.getMinEnchantability(lvl) - (data.enchantment.getMinEnchantability(lvl - 1) / 2);
	}
	
	public List<EnchantmentData> getAvailableEnchants(ItemStack stack) {
		List<EnchantmentData> list = new ArrayList<>();
		Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(stack);
		for (EnchantmentData data : enchants) {
			Integer lvl = map.get(data.enchantment);
			if (lvl == null) list.add(new EnchantmentData(data.enchantment, 1));
			else if (lvl < data.enchantmentLevel) list.add(new EnchantmentData(data.enchantment, lvl + 1));
		}
		
		return list;
	}
	
	public EnchantmentData[] getRandomEnchants(ItemStack stack, Random rand) {
		List<EnchantmentData> apply = new ArrayList<>();
		List<EnchantmentData> upgrade = new ArrayList<>();
		
		Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(stack);
		for (EnchantmentData data : enchants) {
			Integer lvl = map.get(data.enchantment);
			if (lvl == null) apply.add(new EnchantmentData(data.enchantment, 1));
			else if (lvl < data.enchantmentLevel) upgrade.add(new EnchantmentData(data.enchantment, lvl + 1));
		}
		
		EnchantmentData[] datas = new EnchantmentData[3];
		
		if (!apply.isEmpty() || !upgrade.isEmpty())
		{
			//1st is always a new enchant if possible
			if (!apply.isEmpty()) {
				int i = rand.nextInt(apply.size());
				datas[0] = apply.remove(i);
			}
			else {
				int i = rand.nextInt(upgrade.size());
				datas[0] = upgrade.remove(i);
			}
			
			//2nd is always an upgrade if possible
			if (!upgrade.isEmpty()) {
				int i = rand.nextInt(upgrade.size());
				datas[1] = upgrade.remove(i);
			}
			else if (!apply.isEmpty()) {
				int i = rand.nextInt(apply.size());
				datas[1] = apply.remove(i);
			}
			
			//3rd is random
			apply.addAll(upgrade);
			if (!apply.isEmpty()) {
				int i = rand.nextInt(apply.size());
				datas[2] = apply.remove(i);
			}
		}
			
		return datas;
	}
}
