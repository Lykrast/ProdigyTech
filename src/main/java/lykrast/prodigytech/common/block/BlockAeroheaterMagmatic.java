package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.item.ItemBlockAeroheater;
import lykrast.prodigytech.common.tileentity.TileAeroheaterMagmatic;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAeroheaterMagmatic extends BlockGeneric implements ITileEntityProvider, ICustomItemBlock {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockAeroheaterMagmatic(float hardness, float resistance, int harvestLevel) {
		super(Material.IRON, SoundType.METAL, hardness, resistance, "pickaxe", harvestLevel);
		setDefaultState(blockState.getBaseState().withProperty(ACTIVE, Boolean.valueOf(false)));
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, Boolean.valueOf(active)), 3);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterMagmatic();
	}

	private TileAeroheaterMagmatic getTileEntity(IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileAeroheaterMagmatic) return (TileAeroheaterMagmatic)tile;
		else return null;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, Boolean.valueOf((meta & 1) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
	    if (((Boolean)state.getValue(ACTIVE)).booleanValue()) return 1;
	    else return 0;
	}

	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, ACTIVE);
	}

    /**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        TemperatureHelper.hotAirDamage(entityIn, getTileEntity(worldIn, pos).getOutAirTemperature());

        super.onEntityWalk(worldIn, pos, entityIn);
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockAeroheater(this, 80);
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		getTileEntity(worldIn, pos).checkActive();
    }

}
