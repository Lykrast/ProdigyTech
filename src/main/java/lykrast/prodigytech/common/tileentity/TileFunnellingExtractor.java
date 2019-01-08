package lykrast.prodigytech.common.tileentity;

import lykrast.prodigytech.common.util.FacingUtil;
import net.minecraft.util.EnumFacing;

public class TileFunnellingExtractor extends TileExtractor {

	@Override
	public String getName() {
		return super.getName() + "funnelling_extractor";
	}

	@Override
	protected void work(EnumFacing front) {
		push(front.getOpposite());
		for (EnumFacing f : FacingUtil.getRoundRobinExcluding(front.getOpposite())) if (pull(f)) break;
	}

}
