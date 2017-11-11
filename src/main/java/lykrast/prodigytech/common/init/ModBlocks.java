package lykrast.prodigytech.common.init;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.block.BlockExplosionFurnace;
import lykrast.prodigytech.common.block.BlockGeneric;
import lykrast.prodigytech.common.block.ICustomItemBlock;
import lykrast.prodigytech.common.block.ICustomModel;
import lykrast.prodigytech.common.block.ICustomStateMapper;
import lykrast.prodigytech.common.tileentity.TileExplosionFurnace;
import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
	public static Block explosionFurnace,
		ferramicBlock;
	private static List<Block> blockList = new ArrayList<>();
	
	public static void init()
	{
		//Machines
		explosionFurnace = registerBlock(new BlockExplosionFurnace(3.5F, 17.5F, 0), "explosion_furnace");
		GameRegistry.registerTileEntity(TileExplosionFurnace.class, ProdigyTech.MODID + "." + "explosion_furnace");
		
		//Materials
		ferramicBlock = registerBlock(new BlockGeneric(Material.IRON, SoundType.METAL, 5.0F, 30.0F, "pickaxe", 1), "ferramic_block");
	}
	
	public static Block registerBlock(Block block, String name)
	{
		return registerBlock(block, name, CreativeTabsProdigyTech.instance);
	}
	
	public static Block registerBlock(Block block, String name, CreativeTabs tab)
	{
		block.setRegistryName(name);
		block.setUnlocalizedName(ProdigyTech.MODID + "." + name);
		if (tab != null) block.setCreativeTab(tab);
		
		ForgeRegistries.BLOCKS.register(block);
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
			ForgeRegistries.ITEMS.register(item);
		}
		
		return block;
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		for (Block b : blockList) initModel(b);
		//Freeing up memory since it's no longer used after that
		blockList = null;
	}

	@SideOnly(Side.CLIENT)
	private static void initModel(Block b)
	{
		if (b instanceof ICustomModel) ((ICustomModel) b).initModel();
		else ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
		
		if (b instanceof ICustomStateMapper) ((ICustomStateMapper) b).setCustomStateMapper();
	}

}
