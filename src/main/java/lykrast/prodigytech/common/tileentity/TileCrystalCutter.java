package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockEnergionCrystal;
import lykrast.prodigytech.common.block.BlockMachineActiveable;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileCrystalCutter extends TileHotAirMachine {
	protected short clockTime;
	protected boolean processing;

	public TileCrystalCutter() {
		super(1, 0.8F);
		processTimeMax = Config.automaticCrystalCutterHarvestTime * 10;
		processing = false;
	}

	@Override
	public String getName() {
		return super.getName() + "automatic_crystal_cutter";
	}
	
	protected BlockPos getTarget() {
		return pos.offset(world.getBlockState(pos).getValue(BlockHorizontal.FACING));
	}

	@Override
	public void update() {
        boolean flag = processing;
        boolean flag1 = false;
        
        process();
        
        if (!this.world.isRemote)
        {
        	hotAir.updateInTemperature(world, pos);
        	
        	if (canProcess())
        	{
        		if (processing)
        		{
        			BlockPos targetPos = getTarget();
        			IBlockState target = world.getBlockState(targetPos);
        			//Crystal ready for harvesting
        			if (target.getBlock() == ModBlocks.energionCrystal && BlockEnergionCrystal.getAge(target) > 0)
        			{
        				if (processTime <= 0) {
        					//Harvest
        					//Assume the stack is always correct item when non empty
        					ItemStack stack = getStackInSlot(0);
        					if (stack.isEmpty()) setInventorySlotContents(0, new ItemStack(ModItems.energionCrystalSeed));
        					else stack.grow(1);

        					int age = BlockEnergionCrystal.getAge(target);
        	        		world.playEvent(2001, targetPos, Block.getStateId(target));
        	                world.setBlockState(targetPos, ((BlockEnergionCrystal)ModBlocks.energionCrystal).withAge(age - 1));
        					
        					flag1 = true;
        					if (age <= 1) processing = false;
        					processTime = processTimeMax;
        				}
        			}
        			else
        			{
        				//Crystal is done harvesting or not harvestable
                		processing = false;
                		processTime = processTimeMax;
        			}
        		}
        		else
        		{
        			if (clockTime <= 1)
        			{
						clockTime = (short) Config.automaticCrystalCutterIdleTime;
						IBlockState target = world.getBlockState(getTarget());
						if (target.getBlock() == ModBlocks.energionCrystal && BlockEnergionCrystal.getAge(target) >= 5)
						{
							// Crystal is ready for harvest
							processing = true;
							processTime = processTimeMax;
						}
        			}
        			else clockTime--;
        		}
        	}
        	else
        	{
        		//Can't process
        		processing = false;
        		processTime = processTimeMax;
        	}
        	
        	hotAir.updateOutTemperature();
        	
            if (flag != processing)
            {
                flag1 = true;
                BlockMachineActiveable.setState(processing, this.world, this.pos);
            }
        }

        if (flag1) markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	protected int getProcessSpeed() {
		return hotAir.getInAirTemperature() / 10;
	}

	@Override
	protected boolean canProcess() {
		ItemStack stack = getStackInSlot(0);
		return hotAir.getInAirTemperature() >= 100 && (stack.isEmpty() || stack.getCount() < 64);
	}
	
	@Override
	public boolean isProcessing() {
        return processing;
    }

	@Override
	protected IItemHandlerModifiable createInventoryHandler() {
		return new ProdigyInventoryHandler(this, 1, 0, false, true);
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        processTimeMax = Config.automaticCrystalCutterHarvestTime * 10;
        clockTime = compound.getShort("ClockTime");
        processing = compound.getBoolean("Processing");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("ClockTime", clockTime);
        compound.setBoolean("Processing", processing);

        return compound;
    }

}
