package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.capability.IHotAir;
import lykrast.prodigytech.common.util.TemperatureHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockHotAirMachine<T extends TileEntity> extends BlockMachineActiveable<T> {
	public BlockHotAirMachine(float hardness, float resistance, int harvestLevel, Class<T> tile) {
		super(Material.IRON, SoundType.METAL, hardness, resistance, "pickaxe", harvestLevel, tile);
	}

	/**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        TemperatureHelper.hotAirDamage(entityIn, ((IHotAir) getTileEntity(worldIn, pos)).getOutAirTemperature());

        super.onEntityWalk(worldIn, pos, entityIn);
    }

}