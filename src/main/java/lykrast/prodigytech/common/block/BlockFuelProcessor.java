package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileFuelProcessor;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFuelProcessor extends BlockHotAirMachine<TileFuelProcessor> implements ICustomItemBlock {

    public BlockFuelProcessor(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileFuelProcessor.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.FUEL_PROCSESOR;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFuelProcessor();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
