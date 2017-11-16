package lykrast.prodigytech.common.util;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
	
	public static int getBlockTemp(World world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null)
		{
			IHotAir capability = tile.getCapability(CapabilityHotAir.HOT_AIR, EnumFacing.UP);
			if (capability != null)
			{
				return capability.getOutAirTemperature();
			}
		}
		
		return 30;
	}

}
