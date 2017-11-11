package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.client.gui.GuiExplosionFurnace;
import lykrast.prodigytech.common.tileentity.TileExplosionFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ProdigyTechGuiHandler implements IGuiHandler {
	public static final int EXPLOSION_FURNACE = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case EXPLOSION_FURNACE:
				return new ContainerExplosionFurnace(player.inventory, (TileExplosionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case EXPLOSION_FURNACE:
				return new GuiExplosionFurnace(player.inventory, (TileExplosionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
