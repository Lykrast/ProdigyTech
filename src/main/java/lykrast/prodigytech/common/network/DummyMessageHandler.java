package lykrast.prodigytech.common.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DummyMessageHandler implements IMessageHandler<IMessage, IMessage> {
	public static final DummyMessageHandler INSTANCE = new DummyMessageHandler();

	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx) {
		throw new UnsupportedOperationException("This message handler has been called on the wrong side");
	}
}
