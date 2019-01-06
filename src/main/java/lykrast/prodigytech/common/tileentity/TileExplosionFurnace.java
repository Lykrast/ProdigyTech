package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.Dampener;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.ExplosionFurnaceRecipe;
import lykrast.prodigytech.common.recipe.ExplosionFurnaceManager.Explosive;
import lykrast.prodigytech.common.util.ProdigyInventoryHandler;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileExplosionFurnace extends TileMachineInventory {

	public TileExplosionFurnace() {
		super(9);
	}

	@Override
	public String getName() {
		return super.getName() + "explosion_furnace";
	}

	/**
	 * Starts the reaction process
	 */
	public void process(EnumFacing facing)
	{
		ItemStack exp = getStackInSlot(0);
		ItemStack damp = getStackInSlot(1);
		if (!exp.isEmpty())
		{
			//Find explosive and dampener
			Explosive explosive = ExplosionFurnaceManager.findExplosive(exp);
			Dampener dampener = ExplosionFurnaceManager.findDampener(damp);
			if (explosive != null)
			{
				//Get EP and efficiency, remove explosives
				int power = explosive.getPower(exp);
				int dampened = dampener != null ? dampener.getDampening(damp) : 0;
				float efficiency = 1;
				if (dampened < power) {
					//EP was underdampened, reduce efficiency
					efficiency = 1 - ((power - dampened)/(float)power);
				}
				else {
					//EP was overdampened (or just enough), reduce EP by the excess dampening
					power = Math.max(0, power*2-dampened);
				}
				
				removeStackFromSlot(0);
				removeStackFromSlot(1);
				
				//Make explosion
				BlockPos origin = pos.offset(facing);
				world.createExplosion(null, origin.getX() + 0.5, origin.getY() + 0.5, origin.getZ() + 0.5, Math.min(power / 720F, 2.0F), false);

				ItemStack reagent = getStackInSlot(5);
				//For each input slot
				for (int slot = 2; slot <= 4; slot++)
				{
					ItemStack stack = getStackInSlot(slot);
					if (!stack.isEmpty())
					{
						//Used to check if we should try for another recipe with the same output if we run out of reagent
						boolean shouldCraft = true;
						while (shouldCraft)
						{
							shouldCraft = false;
							//Find recipe using the reagent (or no reagent)
							ExplosionFurnaceRecipe recipe = ExplosionFurnaceManager.findRecipe(stack, reagent);
							if (recipe != null && recipe.getRequiredPower() <= power)
							{
								ItemStack output = recipe.getOutput();
								int inCount = recipe.getInputCount();
								int outCount = output.getCount();
								int cost = recipe.getRequiredPower();
								output.setCount(0);
								
								boolean requireReagent = recipe.needReagent();
								int craftLeft = 0;
								
								//Craft stacks until we run out of input, EP or reagent
								while (!shouldCraft && stack.getCount() >= inCount && power >= cost)
								{
									//Reagent check
									if (requireReagent)
									{
										//No crafts left for current reagent
										if (craftLeft <= 0)
										{
											//Decrement reagent if some is left
											//findRecipe can only give something where reagent matches or isn't required
											if (!reagent.isEmpty() && recipe.isValidReagent(reagent))
											{
												reagent.shrink(1);
												craftLeft += recipe.getCraftPerReagent() - 1;
												//Remove from inventory if it's empty
												if (reagent.getCount() <= 0)
												{
													removeStackFromSlot(5);
													reagent = getStackInSlot(5);
												}
											}
											else
											{
												//No reagent left, for another craft, don't consume anything and get ready for another crafting loop
												shouldCraft = true;
												break;
											}
										}
										//Reagents has craft left, use some up
										else craftLeft--;
									}

									stack.shrink(inCount);
									power -= cost;
									output.grow(outCount);
								}
								//Generate output
								if (stack.getCount() <= 0) removeStackFromSlot(slot);
								
								output.setCount((int)(output.getCount() * efficiency));
								if (output.getCount() > 0) fillOutput(output, origin);
							}
						}
					}
				}
				
				markDirty();
			}
		}
	}
	
	private void fillOutput(ItemStack stack, BlockPos dump)
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
				else return;
			}
			else if (slotStack.getItem() == stack.getItem())
			{
				int old = slotStack.getCount();
				slotStack.grow(stack.getCount());
				if (slotStack.getCount() > getInventoryStackLimit()) slotStack.setCount(getInventoryStackLimit());
				
				stack.shrink(slotStack.getCount() - old);
				if (stack.getCount() <= 0) return;
			}
		}
		InventoryHelper.spawnItemStack(world, dump.getX(), dump.getY(), dump.getZ(), stack);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) return ExplosionFurnaceManager.isValidExplosive(stack);
		else if (index == 1) return ExplosionFurnaceManager.isValidDampener(stack);
		else if (index == 5) return ExplosionFurnaceManager.isValidReagent(stack);
		else if (index >= 2 && index <= 4) return ExplosionFurnaceManager.isValidInput(stack);
		else return false;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	private ProdigyInventoryHandler invHandler = new ProdigyInventoryHandler(this, 9, 0, 
			new boolean[]{true,true,true,true,true,true,false,false,false}, 
			new boolean[]{false,false,false,false,false,false,true,true,true});
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)invHandler;
		return super.getCapability(capability, facing);
	}

}
