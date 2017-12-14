package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.capability.CapabilityThermionicOscillation;
import lykrast.prodigytech.common.capability.IThermionicOscillation;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.recipe.RotaryGrinderManager;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileCrystalGrowthChamber extends TileMachineInventory implements ITickable {
	/** The number of ticks that the machine needs to process */
	private int processTime;
	/** The current danger of the machine */
	private int danger;
	/** Progress in thermionic oscillation */
	private int progressN, progressS, progressE, progressW;

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
		if (index == 0) return stack.getItem() == ModItems.energionCrystalSeed;
		else return false;
	}
	
	private boolean canProcess()
	{
		if (getStackInSlot(0).isEmpty() || !getStackInSlot(1).isEmpty()) return false;
		return getStackInSlot(0).getItem() == ModItems.energionCrystalSeed;
	}

	@Override
	public void update() {
		if (!world.isRemote)
		{
			if (canProcess())
			{
				//---------------
				//Oscillations
				//---------------
				int north = getOscillation(EnumFacing.NORTH);
				int south = getOscillation(EnumFacing.SOUTH);
				int east = getOscillation(EnumFacing.EAST);
				int west = getOscillation(EnumFacing.WEST);
				
				//Increment everything
				if (north <= 0) progressN = 0;
				else if (north < 20) progressN++;
				if (south <= 0) progressS = 0;
				else if (south < 20) progressS++;
				if (east <= 0) progressE = 0;
				else if (east < 20) progressE++;
				if (west <= 0) progressW = 0;
				else if (west < 20) progressW++;
				
				//North/South
				if (progressN >= 20 && progressS >= 20)
				{
					progressN = 0;
					progressS = 0;
					processTime += weightedMean(north, south);
				}
				else if (progressN >= 20) danger += north;
				else if (progressS >= 20) danger += south;
				
				//East/West
				if (progressE >= 20 && progressW >= 20)
				{
					progressE = 0;
					progressW = 0;
					processTime += weightedMean(east, west);
				}
				else if (progressE >= 20) danger += east;
				else if (progressW >= 20) danger += west;

				//---------------
				//Danger
				//---------------
				if (danger >= Config.crystalGrowthChamberMaxDesync)
				{
					world.setBlockToAir(pos);
					world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);
				}
				else
				{
					if (danger > 0) danger--;
					
					if (processTime >= Config.crystalGrowthChamberProcessTime * 10)
					{
						getStackInSlot(0).shrink(1);
						if (getStackInSlot(1).isEmpty())
						{
							setInventorySlotContents(1, new ItemStack(ModItems.energionCrystal));
						}
						else
						{
							getStackInSlot(1).grow(1);
						}
					}
				}
			}
			else
			{
				if (danger > 0) danger--;
				if (progressN > 0) progressN--;
				if (progressS > 0) progressS--;
				if (progressE > 0) progressE--;
				if (progressW > 0) progressW--;
				if (processTime != 0) processTime = 0;
			}
		}
	}
	
	private int getOscillation(EnumFacing facing)
	{
		TileEntity tile = world.getTileEntity(pos.offset(facing));
		if (tile != null)
		{
			IThermionicOscillation capability = tile.getCapability(CapabilityThermionicOscillation.THERMIONIC_OSCILLATION, facing.getOpposite());
			if (capability != null)
			{
				return capability.getOscillationPower();
			}
		}
		
		return 0;
	}
	
	private static int weightedMean(int a, int b)
	{
		int c = Math.min(a, b);
		return (a+b+c)/3;
	}

	public int getField(int id) {
	    switch (id)
	    {
	        case 0:
	            return this.processTime;
	        case 1:
	            return this.danger;
	        case 2:
	            return this.progressN;
	        case 3:
	            return this.progressS;
	        case 4:
	            return this.progressE;
	        case 5:
	            return this.progressW;
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
	            this.progressN = value;
	            break;
	        case 3:
	            this.progressS = value;
	            break;
	        case 4:
	            this.progressE = value;
	            break;
	        case 5:
	            this.progressW = value;
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
        this.progressN = compound.getInteger("ProgressNorth");
        this.progressS = compound.getInteger("ProgressSouth");
        this.progressE = compound.getInteger("ProgressEast");
        this.progressW = compound.getInteger("ProgressWest");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProcessTime", (short)this.processTime);
        compound.setInteger("Danger", (short)this.danger);
        compound.setInteger("ProgressNorth", (short)this.progressN);
        compound.setInteger("ProgressSouth", (short)this.progressS);
        compound.setInteger("ProgressEast", (short)this.progressE);
        compound.setInteger("ProgressWest", (short)this.progressW);

        return compound;
    }

}
