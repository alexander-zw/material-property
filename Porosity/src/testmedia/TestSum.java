package testmedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestSum {
	
	private static File file;
	private static Scanner scan;
	
	private static void initialize() {
		file = new File("results/done cork1a_porosity.txt");
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static double getNumber() {
		return scan.nextDouble();
	}
	
	public static void main(String[] args) {
		
		initialize();
		double sum = 0;
		while (scan.hasNext()) {
			sum += getNumber();
		}
		System.out.println(sum / 499);

	}

}
