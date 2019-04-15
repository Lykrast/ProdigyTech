package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

public class ExplosionFurnaceManager {
	public static final List<ExplosionFurnaceRecipe> RECIPES = new ArrayList<>();
	public static final ItemMap<Explosive> EXPLOSIVES = new ItemMap<>();
	public static final ItemMap<Dampener> DAMPENERS = new ItemMap<>();
	
	//Normal input, no reagent
	public static ExplosionFurnaceRecipe addRecipe(ItemStack input, ItemStack output, int power) {
		return addRecipe(new ExplosionFurnaceRecipe(input, output, power));
	}
	
	//Oredict input, no reagent
	public static ExplosionFurnaceRecipe addRecipe(String input, int inputCount, ItemStack output, int power) {
		return addRecipe(new ExplosionFurnaceRecipe(input, inputCount, output, power));
	}
	
	//Normal input, normal reagent
	public static ExplosionFurnaceRecipe addRecipe(ItemStack input, ItemStack output, int power, ItemStack reagent, int craftPerReagent) {
		return addRecipe(new ExplosionFurnaceRecipe(input, output, power, reagent, craftPerReagent));
	}
	
	//Oredict input, normal reagent
	public static ExplosionFurnaceRecipe addRecipe(String input, int inputCount, ItemStack output, int power, ItemStack reagent, int craftPerReagent) {
		return addRecipe(new ExplosionFurnaceRecipe(input, inputCount, output, power, reagent, craftPerReagent));
	}
	
	//Normal input, oredict reagent
	public static ExplosionFurnaceRecipe addRecipe(ItemStack input, ItemStack output, int power, String reagent, int craftPerReagent) {
		return addRecipe(new ExplosionFurnaceRecipe(input, output, power, reagent, craftPerReagent));
	}
	
	//Oredict input, oredict reagent
	public static ExplosionFurnaceRecipe addRecipe(String input, int inputCount, ItemStack output, int power, String reagent, int craftPerReagent) {
		return addRecipe(new ExplosionFurnaceRecipe(input, inputCount, output, power, reagent, craftPerReagent));
	}
	
	public static ExplosionFurnaceRecipe addRecipe(ExplosionFurnaceRecipe recipe) {
		RECIPES.add(recipe);
		return recipe;
	}
	
	public static ExplosionFurnaceRecipe removeRecipeByOutput(ItemStack out) {
		for (ExplosionFurnaceRecipe recipe : RECIPES) {
			if (out.isItemEqual(recipe.getOutput())) {
				RECIPES.remove(recipe);
				return recipe;
			}
		}

		return null;
	}
	
	public static ExplosionFurnaceRecipe findRecipe(ItemStack in, ItemStack reagent) {
		ExplosionFurnaceRecipe backup = null;
		for (ExplosionFurnaceRecipe recipe : RECIPES)
			if (recipe.isValidInput(in)) {
				//Valid reagent takes priority
				if (recipe.isValidReagent(reagent)) return recipe;
				//If no valid reagent is found but empty reagent exists, give it
				else if (!recipe.needReagent()) backup = recipe;
			}
		
		return backup;
	}
	
	public static void removeAllRecipes() {
		RECIPES.clear();
	}
	
	public static Explosive addExplosive(ItemStack item, int power){
		Explosive explosive = new Explosive(item, power);
		
		EXPLOSIVES.add(item, explosive);
		return explosive;
	}
	
	public static Explosive addExplosive(String ore, int power){
		Explosive explosive = new Explosive(ore, power);
		
		EXPLOSIVES.add(ore, explosive);
		return explosive;
	}
	
	public static Dampener addDampener(ItemStack item, int power){
		Dampener dampener = new Dampener(item, power);
		
		DAMPENERS.add(item, dampener);
		return dampener;
	}
	
	public static Dampener addDampener(String ore, int power){
		Dampener dampener = new Dampener(ore, power);
		
		DAMPENERS.add(ore, dampener);
		return dampener;
	}
	
	public static Explosive findExplosive(ItemStack explosive) {
		return EXPLOSIVES.find(explosive);
	}
	
