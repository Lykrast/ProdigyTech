package lykrast.prodigytech.common.block;

import java.util.Random;

import lykrast.prodigytech.common.worldgen.WorldGenZorraTree;
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
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockZorraSapling extends BlockSapling implements ICustomStateMapper {
	private static final WorldGenerator GENERATOR = new WorldGenZorraTree(true);
	
	public BlockZorraSapling(float hardness) {
		setSoundType(SoundType.PLANT);
		setHardness(hardness);
		setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
	}
	
	@Override
	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

        if (!GENERATOR.generate(worldIn, rand, pos)) worldIn.setBlockState(pos, state, 4);
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
