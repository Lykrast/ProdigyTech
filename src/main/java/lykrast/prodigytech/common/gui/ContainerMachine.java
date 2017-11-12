package lykrast.prodigytech.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public abstract class ContainerMachine<T extends IInventory> extends Container {
	protected T tile;

	public ContainerMachine(T tile) {
		super();
		this.tile = tile;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.isUsableByPlayer(playerIn);
	}

}