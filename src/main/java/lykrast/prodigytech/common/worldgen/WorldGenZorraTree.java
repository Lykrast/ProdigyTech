package lykrast.prodigytech.common.worldgen;

import java.util.Random;

import lykrast.prodigytech.common.init.ModBlocks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenZorraTree extends WorldGenAbstractTree {
	private static final IBlockState LOG = ModBlocks.zorraLog.getDefaultState();
	private static final IBlockState LEAVES = ModBlocks.zorraLeaves.getDefaultState();
	
	public WorldGenZorraTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		//Adapted from the Birch code
		int height = rand.nextInt(3) + 5;

		boolean canGenerate = true;

		if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
		{
			//Checking for room
			for (int y = position.getY(); y <= position.getY() + 1 + height; ++y)
			{
				int radius = 1;

				if (y == position.getY()) radius = 0;

				if (y >= position.getY() + 1 + height - 2) radius = 2;

				BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - radius; l <= position.getX() + radius && canGenerate; ++l)
				{
					for (int i1 = position.getZ() - radius; i1 <= position.getZ() + radius && canGenerate; ++i1)
					{
						if (y >= 0 && y < worldIn.getHeight())
						{
							if (!this.isReplaceable(worldIn, mutpos.setPos(l, y, i1))) canGenerate = false;
						}
						else canGenerate = false;
					}
				}
			}

			if (!canGenerate) return false;
			else
			{
				//Generating
				BlockPos down = position.down();
				IBlockState state = worldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, EnumFacing.UP, (BlockSapling) Blocks.SAPLING);

				if (isSoil && position.getY() < worldIn.getHeight() - height - 1)
				{
					state.getBlock().onPlantGrow(state, worldIn, down, position);

					for (int y = position.getY() - 4 + height; y <= position.getY() + height; ++y)
					{
						int deltaY = y - (position.getY() + height);
						int radius = deltaY == -4 ? 1 : (1 - deltaY / 2);

						for (int x = position.getX() - radius; x <= position.getX() + radius; ++x)
						{
							int deltaX = x - position.getX();

							for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z)
							{
								int deltaZ = z - position.getZ();

								if (Math.abs(deltaX) != radius || Math.abs(deltaZ) != radius || rand.nextInt(2) != 0 && deltaY != 0)
								{
									BlockPos blockpos = new BlockPos(x, y, z);
									IBlockState state2 = worldIn.getBlockState(blockpos);

									if (state2.getBlock().isAir(state2, worldIn, blockpos)) setBlockAndNotifyAdequately(worldIn, blockpos, LEAVES);
								}
							}
						}
					}

					for (int y = 0; y < height; ++y)
					{
						BlockPos upN = position.up(y);
						IBlockState state2 = worldIn.getBlockState(upN);

						if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN))
							setBlockAndNotifyAdequately(worldIn, position.up(y), LOG);
					}

					return true;
				}
				else return false;
			}
		}
		else return false;
	}

}
