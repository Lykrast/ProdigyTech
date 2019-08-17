package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.item.ItemFoodPurified;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class TileFoodEnricher extends TileHotAirMachineSimple {
	//Recipe functions
	public static boolean isValidInput(ItemStack stack) {
		if (stack.getItem() != ModItems.purifiedFood) return false;
		ItemFood item = (ItemFood)stack.getItem();
		//Can't enrich food already at the caps
		return !(item.getHealAmount(stack) >= Config.foodEnricherFoodCap && item.getSaturationModifier(stack) >= Config.foodEnricherSaturationCap);
	}
	
	public static int getProcessTime(ItemStack stack) {
		//Assumes it is a valid input
		//Base formula is ((improved food restored + improved saturation restored) - (food restored + saturation restored))²
		//Simplified with wolfram alpha to (2*(food * saturation ratio increase + saturation ratio * food increase + food increase * saturation ratio increase) + food increase)²
		ItemFood food = (ItemFood) stack.getItem();
		int value = food.getHealAmount(stack), valueInc = Config.foodEnricherFoodIncrease;
		float saturation = food.getSaturationModifier(stack), saturationInc = Config.foodEnricherSaturationIncrease;
		double time = 2*(value*saturationInc + saturation*valueInc + valueInc*saturationInc) + valueInc;
		time *= time;
		return (int)(time * Config.foodEnricherBaseTime);
	}
	
	public static ItemStack enrich(ItemStack stack) {
		//Assumes it is a valid input
		ItemFood food = (ItemFood) stack.getItem();
		int value = food.getHealAmount(stack);
		float saturation = food.getSaturationModifier(stack);
		
		if (value < Config.foodEnricherFoodCap) value = Math.min(value + Config.foodEnricherFoodIncrease, Config.foodEnricherFoodCap);
		if (saturation < Config.foodEnricherSaturationCap) saturation = Math.min(saturation + Config.foodEnricherSaturationIncrease, Config.foodEnricherSaturationCap);
		
		return ItemFoodPurified.make(value, saturation);
	}
	
	//Tile stuff
    public TileFoodEnricher() {
		super(0.75F);
	}

	@Override
	public String getName() {
		return super.getName() + "food_enricher";
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
			cachedResult = enrich(input);
		}
		//Input changed
		else if (input != lastInput) {
			lastInput = input;
			cachedResult = enrich(input);
			if (!world.isRemote) {
				processTimeMax = 0;
				processTime = 0;
			}
		}
		//Missing the cache
		else if (cachedResult == null) cachedResult = enrich(input);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected boolean canProcess() {
    	if (getStackInSlot(0).isEmpty() || hotAir.getInAirTemperature() < 125) return false;
    	
    	updateCachedResult();
    	//Assume that if something was inserted it is valid input and that whatever is in the output is Purified Food
	    ItemStack curOutput = getStackInSlot(1);
        return curOutput.isEmpty() || (ItemStack.areItemStackTagsEqual(curOutput, cachedResult) && curOutput.getCount() + 1 <= ModItems.purifiedFood.getItemStackLimit());
    }
	
	@Override
	protected int getProcessSpeed() {
		return (int) (hotAir.getInAirTemperature() / 12.5);
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
        if (curOutput.isEmpty()) setInventorySlotContents(1, enrich(input));
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
