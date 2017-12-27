package lykrast.prodigytech.common.init;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.block.*;
import lykrast.prodigytech.common.tileentity.*;
import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModBlocks {
	public static Block explosionFurnace, aeroheaterSolid, incinerator, blowerFurnace, rotaryGrinder, solderer,
		crystalGrowthChamber, thermionicOscillator,
		airFunnel,
		ferramicBlock, energionCrystalBlock,
		ashBricks;
	private static List<Block> blockList = new ArrayList<>();
	private static final String PREFIX = ProdigyTech.MODID + ".";
	
	static
	{
		//Machines
		explosionFurnace = initBlock(new BlockExplosionFurnace(3.5F, 17.5F, 0), "explosion_furnace");
		GameRegistry.registerTileEntity(TileExplosionFurnace.class, PREFIX + "explosion_furnace");
		//Aeroheaters
		aeroheaterSolid = initBlock(new BlockAeroheaterSolid(6.0F, 45.0F, 1), "solid_fuel_aeroheater");
		GameRegistry.registerTileEntity(TileAeroheaterSolid.class, PREFIX + "solid_fuel_aeroheater");
		//Hot air machines
		incinerator = initBlock(new BlockIncinerator(6.0F, 45.0F, 1), "incinerator");
		GameRegistry.registerTileEntity(TileIncinerator.class, PREFIX + "incinerator");
		blowerFurnace = initBlock(new BlockBlowerFurnace(6.0F, 45.0F, 1), "blower_furnace");
		GameRegistry.registerTileEntity(TileBlowerFurnace.class, PREFIX + "blower_furnace");
		rotaryGrinder = initBlock(new BlockRotaryGrinder(6.0F, 45.0F, 1), "rotary_grinder");
		GameRegistry.registerTileEntity(TileRotaryGrinder.class, PREFIX + "rotary_grinder");
		solderer = initBlock(new BlockSolderer(6.0F, 45.0F, 1), "solderer");
		GameRegistry.registerTileEntity(TileSolderer.class, PREFIX + "solderer");
		//Crystal
		crystalGrowthChamber = initBlock(new BlockCrystalGrowthChamber(6.0F, 45.0F, 1), "crystal_growth_chamber");
		GameRegistry.registerTileEntity(TileCrystalGrowthChamber.class, PREFIX + "crystal_growth_chamber");
		thermionicOscillator = initBlock(new BlockThermionicOscillator(6.0F, 45.0F, 1), "thermionic_oscillator");
		GameRegistry.registerTileEntity(TileThermionicOscillator.class, PREFIX + "thermionic_oscillator");
		//Hot air misc
		airFunnel = initBlock(new BlockAirFunnel(6.0F, 45.0F, 1), "air_funnel");
		GameRegistry.registerTileEntity(TileAirFunnel.class, PREFIX + "air_funnel");
		
		//Materials
		ferramicBlock = initBlock(new BlockGeneric(Material.IRON, SoundType.METAL, 6.0F, 45.0F, "pickaxe", 1), "ferramic_block");
		energionCrystalBlock = initBlock(new BlockEnergionCrystal(2.5F, 20.0F, 2), "energion_crystal_block");
		
		//Decoration
		ashBricks = initBlock(new BlockGeneric(Material.ROCK, SoundType.STONE, 2.0F, 10.0F, "pickaxe", 0), "ash_bricks");
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		for (Block b : blockList) event.getRegistry().register(b);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent evt)
	{
		for (Block b : blockList) initModel(b);
	}
	
	public static Block initBlock(Block block, String name)
	{
		return initBlock(block, name, CreativeTabsProdigyTech.instance);
	}
	
	public static Block initBlock(Block block, String name, CreativeTabs tab)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(ProdigyTech.MODID + "." + name);
		if (tab != null) block.setCreativeTab(tab);
		
		blockList.add(block);
		
		ItemBlock item;
		if (block instanceof ICustomItemBlock)
		{
			item = ((ICustomItemBlock) block).getItemBlock();
		}
		else
		{
			item = new ItemBlock(block);
		}
		
		if (item != null)
		{
			item.setRegistryName(block.getRegistryName());
			ModItems.itemBlockList.add(item);
		}
		
		return block;
	}

	@SideOnly(Side.CLIENT)
	private static void initModel(Block b)
	{
		if (b instanceof ICustomModel) ((ICustomModel) b).initModel();
		else ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
		
		if (b instanceof ICustomStateMapper) ((ICustomStateMapper) b).setCustomStateMapper();
	}

}
