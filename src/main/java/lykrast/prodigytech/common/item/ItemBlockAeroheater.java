package lykrast.prodigytech.common.item;

import java.util.List;

import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockAeroheater extends ItemBlockInfoShift {
	private final int max;
	
	public ItemBlockAeroheater(Block block, int heatMax) {
		super(block);
		max = heatMax;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addInfo(ItemStack stack, List<String> tooltip)
	{
		super.addInfo(stack, tooltip);
		TooltipUtil.addAeroheaterInfo(stack, tooltip, max);
	}

}
