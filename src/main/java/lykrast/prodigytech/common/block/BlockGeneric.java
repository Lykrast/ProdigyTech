package lykrast.prodigytech.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGeneric extends Block {
	
	public BlockGeneric(Material material, SoundType soundType, float hardness, float resistance)
	{
		super(material);
		setSoundType(soundType);
		setHardness(hardness);
		setResistance(resistance);
	}
	
	public BlockGeneric(Material material, SoundType soundType, float hardness, float resistance, String tool, int harvestLevel)
	{
		this(material, soundType, hardness, resistance);
		setHarvestLevel(tool, harvestLevel);
	}

}
