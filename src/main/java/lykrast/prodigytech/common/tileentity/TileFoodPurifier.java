package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.item.ItemFoodPurified;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class TileFoodPurifier extends TileHotAirMachineSimple {
	//Recipe functions
	public static boolean isValidInput(ItemStack stack) {
		Item item = stack.getItem();
		return item != ModItems.purifiedFood && item instanceof ItemFood;
	}
	
	public static int getProcessTime(ItemStack stack) {
		//Assumes it is a valid input
		//Formula is (food restored + saturation restored) * config time
		ItemFood food = (ItemFood) stack.getItem();
		return (int)(food.getHealAmount(stack) * (1 + 2 * food.getSaturationModifier(stack)) * Config.foodPurifierBaseTime);
	}
	
	//Tile stuff
    public TileFoodPurifier() {
		super(0.8F);
	}

	@Override
	public String getName() {
		return super.getName() + "food_purifier";
	}
    
	private ItemStack cachedResult, lastInput  = ItemStack.EMPTY;
	private void updateCachedResult() {
		ItemStack input = getStackInSlot(0);
		//No input
		if (input.isEmpty()) {
			cachedResult = null;
			lastInput = ItemStack.EMPTY;
		}
		//No cached input, let it slide
		else if (lastInput.isEmpty()) {
			lastInput = input;
			cachedResult = ItemFoodPurified.make(input);
		}
		//Input changed
		else if (input != lastInput) {
			lastInput = input;
			cachedResult = ItemFoodPurified.make(input);
			processTimeMax = 0;
			processTime = 0;
		}
		//Missing the cache
		else if (cachedResult == null) cachedResult = ItemFoodPurified.make(input);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected boolean canProcess() {
    	if (getStackInSlot(0).isEmpty() || hotAir.getInAirTemperature() < 80) return false;
    	
    	updateCachedResult();
    	//Assume that if something was inserted it is valid input and that whatever is in the output is Purified Food
	    ItemStack curOutput = getStackInSlot(1);
        return curOutput.isEmpty() || (ItemStack.areItemStackTagsEqual(curOutput, cachedResult) && curOutput.getCount() + 1 <= ModItems.purifiedFood.getItemStackLimit());
    }
	
	@Override
	protected int getProcessSpeed() {
		return hotAir.getInAirTemperature() / 8;
	}

	@Override
	public void update() {
        boolean wasProcessing = isProcessing();
        boolean shouldDirty = false;
        
        process();
        
        if (!world.isRemote) {
        	hotAir.updateInTemperature(world, pos);
        	
        	if (canProcess()) {
            	if (processTimeMax <= 0) {
            		processTimeMax = getProcessTime(getStackInSlot(0)) * 10;
            		processTime = processTimeMax;
            	}
            	else if (processTime <= 0) {
            		smelt();
            		shouldDirty = true;
            		
            		if (canProcess()) {
            			processTimeMax = getProcessTime(getStackInSlot(0)) * 10;
                		processTime = processTimeMax;
            		}
            		else {
            			processTimeMax = 0;
            			processTime = 0;
            		}
            	}
        	}
        	else if (processTime >= processTimeMax) {
            	updateCachedResult();
    			processTimeMax = 0;
    			processTime = 0;
    		}
        	
        	hotAir.updateOutTemperature();
        	
            if (wasProcessing != isProcessing()) {
                shouldDirty = true;
                BlockMachineActiveable.setState(isProcessing(), world, pos);
            }
        }

        if (shouldDirty) markDirty();
	}
	
	private void smelt() {
		ItemStack input = getStackInSlot(0);
        ItemStack curOutput = getStackInSlot(1);
        
        //Assume that whatever was in the output slot is matching and there's room, since canProcess checks for that
        if (curOutput.isEmpty()) setInventorySlotContents(1, ItemFoodPurified.make(input));
        else curOutput.grow(1);

        input.shrink(1);
    	updateCachedResult();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return isValidInput(stack);
		else return false;
	}

}
