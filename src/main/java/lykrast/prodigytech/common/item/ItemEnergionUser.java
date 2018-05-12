package lykrast.prodigytech.common.item;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.recipe.EnergionBatteryManager;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ItemEnergionUser extends Item {
	
	public ItemEnergionUser() {
		setMaxStackSize(1);
		setNoRepair();
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
	
	protected IItemHandler getItemHandler(ItemStack stack) {
		return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	}
	
	protected boolean hasBattery(ItemStack stack) {
		IItemHandler handler = getItemHandler(stack);
		if (handler == null) return false;
		
		ItemStack battery = handler.getStackInSlot(0);
		return !battery.isEmpty() && EnergionBatteryManager.isBattery(battery);
	}
	
	protected ItemStack getBattery(ItemStack stack) {
		IItemHandler handler = getItemHandler(stack);
		if (handler == null) return ItemStack.EMPTY;
		
		ItemStack battery = handler.getStackInSlot(0);
		if (battery.isEmpty() || !EnergionBatteryManager.isBattery(battery)) return ItemStack.EMPTY;
		return battery;
	}
	
	protected int extract(ItemStack stack, int amount) {
		IItemHandler handler = getItemHandler(stack);
		if (handler == null) return 0;
		
		ItemStack battery = getBattery(stack);
		if (battery.isEmpty()) return 0;
		
		int extracted = EnergionBatteryManager.extract(battery, amount);
		//The handler should always be castable
		((IItemHandlerModifiable)handler).setStackInSlot(0, EnergionBatteryManager.checkDepleted(battery));
		
		return extracted;
	}

}
