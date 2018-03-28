package lykrast.prodigytech.common.item;

import java.util.List;

import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockMachineHotAir extends ItemBlockInfoShift {
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
		TooltipUtil.addAirMachineInfo(stack, tooltip, min, transfer);
	}

}
