package lykrast.prodigytech.common.util;

import static net.minecraft.util.EnumFacing.*;

import net.minecraft.util.EnumFacing;

public class FacingUtil {
	private FacingUtil() {}
	
	private static final EnumFacing[][] ROUND_ROBIN_EXCLUDE = {
			{UP, NORTH, EAST, SOUTH, WEST},
			{DOWN, SOUTH, WEST, NORTH, EAST},
			{SOUTH, UP, WEST, DOWN, EAST},
			{NORTH, UP, EAST, DOWN, WEST},
			{EAST, UP, SOUTH, DOWN, NORTH},
			{WEST, UP, NORTH, DOWN, SOUTH}
	};
	
	/**
	 * Returns an array of facing to iterate over for a round robin that excludes the given side.
	 * <br>
	 * This starts with the opposite side first, then turns clockwise on the perpendicular plane from
	 * the perspective of the excluded side.
	 * <br>
	 * This second part starts up for horizontal facings, north when up is excluded, and south when down is excluded.
	 * @param exclude
	 * @return
	 */
	public static EnumFacing[] getRoundRobinExcluding(EnumFacing exclude) {
		return ROUND_ROBIN_EXCLUDE[exclude.ordinal()];
	}
}
