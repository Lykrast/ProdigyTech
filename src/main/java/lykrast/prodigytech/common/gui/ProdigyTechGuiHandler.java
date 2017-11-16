package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.client.gui.GuiAeroheaterSolid;
import lykrast.prodigytech.client.gui.GuiExplosionFurnace;
import lykrast.prodigytech.client.gui.GuiIncinerator;
import lykrast.prodigytech.common.tileentity.TileAeroheaterSolid;
import lykrast.prodigytech.common.tileentity.TileExplosionFurnace;
import lykrast.prodigytech.common.tileentity.TileIncinerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ProdigyTechGuiHandler implements IGuiHandler {
	public static final int EXPLOSION_FURNACE = 0, AEROHEATER_SOLID = 1, INCINERATOR = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case EXPLOSION_FURNACE:
				return new ContainerExplosionFurnace(player.inventory, (TileExplosionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_SOLID:
				return new ContainerAeroheaterSolid(player.inventory, (TileAeroheaterSolid)world.getTileEntity(new BlockPos(x, y, z)));
			case INCINERATOR:
				return new ContainerIncinerator(player.inventory, (TileIncinerator)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case EXPLOSION_FURNACE:
				return new GuiExplosionFurnace(player.inventory, (TileExplosionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case AEROHEATER_SOLID:
				return new GuiAeroheaterSolid(player.inventory, (TileAeroheaterSolid)world.getTileEntity(new BlockPos(x, y, z)));
			case INCINERATOR:
				return new GuiIncinerator(player.inventory, (TileIncinerator)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
