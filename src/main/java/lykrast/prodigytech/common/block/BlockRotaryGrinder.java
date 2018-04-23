package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileRotaryGrinder;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRotaryGrinder extends BlockHotAirMachine<TileRotaryGrinder> implements ICustomItemBlock {

    public BlockRotaryGrinder(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileRotaryGrinder.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.ROTARY_GRINDER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRotaryGrinder();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
