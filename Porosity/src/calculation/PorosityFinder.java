package calculation;

import images.APImage;

/**
 * Provides method to calculate the porosity.
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class PorosityFinder {
	
	public static double porosity(APImage img, Bounds b) {
		return porosityWithArea(img, b, b.area());
	}

	/**
	 * If the area of the box is fixed, you can just use a constant area instead of
	 * calculating it each time.
	 */
	public static double porosityWithArea(APImage img, Bounds b, int area) {
		int numBlack = 0;
		for (int x = b.left; x < b.right + 1; x++) {
			for (int y = b.up; y < b.down + 1; y++) {
				if (Pixels.isBlack(img.getPixel(x, y)))
					numBlack++;
			}
		}
		return ((double) numBlack) / area;
	}
	
 	/** tests */
	public static void main(String[] args) {

		APImage img = new APImage("cork1b/cork1B transversal000_v1_current.jpg");
		
		// all values below using image 169
		
		// horizontal span: 487 - 1571
		// vertical span:   380 - 790
		Bounds b = BoxFinder.findSemifixedBounds1(img); // using this, porosity is 0.20089474923475395
		
		System.out.println("Porosity: " + porosity(img, b));

//		ImageVisual.showBox(img, b);
//		img.saveAs();
		
		System.out.println("Finished!");
		System.exit(0);
		
	}

}
