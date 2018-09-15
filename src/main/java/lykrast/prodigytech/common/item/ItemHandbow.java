package lykrast.prodigytech.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHandbow extends ItemBow {
	
	public ItemHandbow(int durability) {
		setMaxStackSize(1);
        setMaxDamage(durability);
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 20;
    }
	
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		//Make the handbow automatically fire when fully charged
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, 0);
        return stack;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		//Prevent firing with less than 8 ticks of charging to prevent accidental double fire
		if (timeLeft <= 12) super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
	}

}
