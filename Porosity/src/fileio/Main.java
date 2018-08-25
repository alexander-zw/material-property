package fileio;

import calculation.Bounds;
import calculation.BoxFinder;
import calculation.PorosityFinder;
import images.APImage;

/**
 * Main class. Run this class to begin calculating.
 * <p>
 * Notes for this program:<ul>
 * <li> All code assumes the original data. There is suspicion that bamboo1a and 1b
 *      may have been incorrectly swapped, but this program assumes otherwise.
 * <li> The program makes use of the images package from the textbook Fundamentals of Java.
 *      The library is open-source, developed for use in introductory programming courses.
 *      It can be obtained in three different ways:<ol>
 *      <li> From the instructor resource CD, available from the publisher
 *      <li> From the publisher’s Web site, at www.cengage.com/coursetechnology
 *      <li> From the author’s Web site, at home.wlu.edu/~lambertk/hsjava
 * </ol>Also, Alexander Wu has a copy of the jar files.</ul>
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class Main {
	
	private static final int AREA = Bounds.CORK1B_BOX_AREA; // CHANGE THIS
	
	private static ImageFinder imgFinder() throws Exception {
		return new ImageFinder(ImageFinder.CORK1B_HEAD, ImageFinder.IMAGE_TAIL); // CHANGE THIS
	}

	private static DataWriter writer() throws Exception {
		return new DataWriter("cork1b_porosity.txt"); // CHANGE THIS
	}
	
	/**
	 * Reads in images, finds bounds for each and calculates porosity, writes result in txt file. 
	 */
	public static void main(String[] args) {
		try {
			
			ImageFinder in = imgFinder();
			DataWriter out = writer();
			
			try {

				for (APImage img : in) {
					Bounds b = BoxFinder.findSemifixedBounds1(img);
					double porosity = PorosityFinder.porosityWithArea(img, b, AREA);
					out.write(porosity);
					if (in.progress() % 4 == 0)
						System.out.print(".");
				}

				System.out.println("Finished!");

			} catch (Exception e) {
				System.out.println("Interrupted at progress " + in.progress());
				e.printStackTrace();
			} finally {
				in.close();
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
