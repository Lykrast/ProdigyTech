package lykrast.prodigytech.common.util;

import net.minecraft.util.math.AxisAlignedBB;

public class AABBUtil {
	/**
	 * How much of a block is a single texture pixel. This is calibrated for 16x16 textures.
	 */
	public static final double PIXEL_TO_BLOCK = 1.0/16.0;
	private AABBUtil() {}
	
	/**
	 * Creates a bounding box using texture pixel measurements instead of raw values.
	 * <br>
	 * This assume a scale of 1 block = 16 pixels.
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return a bounding box with the corresponding coordinates
	 */
	public static AxisAlignedBB getFromPixels(double x1, double y1, double z1, double x2, double y2, double z2) {
		return new AxisAlignedBB(x1 * PIXEL_TO_BLOCK, y1 * PIXEL_TO_BLOCK, z1 * PIXEL_TO_BLOCK, 
				x2 * PIXEL_TO_BLOCK, y2 * PIXEL_TO_BLOCK, z2 * PIXEL_TO_BLOCK);
	}
	
	/**
	 * Creates a bounding box that's vertically symmetrical and is put on the ground.
	 * <br>
	 * Yes this is stupidly specific...
	 * @param x total size on all 4 horizontal sides, an even number gives better behavior
	 * @param y total vertical size
	 * @return a bounding box
	 */
	public static AxisAlignedBB getCenteredDownSquareFromPixels(double x, double y) {
		double h = (16.0 - x)/2;
		return getFromPixels(h, 0, h, 16.0 - h, y, 16.0 - h);
	}
}
