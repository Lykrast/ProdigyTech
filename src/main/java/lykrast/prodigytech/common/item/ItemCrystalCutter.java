package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.block.BlockEnergionCrystal;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCrystalCutter extends ItemProdigyTool {

	public ItemCrystalCutter(int harvestLevel, int durability, float harvestSpeed, int enchatability) {
		super("crystalcutter", harvestLevel, durability, harvestSpeed, enchatability, new ItemStack(ModItems.ferramicIngot));
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState block) {
		Material material = block.getMaterial();
		return material == Material.GLASS;
	}
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote && state.getBlock() == ModBlocks.energionCrystal)
        {
        	//Breaking energion crystal heavily damages the tool but defuses the crystal
            stack.damageItem(((BlockEnergionCrystal) ModBlocks.energionCrystal).getAge(state) + 1, entityLiving);
            BlockEnergionCrystal.defuse(worldIn, pos);
            return true;
        }
        
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

}
