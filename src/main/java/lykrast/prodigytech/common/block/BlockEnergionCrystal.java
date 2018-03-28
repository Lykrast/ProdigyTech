package lykrast.prodigytech.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.AABBUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnergionCrystal extends BlockGeneric implements ICustomStateMapper, ICustomItemBlock {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
    //Used as a cheap workaround to make the crystal cutter not cause an explosion
    //Because onBlockDestroyedByPlayer does not allow me to see if it was the correct tool
    public static final PropertyBool DEFUSED = PropertyBool.create("defused");
    private static final AxisAlignedBB[] AABB = new AxisAlignedBB[] {
    		AABBUtil.getCenteredDownSquareFromPixels(6, 4), 
    		AABBUtil.getCenteredDownSquareFromPixels(8, 6), 
    		AABBUtil.getCenteredDownSquareFromPixels(12, 9), 
    		AABBUtil.getCenteredDownSquareFromPixels(14, 9), 
    		AABBUtil.getCenteredDownSquareFromPixels(14, 12), 
    		AABBUtil.getCenteredDownSquareFromPixels(14, 15)
    	};
    
	public BlockEnergionCrystal(float hardness, float resistance, int harvestLevel) {
		super(Material.ROCK, SoundType.GLASS, hardness, resistance, "crystalcutter", harvestLevel);
		setDefaultState(blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(DEFUSED, false));
		setLightLevel(0.6F);
        setTickRandomly(true);
	}

	//Just like wheat
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModItems.energionCrystalSeed;
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(ModItems.energionCrystalSeed);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        //No fortune on the guaranteed drop
        drops.add(new ItemStack(ModItems.energionCrystalSeed, 1, 0));
        
        if (age > 0) for (int i = 0; i < age; i++)
        {
        	drops.add(new ItemStack(ModItems.energionCrystalSeed, 1, 0));

        	if (fortune > 0) for (int j = 0; j < fortune; j++)
        	{
        		if (rand.nextInt(2) == 0)
        		{
        			drops.add(new ItemStack(ModItems.energionCrystalSeed, 1, 0));
        		}
        	}
        }
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
        return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP);
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
            worldIn.destroyBlock(pos, false);
        }
    }
	
	@Override
    public void onBlockExploded(World worldIn, BlockPos pos, Explosion explosionIn)
    {
		explode(worldIn, pos, worldIn.getBlockState(pos), explosionIn.getExplosivePlacedBy());        
        super.onBlockExploded(worldIn, pos, explosionIn);
    }
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
    	boolean destroyed = super.removedByPlayer(state, world, pos, player, willHarvest);
    	if (destroyed && !player.isCreative()) explode(world, pos, state, player);
    	return destroyed;
    }
    
	private void explode(World world, BlockPos pos, IBlockState state, EntityLivingBase igniter)
	{
        if (!world.isRemote)
        {
	    	int age = getAge(state);
	    	
	    	if (!isDefused(state) && age > 0)
	    	{
	    		for (int i=0; i<age; i++)
	    		{
	                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), igniter);
	                entitytntprimed.setFuse(5 + 4*i);
	                world.spawnEntity(entitytntprimed);
	    		}
	    	}
        }
	}

	@Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

	@Override
	public ItemBlock getItemBlock() {
		return null;
	}

	//Copied from crops
    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(age)).withProperty(DEFUSED, false);
    }

    public int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }
    
    private boolean isDefused(IBlockState state)
    {
    	return state.getValue(DEFUSED);
    }
    
    public static void defuse(World world, BlockPos pos) {
    	world.setBlockState(pos, world.getBlockState(pos).withProperty(DEFUSED, Boolean.TRUE));
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
        return new BlockStateContainer(this, new IProperty[] {AGE, DEFUSED});
    }

	@Override
	public void setCustomStateMapper() {
		ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).ignore(DEFUSED).build());
	}
}
