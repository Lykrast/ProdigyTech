package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockMachineHotAir;
import lykrast.prodigytech.common.tileentity.TileRotaryGrinder;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRotaryGrinder extends BlockHotAirMachine<TileRotaryGrinder> implements ICustomItemBlock {

    public BlockRotaryGrinder(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileRotaryGrinder.class);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRotaryGrinder();
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
        	TileRotaryGrinder tile = getTileEntity(worldIn,pos);

            if (tile != null)
            {
                playerIn.openGui(ProdigyTech.instance, ProdigyTechGuiHandler.ROTARY_GRINDER, worldIn, pos.getX(), pos.getY(), pos.getZ());
                playerIn.openContainer.detectAndSendChanges();
            }

            return true;
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	TileRotaryGrinder tile = getTileEntity(worldIn, pos);

        if (tile != null)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, tile);
        }
        
        super.breakBlock(worldIn, pos, state);
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockMachineHotAir(this, 80, 80);
	}

}
