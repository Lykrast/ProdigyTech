package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.recipe.SoldererManager;
import lykrast.prodigytech.common.recipe.SoldererManager.SoldererRecipe;
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

public class TileSolderer extends TileMachineInventory implements ITickable {
	/** The number of ticks that the machine needs to process */
	private int processTime;
	/** The number of ticks that the current recipes needs in total */
	private int processTimeMax;
	/** The current temperature of the machine */
	private int temperature;
	/** The amount of gold in the machine */
	private int gold;

	//Slots :
	//0 Pattern
	//1 Gold
	//2 Additive
	//3 Plates
	//4 Output
	public TileSolderer() {
		super(5);
	}

	@Override
	public String getName() {
		return super.getName() + "solderer";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return SoldererManager.isValidPattern(stack);
		else if (index == 1) return SoldererManager.getGoldAmount(stack) > 0;
		else if (index == 2) return SoldererManager.isValidAdditive(stack);
		else if (index == 3) return SoldererManager.isPlate(stack);
		else return false;
	}
	
	private int canSmeltGold()
	{
		if (getStackInSlot(1).isEmpty() || temperature < 125) return 0;
		
		int amount = SoldererManager.getGoldAmount(getStackInSlot(1));
		if (amount > (Config.soldererMaxGold - gold)) return 0;
		else return amount;
	}

	private SoldererRecipe cachedRecipe;
    private void updateCachedRecipe() {
    	if (cachedRecipe == null) cachedRecipe = SoldererManager.findRecipe(getStackInSlot(0), getStackInSlot(2), gold);
    	else if (!cachedRecipe.isValidInput(getStackInSlot(0), getStackInSlot(2), gold)) cachedRecipe = SoldererManager.findRecipe(getStackInSlot(0), getStackInSlot(2), gold);
    }
    
	private boolean canProcess()
    {
    	if (getStackInSlot(0).isEmpty() || getStackInSlot(3).isEmpty() || temperature < 125)
    	{
    		cachedRecipe = null;
    		return false;
    	}
    	
    	updateCachedRecipe();
    	if (cachedRecipe == null) return false;
    	
    	ItemStack itemstack = cachedRecipe.getOutput();
    	
	    ItemStack itemstack1 = getStackInSlot(4);
		
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

	@Override
	public void update() {
        boolean flag = this.isProcessing();
        boolean flag1 = false;
        
        process();
        
        if (!this.world.isRemote)
        {
        	updateInTemperature();

    		int goldAmount = canSmeltGold();
    		if (goldAmount > 0)
    		{
    			gold += goldAmount;
    			getStackInSlot(1).shrink(1);
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
        ItemStack itemstack1 = cachedRecipe.getOutput();
        ItemStack itemstack2 = getStackInSlot(4);

        if (itemstack2.isEmpty())
        {
        	setInventorySlotContents(4, itemstack1);
        }
        else if (itemstack2.getItem() == itemstack1.getItem())
        {
            itemstack2.grow(itemstack1.getCount());
        }

        if (cachedRecipe.requiresAdditive()) getStackInSlot(2).shrink(cachedRecipe.getAdditive().getCount());
        getStackInSlot(3).shrink(1);
        gold -= cachedRecipe.getGoldAmount();
	}
	
	private int getProcessSpeed()
	{
		return (int) (temperature / 12.5);
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
	            return this.gold;
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
	            this.gold = value;
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
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 5, 0, 
			new boolean[]{true,true,true,true,false}, 
			new boolean[]{false,false,false,false,true});

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
        this.gold = compound.getInteger("Gold");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProcessTime", processTime);
        compound.setInteger("ProcessTimeMax", processTimeMax);
        compound.setInteger("Temperature", temperature);
        compound.setInteger("Gold", gold);

        return compound;
    }

}
