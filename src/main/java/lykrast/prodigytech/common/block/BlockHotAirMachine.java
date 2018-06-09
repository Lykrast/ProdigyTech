package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.tileentity.TileMachineInventory;
import lykrast.prodigytech.common.util.TemperatureHelper;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockHotAirMachine<T extends TileMachineInventory> extends BlockMachineActiveable<T> {
	public BlockHotAirMachine(float hardness, float resistance, int harvestLevel, Class<T> tile) {
		super(Material.IRON, SoundType.METAL, hardness, resistance, "pickaxe", harvestLevel, tile);
	}

	/**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        TemperatureHelper.hotAirDamage(entityIn, getTileEntity(worldIn, pos).getCapability(CapabilityHotAir.HOT_AIR, EnumFacing.UP));

        super.onEntityWalk(worldIn, pos, entityIn);
    }
    
    protected abstract int getGuiID();

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
        	T tile = getTileEntity(worldIn,pos);

            if (tile != null)
            {
                playerIn.openGui(ProdigyTech.instance, getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
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
    	T tile = getTileEntity(worldIn, pos);

        if (tile != null)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, tile);
        }
        
        super.breakBlock(worldIn, pos, state);
    }

}