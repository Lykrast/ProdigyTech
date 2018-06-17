package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockAeroheater;
import lykrast.prodigytech.common.tileentity.TileAeroheaterTartaric;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAeroheaterTartaric extends BlockHotAirMachine<TileAeroheaterTartaric> implements ICustomItemBlock {

    public BlockAeroheaterTartaric(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAeroheaterTartaric.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.AEROHEATER_TARTARIC;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterTartaric();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockAeroheater(this, 1000);
	}

}
