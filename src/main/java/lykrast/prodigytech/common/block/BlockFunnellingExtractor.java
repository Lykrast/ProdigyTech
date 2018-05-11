package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.tileentity.TileFunnellingExtractor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFunnellingExtractor extends BlockExtractor {
	public BlockFunnellingExtractor(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel);
	}
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFunnellingExtractor();
	}
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return face.getOpposite() == state.getValue(FACING) ? BlockFaceShape.SOLID : BlockFaceShape.BOWL;
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing);
    }
}
