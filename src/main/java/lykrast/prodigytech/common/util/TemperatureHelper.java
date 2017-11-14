package lykrast.prodigytech.common.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class TemperatureHelper {
	private TemperatureHelper() {}
	
	public static void hotAirDamage(Entity entity, int temperature)
	{
        if (!entity.isImmuneToFire() && entity instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entity))
        {
        	float damage = getDamageFromTemperature(temperature);
        	if (damage >= 1.0F) entity.attackEntityFrom(DamageSource.HOT_FLOOR, damage);
        }
	}
	
	public static float getDamageFromTemperature (int temperature)
	{
		if (temperature < 100) return 0.0F;
		
		int tmp = (temperature - 100) / 50;
		return (float) Math.pow(2, tmp);
	}

}
