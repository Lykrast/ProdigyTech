package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lykrast.prodigytech.common.util.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ZorraAltarManager {
	public static final ZorraAltarManager 
			SWORD = new ZorraAltarManager(),
			BOW = new ZorraAltarManager();
	
	public static void init() {
		//Vanilla
		SWORD.addEnchantBonusLevel(Enchantments.SHARPNESS, Config.altarBonusLvl);
		SWORD.addEnchantBonusLevel(Enchantments.SMITE, Config.altarBonusLvl);
		SWORD.addEnchantBonusLevel(Enchantments.BANE_OF_ARTHROPODS, Config.altarBonusLvl);
		SWORD.addEnchantBonusLevel(Enchantments.FIRE_ASPECT, Config.altarBonusLvl);
		SWORD.addEnchantBonusLevel(Enchantments.KNOCKBACK, Config.altarBonusLvl);
		SWORD.addEnchantBonusLevel(Enchantments.LOOTING, Config.altarBonusLvl);
		SWORD.addEnchantBonusLevel(Enchantments.SWEEPING, Config.altarBonusLvl);
		
		BOW.addEnchantBonusLevel(Enchantments.POWER, Config.altarBonusLvl);
		BOW.addEnchantBonusLevel(Enchantments.PUNCH, Config.altarBonusLvl);
		BOW.addEnchant(Enchantments.FLAME, 1);
		BOW.addEnchant(Enchantments.INFINITY, 1);

		ItemStack checkerSword = new ItemStack(Items.IRON_SWORD);
		ItemStack checkerBow = new ItemStack(Items.BOW);
		//We only want 1 Soulbound
		boolean hasSoulbound = false;

		//EnderCore
		if (Loader.isModLoaded("endercore")) {
			SWORD.addModdedEnchantBonusLevel("endercore:xpboost", Config.altarBonusLvl, checkerSword);
			BOW.addModdedEnchantBonusLevel("endercore:xpboost", Config.altarBonusLvl, checkerBow);
		}
		
		//Ender IO
		if (Loader.isModLoaded("enderio")) {
			SWORD.addModdedEnchant("enderio:witherweapon", 1, checkerSword);
			BOW.addModdedEnchant("enderio:witherarrow", 1, checkerBow);
			
			Enchantment soulbound = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("enderio:soulbound"));
			if (!hasSoulbound && soulbound != null && (soulbound.canApply(checkerSword) || soulbound.canApply(checkerBow))) {
				hasSoulbound = true;
				SWORD.addEnchant(soulbound, 1);
				BOW.addEnchant(soulbound, 1);
			}
		}
		
		//CoFH Core
		if (Loader.isModLoaded("cofhcore")) {
			//CoFH Core makes enchants unapplicable and useless instead of not registering them when disabled individually
			//So we use this dummy ItemStack to check if it's applicable
			SWORD.addModdedEnchantBonusLevel("cofhcore:insight", Config.altarBonusLvl, checkerSword);
			SWORD.addModdedEnchantBonusLevel("cofhcore:leech", Config.altarBonusLvl, checkerSword);
			SWORD.addModdedEnchantBonusLevel("cofhcore:vorpal", Config.altarBonusLvl, checkerSword);
			BOW.addModdedEnchantBonusLevel("cofhcore:insight", Config.altarBonusLvl, checkerBow);
			//Multishot doesn't work :(
			//BOW.addModdedEnchantBonusLevel("cofhcore:multishot", 0, checkerBow);

			Enchantment soulbound = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("cofhcore:soulbound"));
			if (!hasSoulbound && soulbound != null && (soulbound.canApply(checkerSword) || soulbound.canApply(checkerBow))) {
				hasSoulbound = true;
				//Check if it was configured to be permanent
				if (soulbound.getMaxLevel() == 1) {
					SWORD.addEnchant(soulbound, 1);
					BOW.addEnchant(soulbound, 1);
				}
				else {
					SWORD.addEnchantBonusLevel(soulbound, Config.altarBonusLvl);
					BOW.addEnchantBonusLevel(soulbound, Config.altarBonusLvl);
				}
			}
		}
		
		//Cyclic
		if (Loader.isModLoaded("cyclicmagic")) {
			SWORD.addModdedEnchant("cyclicmagic:enchantment.beheading", 1, checkerSword);
			SWORD.addModdedEnchantBonusLevel("cyclicmagic:enchantment.lifeleech", Config.altarBonusLvl, checkerSword);
			SWORD.addModdedEnchantBonusLevel("cyclicmagic:enchantment.venom", Config.altarBonusLvl, checkerSword);
			BOW.addModdedEnchant("cyclicmagic:enchantment.quickdraw", 1, checkerBow);
			//Bugged
			//BOW.addModdedEnchant("cyclicmagic:enchantment.multishot", 1, checkerBow);
		}
		
		//Draconic Evolution
		if (Loader.isModLoaded("draconicevolution")) SWORD.addModdedEnchantBonusLevel("draconicevolution:enchant_reaper", Config.altarBonusLvl, checkerSword);
		
		//AbyssalCraft
		if (Loader.isModLoaded("abyssalcraft")) SWORD.addModdedEnchantBonusLevel("abyssalcraft:light_pierce", Config.altarBonusLvl, checkerSword);
		
		//Soul Shards Respawn
		if (Loader.isModLoaded("soulshardsrespawn")) SWORD.addModdedEnchantBonusLevel("soulshardsrespawn:soul_stealer", Config.altarBonusLvl, checkerSword);
		
		//EvilCraft
		if (Loader.isModLoaded("evilcraft")) {
			SWORD.addModdedEnchantBonusLevel("evilcraft:life_stealing", Config.altarBonusLvl, checkerSword);
			BOW.addModdedEnchantBonusLevel("evilcraft:poison_tip", Config.altarBonusLvl, checkerBow);
		}
		
		//Woot
		if (Loader.isModLoaded("woot")) SWORD.addModdedEnchantBonusLevel("woot:headhunter", 0, checkerSword);
		
		//Apotheosis
		if (Loader.isModLoaded("apotheosis")) {
			SWORD.addModdedEnchantBonusLevel("apotheosis:hell_infusion", Config.altarBonusLvl, checkerSword);
			SWORD.addModdedEnchantBonusLevel("apotheosis:mounted_strike", Config.altarBonusLvl, checkerSword);
			SWORD.addModdedEnchantBonusLevel("apotheosis:scavenger", Config.altarBonusLvl, checkerSword);
			SWORD.addModdedEnchantBonusLevel("apotheosis:capturing", Config.altarBonusLvl, checkerSword);
			//Better than Infinity so takes priority
			if (BOW.addModdedEnchantBonusLevel("apotheosis:true_infinity", 0, checkerBow)) BOW.removeEnchant(Enchantments.INFINITY);
		}
		
	}
	
	private List<EnchantmentData> enchants;
	
	public ZorraAltarManager() {
		enchants = new ArrayList<>();
	}
	
	public void addEnchant(EnchantmentData enchant) {
		enchants.add(enchant);
	}
	
	/**
	 * Adds the given enchantment to the pool, with the given maximum level
	 * @param enchant enchantment to add
	 * @param maxLvl maximum applicable level
	 */
	public void addEnchant(Enchantment enchant, int maxLvl) {
		enchants.add(new EnchantmentData(enchant, maxLvl));
	}

	/**
	 * Adds the given enchantment to the pool, with the given number of levels above its maximum
	 * @param enchant enchantment to add
	 * @param bonusLvl levels beyond the max the enchant can be applied
	 */
	public void addEnchantBonusLevel(Enchantment enchant, int bonusLvl) {
		enchants.add(new EnchantmentData(enchant, enchant.getMaxLevel() + bonusLvl));
	}
	
	/**
	 * Attempts to add the given enchantment given its registry name
	 * @param key registry name of the enchantment to add
	 * @param maxLvl maximum applicable level
	 * @param checker ItemStack that should be enchantable with the target enchantment, used to check if the enchantment is disabled
	 * @return true if the enchant was found, could be applied and has been added, false otherwise
	 */
	public boolean addModdedEnchant(String key, int maxLvl, ItemStack checker) {
		Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(key));
		//CoFH Core makes enchantments unapplicable and useless instead of not registering them when disabled individually
		//So we use this dummy ItemStack to check if it's applicable
		if (enchant == null || !enchant.canApply(checker)) return false;
		
		addEnchant(enchant, maxLvl);
		return true;
	}
	
	/**
	 * Attempts to add the given enchantment given its registry name
	 * @param key registry name of the enchantment to add
	 * @param bonusLvl levels beyond the max the enchant can be applied
	 * @param checker ItemStack that should be enchantable with the target enchantment, used to check if the enchantment is disabled
	 * @return true if the enchant was found, could be applied and has been added, false otherwise
	 */
	public boolean addModdedEnchantBonusLevel(String key, int bonusLvl, ItemStack checker) {
		Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(key));
		if (enchant == null || !enchant.canApply(checker)) return false;
		
		addEnchantBonusLevel(enchant, bonusLvl);
		return true;
	}
	
	/**
	 * Attempts to remove the given enchantment to the pool
	 * @param enchant enchantment to remove
	 * @return true if the enchantment was found and removed, false if it wasn't in the pool
	 */
	public boolean removeEnchant(Enchantment enchant) {
		for (int i = 0; i < enchants.size(); i++) {
			if (enchants.get(i).enchantment == enchant) {
				enchants.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gives the base cost in levels of applying this enchantment
	 * @param data EnchantmentData to apply
	 * @return cost in level to apply it
	 */
	public int getLevelCost(EnchantmentData data) {
		int lvl = data.enchantmentLevel, cost;
		if (lvl <= 1) cost = data.enchantment.getMinEnchantability(1);
		else cost = data.enchantment.getMinEnchantability(lvl) - (data.enchantment.getMinEnchantability(lvl - 1) / 2);
		return Math.max(1, (int)(cost * Config.altarCostMult));
	}
	
	/**
	 * Applies a random deviation to the given level cost
	 * @param cost level cost to deviate
	 * @param rand Random to use
	 * @return the cost randomly deviated
	 */
	public int deviate(int cost, Random rand) {
		int deviation = Math.max(2, (int)(cost * 0.1));
		return Math.max(1, cost - deviation + rand.nextInt(deviation * 2 + 1));
	}
	
	/**
	 * Gives a deviated level cost for applying the given enchantment
	 * @param data EnchantmentData to apply
	 * @param rand Random to use
	 * @return cost in level to apply it, randomly deviated
	 */
	public int getRandomLevelCost(EnchantmentData data, Random rand) {
		return deviate(getLevelCost(data), rand);
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
	
	/**
	 * Gives 3 random enchantments for the Zorra Altar
	 * @param stack ItemStack to apply enchantments for
	 * @param rand Random to use
	 * @return up to 3 applicable enchantments
	 */
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
