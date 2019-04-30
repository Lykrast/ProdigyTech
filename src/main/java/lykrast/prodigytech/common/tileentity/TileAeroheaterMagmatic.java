package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.block.BlockAeroheaterMagmatic;
import lykrast.prodigytech.common.capability.CapabilityHotAir;
import lykrast.prodigytech.common.capability.HotAirAeroheater;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileAeroheaterMagmatic extends TileEntity implements ITickable {
    private HotAirAeroheater hotAir;
    private boolean active;
    private boolean checkNextTick = false;

	public TileAeroheaterMagmatic() {
		hotAir = new HotAir();
		active = false;
		checkNextTick = true;
	}
	
	public void checkActive()
	{
		Block below = world.getBlockState(pos.down()).getBlock();
		if (below == Blocks.LAVA || below == Blocks.FLOWING_LAVA) active = true;
		else active = false;
	}

	@Override
	public void update() {
        boolean flag = world.getBlockState(pos).getValue(BlockAeroheaterMagmatic.ACTIVE);
        boolean flag1 = false;
        
        if (!this.world.isRemote)
        {
        	if (checkNextTick)
        	{
        		checkNextTick = false;
        		checkActive();
        	}
        	
            if (active) hotAir.raiseTemperature();
            else hotAir.lowerTemperature();
        	
            if (flag != active)
            {
                flag1 = true;
                BlockAeroheaterMagmatic.setState(active, this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        active = compound.getBoolean("Active");
        hotAir.deserializeNBT(compound.getCompoundTag("HotAir"));
        checkNextTick = true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("Active", active);
        compound.setTag("HotAir", hotAir.serializeNBT());

        return compound;
    }
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityHotAir.HOT_AIR && (facing == EnumFacing.UP || facing == null))
			return (T)hotAir;
		return super.getCapability(capability, facing);
	}
	
	private static class HotAir extends HotAirAeroheater {
		public HotAir() {
			super(80);
		}

		@Override
		protected void resetRaiseClock() {
			//10 seconds to reach 80 °C (when Draft Furnace starts working)
			temperatureClock = 4;
		}

		@Override
		protected void resetLowerClock() {
			//20 seconds to cool down fully
			temperatureClock = 8;
		}
		
	}

}
