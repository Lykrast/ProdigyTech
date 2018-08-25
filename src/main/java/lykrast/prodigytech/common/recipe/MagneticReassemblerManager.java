package lykrast.prodigytech.common.recipe;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.Config;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MagneticReassemblerManager extends SimpleRecipeManager {
	public static final MagneticReassemblerManager INSTANCE = new MagneticReassemblerManager();
	
	public SimpleRecipe addRecipe(ItemStack in, ItemStack out)
	{
		return addRecipe(in, out, Config.magneticReassemblerProcessTime);
	}
	
	public SimpleRecipe addRecipe(String inOre, ItemStack out)
	{
		return addRecipe(inOre, out, Config.magneticReassemblerProcessTime);
	}
	
	public void init()
	{		
		addRecipe("cobblestone", new ItemStack(Blocks.STONE));
		addRecipe("gravel", new ItemStack(Blocks.COBBLESTONE));
		addRecipe("sand", new ItemStack(Blocks.GRAVEL));
		
		addRecipe("dustCoal", new ItemStack(Items.COAL));
		if (!Config.autoOreRecipes)
		{
			addRecipe("dustIron", new ItemStack(Items.IRON_INGOT));
			addRecipe("dustTinyIron", new ItemStack(Items.IRON_NUGGET), Config.magneticReassemblerProcessTime / 9);
			addRecipe("dustGold", new ItemStack(Items.GOLD_INGOT));
			addRecipe("dustTinyGold", new ItemStack(Items.GOLD_NUGGET), Config.magneticReassemblerProcessTime / 9);
			addRecipe("dustDiamond", new ItemStack(Items.DIAMOND));
			addRecipe("dustEmerald", new ItemStack(Items.EMERALD));
		}
		addRecipe("dustQuartz", new ItemStack(Items.QUARTZ));
		
		addRecipe("dustFerramic", new ItemStack(ModItems.ferramicIngot));
		addRecipe("dustTinyFerramic", new ItemStack(ModItems.ferramicNugget), Config.magneticReassemblerProcessTime / 9);
		addRecipe("dustEnergion", new ItemStack(ModItems.energionCrystalSeed));
		
		addRecipe(new ItemStack(ModItems.infernoFuel), new ItemStack(ModItems.infernoCrystal), Config.magneticReassemblerProcessTime * 2);
		addRecipe(new ItemStack(Items.SUGAR), new ItemStack(ModItems.sugarCube));
	}

}
