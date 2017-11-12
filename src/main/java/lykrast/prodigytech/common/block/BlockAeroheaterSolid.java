package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.tileentity.TileAeroheaterSolid;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAeroheaterSolid extends BlockMachine<TileAeroheaterSolid> {

    public static final PropertyBool ACTIVE = PropertyBool.create("active");
    
	public BlockAeroheaterSolid(float hardness, float resistance, int harvestLevel) {
		super(Material.IRON, TileAeroheaterSolid.class);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.valueOf(false)));
		setSoundType(SoundType.METAL);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel("pickaxe", harvestLevel);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterSolid();
	}

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileAeroheaterSolid tile = getTileEntity(worldIn,pos);

            if (tile != null)
            {
                playerIn.openGui(ProdigyTech.instance, ProdigyTechGuiHandler.AEROHEATER_SOLID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                playerIn.openContainer.detectAndSendChanges();
            }

            return true;
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
    	worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, Boolean.valueOf(active)), 4);
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, Boolean.valueOf(false));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7)).withProperty(ACTIVE, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();

        if (((Boolean)state.getValue(ACTIVE)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, ACTIVE});
    }
	
	/**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	TileAeroheaterSolid tile = getTileEntity(worldIn, pos);

        if (tile != null)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, tile);
        }
        
        super.breakBlock(worldIn, pos, state);
    }

}
