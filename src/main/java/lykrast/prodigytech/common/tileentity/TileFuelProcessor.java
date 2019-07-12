package lykrast.prodigytech.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileFuelProcessor extends TileHotAirMachineSimple {
	//Recipe functions
	private static final List<Item> BLACKLIST = new ArrayList<>();
	
	public static void initBlacklist() {
		//Called when the items are ready, just in case static screws stuff up
		BLACKLIST.add(ModItems.fuelPellet1);
		BLACKLIST.add(ModItems.fuelPellet4);
		BLACKLIST.add(ModItems.fuelPellet16);
		BLACKLIST.add(ModItems.fuelPellet64);
	}
	
	public static boolean isValidInput(ItemStack stack) {
		//Item must be fuel, burn for at least 1 item, not burn for more than 64 items, not have a container (no buckets) and must not be a Fuel Pellet
		int burn = TileEntityFurnace.getItemBurnTime(stack);
		return burn >= 200 && burn <= 12800 && stack.getItem().getContainerItem(stack).isEmpty() && !BLACKLIST.contains(stack.getItem());
	}
	
	public static int getProcessTime(ItemStack stack) {
		//Assumes it is a valid input
		return ((int)Math.sqrt(TileEntityFurnace.getItemBurnTime(stack) / 200)) * Config.fuelProcessorBaseTime;
	}
	
	public static int getPelletsAmount(ItemStack stack) {
		//Assumes it is a valid input
		return TileEntityFurnace.getItemBurnTime(stack) / 200;
	}
	
	//Tile stuff
    public TileFuelProcessor() {
		super(0.8F);
	}

	@Override
	public String getName() {
		return super.getName() + "fuel_processor";
	}
    
    @SuppressWarnings("deprecation")
	@Override
	protected boolean canProcess() {
    	if (getStackInSlot(0).isEmpty() || hotAir.getInAirTemperature() < 80) return false;
    	
    	//Assume that if something was inserted it is valid input
    	//And that whatever is in the output slot is a Fuel Pellet if there's something
	    ItemStack curOutput = getStackInSlot(1);
        if (curOutput.isEmpty()) return true;
        
	    int amount = getPelletsAmount(getStackInSlot(0));
	    return curOutput.getCount() + amount <= getInventoryStackLimit() && curOutput.getCount() + amount <= ModItems.fuelPellet1.getItemStackLimit();
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
        int amount = getPelletsAmount(input);
        
        //Assume that whatever was in the output slot is a Fuel Pellet and that there was enough room

        if (curOutput.isEmpty()) setInventorySlotContents(1, new ItemStack(ModItems.fuelPellet1, amount));
        else curOutput.grow(amount);

        input.shrink(1);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return isValidInput(stack);
		else return false;
	}

}
