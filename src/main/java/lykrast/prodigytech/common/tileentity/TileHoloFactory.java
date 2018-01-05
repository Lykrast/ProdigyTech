package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import lykrast.prodigytech.common.util.ProdigyInventoryHandlerEnergion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileHoloFactory extends TileMachineInventory implements ITickable {
	private int buffer;

	public TileHoloFactory() {
		super(12);
		buffer = 0;
	}

	@Override
	public String getName() {
		return super.getName() + "holo_factory";
	}

	@Override
	public void update() {
        boolean flag = world.getBlockState(pos).getValue(BlockMachineActiveable.ACTIVE);
        boolean flag1 = false;
        
        if (!this.world.isRemote)
        {
        	if (!world.isBlockPowered(pos) && canWork())
        	{
            	if (canExtract()) buffer += extractPower();
        		
        		ItemStack production = produce();
        		boolean worked = !production.isEmpty();
        		
        		if (worked) fillOutput(production);
                
                if (flag != worked)
                {
                	flag1 = true;
                	BlockMachineActiveable.setState(worked, this.world, this.pos);
                }
        	}
        	else if (flag)
        	{
            	flag1 = true;
            	BlockMachineActiveable.setState(false, this.world, this.pos);
        	}
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private boolean canExtract()
	{
		for (int slot = 0; slot <= 2; slot++)
		{
			ItemStack slotStack = getStackInSlot(slot);
			if (!slotStack.isEmpty() && EnergionBatteryManager.isBattery(slotStack))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean canWork()
	{
		for (int slot = 3; slot <= 11; slot++)
		{
			ItemStack slotStack = getStackInSlot(slot);
			if (slotStack.isEmpty() || slotStack.getCount() < 64) return true;
		}
		
		return false;
	}
	
	private int extractPower()
	{
		int energy = 0;
		
    	for (int i=0;i<3;i++)
    	{
        	ItemStack battery = getStackInSlot(i);
        	if (!battery.isEmpty() && EnergionBatteryManager.isBattery(battery))
        	{
        		energy += EnergionBatteryManager.extract(battery, 10);
        		setInventorySlotContents(i, EnergionBatteryManager.checkDepleted(battery));
        	}
    	}
    	
    	return energy;
	}
	
	private ItemStack produce()
	{
		if (buffer < 10) return ItemStack.EMPTY;
		
		int count = buffer / 10;
		buffer -= count * 10;
		
		return new ItemStack(ModBlocks.hologram, count);
	}
	
	private void fillOutput(ItemStack stack)
	{
		for (int slot = 3; slot <= 11; slot++)
		{
			ItemStack slotStack = getStackInSlot(slot);
			if (slotStack.isEmpty())
			{
				int old = stack.getCount();
				setInventorySlotContents(slot, stack);
				if (stack.getCount() < old)
				{
					stack = stack.copy();
					stack.setCount(old - stack.getCount());
				}
				else return;
			}
			else if (slotStack.getItem() == stack.getItem())
			{
				int old = slotStack.getCount();
				slotStack.grow(stack.getCount());
				if (slotStack.getCount() > getInventoryStackLimit()) slotStack.setCount(getInventoryStackLimit());
				
				stack.shrink(slotStack.getCount() - old);
				if (stack.getCount() <= 0) return;
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index <= 2) return EnergionBatteryManager.isBattery(stack);
		else return false;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.buffer = compound.getInteger("Buffer");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Buffer", (short)this.buffer);

        return compound;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandlerEnergion(this, 12, 0, 3);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)invHandler;
		return super.getCapability(capability, facing);
	}

}
