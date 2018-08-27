package lykrast.prodigytech.common.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.block.BlockWormholeFunnel;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.tileentity.TileWormholeFunnel;
import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWormholeLinker extends Item {
	
	public ItemWormholeLinker() {
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() != ModBlocks.wormholeFunnel) return EnumActionResult.FAIL;
		
		TileWormholeFunnel tile = BlockWormholeFunnel.getTileEntity(worldIn, pos);
		if (tile == null) return EnumActionResult.FAIL;
		
		ItemStack stack = player.getHeldItem(hand);
		//Linked pos
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Pos"))
		{
			NBTTagCompound nbt = stack.getTagCompound();
			BlockPos targetPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("Pos"));
			
			//Clear the pos in the nbt
			nbt.removeTag("Pos");
			stack.setTagCompound(nbt);
			
			TileWormholeFunnel targetTile = BlockWormholeFunnel.getTileEntity(worldIn, targetPos);
			if (targetTile == null)
			{
				player.sendStatusMessage(new TextComponentString("Original Funnel was removed."), true);
				return EnumActionResult.FAIL;
			}
			
			if (tile.createLink(targetTile))
			{
				player.sendStatusMessage(new TextComponentString("Link created between " + targetPos + " and " + pos), true);
				return EnumActionResult.SUCCESS;
			}
			else
			{
				player.sendStatusMessage(new TextComponentString("Link could not be created"), true);
				return EnumActionResult.FAIL;
			}
		}
		//No linked pos
		else
		{
			NBTTagCompound nbt;
			if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
			else nbt = stack.getTagCompound();
			
			nbt.setTag("Pos", NBTUtil.createPosTag(pos));
			
			stack.setTagCompound(nbt);
			
			player.sendStatusMessage(new TextComponentString("Linking with " + pos), true);
			return EnumActionResult.SUCCESS;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (TooltipUtil.addShiftTooltip(tooltip)) TooltipUtil.addTooltip(stack, tooltip);
	}
}
