package lykrast.prodigytech.common.recipe;

import java.util.HashMap;
import java.util.Map;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import lykrast.prodigytech.common.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class OreRefineryManager extends SimpleRecipeManagerSecondaryOutput {
	public static final OreRefineryManager INSTANCE = new OreRefineryManager();
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out) {
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out) {
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, ItemStack secondary) {
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, secondary, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, ItemStack secondary) {
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, secondary, Config.oreRefineryProcessTime));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(ItemStack in, ItemStack out, ItemStack secondary, float secondaryChance) {
		return addRecipe(new SimpleRecipeSecondaryOutput(in, out, secondary, Config.oreRefineryProcessTime, secondaryChance));
	}
	
	public SimpleRecipeSecondaryOutput addRecipe(String inOre, ItemStack out, ItemStack secondary, float secondaryChance) {
		return addRecipe(new SimpleRecipeSecondaryOutput(inOre, out, secondary, Config.oreRefineryProcessTime, secondaryChance));
	}
	
	//Preferred secondary output for each ore
	private Map<String,String[]> secondaryOres;

	@Override
	public void init() {
		secondaryOres = new HashMap<>();
		
		//Use Thermal Expansion secondary dusts first, then IndustrialCraft 2, then Immersive Engineering
		secondaryOres.put("Iron", new String[] {"dustNickel", "dustGold"});
		secondaryOres.put("Gold", new String[] {"dustCopper", "dustSilver"});
		secondaryOres.put("Copper", new String[] {"dustGold"});
		secondaryOres.put("Tin", new String[] {"dustIron"});
		secondaryOres.put("Silver", new String[] {"dustLead"});
		secondaryOres.put("Lead", new String[] {"dustSilver", "dustCopper"});
		secondaryOres.put("Aluminum", new String[] {"dustIron"});
		secondaryOres.put("Aluminium", new String[] {"dustIron"});
		secondaryOres.put("Nickel", new String[] {"dustPlatinum", "dustIron"});
		secondaryOres.put("Platinum", new String[] {"dustIridium", "dustNickel"});
		secondaryOres.put("Iridium", new String[] {"dustPlatinum"});
		
		addRecipe("oreCoal", new ItemStack(ModItems.coalDust, Config.oreRefineryOreMultiplier));
		if (!Config.autoOreRecipes) addRecipe("oreIron", new ItemStack(ModItems.ironDust, Config.oreRefineryOreMultiplier), new ItemStack(ModItems.goldDust), Config.oreRefineryChance);
		if (!Config.autoOreRecipes) addRecipe("oreGold", new ItemStack(ModItems.goldDust, Config.oreRefineryOreMultiplier));
		addRecipe("oreLapis", new ItemStack(Items.DYE, 6 * Config.oreRefineryOreMultiplier, 4));
		addRecipe("oreRedstone", new ItemStack(Items.REDSTONE, (int)(4.5 * Config.oreRefineryOreMultiplier)));
		if (!Config.autoOreRecipes) addRecipe("oreDiamond", new ItemStack(ModItems.diamondDust, Config.oreRefineryOreMultiplier));
		if (!Config.autoOreRecipes) addRecipe("oreEmerald", new ItemStack(ModItems.emeraldDust, Config.oreRefineryOreMultiplier));
		addRecipe("oreQuartz", new ItemStack(ModItems.quartzDust, Config.oreRefineryOreMultiplier));
	}
	
	public void addOreRecipe(String ore, String input, String output) {
		ItemStack stackOutput = RecipeUtil.getPreferredOreStack(output);
		stackOutput.setCount(Config.oreRefineryOreMultiplier);
		ItemStack stackSecondary = getPreferredSecondary(ore);
		
		addRecipe(input, stackOutput, stackSecondary, Config.oreRefineryChance);
	}
	
	private ItemStack getPreferredSecondary(String ore) {
		String[] secondary = secondaryOres.get(ore);
		if (secondary == null) return ItemStack.EMPTY;
		
		for (String s : secondary)
		{
			if (RecipeUtil.oreExists(s)) return RecipeUtil.getPreferredOreStack(s);
		}
		
		return ItemStack.EMPTY;
	}

}
