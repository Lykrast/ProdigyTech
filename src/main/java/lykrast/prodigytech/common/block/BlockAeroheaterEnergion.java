package lykrast.prodigytech.common.block;

import java.util.List;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockAeroheater;
import lykrast.prodigytech.common.tileentity.TileAeroheaterEnergion;
import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAeroheaterEnergion extends BlockHotAirMachine<TileAeroheaterEnergion> implements ICustomItemBlock {

    public BlockAeroheaterEnergion(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileAeroheaterEnergion.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.AEROHEATER_ENERGION;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAeroheaterEnergion();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockAeroheater(this, 250) {
			@Override
			@SideOnly(Side.CLIENT)
			protected void addInfo(ItemStack stack, List<String> tooltip)
			{
				super.addInfo(stack, tooltip);
				TooltipUtil.addEnergionInfo(stack, tooltip, 1, 6, 1);
			}
		};
	}

}
