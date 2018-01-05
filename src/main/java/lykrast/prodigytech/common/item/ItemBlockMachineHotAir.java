package lykrast.prodigytech.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockMachineHotAir extends ItemBlockInfoShift {
	public static final String HEAT_MINIMUM = "tooltip.prodigytech.temperature.min";
	public static final String HEAT_TRANSFER = "tooltip.prodigytech.temperature.transfer";
	private final int min, transfer;
	
	public ItemBlockMachineHotAir(Block block, int heatMin, int heatTransfer) {
		super(block);
		min = heatMin;
		transfer = heatTransfer;
	}
	
	public ItemBlockMachineHotAir(Block block, int heatMin) {
		this(block, heatMin, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addInfo(ItemStack stack, List<String> tooltip)
	{
		super.addInfo(stack, tooltip);
		tooltip.add(I18n.format(HEAT_MINIMUM, min));
		if (transfer > 0) tooltip.add(I18n.format(HEAT_TRANSFER, transfer));
	}

}
