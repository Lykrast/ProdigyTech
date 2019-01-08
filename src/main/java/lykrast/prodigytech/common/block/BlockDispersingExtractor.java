package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.tileentity.TileDispersingExtractor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDispersingExtractor extends BlockExtractor {
	public BlockDispersingExtractor(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel);
        setLightOpacity(0);
	}
    
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDispersingExtractor();
	}

    /**
     * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
     */
    @Override
    public boolean isTopSolid(IBlockState state) {
        return state.getValue(FACING) == EnumFacing.UP;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == state.getValue(FACING) ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
    }
}
