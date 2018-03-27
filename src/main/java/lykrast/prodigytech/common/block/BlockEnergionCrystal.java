package lykrast.prodigytech.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.item.ItemBlockInfoShift;
import lykrast.prodigytech.common.util.AABBUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnergionCrystal extends BlockGeneric implements ICustomItemBlock {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
    private static final AxisAlignedBB[] AABB = new AxisAlignedBB[] {
    		AABBUtil.getCenteredDownSquareFromPixels(6, 4), 
    		AABBUtil.getCenteredDownSquareFromPixels(8, 6), 
    		AABBUtil.getCenteredDownSquareFromPixels(12, 9), 
    		AABBUtil.getCenteredDownSquareFromPixels(14, 9), 
    		AABBUtil.getCenteredDownSquareFromPixels(14, 12), 
    		AABBUtil.getCenteredDownSquareFromPixels(14, 15)
    	};
    
	public BlockEnergionCrystal(float hardness, float resistance, int harvestLevel) {
		super(Material.GLASS, SoundType.GLASS, hardness, resistance, "pickaxe", harvestLevel);
		setDefaultState(blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		setLightLevel(0.6F);
        setTickRandomly(true);
	}

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
        
        int age = getAge(state);
        
        if (age < 5)
        {
            float f = getGrowthChance(worldIn, pos);

            if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
            {
                worldIn.setBlockState(pos, this.withAge(age + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        }
    }
    
    protected float getGrowthChance(World worldIn, BlockPos pos)
    {
    	//TODO: something more sophisticated
    	return 1.0f;
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB[((Integer)state.getValue(AGE)).intValue()];
    }

    @Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB[((Integer)state.getValue(AGE)).intValue()];
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).isFullBlock();
    }

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.canPlaceBlockAt(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
    }
	
	@Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
    {
        if (!worldIn.isRemote)
        {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
            entitytntprimed.setFuse(5);
            worldIn.spawnEntity(entitytntprimed);
        }
    }

	@Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockInfoShift(this);
	}

	//Copied from crops
    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(age));
    }

    protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.withAge(meta);
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        return this.getAge(state);
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
}
