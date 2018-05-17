package lykrast.prodigytech.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockMachineActiveable<T extends TileEntity> extends BlockMachine<T> {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	
	public BlockMachineActiveable(Material material, SoundType soundType, float hardness, float resistance, Class<T> tile) {
		super(material, tile);
		setSoundType(soundType);
		setHardness(hardness);
		setResistance(resistance);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.valueOf(false)));
	}
	
	public BlockMachineActiveable(Material material, SoundType soundType, float hardness, float resistance, String tool, int harvestLevel, Class<T> tile) {
		this(material, soundType, hardness, resistance, tile);
		setHarvestLevel(tool, harvestLevel);
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, Boolean.valueOf(active)), 3);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, Boolean.valueOf(false));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7)).withProperty(ACTIVE, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
	    int i = 0;
	    i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	
	    if (((Boolean)state.getValue(ACTIVE)).booleanValue())
	    {
	        i |= 8;
	    }
	
	    return i;
	}

	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, FACING, ACTIVE);
	}

}