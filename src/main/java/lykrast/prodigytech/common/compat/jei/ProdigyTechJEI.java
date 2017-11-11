package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.common.init.ModBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ProdigyTechJEI implements IModPlugin {
	@Override
	public void register(IModRegistry registry)
	{
		ExplosionFurnaceExplosiveWrapper.registerRecipes(registry);
		
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.explosionFurnace), ExplosionFurnaceExplosiveCategory.UID);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new ExplosionFurnaceExplosiveCategory(guiHelper));
	}
}
