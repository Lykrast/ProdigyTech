package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEnergionTool extends Item {
	
	public ItemEnergionTool() {
		setMaxStackSize(1);
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);
        
		if (player.isSneaking())
		{
	        player.openGui(ProdigyTech.instance, ProdigyTechGuiHandler.ENERGION_TOOL, world, hand == EnumHand.OFF_HAND ? 1 : 0, 0, 0);

	        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
		}
        return ActionResult.newResult(EnumActionResult.PASS, held);
    }

}
