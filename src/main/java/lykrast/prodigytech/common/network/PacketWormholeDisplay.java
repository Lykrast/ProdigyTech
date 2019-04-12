package lykrast.prodigytech.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Used to display particles on linked Wormhole Funnels
 * @author Lykrast
 */
public class PacketWormholeDisplay implements IMessage {
	private BlockPos origin, target;

	@Override
	public void fromBytes(ByteBuf buf) {
		origin = BlockPos.fromLong(buf.readLong());
		target = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(origin.toLong());
		buf.writeLong(target.toLong());
	}

	public PacketWormholeDisplay() {
		this(BlockPos.ORIGIN, BlockPos.ORIGIN);
    }

	public PacketWormholeDisplay(BlockPos origin, BlockPos target) {
		this.origin = origin;
		this.target = target;
    }
	
	public static class Handler implements IMessageHandler<PacketWormholeDisplay, IMessage> {
		@Override
		public IMessage onMessage(PacketWormholeDisplay message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketWormholeDisplay message, MessageContext ctx) {
			World world = Minecraft.getMinecraft().world;
			BlockPos origin = message.origin;
			BlockPos target = message.target;
			Vec3d dir = new Vec3d(target.getX() - origin.getX(), target.getY() - origin.getY(), target.getZ() - origin.getZ());
			int count = (int) (dir.length() * 4);
			dir = dir.normalize();
			double dx = dir.x * 0.25, dy = dir.y * 0.25, dz = dir.z * 0.25;
			double vx = dx * 0.1, vy = dy * 0.1, vz = dz * 0.1;

			double x = origin.getX() + 0.5, y = origin.getY() + 0.5, z = origin.getZ() + 0.5;
			for (int i = 0; i < count; i++)
			{
				world.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, vx, vy, vz);
				x += dx;
				y += dy;
				z += dz;
			}
		}
	}

}
