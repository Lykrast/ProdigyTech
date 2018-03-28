package lykrast.prodigytech.common.block;

import java.util.List;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockAeroheater;
import lykrast.prodigytech.common.tileentity.TileAeroheaterEnergion;
import lykrast.prodigytech.common.util.TooltipUtil;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAeroheaterEnergion extends BlockHotAirMachine<TileAeroheaterEnergion> implements ICustomItemBlock {

    public BlockAeroheaterEnergion(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAeroheaterEnergion.class);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterEnergion();
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
            TileAeroheaterEnergion tile = getTileEntity(worldIn,pos);

            if (tile != null)
            {
                playerIn.openGui(ProdigyTech.instance, ProdigyTechGuiHandler.AEROHEATER_ENERGION, worldIn, pos.getX(), pos.getY(), pos.getZ());
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
    	TileAeroheaterEnergion tile = getTileEntity(worldIn, pos);

        if (tile != null)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, tile);
        }
        
        super.breakBlock(worldIn, pos, state);
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockAeroheater(this, 250) {
			@Override
			@SideOnly(Side.CLIENT)
			protected void addInfo(ItemStack stack, List<String> tooltip)
			{
				super.addInfo(stack, tooltip);
				TooltipUtil.addEnergionInfo(stack, tooltip, 1, 6, 1);
			}
		};
	}

}
