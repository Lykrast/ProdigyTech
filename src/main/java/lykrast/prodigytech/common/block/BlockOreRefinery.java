package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileOreRefinery;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOreRefinery extends BlockHotAirMachine<TileOreRefinery> implements ICustomItemBlock {

    public BlockOreRefinery(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileOreRefinery.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.ORE_REFINERY;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileOreRefinery();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 125, 75);
	}

}
