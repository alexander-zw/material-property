package testmedia;

import java.awt.Color;

import images.APImage;
import images.Pixel;

@SuppressWarnings("restriction")
public class TestPng {

	public static void main(String[] args) {

		APImage img = new APImage("b1A_frame.png");
		
		printPixels(img, 360, 380, 380, 400);
		
//		showBox(img, 480, 500, 480, 500);

		System.out.println("Finished!");

	}
	
	public static void printSpan(APImage img) {
		
		int leftMostX = img.getWidth();
		int rightMostX = -1;
		int upMostY = img.getHeight();
		int downMostY = -1;

		/*
		 * size:            1824 * 1304
		 * 
		 * Not black
		 * horizontal span: 112 - 1711
		 * vertical span:   64 - 1143
		 * 
		 * White
		 * horizontal span: 365 - 1490
		 * vertical span:   385 - 844
		 * 
		 * first one not black seems to be (112, 69)
		 */
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				Pixel p = img.getPixel(x, y);
				if (isWhitePixel(p)) {
					if (x < leftMostX)
						leftMostX = x;
					if (x > rightMostX)
						rightMostX = x;
					if (y < upMostY)
						upMostY = y;
					if (y > downMostY)
						downMostY = y;
				}
			}
		}
		
		System.out.println("size:            " + img.getWidth() + " * " + img.getHeight());
		System.out.println("horizontal span: " + leftMostX + " - " + rightMostX);
		System.out.println("vertical span:   " + upMostY + " - " + downMostY);
		
	}

	public static void printPixels(APImage img, int x1, int x2, int y1, int y2) {
		
		for (int y = y1; y < y2; y++) {
			for (int x = x1; x < x2; x++) {
				Pixel p = img.getPixel(x, y);
				System.out.print(formatPixel(p) + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void showBounds(APImage img) {
		
		int leftMostX = 112;
		int rightMostX = 1711;
		int upMostY = 64;
		int downMostY = 1143;
		
		Color col = Color.BLUE;
		addVerticalLine(img, leftMostX, col);
		addVerticalLine(img, rightMostX, col);
		addHorizontalLine(img, upMostY, col);
		addHorizontalLine(img, downMostY, col);

		col = Color.RED;
		leftMostX = 365;
		rightMostX = 1490;
		upMostY = 385;
		downMostY = 844;
		addVerticalLine(img, leftMostX, col);
		addVerticalLine(img, rightMostX, col);
		addHorizontalLine(img, upMostY, col);
		addHorizontalLine(img, downMostY, col);
		
		img.draw();
		
	}
	
	public static void showBox(APImage img, int leftMostX, int rightMostX, int upMostY, int downMostY) {
		
		Color col = Color.BLUE;
		addVerticalLine(img, leftMostX, col);
		addVerticalLine(img, rightMostX, col);
		addHorizontalLine(img, upMostY, col);
		addHorizontalLine(img, downMostY, col);
		
		img.draw();
		
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
	
 	public static boolean isBlackPixel(Pixel p) {
		return p.getRed() == 0 && p.getGreen() == 0 && p.getBlue() == 0;
	}
	
	public static boolean isWhitePixel(Pixel p) {
		return p.getRed() == 255 && p.getGreen() == 255 && p.getBlue() == 255;
	}

	public static String formatCoord(int x, int y) {
		return "(" + x + ", " + y + ")";
	}

	public static String formatPixel(Pixel p) {
		return formatCoord(p.getRed(), p.getGreen(), p.getBlue());
	}

	public static String formatCoord(int x, int y, int z) {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
