package lykrast.prodigytech.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class ItemCircuit extends Item implements IItemCustomModel {
	public static final int CRUDE = 0, REFINED = 1, PERFECTED = 2;
	private static final String[] NAMES = {"crude","refined","perfected"};
	
	public ItemCircuit()
	{
		setHasSubtypes(true);
		setMaxDamage(0);
	}

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getMetadata();
        if (i < 0 || i >= NAMES.length) i = 0;
        return super.getUnlocalizedName() + "." + NAMES[i];
    }

	@Override
	public void initModel() {
		for (int i = 0; i < 3; i++)
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(this.getRegistryName() + "_" + NAMES[i], "inventory"));
	}

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 3; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

}
