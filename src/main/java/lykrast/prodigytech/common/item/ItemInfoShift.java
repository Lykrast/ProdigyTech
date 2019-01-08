package lykrast.prodigytech.common.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInfoShift extends Item {
	protected static final String SHIFT = "tooltip.prodigytech.shift";
	
	public ItemInfoShift() {
		super();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (TooltipUtil.addShiftTooltip(tooltip)) addInfo(stack, tooltip);
	}

	@SideOnly(Side.CLIENT)
	protected void addInfo(ItemStack stack, List<String> tooltip) {
		TooltipUtil.addTooltip(stack, tooltip);
	}

}
