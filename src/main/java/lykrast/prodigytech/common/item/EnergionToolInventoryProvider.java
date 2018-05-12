package lykrast.prodigytech.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class EnergionToolInventoryProvider implements ICapabilitySerializable<NBTTagCompound> {
	private EnergionToolInventory inventory;
	
	public EnergionToolInventoryProvider() {
		inventory = new EnergionToolInventory();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
        NBTTagCompound ret = new NBTTagCompound();
        inventory.getStackInSlot(0).writeToNBT(ret);
		return ret;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		inventory.setStackInSlot(0, new ItemStack(nbt));
	}

}
