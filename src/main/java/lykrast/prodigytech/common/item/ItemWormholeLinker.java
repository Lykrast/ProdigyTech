package lykrast.prodigytech.common.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.prodigytech.common.block.BlockWormholeFunnel;
import lykrast.prodigytech.common.init.ModBlocks;
import lykrast.prodigytech.common.network.PacketHandler;
import lykrast.prodigytech.common.network.PacketWormholeDisplay;
import lykrast.prodigytech.common.tileentity.TileWormholeFunnel;
import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
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
		
		if (worldIn.isRemote) return EnumActionResult.SUCCESS;
		
		ItemStack stack = player.getHeldItem(hand);
		//Linked pos
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Pos"))
		{
			NBTTagCompound nbt = stack.getTagCompound();
			BlockPos targetPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("Pos"));
			
			//Clear the pos in the nbt
			nbt.removeTag("Pos");
			stack.setTagCompound(nbt);
			
			//Check range
			if (!tile.isInRange(targetPos)) 
			{
				player.sendStatusMessage(new TextComponentTranslation("status.prodigytech.wormhole_linker.error.distance"), true);
				return EnumActionResult.SUCCESS;
			}
			
			//Check if original tile is here
			TileWormholeFunnel targetTile = BlockWormholeFunnel.getTileEntity(worldIn, targetPos);
			if (targetTile == null)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.prodigytech.wormhole_linker.error.lost"), true);
				return EnumActionResult.SUCCESS;
			}
			
			//Check if funnels are compatible
			if (tile.isInput() == targetTile.isInput())
			{
				player.sendStatusMessage(new TextComponentTranslation("status.prodigytech.wormhole_linker.error.incompatible"), true);
				return EnumActionResult.SUCCESS;
			}
			
			//Create the link
			if (tile.createLink(targetTile))
			{
				player.sendStatusMessage(new TextComponentTranslation("status.prodigytech.wormhole_linker.success"), true);
				if (player instanceof EntityPlayerMP)
            	PacketHandler.INSTANCE.sendTo(new PacketWormholeDisplay(targetPos, pos), (EntityPlayerMP) player);
			}
			else player.sendStatusMessage(new TextComponentTranslation("status.prodigytech.wormhole_linker.error"), true);
			
			return EnumActionResult.SUCCESS;
		}
		//No linked pos
		else
		{
			NBTTagCompound nbt;
			if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
			else nbt = stack.getTagCompound();
			
			nbt.setTag("Pos", NBTUtil.createPosTag(pos));
			
			stack.setTagCompound(nbt);
			
			player.sendStatusMessage(new TextComponentTranslation("status.prodigytech.wormhole_linker.start"), true);
			return EnumActionResult.SUCCESS;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Pos")) tooltip.add(I18n.format(stack.getUnlocalizedName() + ".tooltip.linking"));
		if (TooltipUtil.addShiftTooltip(tooltip)) TooltipUtil.addTooltip(stack, tooltip);
	}
}
