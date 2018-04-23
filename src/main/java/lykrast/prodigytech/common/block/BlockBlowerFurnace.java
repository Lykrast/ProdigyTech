package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileBlowerFurnace;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBlowerFurnace extends BlockHotAirMachine<TileBlowerFurnace> implements ICustomItemBlock {

    public BlockBlowerFurnace(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileBlowerFurnace.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.BLOWER_FURNACE;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBlowerFurnace();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
