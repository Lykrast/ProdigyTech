package lykrast.prodigytech.common.tileentity;

import java.util.Arrays;

import lykrast.prodigytech.common.block.BlockExplosionFurnace;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceRecipe;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileExplosionFurnace extends TileEntity implements IInventory {

	//Slot IDs
	//Explosives - Explosives	: 0
	//Explosives - Reactant		: 1
	//Input						: 2-4
	//Reagent					: 5
	//Output					: 6-8
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
	
	/**
	 * Starts the reaction process
	 */
	public void process(EnumFacing facing)
	{
		BlockPos origin = pos.offset(facing);
		world.createExplosion(null, origin.getX() + 0.5, origin.getY() + 0.5, origin.getZ() + 0.5, 2.0F, false);
		
		for (int slot = 2; slot <= 4; slot++)
		{
			ItemStack stack = getStackInSlot(slot);
			if (!stack.isEmpty())
			{
				ExplosionFurnaceRecipe recipe = ExplosionFurnaceRecipe.findRecipe(stack);
				if (recipe != null)
				{
					ItemStack output = recipe.getOutput();
					int inCount = recipe.getInput().getCount();
					int outCount = output.getCount();
					output.setCount(0);
					
					while (stack.getCount() >= inCount)
					{
						stack.shrink(inCount);
						output.grow(outCount);
					}
					if (stack.getCount() <= 0) removeStackFromSlot(slot);
					
					fillOutput(output);
				}
			}
		}
		
		markDirty();
	}
	
	private void fillOutput(ItemStack stack)
	{
		for (int slot = 6; slot <= 8; slot++)
		{
			ItemStack slotStack = getStackInSlot(slot);
			if (slotStack.isEmpty())
			{
				int old = stack.getCount();
				setInventorySlotContents(slot, stack);
				if (stack.getCount() < old)
				{
					stack = stack.copy();
					stack.setCount(old - stack.getCount());
				}
				else break;
			}
			else if (slotStack.getItem() == stack.getItem())
			{
				int old = slotStack.getCount();
				slotStack.grow(stack.getCount());
				if (slotStack.getCount() > getInventoryStackLimit()) slotStack.setCount(getInventoryStackLimit());
				
				stack.shrink(slotStack.getCount() - old);
				if (stack.getCount() <= 0) break;
			}
		}
	}
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.withProperty(BlockExplosionFurnace.TRIGGERED, Boolean.valueOf(false)) != newState.withProperty(BlockExplosionFurnace.TRIGGERED, Boolean.valueOf(false));
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
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
        for (ItemStack itemstack : inventory)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = inventory.get(index);
        inventory.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, inventory);

        return compound;
    }

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		//Output slots
		if (index >= 6) return false;
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

}
