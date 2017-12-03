package lykrast.prodigytech.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockAeroheater extends ItemBlockInfoShift {
	protected static final String HEAT_MAXIMUM = "tooltip.prodigytech.temperature.max";
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
		tooltip.add(I18n.format(HEAT_MAXIMUM, max));
	}

}
