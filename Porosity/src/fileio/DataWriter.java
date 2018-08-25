package fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes the porosities in a txt file.
 * 
 * @author AlexanderWu
 */
public class DataWriter {
	
	private final BufferedWriter write;
	
	public DataWriter(String filename) throws IOException {
		write = new BufferedWriter(new FileWriter("results/" + filename, true));
	}
	
	public void write(double num) throws IOException {
		write.append(Double.toString(num) + "\n");
	}
	
	public void close() throws IOException {
		write.close();
	}

	/** tests */
	public static void main(String[] args) {
		try {
			
			DataWriter dw = new DataWriter("experiment.txt");
			dw.write(-0.3);
			dw.close();
			System.out.println("Finished!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
