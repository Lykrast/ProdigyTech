package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileBatteryReplenisher;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBatteryReplenisher extends BlockHotAirMachine<TileBatteryReplenisher> implements ICustomItemBlock {

    public BlockBatteryReplenisher(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileBatteryReplenisher.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.BATTERY_REPLENISHER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBatteryReplenisher();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
