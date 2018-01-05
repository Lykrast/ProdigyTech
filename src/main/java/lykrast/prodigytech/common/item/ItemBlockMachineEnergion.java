package lykrast.prodigytech.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockMachineEnergion extends ItemBlockInfoShift {
	public static final String BATTERIES_REQUIRED_SINGLE = "tooltip.prodigytech.energion.required.single";
	public static final String BATTERIES_REQUIRED_INTERVAL = "tooltip.prodigytech.energion.required.interval";
	public static final String ENERGY_CONSUMPTION = "tooltip.prodigytech.energion.consumption";
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
		if (max > min) tooltip.add(I18n.format(BATTERIES_REQUIRED_INTERVAL, min, max));
		else tooltip.add(I18n.format(BATTERIES_REQUIRED_SINGLE, min));
		tooltip.add(I18n.format(ENERGY_CONSUMPTION, consumption));
	}

}
