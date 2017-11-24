package lykrast.prodigytech.common.item;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockInfoShift extends ItemBlock {
	protected static final String SHIFT = "tooltip.prodigytech.shift";
	
	public ItemBlockInfoShift(Block block) {
		super(block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			addInfo(stack, tooltip);
		}
		else
		{
			tooltip.add(I18n.format(SHIFT));
		}
	}

	@SideOnly(Side.CLIENT)
	protected void addInfo(ItemStack stack, List<String> tooltip)
	{
		String tip = I18n.format(stack.getUnlocalizedName() + ".tooltip");
		String[] lines = tip.split("\\\\n");
		for (String s : lines) tooltip.add(TextFormatting.GRAY + s);
	}

}
