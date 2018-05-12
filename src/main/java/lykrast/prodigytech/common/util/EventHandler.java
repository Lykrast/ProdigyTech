package lykrast.prodigytech.common.util;

import lykrast.prodigytech.common.item.EnergionToolInventoryProvider;
import lykrast.prodigytech.common.item.ItemEnergionTool;
import lykrast.prodigytech.core.ProdigyTech;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {

	@SubscribeEvent
	public static void attachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof ItemEnergionTool) {
			event.addCapability(ProdigyTech.resource("battery_inv"), new EnergionToolInventoryProvider());
		}
	}

}
