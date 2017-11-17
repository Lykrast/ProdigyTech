package lykrast.prodigytech.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFuel extends Item {
	private int burnTime;
	
	public ItemFuel(int burnTime)
	{
		this.burnTime = burnTime;
	}
	
	@Override
	public int getItemBurnTime(ItemStack fuel)
    {
    	return burnTime;
    }

}
