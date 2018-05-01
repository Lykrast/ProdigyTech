package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileAtomicReshaper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAtomicReshaper extends BlockHotAirMachine<TileAtomicReshaper> implements ICustomItemBlock {

    public BlockAtomicReshaper(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAtomicReshaper.class);
	}
    
    //No heat transfer so no damage
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
    }

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.ATOMIC_RESHAPER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAtomicReshaper();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 250);
	}

}
