package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileFoodEnricher;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFoodEnricher extends BlockHotAirMachine<TileFoodEnricher> implements ICustomItemBlock {

    public BlockFoodEnricher(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileFoodEnricher.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.FOOD_ENRICHER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFoodEnricher();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 125, 75);
	}

}
