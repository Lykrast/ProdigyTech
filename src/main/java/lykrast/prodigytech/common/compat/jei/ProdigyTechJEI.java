package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.core.ProdigyTech;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class ProdigyTechJEI implements IModPlugin {
	public static final ResourceLocation GUI = new ResourceLocation(ProdigyTech.MODID, "textures/gui/jei.png");
	
	@Override
	public void register(IModRegistry registry)
	{
		ExplosionFurnaceWrapper.registerRecipes(registry);
		ExplosionFurnaceExplosiveWrapper.registerRecipes(registry);
		if (Config.incineratorChance > 0) IncineratorWrapper.registerRecipes(registry);

		registry.addRecipeCatalyst(new ItemStack(ModBlocks.explosionFurnace), ExplosionFurnaceCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.explosionFurnace), ExplosionFurnaceExplosiveCategory.UID);
		if (Config.incineratorChance > 0) registry.addRecipeCatalyst(new ItemStack(ModBlocks.incinerator), IncineratorCategory.UID);
		
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.aeroheaterSolid), VanillaRecipeCategoryUid.FUEL);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new ExplosionFurnaceCategory(guiHelper));
		registry.addRecipeCategories(new ExplosionFurnaceExplosiveCategory(guiHelper));
		if (Config.incineratorChance > 0) registry.addRecipeCategories(new IncineratorCategory(guiHelper));
	}
}
