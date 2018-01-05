package lykrast.prodigytech.common.block;

import java.util.Random;

import lykrast.prodigytech.common.item.ItemBlockInfoShift;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHologram extends BlockGeneric implements ICustomItemBlock {

	public BlockHologram(float hardness, float resistance) {
		super(Material.GLASS, SoundType.GLASS, hardness, resistance);
		//setTickRandomly(true);
        setLightOpacity(0);
		setLightLevel(0.25F);
	}

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	worldIn.setBlockToAir(pos);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
    	worldIn.scheduleUpdate(pos, this, Config.hologramDuration);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockInfoShift(this);
	}
}
