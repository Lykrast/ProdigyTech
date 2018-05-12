package lykrast.prodigytech.common.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//A lighter ItemTool for my custom tools needs
//Honestly I don't know if I'll ever use this for something that's not the Crystal Cutter
public class ItemProdigyTool extends Item {
	protected float harvestSpeed;
	private int enchatability;
	private ItemStack repairItem;
	
	public ItemProdigyTool(String toolClass, int harvestLevel, int durability, float harvestSpeed, int enchatability, ItemStack repairItem) {
		setMaxDamage(durability);
		setMaxStackSize(1);
		setHarvestLevel(toolClass, harvestLevel);
		this.harvestSpeed = harvestSpeed;
		this.enchatability = enchatability;
		this.repairItem = repairItem;
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, IBlockState state) {
		for (String type : getToolClasses(itemstack)) {
			if (state.getBlock().isToolEffective(type, state)) {
				return harvestSpeed;
			}
		}
		
		return canHarvestBlock(state) ? super.getDestroySpeed(itemstack, state) : harvestSpeed;
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(2, attacker);
        return true;
    }
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote && (double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(1, entityLiving);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
	@Override
    public boolean isFull3D()
    {
        return true;
    }
    
    @Override
    public int getItemEnchantability()
    {
        return enchatability;
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if (!repairItem.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(repairItem, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

}
