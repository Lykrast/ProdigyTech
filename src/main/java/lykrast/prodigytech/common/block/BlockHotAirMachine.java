package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.tileentity.TileAeroheaterSolid;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockHotAirMachine<T extends TileEntity> extends BlockMachine<T> {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockHotAirMachine(float hardness, float resistance, int harvestLevel, Class<T> tile) {
		super(Material.IRON, tile);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.valueOf(false)));
		setSoundType(SoundType.METAL);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel("pickaxe", harvestLevel);
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
	    return new BlockStateContainer(this, new IProperty[] {FACING, ACTIVE});
	}

    /**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        TemperatureHelper.hotAirDamage(entityIn, ((IHotAir) getTileEntity(worldIn, pos)).getOutAirTemperature());

        super.onEntityWalk(worldIn, pos, entityIn);
    }

}