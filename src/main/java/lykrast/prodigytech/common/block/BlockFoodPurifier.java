package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileFoodPurifier;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFoodPurifier extends BlockHotAirMachine<TileFoodPurifier> implements ICustomItemBlock {

    public BlockFoodPurifier(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileFoodPurifier.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.FOOD_PURIFIER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFoodPurifier();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
