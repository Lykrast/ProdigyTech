package lykrast.prodigytech.common.init;

import java.util.ArrayList;
import java.util.List;

import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
	public static Item ferramicIngot, ferramicNugget;
	private static List<Item> itemList = new ArrayList<>();
	
	public static void init()
	{
		//Materials
		ferramicIngot = registerItem(new Item(), "ferramic_ingot");
		ferramicNugget = registerItem(new Item(), "ferramic_nugget");
	}
	
	public static Item registerItem(Item item, String name)
	{
		return registerItem(item, name, CreativeTabsProdigyTech.instance);
	}
	
	public static Item registerItem(Item item, String name, CreativeTabs tab)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(ProdigyTech.MODID + "." + name);
		if (tab != null) item.setCreativeTab(tab);
		
		ForgeRegistries.ITEMS.register(item);
		itemList.add(item);
		
		return item;
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels()
	{
		for (Item i : itemList) initModel(i);
		//Freeing up memory since it's no longer used after that
		itemList = null;
	}

	@SideOnly(Side.CLIENT)
	private static void initModel(Item i)
	{
		ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}
}
