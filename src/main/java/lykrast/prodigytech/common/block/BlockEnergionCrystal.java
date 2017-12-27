package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.item.ItemBlockInfoShift;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockEnergionCrystal extends BlockGeneric implements ICustomItemBlock {

	public BlockEnergionCrystal(float hardness, float resistance, int harvestLevel) {
		super(Material.GLASS, SoundType.GLASS, hardness, resistance, "pickaxe", harvestLevel);
		setLightLevel(1.0F);
	}
	
	@Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
    {
        if (!worldIn.isRemote)
        {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
            entitytntprimed.setFuse((short)5);
            worldIn.spawnEntity(entitytntprimed);
        }
    }

	@Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockInfoShift(this);
	}

}
