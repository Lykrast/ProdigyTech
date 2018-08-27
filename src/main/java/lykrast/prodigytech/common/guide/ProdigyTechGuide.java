package lykrast.prodigytech.common.guide;

import java.awt.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import lykrast.prodigytech.common.guide.categories.CategoryLogistics;
import lykrast.prodigytech.common.guide.categories.CategoryCircuits;
import lykrast.prodigytech.common.guide.categories.CategoryEnergion;
import lykrast.prodigytech.common.guide.categories.CategoryHotAir;
import lykrast.prodigytech.common.guide.categories.CategoryIntroduction;
import lykrast.prodigytech.common.guide.categories.CategoryPrimordium;
import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@GuideBook
public class ProdigyTechGuide implements IGuideBook {
	public static final String PREFIX = "guide." + ProdigyTech.MODID + ".";
	
	public static String prefix(String str) {
		return PREFIX + str;
	}
	
	@Override
	public Book buildBook() {
		BookBinder book = new BookBinder(ProdigyTech.resource("guide"))
				.setCreativeTab(CreativeTabsProdigyTech.INSTANCE)
				.setItemName("item.prodigytech.guide.name")
				.setGuideTitle(prefix("title"))
				.setHeader(prefix("header"))
				.setAuthor(prefix("author"))
				.setHasCustomModel()
				.setColor(Color.WHITE);
		
		book.addCategory(CategoryIntroduction.build());
		book.addCategory(CategoryHotAir.build());
		book.addCategory(CategoryCircuits.build());
		book.addCategory(CategoryEnergion.build());
		book.addCategory(CategoryPrimordium.build());
		book.addCategory(CategoryLogistics.build());
		
		return book.build();
	}
	
	@Override
	public void handlePost(@Nonnull ItemStack bookStack) {
		CategoryIntroduction.buildMap();
		CategoryHotAir.buildMap();
		CategoryCircuits.buildMap();
		CategoryEnergion.buildMap();
		CategoryPrimordium.buildMap();
		CategoryLogistics.buildMap();
	}
	
    @Nullable
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapelessOreRecipe(null, bookStack, Items.BOOK, "sand").setRegistryName(ProdigyTech.resource("guide"));
    }
	
    @SideOnly(Side.CLIENT)
    public void handleModel(@Nonnull ItemStack bookStack) {
    	ModelLoader.setCustomModelResourceLocation(bookStack.getItem(), bookStack.getMetadata(), new ModelResourceLocation(ProdigyTech.MODID + ".guide", "inventory"));
    }

}
