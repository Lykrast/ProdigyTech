package lykrast.prodigytech.common.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemMysteryTreat extends ItemFood {
	public static final IItemColor COLOR = new Color();
	private static final List<PotionEffect[]> EFFECTS = new ArrayList<>();
	
	public static void initEffects() {
		registerEffect(MobEffects.SPEED, 3600);
		registerEffect(MobEffects.SLOWNESS, 3600);
		registerEffect(MobEffects.HASTE, 3600);
		registerEffect(MobEffects.MINING_FATIGUE, 3600);
		registerEffect(MobEffects.STRENGTH, 3600);
		registerEffect(MobEffects.INSTANT_HEALTH, 1);
		registerEffect(MobEffects.INSTANT_DAMAGE, 1);
		registerEffect(MobEffects.JUMP_BOOST, 3600);
		registerEffect(MobEffects.REGENERATION, 900);
		registerEffect(MobEffects.RESISTANCE, 3600);
		registerEffectSingleAmplifier(MobEffects.FIRE_RESISTANCE, 3600, 7200, 10800);
		registerEffectSingleAmplifier(MobEffects.WATER_BREATHING, 3600, 7200, 10800);
		registerEffectSingleAmplifier(MobEffects.INVISIBILITY, 3600, 7200, 10800);
		registerEffectSingleAmplifier(MobEffects.BLINDNESS, 3600, 7200, 10800);
		registerEffectSingleAmplifier(MobEffects.NIGHT_VISION, 3600, 7200, 10800);
		registerEffect(MobEffects.HUNGER, 900);
		registerEffect(MobEffects.WEAKNESS, 3600);
		registerEffect(MobEffects.POISON, 900);
		registerEffect(MobEffects.WITHER, 900);
		registerEffect(MobEffects.HEALTH_BOOST, 3600);
		registerEffect(MobEffects.ABSORPTION, 3600);
		registerEffect(MobEffects.LEVITATION, 400);
		registerEffect(MobEffects.LUCK, 3600);
		registerEffect(MobEffects.UNLUCK, 3600);
	}
	
	public static void registerEffect(Potion potion, int duration) {
		PotionEffect[] array = new PotionEffect[3];
		for (int i=0;i<3;i++) array[i] = new PotionEffect(potion, duration, i);
		EFFECTS.add(array);
	}
	
	public static void registerEffectSingleAmplifier(Potion potion, int duration1, int duration2, int duration3) {
		PotionEffect[] array = new PotionEffect[3];
		array[0] = new PotionEffect(potion, duration1);
		array[1] = new PotionEffect(potion, duration2);
		array[2] = new PotionEffect(potion, duration3);
		EFFECTS.add(array);
	}

	public ItemMysteryTreat(int amount, float saturation) {
		super(amount, saturation, false);
		setAlwaysEdible();
	}
	
	@Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote && stack.hasTagCompound())
        {
        	NBTTagCompound nbt = stack.getTagCompound();
        	if (nbt.hasKey("back")) applyEffect(PotionEffect.readCustomPotionEffectFromNBT(nbt.getCompoundTag("back")), player);
        	if (nbt.hasKey("front")) applyEffect(PotionEffect.readCustomPotionEffectFromNBT(nbt.getCompoundTag("front")), player);
        }
    }
	
	private void applyEffect(PotionEffect effect, EntityPlayer player) {
		if (effect == null) return;
        if (effect.getPotion().isInstant()) effect.getPotion().affectEntity(player, player, player, effect.getAmplifier(), 1.0D);
        else player.addPotionEffect(new PotionEffect(effect));
	}
	
	public static ItemStack createWithEffect(PotionEffect back) {
		return createWithEffect(back, null);
	}
	
	public static ItemStack createWithEffect(PotionEffect back, PotionEffect front) {
		ItemStack stack = new ItemStack(ModItems.mysteryTreat);
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setTag("back", back.writeCustomPotionEffectToNBT(new NBTTagCompound()));
		if (front != null) nbt.setTag("front", front.writeCustomPotionEffectToNBT(new NBTTagCompound()));
		
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static ItemStack createRandom(Random rand) {
    	int size = EFFECTS.size();
    	int i = rand.nextInt(size), j = rand.nextInt(size);	
    	
		if (j == i) return createWithEffect(EFFECTS.get(i)[2]);
		else return createWithEffect(EFFECTS.get(i)[1], EFFECTS.get(j)[0]);	
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
        	int size = EFFECTS.size();
        	for (int i=0;i<size;i++) {
        		for (int j=0;j<size;j++) {
        			if (j == i) items.add(createWithEffect(EFFECTS.get(i)[2]));
        			else items.add(createWithEffect(EFFECTS.get(i)[1], EFFECTS.get(j)[0]));
        		}
        	}
        }
    }
	
	private static class Color implements IItemColor {
		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if (!stack.hasTagCompound()) return -1;
			NBTTagCompound nbt = stack.getTagCompound();
			if (tintIndex == 0 && nbt.hasKey("back"))
				return PotionUtils.getPotionColorFromEffectList(Collections.singleton(
						PotionEffect.readCustomPotionEffectFromNBT(nbt.getCompoundTag("back"))));
			else if (tintIndex == 1)
				if (nbt.hasKey("front")) return PotionUtils.getPotionColorFromEffectList(Collections.singleton(
						PotionEffect.readCustomPotionEffectFromNBT(nbt.getCompoundTag("front"))));
				else return colorMultiplier(stack, 0);
			else return -1;
		}
		
	}

}
