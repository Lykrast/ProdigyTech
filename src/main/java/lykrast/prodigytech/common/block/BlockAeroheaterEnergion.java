package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockAeroheater;
import lykrast.prodigytech.common.tileentity.TileAeroheaterEnergion;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAeroheaterEnergion extends BlockHotAirMachine<TileAeroheaterEnergion> implements ICustomItemBlock {

    public BlockAeroheaterEnergion(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAeroheaterEnergion.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.AEROHEATER_ENERGION;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterEnergion();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockAeroheater(this, 250);
	}

}
