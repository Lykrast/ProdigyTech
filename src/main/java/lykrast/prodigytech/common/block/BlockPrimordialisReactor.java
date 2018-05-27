package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TilePrimordialisReactor;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPrimordialisReactor extends BlockHotAirMachine<TilePrimordialisReactor> implements ICustomItemBlock {

    public BlockPrimordialisReactor(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TilePrimordialisReactor.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.PRIMORDIALIS_REACTOR;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePrimordialisReactor();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 250, 50);
	}

}
