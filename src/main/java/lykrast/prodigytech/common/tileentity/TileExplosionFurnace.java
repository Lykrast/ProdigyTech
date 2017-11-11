package lykrast.prodigytech.common.tileentity;

import java.util.Arrays;

import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileExplosionFurnace extends TileEntity implements IInventory {
	
	private ItemStack[] inventory;
	public TileExplosionFurnace()
	{
		inventory = new ItemStack[9];
		clear();
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(this.getName(), new Object[0]);
	}

	@Override
	public String getName() {
		return "container." + ProdigyTech.MODID + ".explosion_furnace";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
	protected boolean isValidIndex(int index)
	{
		return (index >= 0 && index < getSizeInventory());
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (!isValidIndex(index)) return ItemStack.EMPTY;
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		if (stack.isEmpty()) return ItemStack.EMPTY;
		else stack = stack.copy();
		
		if (stack.getCount() < count) count = stack.getCount();
		
		stack.setCount(count);
		getStackInSlot(index).shrink(count);
		markDirty();
		
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return decrStackSize(index, getStackInSlot(index).getCount());
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (isValidIndex(index)) inventory[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Do some checking
		return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		Arrays.fill(inventory, ItemStack.EMPTY);
	}

}
