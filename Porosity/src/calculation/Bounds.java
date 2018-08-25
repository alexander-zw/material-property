package calculation;

import images.APImage;

/**
 * A Bounds represents a rectangular area within an image. It contains the leftmost x,
 * the rightmost x, the upmost y, and the downmost y (all inclusive).
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class Bounds {
	
	/**
	 * All widths and heights one less than actual for convenience, so that left + width == right
	 * and up + height == down.
	 */
	public static final int BAMBOO1A_BOX_HEIGHT = 410;
	public static final int BAMBOO1A_BOX_AREA = 445935;

	public static final int BAMBOO1B_BOX_WIDTH = 1134;
	public static final int BAMBOO1B_BOX_HEIGHT = 485;
	public static final int BAMBOO1B_BOX_AREA = 551610;

	public static final int CORK1A_BOX_WIDTH = 893;
	public static final int CORK1A_BOX_HEIGHT = 410;
	public static final int CORK1A_BOX_AREA = 367434;

	public static final int CORK1B_BOX_HEIGHT = 430;
	public static final int CORK1B_BOX_AREA = 473238;
	
	public int left;
	/** inclusive */
	public int right;
	public int up;
	/** inclusive */
	public int down;

	public Bounds() {
		left = -1;
		right = -1;
		up = -1;
		down = -1;
	}

	public Bounds(APImage img) {
		left = img.getWidth() - 1;
		right = 0;
		up = img.getHeight() - 1;
		down = 0;
	}
	
	public Bounds(int l, int r, int u, int d) {
		left = l;
		right = r;
		up = u;
		down = d;
	}
	
	/*
	 * Bamboo1A
	 * #053: 632 - 1528, 373 - 783 (85/50 cutoff) (height 411)
	 * #392: 487 - 1571, 366 - 775 (85/50 cutoff) (height 410)
	 * 
	 * To jump over words, use initial bounds:
	 * int startX = 340;
	 * int endX = 1730;
	 * int startY = 300;
	 * int endY = 900;
	 */
	/**
	 * Represents the fixed left and right bounds. Up and down bounds not used.
	 */
	public static Bounds bamboo1aBounds() {
		return new Bounds(487, 1571, 366, 776);
	}
	
	/*
	 * Bamboo1B
	 * To jump over words, use initial bounds:
	 * int startX = 250;
	 * int endX = 1650;
	 * int startY = 500;
	 * int endY = 1350;
	 * 
	 * Cork1A
	 * int startX = 400;
	 * int endX = 1500;
	 * int startY = 450;
	 * int endY = 970;
	 * 
	 * Cork1B
	 * int startX = 360;
	 * int endX = 1710;
	 * int startY = 450;
	 * int endY = 1090;
	 */
	/**
	 * Represents the fixed left and right bounds. Up and down bounds not used.
	 */
	public static Bounds cork1bBounds() {
		return new Bounds(510, 1607, 570, 1000);
	}
	
	public static Bounds imageBounds(APImage img) {
		return new Bounds(0, img.getWidth() - 1, 0, img.getHeight() - 1);
	}

	/** Checks that this is a valid bounds. Method not used. */
	public void check() throws IllegalStateException {
		if (left >= right || up >= down) {
			throw new IllegalStateException("Invalid bounds:\n" + toString());
		}
	}

	public int width() {
		return right - left;
	}

	public int height() {
		return down - up;
	}
	
	public int area() {
		return (right - left + 1) * (down - up + 1);
	}
	
	@Override
	public String toString() {
		return "horizontal span: " + left + " - " + right +
				"\nvertical span:   " + up + " - " + down;
	}

}
