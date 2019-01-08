package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.util.FacingUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileDispersingExtractor extends TileExtractor {
	//Next face the extractor is gonna attempt to push to, 0-4
	private byte nextRobin = 0;

	@Override
	public String getName() {
		return super.getName() + "dispersing_extractor";
	}

	@Override
	protected void work(EnumFacing front) {
		if (hasItem()) {
			//Push to the next available face by order, starting at the one right after the previous push
			EnumFacing[] facings = FacingUtil.getRoundRobinExcluding(front);
			for (int i = 0; i < 5; i++) {
				int index = (nextRobin + i) % 5;
				if (push(facings[index])) {
					nextRobin = (byte) ((index + 1) % 5);
					break;
				}
			}
		}
		pull(front);
	}

	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        nextRobin = compound.getByte("NextRobin");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("NextRobin", (byte) nextRobin);

        return compound;
    }

}
