package fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import images.APImage;

/**
 * This is an iterator. Looks for images in the file system
 * and returns them one by one.
 * 
 * It saves its progress (an int) in a file called progress.txt
 * 
 * @author AlexanderWu
 */
@SuppressWarnings("restriction")
public class ImageFinder implements Iterable<APImage>, Iterator<APImage> {
	
	public static final String BAMBOO1A_HEAD = "bamboo1a/Bamboo1A transversal";
	public static final String BAMBOO1B_HEAD = "bamboo1b/Bamboo1B tranversal";
	public static final String CORK1A_HEAD = "cork1a/cork1A transversal";
	public static final String CORK1B_HEAD = "cork1b/cork1B transversal";
	
	public static final String IMAGE_TAIL = "_v1_current.jpg";
	
	public static final int IMAGE_NUM = 499;
	
	/**
	 * We have this step because the program somehow runs out of memory after running for
	 * a while.
	 */
	private static final int DEFAULT_STEP = 13;
	
	private int progress;
	private String fileHead;
	private String fileTail;
	/** exclusive */
	private int end;
	
	public ImageFinder(String filenameHead, String filenameTail, int end) throws Exception {
		getProgress();
		fileHead = filenameHead;
		fileTail = filenameTail;
		this.end = end;
		System.out.println("Beginning from: " + progress);
	}

	/** Does the next {@code DEFAULT_STEP} images (because the program can run out of memory). */
	public ImageFinder(String filenameHead, String filenameTail) throws Exception {
		getProgress();
		fileHead = filenameHead;
		fileTail = filenameTail;
		end = progress + DEFAULT_STEP;
		System.out.println("Beginning from: " + progress);
	}


	@Override
	public boolean hasNext() {
		return progress < end;
	}

	@Override
	public APImage next() {
		APImage img = new APImage(fileHead + String.format("%03d", progress) + fileTail);
		progress++;
		return img;
	}
	
	public int progress() {
		return progress;
	}
	
	public void close() throws IOException {
		System.out.println("Current progress: " + progress);
		putProgress();
	}
	
	private void getProgress() throws Exception {
		File file = new File("results/progress.txt");
		Scanner scan = new Scanner(file);
		progress = scan.nextInt();
		scan.close();
	}

	private void putProgress() throws IOException {
		BufferedWriter write = new BufferedWriter(new FileWriter("results/progress.txt"));
		write.write(Integer.toString(progress));
		write.close();
	}

	@Override
	public Iterator<APImage> iterator() {
		return this;
	}
	
 	/** tests */
	public static void main(String[] args) {
		try {
			
			ImageFinder imgf = new ImageFinder(BAMBOO1A_HEAD, IMAGE_TAIL, IMAGE_NUM);
			
			imgf.next().draw();
			
			Scanner scan = new Scanner(System.in);
			scan.nextLine();
			scan.close();
			
			imgf.next().draw();
			System.out.println("Finished!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
