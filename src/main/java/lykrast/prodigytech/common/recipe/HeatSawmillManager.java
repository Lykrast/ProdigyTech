package lykrast.prodigytech.common.recipe;

import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreIngredient;

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
		
		if (!Config.heatSawmillAutoPlankRecipes) {
			for (int i=0;i<=3;i++)
				registerPlank(new ItemStack(Blocks.LOG, 1, i), new ItemStack(Blocks.PLANKS, 4, i));
			for (int i=0;i<=1;i++)
				registerPlank(new ItemStack(Blocks.LOG2, 1, i), new ItemStack(Blocks.PLANKS, 4, i+4));
			registerPlank(new ItemStack(ModBlocks.zorraLog), new ItemStack(ModBlocks.zorraPlanks, 4));
		}
		
		//So it turns out there are several mods that do their oredict in Init like barbarians
		//So that got moved into the proxies' PostInit in order to work
		//registerPlanks();
	}
	
	//Lots of stuff below is from CoFH to automatically register all registered woods
	//Credits to them for having this stuff open source on a permissive license
	
	//From Thermal Expansion (and modified a bit)
	//https://github.com/CoFH/ThermalExpansion/blob/1.12/src/main/java/cofh/thermalexpansion/util/managers/machine/SawmillManager.java
	public void registerPlanks() {
		if (!Config.heatSawmillAutoPlankRecipes) return;
		
		InventoryCraftingFalse tempCrafting = new InventoryCraftingFalse(3, 3);

		for (int i = 0; i < 9; i++) {
			tempCrafting.setInventorySlotContents(i, ItemStack.EMPTY);
		}
		
		//Thanks Thiakil
		ItemStack[] ores = new OreIngredient("logWood").getMatchingStacks();
		
		for (ItemStack log : ores)
		{
			ItemStack log1 = log.copy();
			log1.setCount(1);
			tempCrafting.setInventorySlotContents(0, log1);
			ItemStack result = CraftingManager.findMatchingResult(tempCrafting, null);
			if (!result.isEmpty()) registerPlank(log1, result.copy());
		}
	}
	
	private void registerPlank(ItemStack log, ItemStack plank) {
		plank.setCount(Math.min(plank.getMaxStackSize(), (int)(plank.getCount() * Config.heatSawmillPlankMultiplier)));
		addRecipe(log, plank, new ItemStack(ModItems.sawdust));
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
