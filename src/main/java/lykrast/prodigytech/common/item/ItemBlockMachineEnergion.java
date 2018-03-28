package lykrast.prodigytech.common.item;

import java.util.List;

import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockMachineEnergion extends ItemBlockInfoShift {
	private final int min, max, consumption;
	
	public ItemBlockMachineEnergion(Block block, int batteriesMin, int batteriesMax, int consumption) {
		super(block);
		min = batteriesMin;
		max = batteriesMax;
		this.consumption = consumption;
	}
	
	public ItemBlockMachineEnergion(Block block, int batteriesMin, int consumption) {
		this(block, batteriesMin, batteriesMin, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addInfo(ItemStack stack, List<String> tooltip)
	{
		super.addInfo(stack, tooltip);
		TooltipUtil.addEnergionInfo(stack, tooltip, min, max, consumption);
	}

}
