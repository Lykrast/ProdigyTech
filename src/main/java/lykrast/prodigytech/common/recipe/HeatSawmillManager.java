package lykrast.prodigytech.common.recipe;

import java.util.List;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class HeatSawmillManager extends SimpleRecipeManagerSecondaryOutput {
	public static final HeatSawmillManager INSTANCE = new HeatSawmillManager();
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, Config.heatSawmillProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, Config.heatSawmillProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, ItemStack secondary)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, secondary, Config.heatSawmillProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, ItemStack secondary)
	{
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, secondary, Config.heatSawmillProcessTime));
	}

	@Override
	public void init() {
		addRecipe("plankWood", new ItemStack(Items.STICK, (int)(2 * Config.heatSawmillStickMultiplier)), new ItemStack(ModItems.sawdust));
		
		registerPlanks();
	}
	
	//Lots of stuff below is from CoFH to automatically register all registered woods
	//Credits to them for having this stuff open source on a permissive license
	
	//From Thermal Expansion
	//https://github.com/CoFH/ThermalExpansion/blob/1.12/src/main/java/cofh/thermalexpansion/util/managers/machine/SawmillManager.java
	private void registerPlanks() {
		InventoryCraftingFalse tempCrafting = new InventoryCraftingFalse(3, 3);

		for (int i = 0; i < 9; i++) {
			tempCrafting.setInventorySlotContents(i, ItemStack.EMPTY);
		}
		
		List<ItemStack> ores = OreDictionary.getOres("logWood", false);
		
		for (ItemStack log : ores)
		{
			//That's a workaround used in CoFH Core to ignore an overridden getDamage()
			if (Items.DIAMOND.getDamage(log) == OreDictionary.WILDCARD_VALUE)
			{
				NonNullList<ItemStack> logList = NonNullList.create();
				Block block = Block.getBlockFromItem(log.getItem());
				block.getSubBlocks(block.getCreativeTabToDisplayOn(), logList);
				
				for (ItemStack log1 : logList) registerPlank(tempCrafting, log1);
			}
			else
			{
				ItemStack log1 = log.copy();
				log1.setCount(1);
				registerPlank(tempCrafting, log1);
			}
		}
	}
	
	private void registerPlank(InventoryCrafting inv, ItemStack log) {
		inv.setInventorySlotContents(0, log);
		ItemStack result = CraftingManager.findMatchingResult(inv, null);
		
		if (!result.isEmpty())
		{
			result = result.copy();
			result.setCount(Math.min(result.getMaxStackSize(), (int)(result.getCount() * Config.heatSawmillPlankMultiplier)));
			addRecipe(log, result, new ItemStack(ModItems.sawdust));
		}
	}
	
	//From CoFH Core
	//https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/inventory/InventoryCraftingFalse.java
	/**
	 * This class is used to get recipes (IRecipe requires it...) with a Container.
	 *
	 * @author King Lemming
	 */
	private static class InventoryCraftingFalse extends InventoryCrafting {

		private static final NullContainer nullContainer = new NullContainer();

		/* NULL INNER CLASS */
		public static class NullContainer extends Container {

			@Override
			public void onCraftMatrixChanged(IInventory inventory) {

			}

			@Override
			public boolean canInteractWith(EntityPlayer player) {

				return false;
			}

		}

		public InventoryCraftingFalse(int width, int height) {

			super(nullContainer, width, height);
		}

	}

}
