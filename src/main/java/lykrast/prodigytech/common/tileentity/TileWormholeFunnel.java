package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockWormholeFunnel;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileWormholeFunnel extends TileEntity implements IHotAir {
	private boolean down;
	private BlockPos linkedPos;
	private TileWormholeFunnel linkedTile;
	
	public void setDown(boolean isDown) {
		down = isDown;
	}
	
	public boolean isInput() {
		return down;
	}
	
	public boolean isLinked() {
		return linkedPos != null;
	}
	
	public boolean isActive() {
		return linkedTile != null;
	}
	
	public BlockPos getLinkedPos() {
		return linkedPos;
	}
	
	public boolean isInRange(BlockPos target) {
		return distance(pos.getX(), target.getX()) <= 8
				&& distance(pos.getY(), target.getY()) <= 8
				&& distance(pos.getZ(), target.getZ()) <= 8;
	}
	
	private int distance(int a, int b) {
		return Math.abs(a - b);
	}
	
	/**
	 * Attempts to create a link with the targeted position.
	 * Does not check for range.
	 */
	public boolean createLink(TileWormholeFunnel other) {
		//Check if compatible type
		if (down == other.down) return false;
		
		//Destroy previous link
		destroyLink(true);
		other.destroyLink(true);
		
		//Make the link
		linkedPos = other.pos;
		linkedTile = other;
		other.linkedPos = pos;
		other.linkedTile = this;
		
		BlockWormholeFunnel.setActive(true, world, pos);
		BlockWormholeFunnel.setActive(true, world, linkedPos);
		
		markDirty();
		
		return true;
	}
	
	/**
	 * Makes the link active.
	 */
	private void restoreLink(TileWormholeFunnel other) {
		//Funnels are not compatible or one of them had the link severed, destroy link
		if (down == other.down || linkedPos == null || other.linkedPos == null) destroyLink(false);
		else
		{
			linkedTile = other;
			other.linkedTile = this;
		}
	}
	
	/**
	 * Destroys the current link.
	 */
	public void destroyLink(boolean causeUpdate) {
		//No linked pos, nothing to do
		if (linkedPos == null) return;
		
		//Linked Funnel is loaded, destroy everything
		if (linkedTile != null) {
			BlockWormholeFunnel.setActive(false, world, pos);
			BlockWormholeFunnel.setActive(false, world, linkedPos);
			
			linkedTile.linkedPos = null;
			linkedTile.linkedTile = null;
			linkedPos = null;
			linkedTile = null;
		}
		//Linked Funnel is not loaded, figure something out
		else
		{
			BlockWormholeFunnel.setActive(false, world, pos);
			linkedPos = null;
		}
		
		markDirty();
	}
	
	/**
	 * Makes the link dormant until restoreLink is called.
	 */
	private void sleepLink() {
		//No linked pos or linked tile, nothing to do
		if (linkedPos == null || linkedTile == null) return;
		
		linkedTile.linkedTile = null;
		linkedTile = null;
	}
	
	@Override
    public void invalidate() {
		sleepLink();
        super.invalidate();
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null) && !down)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null) && !down)
			return (T)this;
		return super.getCapability(capability, facing);
	}
	
	//Prevent infinite loops
	private boolean hasLooped = false;

	@Override
	public int getOutAirTemperature() {
		if (!isActive() || hasLooped) return 30;
		hasLooped = true;
		
		int temp;
		//Input, only called by output funnels
		if (down) temp = TemperatureHelper.getBlockTemp(world, pos.down());
		//Output
		else temp = linkedTile.getOutAirTemperature();
		
		hasLooped = false;
		return temp;
	}
	
	@Override
    public void onLoad() {
		if (!isLinked()) return;
		
    	TileEntity found = world.getTileEntity(linkedPos);
    	if (found != null && found instanceof TileWormholeFunnel) restoreLink((TileWormholeFunnel)found);
    	//No tile or invalid tile found yet it is loaded
    	else if (world.isBlockLoaded(linkedPos)) destroyLink(false);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        down = compound.getBoolean("Down");
        if (compound.hasKey("Linked"))
        {
        	linkedPos = NBTUtil.getPosFromTag(compound.getCompoundTag("Linked"));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("Down", down);
        if (linkedPos != null) compound.setTag("Linked", NBTUtil.createPosTag(linkedPos));

        return compound;
    }
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		if (oldState.getBlock() != newState.getBlock()) return true;
		return oldState.getValue(BlockWormholeFunnel.DOWN) != newState.getValue(BlockWormholeFunnel.DOWN);
    }

}
