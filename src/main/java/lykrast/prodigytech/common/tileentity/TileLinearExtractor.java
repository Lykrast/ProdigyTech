package lykrast.prodigytech.common.tileentity;

import net.minecraft.util.EnumFacing;

public class TileLinearExtractor extends TileExtractor {

	public TileLinearExtractor() {
		super();
	}

	@Override
	public String getName() {
		return super.getName() + "linear_extractor";
	}

	@Override
	protected void work(EnumFacing front) {
		push(front.getOpposite());
		pull(front);
	}

}
