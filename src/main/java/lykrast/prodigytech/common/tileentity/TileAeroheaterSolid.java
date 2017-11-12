package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockAeroheaterSolid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileAeroheaterSolid extends TileMachineInventory implements ITickable {
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int currentItemBurnTime;
    /** The current temperature of the heater */
    private int temperature;
    /** A clock to change the speed at which temperature raises */
    private int temperatureClock;

	public TileAeroheaterSolid() {
		super(1);
		temperature = 30;
	}

	@Override
	public String getName() {
		return super.getName() + "solid_fuel_aeroheater";
	}
	
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

	@Override
	public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.furnaceBurnTime;
        }
        
        if (!this.world.isRemote)
        {
        	ItemStack fuel = getStackInSlot(0);
        	
        	if (this.isBurning() || !fuel.isEmpty())
            {
        		if (!this.isBurning())
                {
                    this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isBurning())
                    {
                        flag1 = true;

                        if (!fuel.isEmpty())
                        {
                            Item item = fuel.getItem();
                            fuel.shrink(1);

                            if (fuel.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(fuel);
                                this.setInventorySlotContents(0, item1);
                            }
                        }
                    }
                }
            }

            if (this.isBurning()) raiseTemperature();
            else lowerTemperature();
        	
            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockAeroheaterSolid.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private void raiseTemperature()
	{
		if (temperature >= 200) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature++;
			
			//5 seconds to reach 80 °C (when Draft Furnace starts working)
			if (temperature < 80) temperatureClock = 2;
			//10 more seconds to reach 100 °C (Draft Furnace reaches Furnace speed and 2 can get fueled at once)
			else if (temperature < 100) temperatureClock = 10;
			//30 more seconds to reach 125 °C (3 Draft Furnaces at once)
			else if (temperature < 125) temperatureClock = 24;
			//70 more seconds to reach 160 °C (4 Draft Furnaces at once)
			else if (temperature < 160) temperatureClock = 40;
			//120 more seconds to reach 200 °C (5 Draft Furnaces at once)
			else temperatureClock = 60;
		}
	}
	
	private void lowerTemperature()
	{
		if (temperature <= 30) return;
		
		if (temperatureClock > 1) temperatureClock--;
		else
		{
			temperature--;
			
			//Stays at 4+ furnaces (200-160) for 4 seconds
			if (temperature > 160) temperatureClock = 2;
			//Stays at 3+ furnaces (160-125) for 7 seconds
			else if (temperature > 125) temperatureClock = 4;
			//Stays at 2+ furnaces (125-100) for 10 seconds
			else if (temperature > 100) temperatureClock = 8;
			//Stays at 1+ furnaces (100-80) for 15 seconds
			else if (temperature > 80) temperatureClock = 15;
			//Fully cools (80-30) in 50 seconds
			else temperatureClock = 20;
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return TileEntityFurnace.isItemFuel(stack);
		else return false;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(getStackInSlot(0));
        this.temperature = compound.getInteger("Temperature");
        this.temperatureClock = compound.getInteger("TemperatureClock");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("Temperature", (short)this.temperature);
        compound.setInteger("TemperatureClock", (short)this.temperatureClock);

        return compound;
    }

    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.temperature;
            case 3:
                return this.temperatureClock;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.temperature = value;
                break;
            case 3:
                this.temperatureClock = value;
        }
    }

    public int getFieldCount()
    {
        return 4;
    }

}
