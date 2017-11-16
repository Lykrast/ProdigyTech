package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.tileentity.TileAirFunnel;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAirFunnel extends BlockGeneric implements ITileEntityProvider {
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

	public BlockAirFunnel(float hardness, float resistance, int harvestLevel) {
		super(Material.IRON, SoundType.METAL, hardness, resistance, "pickaxe", harvestLevel);
        this.setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAirFunnel();
	}

	private TileAirFunnel getTileEntity(IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileAirFunnel) return (TileAirFunnel)tile;
		else return null;
	}

    /**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        TemperatureHelper.hotAirDamage(entityIn, getTileEntity(worldIn, pos).getOutAirTemperature());

        super.onEntityWalk(worldIn, pos, entityIn);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     * 
     * @return an approximation of the form of the given face
     */
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
    	if (face == EnumFacing.DOWN || face == EnumFacing.UP) return BlockFaceShape.SOLID;
    	else return BlockFaceShape.UNDEFINED;
    }

}
