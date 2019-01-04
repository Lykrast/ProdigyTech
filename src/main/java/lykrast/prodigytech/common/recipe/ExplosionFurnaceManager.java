package lykrast.prodigytech.common.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public static ExplosionFurnaceRecipe addRecipe(ItemStack in, ItemStack out, int power)
	{
		return addRecipe(new ExplosionFurnaceRecipe(in, out, power));
	}
	
	public static ExplosionFurnaceRecipe addRecipe(ItemStack in, ItemStack out, int power, ItemStack reagent, int craftPerReagent)
	{
		return addRecipe(new ExplosionFurnaceRecipe(in, out, power, reagent, craftPerReagent));
	}
	
	public static ExplosionFurnaceRecipe addRecipe(ExplosionFurnaceRecipe recipe)
	{
		RECIPES.add(recipe);
		return recipe;
	}
	
	public static ExplosionFurnaceRecipe removeRecipe(ItemStack in)
	{
		ExplosionFurnaceRecipe recipe = findRecipe(in);
		if (recipe != null) RECIPES.remove(recipe);
		
		return recipe;
	}
	
	public static ExplosionFurnaceRecipe findRecipe(ItemStack in)
	{
		for (ExplosionFurnaceRecipe recipe : RECIPES)
			if (recipe.isValidInput(in)) return recipe;
		
		return null;
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
		addRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.ferramicIngot), 90, new ItemStack(Items.CLAY_BALL), 4);
		//addRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.ferramicIngot), 90, new ItemStack(Blocks.CLAY), 16);
		addRecipe(new ItemStack(Items.IRON_NUGGET), new ItemStack(ModItems.ferramicNugget), 10, new ItemStack(Items.CLAY_BALL), 36);
		//addRecipe(new ItemStack(Items.IRON_NUGGET), new ItemStack(ModItems.ferramicNugget), 10, new ItemStack(Blocks.CLAY), 144);
		
		//Zorrasteel
		addRecipe(new ItemStack(ModItems.zorrasteelRaw), new ItemStack(ModItems.zorrasteelIngot), 360);
		
		//Stone
		//Those were tested by blowing up TNT in a 11x11x11 block and counting how many blocks were destroyed
		addRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE), 45);
		addRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL), 45);
		addRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), 10);

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
		private final ItemStack input;
		private final ItemStack output;
		private final ItemStack reagent;
		private final int power;
		private final int craftPerReagent;
		
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power)
		{
			this(input, output, power, ItemStack.EMPTY, 0);
		}
		
		public ExplosionFurnaceRecipe(ItemStack input, ItemStack output, int power, ItemStack reagent, int craftPerReagent)
		{
			this.input = input;
			this.output = output;
			this.power = power;
			this.reagent = reagent;
			this.reagent.setCount(1);
			this.craftPerReagent = craftPerReagent;
		}
		
		public ItemStack getInput()
		{
			return input.copy();
		}
		
		public ItemStack getOutput()
		{
			return output.copy();
		}
		
		public int getRequiredPower()
		{
			return power;
		}
		
		public boolean isValidInput(ItemStack in)
		{
			return (!in.isEmpty() && in.isItemEqual(input) && in.getCount() >= input.getCount());
		}
		
		public ItemStack getReagent()
		{
			if (reagent.isEmpty()) return ItemStack.EMPTY;
			return reagent.copy();
		}
		
		public int getCraftPerReagent()
		{
			return craftPerReagent;
		}
		
		public boolean needReagent()
		{
			return (!reagent.isEmpty() && craftPerReagent > 0);
		}
		
		public boolean isValidReagent(ItemStack reag)
		{
			return reag.isItemEqual(reagent);
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
