package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockAeroheater;
import lykrast.prodigytech.common.tileentity.TileAeroheaterSolid;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAeroheaterSolid extends BlockHotAirMachine<TileAeroheaterSolid> implements ICustomItemBlock {

    public BlockAeroheaterSolid(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAeroheaterSolid.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.AEROHEATER_SOLID;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterSolid();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockAeroheater(this, 200);
	}

}
