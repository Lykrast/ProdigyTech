package lykrast.prodigytech.core;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		//Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ItemMysteryTreat.COLOR, ModItems.mysteryTreat);
	}

}
