package calculation;

import images.Pixel;

/**
 * Useful tools regarding pixels.
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class Pixels {

	/**
	 * Ask for RGB values all to be under 5, because this is about when the pixel is
	 * indistinguishable from black to the eye.
	 */
 	public static boolean isBlack(Pixel p) {
		return p.getRed() < 5 && p.getGreen() < 5 && p.getBlue() < 5;
	}

	public static String formatCoord(int x, int y) {
		return "(" + x + ", " + y + ")";
	}

	public static String formatColor(Pixel p) {
		return formatCoord(p.getRed(), p.getGreen(), p.getBlue());
	}

	public static String formatCoord(int x, int y, int z) {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
