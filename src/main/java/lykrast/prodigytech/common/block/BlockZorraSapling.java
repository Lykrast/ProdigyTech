package lykrast.prodigytech.common.block;

import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockZorraSapling extends BlockSapling implements ICustomStateMapper {
	public BlockZorraSapling(float hardness) {
		setSoundType(SoundType.PLANT);
		setHardness(hardness);
		setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
	}
	
	@Override
	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = new WorldGenBirchTree(true, false);
        int i = 0;
        int j = 0;
        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
        worldIn.setBlockState(pos, iblockstate2, 4);

        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j))) worldIn.setBlockState(pos, state, 4);
    }

	@Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

	@Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this));
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((Integer)state.getValue(STAGE)).intValue() << 3;
        return i;
    }

	//Turns out removing TYPE, despite not using it, makes the game crash
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, STAGE});
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void setCustomStateMapper() {
		ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).ignore(TYPE, STAGE).build());
	}
}
