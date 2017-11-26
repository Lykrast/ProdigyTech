package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockHotAirMachine;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager.RotaryGrinderRecipe;
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

public class TileCrystalGrowthChamber extends TileMachineInventory implements ITickable {
	/** The number of ticks that the machine needs to process */
	private int processTime;
	/** The current danger of the machine */
	private int danger;
	/** Expected thermionic oscillation phase */
	private int expectedN, expectedS, expectedE, expectedW;

	//Slots :
	//0 Input
	//1 Output
	public TileCrystalGrowthChamber() {
		super(2);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public String getName() {
		return super.getName() + "crystal_growth_chamber";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0 && getStackInSlot(1).isEmpty()) return true;
		else return false;
	}

	@Override
	public void update() {
		
	}

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return this.processTime;
	        case 1:
	            return this.danger;
	        case 2:
	            return this.expectedN;
	        case 3:
	            return this.expectedS;
	        case 4:
	            return this.expectedE;
	        case 5:
	            return this.expectedW;
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
	            this.danger = value;
	            break;
	        case 2:
	            this.expectedN = value;
	            break;
	        case 3:
	            this.expectedS = value;
	            break;
	        case 4:
	            this.expectedE = value;
	            break;
	        case 5:
	            this.expectedW = value;
	            break;
	    }
	}

	public int getFieldCount() {
	    return 6;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 2, 0, 
			new boolean[]{true,false}, 
			new boolean[]{false,true});

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)invHandler;
		return super.getCapability(capability, facing);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.processTime = compound.getInteger("ProcessTime");
        this.danger = compound.getInteger("Danger");
        this.expectedN = compound.getInteger("ExpectedNorth");
        this.expectedS = compound.getInteger("ExpectedSouth");
        this.expectedE = compound.getInteger("ExpectedEast");
        this.expectedW = compound.getInteger("ExpectedWest");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProcessTime", (short)this.processTime);
        compound.setInteger("Danger", (short)this.danger);
        compound.setInteger("ExpectedNorth", (short)this.expectedN);
        compound.setInteger("ExpectedSouth", (short)this.expectedS);
        compound.setInteger("ExpectedEast", (short)this.expectedE);
        compound.setInteger("ExpectedWest", (short)this.expectedW);

        return compound;
    }

}
