package calculation;

import images.APImage;
import images.Pixel;
import visualization.ImageVisual;

/**
 * Finds a suitable box (Bounds) to use as sample space to calculate the porosity.
 * <p>
 * This class is modified each time a new material is sampled, because the type of
 * suitable semi-fixed bounds may be different. To adjust this class for a new material,
 * the {@code MAX_PERCENTAGE_BLACK} and the appropriate semi-fixed bounds method
 * (1 and 2) must be chosen. The fixed bounds and fixed width and/or height must
 * be added to the Bounds class.
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class BoxFinder {
	
	/*
	 * NOTE: This is according to the original data. Bamboo1a and 1b may have been swapped.
	 * 
	 * Bamboo1a was produced with a MAX_PERCENTAGE_BLACK of 0.5 for upper limit,
	 * and fixed left and right limits. The lower limit was determined by fixing the height.
	 * 
	 * Bamboo1b had a MAX_PERCENTAGE_BLACK of 0.7 for the upper and left limits,
	 * and lower and right limits were determined with fixed width and height. 
	 * 
	 * Cork1a had a MAX_PERCENTAGE_BLACK of 0.7 for the upper and left limits,
	 * and lower and right limits were determined with fixed width and height.
	 * 
	 * Cork1b had a MAX_PERCENTAGE_BLACK of 0.7 for the upper limit,
	 * and fixed left and right limits. The lower limit was determined by fixing the height.
	 */
	public static final double MAX_PERCENTAGE_BLACK = 0.7;
	
	/**
	 * Top calculated, bottom fixed amount below top, left and right fixed.
	 * <p>
	 * First an outer bounds is determined. Then top is calculated by using the
	 * first y value where there are MAX_PERCENTAGE_BLACK percentage of black pixels
	 * in the row.
	 * <p>
	 * Bamboo1a and cork1b use this method.
	 */
	public static Bounds findSemifixedBounds1(APImage img) {

		Bounds outer = findOuterBounds(img);
		Bounds fixed = Bounds.cork1bBounds();
		int upper = beginUpperY(img, outer);
		
		Bounds b = new Bounds(fixed.left, fixed.right, upper, upper + Bounds.CORK1B_BOX_HEIGHT);
//		b.check(); // determined not worth the extra runtime to check
		
		return b;
	}

	/**
	 * Top and left calculated, bottom fixed amount below top, right fixed amount below left.
	 * <p>
	 * First an outer bounds is determined. Then top and left calculated by using the first
	 * x/y value where there are MAX_PERCENTAGE_BLACK percentage of black pixels in
	 * the row or column.
	 * <p>
	 * Bamboo1a and cork1b use this method.
	 */
	public static Bounds findSemifixedBounds2(APImage img) {

		Bounds outer = findOuterBounds(img);
		int upper = beginUpperY(img, outer);
		int left = beginLeftX(img, outer);
		
		Bounds b = new Bounds(left, left + Bounds.CORK1A_BOX_WIDTH,
				upper, upper + Bounds.CORK1A_BOX_HEIGHT);
//		b.check(); // determined not worth the extra runtime to check
		
		return b;
	}
	
	/** When some percentage of layer is not black. */
	public static Bounds findBounds(APImage img) {
		
		Bounds outer = findOuterBounds(img);
		
		Bounds b = new Bounds(beginLeftX(img, outer), beginRightX(img, outer),
				beginUpperY(img, outer), beginLowerY(img, outer));
		b.check();
		return b;
	}

	/**
	 * Outer bounds is an approximate bounds that we use to determine the precise
	 * bounds. It is found by starting at startX, endX, startY, endY and expanding
	 * those bounds until they contain all non-black pixels inside their range.
	 * The bounds we want are definitely inside that range, not outside it.
	 * <p>
	 * We start at startX, endX, etc. because we want to jump over the
	 * part of the image with white words/axes. A few rows or columns are skipped.
	 */
	public static Bounds findOuterBounds(APImage img) {
		
		// jump over words in picture
		int startX = 360;
		int endX = 1710;
		int startY = 450;
		int endY = 1090;
		
		Bounds b = new Bounds(endX, startX, endY, startY);

		for (int x = startX; x < endX + 1; x++) {
			for (int y = startY; y < endY + 1; y++) {
				Pixel p = img.getPixel(x, y);
				if (!Pixels.isBlack(p)) {
					if (x < b.left)
						b.left = x;
					if (x > b.right)
						b.right = x;
					if (y < b.up)
						b.up = y;
					if (y > b.down)
						b.down = y;
				}
			}
		}
		return b;
	}

	private static int beginUpperY(APImage img, Bounds b) {
		int y = b.up;
		for (; y < b.down + 1; y++) {
			double perc = percentageHoriz(img, y, b);
			if (perc < MAX_PERCENTAGE_BLACK)
				break;
		}
		return y;
	}

	private static int beginLowerY(APImage img, Bounds b) {
		int y = b.down;
		for (; y > b.up - 1; y--) {
			double perc = percentageHoriz(img, y, b);
			if (perc < MAX_PERCENTAGE_BLACK)
				break;
		}
		return y;
	}
	
	/** Percentage black in this row. */
 	private static double percentageHoriz(APImage img, int y, Bounds b) {
		int numBlack = 0;
		for (int x = b.left; x < b.right + 1; x++) {
			if (Pixels.isBlack(img.getPixel(x, y))) {
				numBlack++;
			}
		}
		return ((double) numBlack) / (b.right + 1 - b.left);
	}
 	
	private static int beginLeftX(APImage img, Bounds b) {
		int x = b.left;
		for (; x < b.right + 1; x++) {
			double perc = percentageVertic(img, x, b);
			if (perc < MAX_PERCENTAGE_BLACK)
				break;
		}
		return x;
	}

	private static int beginRightX(APImage img, Bounds b) {
		int x = b.right;
		for (; x > b.left - 1; x--) {
			double perc = percentageVertic(img, x, b);
			if (perc < MAX_PERCENTAGE_BLACK)
				break;
		}
		return x;
	}

	/** Percentage black in this column. */
 	private static double percentageVertic(APImage img, int x, Bounds b) {
		int numBlack = 0;
		for (int y = b.up; y < b.down + 1; y++) {
			if (Pixels.isBlack(img.getPixel(x, y))) {
				numBlack++;
			}
		}
		return ((double) numBlack) / (b.down + 1 - b.up);
	}
	
 	/** tests */
	public static void main(String[] args) {

		APImage img = new APImage("cork1a/cork1A transversal120_v1_current.jpg");
		
//		Bounds b = findBounds(img);
		Bounds b = findSemifixedBounds2(img);
//		Bounds b = findOuterBounds(img);
//		Bounds b = new Bounds(510, 1607, 570, 1000);
		
		ImageVisual.addBox(img, b);
//		System.out.println(b);
//		System.out.println(b.area());

		img.saveAs();
		
		System.out.println("Finished!");
		System.exit(0);
		
	}

}