	public static Explosive removeExplosive(ItemStack explosive) {
		return EXPLOSIVES.remove(explosive);
	}
	
	public static Explosive removeExplosive(String ore) {
		return EXPLOSIVES.remove(ore);
	}
	
	public static Dampener findDampener(ItemStack dampener) {
		return DAMPENERS.find(dampener);
	}
	
	public static Dampener removeDampener(ItemStack dampener) {
		return DAMPENERS.remove(dampener);
	}
	
	public static Dampener removeDampener(String ore) {
		return DAMPENERS.remove(ore);
	}
	
	public static void removeAllExplosives() {
		EXPLOSIVES.clear();
	}
	
	public static void removeAllDampeners() {
		DAMPENERS.clear();
	}
	
	//Probably REALLY need to optimise those one day
	public static boolean isValidInput(ItemStack check)
	{
		for (ExplosionFurnaceRecipe recipe : RECIPES)
			if (recipe.isValidInput(check)) return true;
		
		return false;
	}
	
	public static boolean isValidReagent(ItemStack check)
	{
		for (ExplosionFurnaceRecipe recipe : RECIPES)
			if (recipe.isValidReagent(check)) return true;
		
		return false;
	}
	
	public static boolean isValidExplosive(ItemStack check) {
		return EXPLOSIVES.isValid(check);
	}
	
	public static boolean isValidDampener(ItemStack check) {
		return DAMPENERS.isValid(check);
	}
	
	public static void init()
	{
		//---------------------
		//Recipes
		//---------------------
		//Ferramic
		addRecipe("ingotIron", 1, new ItemStack(ModItems.ferramicIngot), 90, new ItemStack(Items.CLAY_BALL), 4);
		addRecipe("ingotIron", 1, new ItemStack(ModItems.ferramicIngot), 90, new ItemStack(Blocks.CLAY), 16);
		addRecipe("nuggetIron", 1, new ItemStack(ModItems.ferramicNugget), 10, new ItemStack(Items.CLAY_BALL), 36);
		addRecipe("nuggetIron", 1, new ItemStack(ModItems.ferramicNugget), 10, new ItemStack(Blocks.CLAY), 144);
		
		//Zorrasteel
		addRecipe("ingotRawZorrasteel", 1, new ItemStack(ModItems.zorrasteelIngot), 360, "dustCoal", 1);
		
		//Stone
		//Those were initially found by blowing up TNT in a 11x11x11 block and counting how many blocks were destroyed
		addRecipe("stone", 1, new ItemStack(ModBlocks.charredCobblestone), 45);
		addRecipe("cobblestone", 1, new ItemStack(Blocks.GRAVEL), 45);
		addRecipe("stoneCharred", 1, new ItemStack(ModBlocks.charredCobblestone), 45);
		addRecipe("cobblestoneCharred", 1, new ItemStack(Blocks.GRAVEL), 45);
		//addRecipe("gravel", 1, new ItemStack(Blocks.SAND), 10);

		//---------------------
		//Explosives and dampeners
		//---------------------
		addExplosive("gunpowder", 288);
		addExplosive("dustWood", 30);
		addExplosive("dustEnergion", 1440);
		
		addDampener("sand", 360);
		addDampener("dustAsh", 30);
	}
	
	public static class ExplosionFurnaceRecipe {
		private final Ingredient input, reagent;
		private final ItemStack output;
		private final int power, inputCount, craftPerReagent;
		
		//Normal input, no reagent
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power) {
			this(input, output, power, ItemStack.EMPTY, 0);
		}
		
		//Oredict input, no reagent
		public ExplosionFurnaceRecipe(String input, int inputCount, ItemStack output, int power) {
			this(input, inputCount, output, power, ItemStack.EMPTY, 0);
		}
		
