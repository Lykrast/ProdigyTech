package lykrast.prodigytech.core;

import lykrast.prodigytech.common.init.ModItems;
import lykrast.prodigytech.common.item.ItemMysteryTreat;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ItemMysteryTreat.COLOR, ModItems.mysteryTreat);
	}

}
