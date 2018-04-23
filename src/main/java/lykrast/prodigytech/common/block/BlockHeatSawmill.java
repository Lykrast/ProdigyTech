package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileHeatSawmill;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeatSawmill extends BlockHotAirMachine<TileHeatSawmill> implements ICustomItemBlock {

    public BlockHeatSawmill(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileHeatSawmill.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.HEAT_SAWMILL;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeatSawmill();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
