package lykrast.prodigytech.common.block;

import java.util.List;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockInfoShift;
import lykrast.prodigytech.common.tileentity.TileAeroheaterCapacitor;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAeroheaterCapacitor extends BlockHotAirMachine<TileAeroheaterCapacitor> implements ICustomItemBlock {

    public BlockAeroheaterCapacitor(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAeroheaterCapacitor.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.AEROHEATER_CAPACITOR;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterCapacitor();
	}
	
	private static final String HEAT_MAXIMUM = "tooltip.prodigytech.temperature.max.variable";

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockInfoShift(this) {
			@Override
			@SideOnly(Side.CLIENT)
			protected void addInfo(ItemStack stack, List<String> tooltip) {
				super.addInfo(stack, tooltip);
				tooltip.add(I18n.format(HEAT_MAXIMUM));
			}
		};
	}

}