		//Normal input, normal reagent
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power, ItemStack reagent, int craftPerReagent) {
			this(Ingredient.fromStacks(input), input.getCount(), output, power, Ingredient.fromStacks(reagent), craftPerReagent);
		}
		
		//Oredict input, normal reagent
		public ExplosionFurnaceRecipe(String input, int inputCount, ItemStack output, int power, ItemStack reagent, int craftPerReagent) {
			this(new OreIngredient(input), inputCount, output, power, Ingredient.fromStacks(reagent), craftPerReagent);
		}
		
		//Normal input, oredict reagent
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power, String reagent, int craftPerReagent) {
			this(Ingredient.fromStacks(input), input.getCount(), output, power, new OreIngredient(reagent), craftPerReagent);
		}
		
		//Oredict input, oredict reagent
		public ExplosionFurnaceRecipe(String input, int inputCount, ItemStack output, int power, String reagent, int craftPerReagent) {
			this(new OreIngredient(input), inputCount, output, power, new OreIngredient(reagent), craftPerReagent);
		}
		
		private ExplosionFurnaceRecipe(Ingredient input, int inputCount, ItemStack output, int power, Ingredient reagent, int craftPerReagent) {
			this.input = input;
			this.inputCount = inputCount;
			this.output = output;
			this.power = power;
			this.reagent = reagent;
			this.craftPerReagent = craftPerReagent;
		}
		
		public ItemStack getOutput() {
			return output.copy();
		}
		
		public int getRequiredPower() {
			return power;
		}
		
		public boolean needReagent() {
			return reagent != Ingredient.EMPTY && craftPerReagent > 0;
		}
		
		public int getCraftPerReagent() {
			return craftPerReagent;
		}
		
		public boolean isValidInput(ItemStack in) {
			return !in.isEmpty() && input.apply(in) && in.getCount() >= inputCount;
		}
		
		public boolean isValidReagent(ItemStack reag) {
			return reagent.apply(reag);
		}
		
		public int getInputCount() {
			return inputCount;
		}
		
		/**
		 * For JEI.
		 */
		public List<ItemStack> getInputs() {
			ItemStack[] array = input.getMatchingStacks();
			List<ItemStack> list = new ArrayList<>();
			int amount = needReagent() ? inputCount * craftPerReagent : inputCount;
			for (int i = 0; i < array.length; i++) {
				ItemStack stack = array[i].copy();
				stack.setCount(amount);
				list.add(stack);
			}
			
			return list;
		}

		/**
		 * For JEI.
		 */
		public List<ItemStack> getReagents() {
			return Arrays.asList(reagent.getMatchingStacks());
		}
	}
	
	public static class Explosive {
		private final Ingredient ingredient;
		private final int power;
		
		public Explosive(ItemStack explosive, int power) {
			ingredient = Ingredient.fromStacks(explosive);
			this.power = power;
		}
		
		public Explosive(String oreExplosive, int power) {
			ingredient = new OreIngredient(oreExplosive);
			this.power = power;
		}
		
		public boolean matches(ItemStack stack) {
			return !stack.isEmpty() && ingredient.apply(stack);
		}
		
		public int getPower() {
			return power;
		}
		
		public int getPower(ItemStack stack) {
			return power * stack.getCount();
		}
		
		/**
		 * For JEI.
		 */
		public List<ItemStack> getMatchingStacks() {
			return Arrays.asList(ingredient.getMatchingStacks());
		}
	}
	
	public static class Dampener {
		private final Ingredient ingredient;
		private final int dampening;
		
		public Dampener(ItemStack explosive, int dampening) {
			ingredient = Ingredient.fromStacks(explosive);
			this.dampening = dampening;
		}
		
		public Dampener(String oreExplosive, int dampening) {
			ingredient = new OreIngredient(oreExplosive);
			this.dampening = dampening;
		}
		
		public boolean matches(ItemStack stack) {
			return !stack.isEmpty() && ingredient.apply(stack);
		}
		
		public int getDampening() {
			return dampening;
		}
		
		public int getDampening(ItemStack stack) {
			return dampening * stack.getCount();
		}
		
		/**
		 * For JEI.
		 */
		public List<ItemStack> getMatchingStacks() {
			return Arrays.asList(ingredient.getMatchingStacks());
		}
	}

}
