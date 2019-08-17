package lykrast.prodigytech.common.compat.jei;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.tileentity.TileFuelProcessor;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class FuelProcessorCategory extends ProdigyCategory<SimpleRecipeWrapper> {
	public static final String UID = "ptfuelprocessor";

	public FuelProcessorCategory(IGuiHelper guiHelper) {
		super(guiHelper, guiHelper.createDrawable(ProdigyTechJEI.GUI, 0, 36, 82, 26), UID);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SimpleRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 4);
		guiItemStacks.init(1, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

	public static void registerRecipes(IModRegistry registry) {
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		List<SimpleRecipeWrapper> list = new ArrayList<>();
		
		List<ItemStack> fuels = registry.getIngredientRegistry().getFuels();
		fuels.stream().forEach(fuel -> {if (TileFuelProcessor.isValidInput(fuel)) list.add(wrap(fuel, guiHelper));});
		
		registry.addRecipes(list, UID);
	}
	
	private static SimpleRecipeWrapper wrap(ItemStack fuel, IGuiHelper guiHelper) {
		return new SimpleRecipeWrapper(fuel.copy(), new ItemStack(TileFuelProcessor.getResultingPellet(fuel), TileFuelProcessor.getPelletsAmount(fuel)), TileFuelProcessor.getProcessTime(fuel), guiHelper);
	}

}
