package visualization;

import java.awt.Color;

import calculation.Bounds;
import calculation.Pixels;
import images.APImage;
import images.Pixel;

/**
 * Includes visualization methods for boxes on images.
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class ImageVisual {

	/**
	 * Adds a box to the image, and then displays it.
	 */
	public static void showBox(APImage img, int leftMostX, int rightMostX, int upMostY, int downMostY) {
		addBox(img, leftMostX, rightMostX, upMostY, downMostY);
		img.draw();
	}
	
	/**
	 * Adds a box to the image, and then displays it.
	 */
	public static void showBox(APImage img, Bounds b) {
		showBox(img, b.left, b.right, b.up, b.down);
	}
	
	/**
	 * Adds a box to the image. The image can then be saved and viewed.
	 */
	public static void addBox(APImage img, int leftMostX, int rightMostX, int upMostY, int downMostY) {
		
		Color col = Color.BLUE;
		addVerticalLine(img, leftMostX, col);
		addVerticalLine(img, rightMostX, col);
		addHorizontalLine(img, upMostY, col);
		addHorizontalLine(img, downMostY, col);
		
	}

	/**
	 * Adds a box to the image. The image can then be saved and viewed.
	 */
	public static void addBox(APImage img, Bounds b) {
		addBox(img, b.left, b.right, b.up, b.down);
	}
	
	public static void addVerticalLine(APImage img, int x, Color col) {

		for (int y = 0; y < img.getHeight(); y++) {
			Pixel p = img.getPixel(x, y);
			p.setRed(col.getRed());
			p.setGreen(col.getGreen());
			p.setBlue(col.getBlue());
		}
		
	}
	
	public static void addHorizontalLine(APImage img, int y, Color col) {

		for (int x = 0; x < img.getWidth(); x++) {
			Pixel p = img.getPixel(x, y);
			p.setRed(col.getRed());
			p.setGreen(col.getGreen());
			p.setBlue(col.getBlue());
		}
		
	}

	/**
	 * Prints RGB values of some pixels in the image.
	 */
	public static void printPixels(APImage img, int x1, int x2, int y1, int y2) {
		
		for (int y = y1; y < y2; y++) {
			for (int x = x1; x < x2; x++) {
				Pixel p = img.getPixel(x, y);
				System.out.print(Pixels.formatColor(p) + " ");
			}
			System.out.println();
		}
		
	}
	
}
