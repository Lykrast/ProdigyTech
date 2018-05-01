package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager;
import lykrast.prodigytech.common.recipe.AtomicReshaperManager.AtomicReshaperRecipe;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileAtomicReshaper extends TileMachineInventory implements ITickable {
	/** The number of ticks that the machine needs to process */
	private int processTime;
	/** The number of ticks that the current recipes needs in total */
	private int processTimeMax;
	/** The current temperature of the machine */
	private int temperature;
	/** The amount of primordium in the machine */
	private int primordium;

	//Slots :
	//0 Primordium
	//1 Input
	//2 Output
	public TileAtomicReshaper() {
		super(3);
	}

	@Override
	public String getName() {
		return super.getName() + "atomic_reshaper";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return stack.getItem() == ModItems.primordium;
		else if (index == 1) return AtomicReshaperManager.INSTANCE.isValidInput(stack);
		else return false;
	}
	
	private int canSmeltPrimordium()
	{
		if (getStackInSlot(0).isEmpty() || primordium > ((Config.atomicReshaperMaxPrimordium - 1) * 100)) return 0;
		else return 100;
	}

	private AtomicReshaperRecipe cachedRecipe;
    private void updateCachedRecipe() {
    	if (cachedRecipe == null) cachedRecipe = AtomicReshaperManager.INSTANCE.findRecipe(getStackInSlot(1));
    	else if (!cachedRecipe.isValidInput(getStackInSlot(1))) cachedRecipe = AtomicReshaperManager.INSTANCE.findRecipe(getStackInSlot(1));
    }
    
	private boolean canProcess()
    {
    	if (getStackInSlot(1).isEmpty() || temperature < 250)
    	{
    		cachedRecipe = null;
    		return false;
    	}
    	
    	updateCachedRecipe();
    	if (cachedRecipe == null) return false;
    	if (cachedRecipe.getPrimordiumAmount() > primordium) return false;
    	
    	//Recipe only has 1 possible output, do merging checks
    	if (cachedRecipe.isSingleOutput())
    	{
        	ItemStack itemstack = cachedRecipe.getSingleOutput();
        	
    	    ItemStack itemstack1 = getStackInSlot(2);
    		
            if (itemstack1.isEmpty())
            {
                return true;
            }
            else if (!itemstack1.isItemEqual(itemstack))
            {
                return false;
            }
            else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
            {
                return true;
            }
            else
            {
                return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
            }
    	}
    	//Recipe can output different items, since the container can't deal with this there's no merging
    	else return getStackInSlot(2).isEmpty();
    }

	@Override
	public void update() {
        boolean flag = this.isProcessing();
        boolean flag1 = false;
        
        process();
        
        if (!this.world.isRemote)
        {
        	updateInTemperature();

    		int primordiumAmount = canSmeltPrimordium();
    		if (primordiumAmount > 0)
    		{
    			primordium += primordiumAmount;
    			getStackInSlot(0).shrink(1);
    		}
    		
        	if (canProcess())
        	{
            	if (processTimeMax <= 0)
            	{
            		processTimeMax = cachedRecipe.getTimeProcessing();
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0)
            	{
            		smelt();
            		flag1 = true;
            		
            		if (canProcess())
            		{
                		processTimeMax = cachedRecipe.getTimeProcessing();
                		processTime = processTimeMax;
            		}
            		else
            		{
            			processTimeMax = 0;
            			processTime = 0;
            		}
            	}
        	}
        	else if (processTime >= processTimeMax)
        	{
        		processTimeMax = 0;
        		processTime = 0;
        	}
        	
            if (flag != this.isProcessing())
            {
                flag1 = true;
                BlockMachineActiveable.setState(this.isProcessing(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private void smelt()
	{
		updateCachedRecipe();
		if (cachedRecipe.isSingleOutput())
		{
	        ItemStack itemstack1 = cachedRecipe.getSingleOutput();
	        ItemStack itemstack2 = getStackInSlot(2);

	        if (itemstack2.isEmpty())
	        {
	        	setInventorySlotContents(2, itemstack1);
	        }
	        else if (itemstack2.getItem() == itemstack1.getItem())
	        {
	            itemstack2.grow(itemstack1.getCount());
	        }
		}
		else
		{
			setInventorySlotContents(2, cachedRecipe.getRandomOutput(world.rand));
		}

        getStackInSlot(1).shrink(1);
        primordium -= cachedRecipe.getPrimordiumAmount();
	}
	
	private int getProcessSpeed()
	{
		return temperature / 25;
	}

	public boolean isProcessing()
    {
        return processTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isProcessing(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }
	
	private void process() {
		if (isProcessing())
		{
			if (canProcess()) processTime -= getProcessSpeed();
			else processTime = processTimeMax;
		}
	}

	private void updateInTemperature() {
		temperature = TemperatureHelper.getBlockTemp(world, pos.down());
	}

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return this.processTime;
	        case 1:
	            return this.processTimeMax;
	        case 2:
	            return this.temperature;
	        case 3:
	            return this.primordium;
	        default:
	            return 0;
	    }
	}

	public void setField(int id, int value) {
	    switch (id)
	    {
	        case 0:
	            this.processTime = value;
	            break;
	        case 1:
	            this.processTimeMax = value;
	            break;
	        case 2:
	            this.temperature = value;
	            break;
	        case 3:
	            this.primordium = value;
	            break;
	    }
	}

	public int getFieldCount() {
	    return 4;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.DOWN)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 3, 0, 
			new boolean[]{true,true,false}, 
			new boolean[]{false,false,true});

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != EnumFacing.DOWN)
			return (T)invHandler;
		return super.getCapability(capability, facing);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.processTime = compound.getInteger("ProcessTime");
        this.processTimeMax = compound.getInteger("ProcessTimeMax");
        this.temperature = compound.getInteger("Temperature");
        this.primordium = compound.getInteger("Primordium");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProcessTime", processTime);
        compound.setInteger("ProcessTimeMax", processTimeMax);
        compound.setInteger("Temperature", temperature);
        compound.setInteger("Primordium", primordium);

        return compound;
    }

}
