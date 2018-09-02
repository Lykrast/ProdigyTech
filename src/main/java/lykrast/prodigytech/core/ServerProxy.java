package lykrast.prodigytech.core;

import lykrast.prodigytech.common.network.DummyMessageHandler;
import lykrast.prodigytech.common.network.PacketWormholeDisplay;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy extends CommonProxy {
	@Override
	public SimpleNetworkWrapper createNetworkChannel() {
		SimpleNetworkWrapper channel = super.createNetworkChannel();
		channel.registerMessage(DummyMessageHandler.INSTANCE, PacketWormholeDisplay.class, 0, Side.CLIENT);
		return channel;
	}

}
