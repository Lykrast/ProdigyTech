package lykrast.prodigytech.common.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSugarCube extends ItemFood {
	private final ItemStack DUMMY_POTION;
	
	public ItemSugarCube(int amount, float saturation) {
		super(amount, saturation, false);
		setAlwaysEdible();
		
		DUMMY_POTION = new ItemStack(Items.POTIONITEM);
		List<PotionEffect> list = new ArrayList<>();
		list.add(new PotionEffect(MobEffects.SPEED, 900, 0));
		list.add(new PotionEffect(MobEffects.HASTE, 900, 0));
		PotionUtils.appendEffects(DUMMY_POTION, list);
	}

	@Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 900, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 900, 0));
        }
    }
	

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		PotionUtils.addPotionTooltip(DUMMY_POTION, tooltip, 1.0F);
	}

}
