package lykrast.prodigytech.common.guide;

import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import lykrast.prodigytech.common.guide.categories.*;
import lykrast.prodigytech.common.util.CreativeTabsProdigyTech;
import lykrast.prodigytech.core.ProdigyTech;

@GuideBook
public class ProdigyTechGuide implements IGuideBook {
	public static final String PREFIX = "guide." + ProdigyTech.MODID + ".";
	
	public static String prefix(String str) {
		return PREFIX + str;
	}
	
	@Override
	public Book buildBook() {
		BookBinder book = new BookBinder(ProdigyTech.resource("guide"))
				.setCreativeTab(CreativeTabsProdigyTech.instance)
				.setItemName("item.prodigytech.guide.name")
				.setGuideTitle(prefix("title"))
				.setHeader(prefix("header"))
				.setAuthor(prefix("author"));
		
		book.addCategory(CategoryIntroduction.build());
		book.addCategory(CategoryHotAir.build());
				
		return book.build();
	}

}
