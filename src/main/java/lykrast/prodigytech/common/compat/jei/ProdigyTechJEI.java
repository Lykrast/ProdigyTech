package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.client.gui.*;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.core.ProdigyTech;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class ProdigyTechJEI implements IModPlugin {
	public static final ResourceLocation GUI = ProdigyTech.resource("textures/gui/jei.png");
	
	public static IDrawableStatic getDefaultProcessArrow(IGuiHelper guiHelper) {
		return guiHelper.createDrawable(GUI, 104, 36, 24, 17);
	}
	
	@Override
	public void register(IModRegistry registry)
	{
		//Recipes
		ExplosionFurnaceCategory.registerRecipes(registry);
		ExplosionFurnaceExplosiveCategory.registerRecipes(registry);
		ExplosionFurnaceDampenerCategory.registerRecipes(registry);
		if (Config.incineratorChance > 0 && Config.incineratorJEIIntegration) IncineratorCategory.registerRecipes(registry);
		RotaryGrinderCategory.registerRecipes(registry);
		HeatSawmillCategory.registerRecipes(registry);
		SoldererCategory.registerRecipes(registry);
		MagneticReassemblerCategory.registerRecipes(registry);
		OreRefineryCategory.registerRecipes(registry);
		EnergionBatteryCategory.registerRecipes(registry);
		BatteryReplenisherCategory.registerRecipes(registry);
		PrimordialisReactorCategory.registerRecipes(registry);
		AtomicReshaperCategory.registerRecipes(registry);

		//Catalysts
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.explosionFurnace), ExplosionFurnaceCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.explosionFurnace), ExplosionFurnaceExplosiveCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.explosionFurnace), ExplosionFurnaceDampenerCategory.UID);
		if (Config.incineratorChance > 0 && Config.incineratorJEIIntegration) registry.addRecipeCatalyst(new ItemStack(ModBlocks.incinerator), IncineratorCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.rotaryGrinder), RotaryGrinderCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.heatSawmill), HeatSawmillCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.solderer), SoldererCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.magneticReassembler), MagneticReassemblerCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.oreRefinery), OreRefineryCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.batteryReplenisher), BatteryReplenisherCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.primordialisReactor), PrimordialisReactorCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.atomicReshaper), AtomicReshaperCategory.UID);
		
		//Battery usage
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.aeroheaterEnergion), EnergionBatteryCategory.UID);

		//Vanilla catalysts
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.blowerFurnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.aeroheaterSolid), VanillaRecipeCategoryUid.FUEL);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.aeroheaterTartaric), VanillaRecipeCategoryUid.FUEL);
		
		//Clickable areas
		registry.addRecipeClickArea(GuiExplosionFurnace.class, 79, 34, 42, 17, ExplosionFurnaceCategory.UID, ExplosionFurnaceExplosiveCategory.UID, ExplosionFurnaceDampenerCategory.UID);
		if (Config.incineratorChance > 0 && Config.incineratorJEIIntegration) registry.addRecipeClickArea(GuiIncinerator.class, 79, 35, 24, 17, IncineratorCategory.UID);
		registry.addRecipeClickArea(GuiRotaryGrinder.class, 79, 35, 24, 17, RotaryGrinderCategory.UID);
		registry.addRecipeClickArea(GuiHeatSawmill.class, 79, 35, 24, 17, HeatSawmillCategory.UID);
		registry.addRecipeClickArea(GuiSolderer.class, 79, 35, 24, 17, SoldererCategory.UID);
		registry.addRecipeClickArea(GuiMagneticReassembler.class, 79, 35, 24, 17, MagneticReassemblerCategory.UID);
		registry.addRecipeClickArea(GuiOreRefinery.class, 79, 35, 24, 17, OreRefineryCategory.UID);
		registry.addRecipeClickArea(GuiPrimordialisReactor.class, 77, 35, 62, 17, PrimordialisReactorCategory.UID);
		registry.addRecipeClickArea(GuiAtomicReshaper.class, 67, 35, 48, 17, AtomicReshaperCategory.UID);
		
		registry.addRecipeClickArea(GuiBlowerFurnace.class, 79, 35, 24, 17, VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeClickArea(GuiAeroheaterSolid.class, 79, 34, 18, 18, VanillaRecipeCategoryUid.FUEL);
		registry.addRecipeClickArea(GuiAeroheaterTartaric.class, 70, 34, 36, 18, VanillaRecipeCategoryUid.FUEL);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new ExplosionFurnaceCategory(guiHelper));
		registry.addRecipeCategories(new ExplosionFurnaceExplosiveCategory(guiHelper));
		registry.addRecipeCategories(new ExplosionFurnaceDampenerCategory(guiHelper));
		if (Config.incineratorChance > 0 && Config.incineratorJEIIntegration) registry.addRecipeCategories(new IncineratorCategory(guiHelper));
		registry.addRecipeCategories(new RotaryGrinderCategory(guiHelper));
		registry.addRecipeCategories(new HeatSawmillCategory(guiHelper));
		registry.addRecipeCategories(new SoldererCategory(guiHelper));
		registry.addRecipeCategories(new MagneticReassemblerCategory(guiHelper));
		registry.addRecipeCategories(new OreRefineryCategory(guiHelper));
		registry.addRecipeCategories(new EnergionBatteryCategory(guiHelper));
		registry.addRecipeCategories(new BatteryReplenisherCategory(guiHelper));
		registry.addRecipeCategories(new PrimordialisReactorCategory(guiHelper));
		registry.addRecipeCategories(new AtomicReshaperCategory(guiHelper));
	}
}
