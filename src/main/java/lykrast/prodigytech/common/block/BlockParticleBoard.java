package lykrast.prodigytech.common.block;

import lykrast.prodigytech.common.item.ItemParticleBoard;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockParticleBoard extends BlockGeneric implements ICustomModel, ICustomItemBlock {
	public static final PropertyEnum<Variants> VARIANT = PropertyEnum.create("variant", Variants.class);

	public BlockParticleBoard(float hardness, float resistance) {
		super(Material.WOOD, SoundType.WOOD, hardness, resistance, "axe", 0);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for (Variants v : Variants.VALUES) 
			list.add(new ItemStack(this, 1, getMetaFromState(blockState.getBaseState().withProperty(VARIANT, v))));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{VARIANT});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Variants) state.getValue(VARIANT)).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, Variants.VALUES[meta]);
	}
	
	@Override
	public ItemBlock getItemBlock()
	{
		return new ItemParticleBoard(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		for (Variants v : Variants.VALUES) 
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), getMetaFromState(blockState.getBaseState().withProperty(VARIANT, v)), 
					new ModelResourceLocation(getRegistryName() + "_" + v, "inventory"));
	}
	
	public enum Variants implements IStringSerializable {
		NORMAL,
		PLANKS;
		
		public static final Variants[] VALUES = Variants.values();
		
		@Override
	    public String getName()
	    {
	        return name().toLowerCase();
	    }
	    @Override
	    public String toString()
	    {
	        return getName();
	    }
	}

}
