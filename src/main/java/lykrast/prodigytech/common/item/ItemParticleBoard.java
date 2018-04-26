package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.block.BlockParticleBoard.Variants;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemParticleBoard extends ItemBlock {
	
	public ItemParticleBoard(Block block)
	{
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return super.getUnlocalizedName(itemStack) + "." + Variants.VALUES[itemStack.getMetadata()];
	}

}
