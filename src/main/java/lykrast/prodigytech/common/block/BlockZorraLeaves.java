package lykrast.prodigytech.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockZorraLeaves extends BlockLeaves implements ICustomStateMapper {
    public static final PropertyBool SOAKED = PropertyBool.create("soaked");
    //To hurt people
    protected static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0625D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    
	public BlockZorraLeaves(float hardness, float resistance) {
		setHardness(hardness);
		setResistance(resistance);
		setDefaultState(getDefaultState().withProperty(SOAKED, false)
				.withProperty(CHECK_DECAY, Boolean.valueOf(true))
				.withProperty(DECAYABLE, Boolean.valueOf(true)));
	}

	@Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn.attackEntityFrom(DamageSource.CACTUS, 3.0F) && entityIn instanceof EntityLivingBase)
        {
        	if (((EntityLivingBase)entityIn).getHealth() <= 0) {
        		worldIn.setBlockState(pos, state.withProperty(SOAKED, Boolean.TRUE));
        		worldIn.scheduleUpdate(pos, this, 40 + worldIn.rand.nextInt(61));
        	}
        }
    }
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		//Only naturally grown leaves can spread
		if (!worldIn.isRemote && 
				state.getValue(SOAKED).booleanValue() && 
				state.getValue(DECAYABLE).booleanValue() && 
				worldIn.isAreaLoaded(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
			//Hopefully that's less overhead than making a List, although not sure it's really needed
			BlockPos[] validPos = new BlockPos[27];
			int valid = 0;
            for (BlockPos p : BlockPos.getAllInBox(pos.add(-1, -1, -1), pos.add(1, 1, 1)))
            {
            	if (worldIn.isAirBlock(p)) validPos[valid++] = p;
            }
            
            if (valid > 0)
            {
            	worldIn.setBlockState(validPos[rand.nextInt(valid)], getDefaultState().withProperty(CHECK_DECAY, Boolean.FALSE));
            	if (rand.nextInt(3) == 0) worldIn.setBlockState(pos, state.withProperty(SOAKED, Boolean.FALSE));
            	else worldIn.scheduleUpdate(pos, this, 40 + rand.nextInt(61));
            }
            else worldIn.setBlockState(pos, state.withProperty(SOAKED, Boolean.FALSE));
		}
		//Decay after spreading
		super.updateTick(worldIn, pos, state, rand);
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {SOAKED, CHECK_DECAY, DECAYABLE});
    }

	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_AABB;
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SOAKED, Boolean.valueOf((meta & 2) > 0))
        		.withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0))
        		.withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }
	
	@Override
	public int getMetaFromState(IBlockState state) {
        int i = 0;
        
        if (state.getValue(SOAKED).booleanValue()) i |= 2;
        if (!state.getValue(DECAYABLE).booleanValue()) i |= 4;
        if (state.getValue(CHECK_DECAY).booleanValue()) i |= 8;

        return i;
    }
	
	@Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return getDefaultState().withProperty(DECAYABLE, Boolean.FALSE);
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void setCustomStateMapper() {
		ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).ignore(CHECK_DECAY, DECAYABLE).build());
	}

}
