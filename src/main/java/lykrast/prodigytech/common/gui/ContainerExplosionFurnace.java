package lykrast.prodigytech.common.gui;

import lykrast.prodigytech.common.tileentity.TileExplosionFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerExplosionFurnace extends Container {
	private TileExplosionFurnace tile;
	
	public ContainerExplosionFurnace(IInventory userInv, TileExplosionFurnace tile) {
		this.tile = tile;
		
		//Explosion Furnace slots
		//Explosives
        for (int i = 0; i < 2; ++i)
        {
        	this.addSlotToContainer(new Slot(tile, i, 17, 26 + i * 18));
        }
		//Input
        for (int i = 0; i < 3; ++i)
        {
        	this.addSlotToContainer(new Slot(tile, i + 2, 53, 17 + i * 18));
        }
        //Reagent
        this.addSlotToContainer(new Slot(tile, 6, 89, 17));
		//Output
        for (int i = 0; i < 3; ++i)
        {
        	this.addSlotToContainer(new Slot(tile, i + 7, 125, 17 + i * 18));
        }

		//Player slots
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(userInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(userInv, k, 8 + k * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.isUsableByPlayer(playerIn);
	}

}
