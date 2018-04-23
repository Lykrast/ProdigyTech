package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileMagneticReassembler;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMagneticReassembler extends BlockHotAirMachine<TileMagneticReassembler> implements ICustomItemBlock {

    public BlockMagneticReassembler(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileMagneticReassembler.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.MAGNETIC_REASSEMBLER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMagneticReassembler();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 125, 75);
	}

}
