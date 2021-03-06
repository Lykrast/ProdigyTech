package lykrast.prodigytech.common.compat.jei;

import lykrast.prodigytech.core.ProdigyTech;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;

public abstract class ProdigyCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {
	private final IDrawable background;
	private final String id;

	public ProdigyCategory(IGuiHelper guiHelper, IDrawable gui, String id)
	{
		this.id = id;
		background = gui;
	}

	@Override
	public String getUid() {
		return id;
	}

	@Override
	public String getTitle() {
		return I18n.format("container." + ProdigyTech.MODID + ".jei." + id);
	}

	@Override
	public String getModName() {
		return ProdigyTech.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

}
