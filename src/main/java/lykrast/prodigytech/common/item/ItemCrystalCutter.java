package lykrast.prodigytech.common.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.block.BlockEnergionCrystal;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentUntouching;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrystalCutter extends ItemProdigyTool {

	public ItemCrystalCutter(int harvestLevel, int durability, float harvestSpeed, int enchatability) {
		super("crystalcutter", harvestLevel, durability, harvestSpeed, enchatability, new ItemStack(ModItems.ferramicIngot));
	}
	
	@Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
		//Crystal cutters can't receive Silk Touch
		if (enchantment.type == EnumEnchantmentType.DIGGER && !(enchantment instanceof EnchantmentUntouching)) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
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
            stack.damageItem(BlockEnergionCrystal.getAge(state) + 1, entityLiving);
            BlockEnergionCrystal.defuse(worldIn, pos);
            return true;
        }
        
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() == ModBlocks.energionCrystal && player.canPlayerEdit(pos, facing, itemstack)) {
        	if (worldIn.isRemote) {
                itemstack.damageItem(1, player);
        		return EnumActionResult.SUCCESS;
        	}
        	
        	int age = BlockEnergionCrystal.getAge(state);
        	if (age > 0)
        	{
        		worldIn.playEvent(2001, pos, Block.getStateId(state));
                worldIn.setBlockState(pos, ((BlockEnergionCrystal)ModBlocks.energionCrystal).withAge(age - 1));
                
                Block.spawnAsEntity(worldIn, pos, new ItemStack(ModItems.energionCrystalSeed));
                
                int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack);
                if (fortune > 0) for (int j = 0; j < fortune; j++) {
                	if (worldIn.rand.nextInt(2) == 0) Block.spawnAsEntity(worldIn, pos, new ItemStack(ModItems.energionCrystalSeed));
                }
        	}
        	else worldIn.destroyBlock(pos, true);

            itemstack.damageItem(1, player);
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (TooltipUtil.addShiftTooltip(tooltip)) TooltipUtil.addTooltip(stack, tooltip);
	}

}
