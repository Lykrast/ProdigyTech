package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileCrystalCutter;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrystalCutter extends BlockHotAirMachine<TileCrystalCutter> implements ICustomItemBlock {

    public BlockCrystalCutter(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileCrystalCutter.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.CRYSTAL_CUTTER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCrystalCutter();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 100, 80);
	}

}
