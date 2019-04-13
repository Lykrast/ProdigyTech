package lykrast.prodigytech.common.block;

import java.util.List;

import lykrast.prodigytech.common.gui.ProdigyTechGuiHandler;
import lykrast.prodigytech.common.item.ItemBlockInfoShift;
import lykrast.prodigytech.common.tileentity.TileCapacitorCharger;
import lykrast.prodigytech.common.util.TooltipUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCapacitorCharger extends BlockHotAirMachine<TileCapacitorCharger> implements ICustomItemBlock {

    public BlockCapacitorCharger(float hardness, float resistance, int harvestLevel) {
		super(hardness, resistance, harvestLevel, TileCapacitorCharger.class);
	}

	@Override
	protected int getGuiID() {
		return ProdigyTechGuiHandler.CAPACITOR_CHARGER;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCapacitorCharger();
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockInfoShift(this) {
			@Override
			@SideOnly(Side.CLIENT)
			protected void addInfo(ItemStack stack, List<String> tooltip) {
				super.addInfo(stack, tooltip);
				tooltip.add(I18n.format(TooltipUtil.HEAT_MINIMUM_VARIABLE));
			}
		};
	}

	//No hot air transmitted, no damage
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {}

}
