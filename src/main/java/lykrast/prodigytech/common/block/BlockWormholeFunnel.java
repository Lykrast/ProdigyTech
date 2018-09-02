package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.item.ItemBlockInfoShift;
import lykrast.prodigytech.common.network.PacketWormholeDisplay;
import lykrast.prodigytech.common.tileentity.TileWormholeFunnel;
import lykrast.prodigytech.common.util.TemperatureHelper;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWormholeFunnel extends BlockGeneric implements ITileEntityProvider, ICustomItemBlock {
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
    protected static final AxisAlignedBB LOWER = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB UPPER = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockWormholeFunnel(float hardness, float resistance, int harvestLevel) {
		super(Material.IRON, SoundType.METAL, hardness, resistance, "pickaxe", harvestLevel);
        setLightOpacity(0);
        setDefaultState(getDefaultState().withProperty(DOWN, true).withProperty(ACTIVE, false));
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Item item = playerIn.getHeldItem(hand).getItem();
		if (item == ModItems.wormholeLinker || item instanceof ItemBlock) return false;
		
		if (!worldIn.isRemote)
		{
        	TileWormholeFunnel tile = getTileEntity(worldIn,pos);

            if (tile != null && tile.isLinked() && tile.isActive() && playerIn instanceof EntityPlayerMP)
            {
            	BlockPos linked = tile.getLinkedPos();
            	ProdigyTech.networkChannel.sendTo(new PacketWormholeDisplay(pos, linked), (EntityPlayerMP) playerIn);
            }
        }
		
		return true;
    }

	public static void setActive(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		if (state.getBlock() == ModBlocks.wormholeFunnel && state.getValue(ACTIVE) != active)
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, active), 3);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		TileWormholeFunnel tile = new TileWormholeFunnel();
		tile.setDown((meta & 0b1) == 0);
		return tile;
	}

	public static TileWormholeFunnel getTileEntity(IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileWormholeFunnel) return (TileWormholeFunnel)tile;
		else return null;
	}

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
    	IBlockState state = worldIn.getBlockState(pos);
        if (!state.getValue(DOWN))
        	TemperatureHelper.hotAirDamage(entityIn, getTileEntity(worldIn, pos).getCapability(CapabilityHotAir.HOT_AIR, EnumFacing.UP));
        
        super.onEntityWalk(worldIn, pos, entityIn);
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	TileWormholeFunnel tile = getTileEntity(worldIn, pos);
    	tile.destroyLink(true);
    	super.breakBlock(worldIn, pos, state);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing == EnumFacing.UP) return getDefaultState().withProperty(DOWN, true);
        else if (facing == EnumFacing.DOWN) return getDefaultState().withProperty(DOWN, false);
        else return getDefaultState().withProperty(DOWN, hitY <= 0.5);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(DOWN) ? LOWER : UPPER;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DOWN, (meta & 0b1) == 0).withProperty(ACTIVE, (meta & 0b10) == 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
    	int i = 0;
    	if (!state.getValue(DOWN)) i |= 0b1;
    	if (state.getValue(ACTIVE)) i |= 0b10;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DOWN, ACTIVE);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    	if ((face == EnumFacing.DOWN && state.getValue(DOWN)) || (face == EnumFacing.UP && !state.getValue(DOWN))) 
    		return BlockFaceShape.SOLID;
    	else return BlockFaceShape.UNDEFINED;
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockInfoShift(this);
	}

}
