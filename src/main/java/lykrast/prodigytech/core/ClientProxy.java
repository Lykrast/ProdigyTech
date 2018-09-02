package lykrast.prodigytech.core;

import lykrast.prodigytech.common.network.PacketWormholeDisplay;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	@Override
	public SimpleNetworkWrapper createNetworkChannel() {
		SimpleNetworkWrapper channel = super.createNetworkChannel();
		channel.registerMessage(new PacketWormholeDisplay.Handler(), PacketWormholeDisplay.class, 0, Side.CLIENT);
		return channel;
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		//Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ItemMysteryTreat.COLOR, ModItems.mysteryTreat);
	}

}
