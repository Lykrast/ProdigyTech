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
	public static Block explosionFurnace, aeroheaterMagmatic, aeroheaterSolid, aeroheaterEnergion, aeroheaterTartaric,
		incinerator, blowerFurnace, rotaryGrinder, heatSawmill, solderer,
		magneticReassembler, oreRefinery, energionCrystal, automaticCrystalCutter, batteryReplenisher, 
		primordialisReactor, atomicReshaper, zorraAltar,
		airFunnel, wormholeFunnel, linearExtractor, funnellingExtractor, dispersingExtractor,
		zorraLog, zorraPlanks, zorraLeaves, zorraSapling,
		ferramicBlock, carbonPlateBlock, zorrasteelBlock,
		ashBricks, particleBoard, particleBoardPlanks;
	private static List<Block> blockList = new ArrayList<>();
	
	static
	{
		//Machines
		explosionFurnace = initBlock(new BlockExplosionFurnace(3.5F, 17.5F, 0), "explosion_furnace");
		GameRegistry.registerTileEntity(TileExplosionFurnace.class, ProdigyTech.resource("explosion_furnace"));
		
		//Aeroheaters
		aeroheaterMagmatic = initBlock(new BlockAeroheaterMagmatic(6.0F, 45.0F, 1), "magmatic_aeroheater");
		GameRegistry.registerTileEntity(TileAeroheaterMagmatic.class, ProdigyTech.resource("magmatic_aeroheater"));
		aeroheaterSolid = initBlock(new BlockAeroheaterSolid(6.0F, 45.0F, 1), "solid_fuel_aeroheater");
		GameRegistry.registerTileEntity(TileAeroheaterSolid.class, ProdigyTech.resource("solid_fuel_aeroheater"));
		aeroheaterEnergion = initBlock(new BlockAeroheaterEnergion(6.0F, 45.0F, 1), "energion_aeroheater");
		GameRegistry.registerTileEntity(TileAeroheaterEnergion.class, ProdigyTech.resource("energion_aeroheater"));
		aeroheaterTartaric = initBlock(new BlockAeroheaterTartaric(6.0F, 45.0F, 1), "tartaric_aeroheater");
		GameRegistry.registerTileEntity(TileAeroheaterTartaric.class, ProdigyTech.resource("tartaric_aeroheater"));
		
		//Hot air machines pre-Solderer
		incinerator = initBlock(new BlockIncinerator(6.0F, 45.0F, 1), "incinerator");
		GameRegistry.registerTileEntity(TileIncinerator.class, ProdigyTech.resource("incinerator"));
		blowerFurnace = initBlock(new BlockBlowerFurnace(6.0F, 45.0F, 1), "blower_furnace");
		GameRegistry.registerTileEntity(TileBlowerFurnace.class, ProdigyTech.resource("blower_furnace"));
		rotaryGrinder = initBlock(new BlockRotaryGrinder(6.0F, 45.0F, 1), "rotary_grinder");
		GameRegistry.registerTileEntity(TileRotaryGrinder.class, ProdigyTech.resource("rotary_grinder"));
		heatSawmill = initBlock(new BlockHeatSawmill(6.0F, 45.0F, 1), "heat_sawmill");
		GameRegistry.registerTileEntity(TileHeatSawmill.class, ProdigyTech.resource("heat_sawmill"));
		
		solderer = initBlock(new BlockSolderer(6.0F, 45.0F, 1), "solderer");
		GameRegistry.registerTileEntity(TileSolderer.class, ProdigyTech.resource("solderer"));
		
		//Hot air machines post-Solderer
		magneticReassembler = initBlock(new BlockMagneticReassembler(6.0F, 45.0F, 1), "magnetic_reassembler");
		GameRegistry.registerTileEntity(TileMagneticReassembler.class, ProdigyTech.resource("magnetic_reassembler"));
		oreRefinery = initBlock(new BlockOreRefinery(6.0F, 45.0F, 1), "ore_refinery");
		GameRegistry.registerTileEntity(TileOreRefinery.class, ProdigyTech.resource("ore_refinery"));
		automaticCrystalCutter = initBlock(new BlockCrystalCutter(6.0F, 45.0F, 1), "automatic_crystal_cutter");
		GameRegistry.registerTileEntity(TileCrystalCutter.class, ProdigyTech.resource("automatic_crystal_cutter"));
		batteryReplenisher = initBlock(new BlockBatteryReplenisher(6.0F, 45.0F, 1), "battery_replenisher");
		GameRegistry.registerTileEntity(TileBatteryReplenisher.class, ProdigyTech.resource("battery_replenisher"));
		
		//Hot air machines post-Energion
		primordialisReactor = initBlock(new BlockPrimordialisReactor(6.0F, 45.0F, 1), "primordialis_reactor");
		GameRegistry.registerTileEntity(TilePrimordialisReactor.class, ProdigyTech.resource("primordialis_reactor"));
		atomicReshaper = initBlock(new BlockAtomicReshaper(6.0F, 45.0F, 1), "atomic_reshaper");
		GameRegistry.registerTileEntity(TileAtomicReshaper.class, ProdigyTech.resource("atomic_reshaper"));
		zorraAltar = initBlock(new BlockZorraAltar(6.0F, 180.0F, 3), "zorra_altar");
		
		//Logistics
		//Funnels
		airFunnel = initBlock(new BlockAirFunnel(6.0F, 45.0F, 1), "air_funnel");
		GameRegistry.registerTileEntity(TileAirFunnel.class, ProdigyTech.resource("air_funnel"));
		wormholeFunnel = initBlock(new BlockWormholeFunnel(6.0F, 45.0F, 1), "wormhole_funnel");
		GameRegistry.registerTileEntity(TileWormholeFunnel.class, ProdigyTech.resource("wormhole_funnel"));
		//Extractors
		linearExtractor = initBlock(new BlockLinearExtractor(6.0F, 45.0F, 1), "linear_extractor");
		GameRegistry.registerTileEntity(TileLinearExtractor.class, ProdigyTech.resource("linear_extractor"));
		funnellingExtractor = initBlock(new BlockFunnellingExtractor(6.0F, 45.0F, 1), "funnelling_extractor");
		GameRegistry.registerTileEntity(TileFunnellingExtractor.class, ProdigyTech.resource("funnelling_extractor"));
		dispersingExtractor = initBlock(new BlockDispersingExtractor(6.0F, 45.0F, 1), "dispersing_extractor");
		GameRegistry.registerTileEntity(TileDispersingExtractor.class, ProdigyTech.resource("dispersing_extractor"));
		
		//Energion
		energionCrystal = initBlock(new BlockEnergionCrystal(0.35F, 0.5F, 0), "energion_crystal");
		
		//Zorra
		zorraLog = initBlock(new BlockLogGeneric(2.0F, 15.0F), "zorra_log");
		zorraPlanks = initBlock(new BlockGeneric(Material.WOOD, SoundType.WOOD, 2.0F, 15.0F, "axe", 0), "zorra_planks");
		zorraLeaves = initBlock(new BlockZorraLeaves(0.5F, 2.0F), "zorra_leaves");
		zorraSapling = initBlock(new BlockZorraSapling(0.0F), "zorra_sapling");
		
		//Materials
		ferramicBlock = initBlock(new BlockGeneric(Material.IRON, SoundType.METAL, 6.0F, 45.0F, "pickaxe", 1), "ferramic_block");
		carbonPlateBlock = initBlock(new BlockGeneric(Material.ROCK, SoundType.STONE, 3.0F, 30.0F, "pickaxe", 1), "carbon_plate_block");
		zorrasteelBlock = initBlock(new BlockGeneric(Material.IRON, SoundType.METAL, 6.0F, 180.0F, "pickaxe", 3), "zorrasteel_block");
		
		//Decoration
		ashBricks = initBlock(new BlockGeneric(Material.ROCK, SoundType.STONE, 2.0F, 30.0F, "pickaxe", 0), "ash_bricks");
		particleBoard = initBlock(new BlockGeneric(Material.WOOD, SoundType.WOOD, 2.0F, 15.0F, "axe", 0), "particle_board");
		particleBoardPlanks = initBlock(new BlockGeneric(Material.WOOD, SoundType.WOOD, 2.0F, 15.0F, "axe", 0), "particle_board_planks");
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
		
		//We no longer use it afterwards
		blockList = null;
	}
	
	public static Block initBlock(Block block, String name)
	{
		return initBlock(block, name, CreativeTabsProdigyTech.INSTANCE);
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
