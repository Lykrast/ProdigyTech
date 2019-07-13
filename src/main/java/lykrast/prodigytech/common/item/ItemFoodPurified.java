package lykrast.prodigytech.common.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFoodPurified extends ItemFood {
	public static final String NBT_FOOD = "Food", NBT_SATURATION = "Saturation";

	public ItemFoodPurified() {
		super(0, 0, false);
		addPropertyOverride(new ResourceLocation("food"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (!stack.hasTagCompound()) return 0;
				return stack.getTagCompound().getInteger(NBT_FOOD);
			}
		});
	}
	
	public static ItemStack make(int food, float saturation) {
		ItemStack stack = new ItemStack(ModItems.purifiedFood);
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setInteger(NBT_FOOD, food);
		nbt.setFloat(NBT_SATURATION, saturation);
		
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static ItemStack make(ItemStack stack) {
		//Assume the item is food
		ItemFood food = (ItemFood) stack.getItem();
		return make(food.getHealAmount(stack), food.getSaturationModifier(stack));
	}
	
	@Override
	public int getHealAmount(ItemStack stack) {
		if (!stack.hasTagCompound()) return 0;
		return stack.getTagCompound().getInteger(NBT_FOOD);
	}

	@Override
	public float getSaturationModifier(ItemStack stack) {
		if (!stack.hasTagCompound()) return 0;
		return stack.getTagCompound().getFloat(NBT_SATURATION);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String tip = I18n.format(stack.getTranslationKey() + ".tooltip", getHealAmount(stack), getSaturationModifier(stack));
		String[] lines = tip.split("\n");
		for (String s : lines) tooltip.add(TextFormatting.GRAY + s);
	}
	
	//In ascending order of saturation: 0,1, 0.3, 0.8, 1.2
	private static final double[][] COLORS = {
			{0x63 / 255.0F, 0x6D / 255.0F, 0x1E / 255.0F},
			{0xFF / 255.0F, 0x77 / 255.0F, 0x77 / 255.0F},
			{0x94 / 255.0F, 0x59 / 255.0F, 0x41 / 255.0F},
			{0xFF / 255.0F, 0xFF / 255.0F, 0x00 / 255.0F}
	};
	public static int getColor(ItemStack stack, int tintIndex) {
		if (tintIndex != 0 || !stack.hasTagCompound()) return -1;
		float saturation = stack.getTagCompound().getFloat(NBT_SATURATION);
		
		//Outside of the ranges, no gradient
		if (saturation >= 1.2F) return 0xFFFF00;
		else if (saturation <= 0.1F) return 0x636D1E;
		else {
			//Find the corresponding range
			int index;
			double up, down;
			if (saturation >= 0.8) {
				index = 2;
				up = 1.2;
				down = 0.8;
			}
			else if (saturation >= 0.3) {
				index = 1;
				up = 0.8;
				down = 0.3;
			}
			else {
				index = 0;
				up = 0.3;
				down = 0.1;
			}
			
			//Linear gradient between extremes
			double gradient = (saturation - down) / (up - down);
			double inv = 1 - gradient;
			int red = (int) ((COLORS[index][0] * inv + COLORS[index+1][0] * gradient) * 255);
			int green = (int) ((COLORS[index][1] * inv + COLORS[index+1][1] * gradient) * 255);
			int blue = (int) ((COLORS[index][2] * inv + COLORS[index+1][2] * gradient) * 255);

			//Compress back to int
            return red << 16 | green << 8 | blue;
		}
	}
}
