package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockLinearExtractor;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class TileExtractor extends TileMachineInventory implements ITickable {
	/** The number of ticks that the machine needs to process */
	protected short clockTime;

	public TileExtractor() {
		super(1);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void update() {
		if (!world.isRemote)
		{
			if (clockTime <= 1)
			{
				if (!world.isBlockPowered(pos))
				{
					clockTime = (short)Config.extractorDelay;
					work(BlockLinearExtractor.getFacing(getBlockMetadata()));
				}
			}
			else clockTime--;
		}
	}
	
	protected abstract void work(EnumFacing front);
	
	protected boolean push(EnumFacing facing)
	{
		//No items inside
		if (getStackInSlot(0).isEmpty()) return false;
		
		IItemHandler handler = getAdjacentTileCapability(facing);
		//No inventory to push to
		if (handler == null) return false;

		ItemStack inside = getStackInSlot(0);
		int initialCount = inside.getCount();
		ItemStack toPush = inside.splitStack(Config.extractorMaxStack);
		//Attempt to insert the stack in all slots
		for (int i=0; i < handler.getSlots(); i++)
		{
			toPush = handler.insertItem(i, toPush, false);
			//Whole stack has been inserted
			if (toPush.isEmpty()) return true;
		}
		//Whole inventory was attempted to be pushed
		if (inside.isEmpty())
		{
			setInventorySlotContents(0, toPush);
			return toPush.getCount() < initialCount;
		}
		//Only part of the inventory was attempted to be pushed
		else
		{
			inside.grow(toPush.getCount());
			return inside.getCount() < initialCount;
		}
	}
	
	protected boolean pull(EnumFacing facing)
	{
		IItemHandler handler = getAdjacentTileCapability(facing);
		if (handler == null) return false;
		
		for (int i=0; i < handler.getSlots(); i++)
		{
			ItemStack extracted = handler.extractItem(i, Config.extractorMaxStack, true);
			if (!extracted.isEmpty())
			{
				ItemStack remainder = invHandler.insertItem(0, extracted, true);
				//Insertion successfull
				if (!ItemStack.areItemStacksEqual(extracted, remainder))
				{
					int amount = Config.extractorMaxStack;
					if (!remainder.isEmpty()) amount -= remainder.getCount();
					
					invHandler.insertItem(0, handler.extractItem(i, amount, false), false);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private IItemHandler getAdjacentTileCapability(EnumFacing facing)
	{
		TileEntity tile = world.getTileEntity(pos.offset(facing));
		if (tile == null) return null;
		else return tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
	}

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return this.clockTime;
	        default:
	            return 0;
	    }
	}

	public void setField(int id, int value) {
	    switch (id)
	    {
	        case 0:
	            this.clockTime = (short)value;
	            break;
	    }
	}

	public int getFieldCount() {
	    return 1;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 1, 0, true, true);

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)invHandler;
		return super.getCapability(capability, facing);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.clockTime = compound.getShort("ClockTime");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("ClockTime", clockTime);

        return compound;
    }

}
