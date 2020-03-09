/**
 * This is the code demonstration for ImageJ Library
 * It involves comparing 2 images based on their RGB values and showing if they are the same or not
 * 
 * This simple class will contain information about 2 files' path location along with a variable of type 
 * BufferredImage which will be used to make sure both images are read in
 * 
 * @author Abdulbasid Guled
 * @author Jaekyoung An
 * @since 05/03/2020
 * 
 */


import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;
import java.lang.Exception;


public class Main {
	
	public static BufferedImage readInImage(File file, BufferedImage img)
	{
		try
		{
			img = ImageIO.read(file);
		} 
		catch(IOException ex)
		{
			System.out.println("ERROR! Could not read in image files");
			ex.printStackTrace();
		}
		
		return img;
	}
	
	public static void displayAnswer(boolean answer, double percentage)
	{
		if (answer)
		{
			System.out.println("The two images are the same!");
			System.out.println("The percentage is: " + String.format("%.2f", percentage));
		}
		else 
		{	
			System.out.println("The two images are different!");
			System.out.println("The percentage is: " + String.format("%.2f", percentage));
		}
	}
	
	
	public static void main(String[] args)
	{
		// Declare variables
		
		BufferedImage img1 = null, img2 = null; // Used to read in images
		
		File file1 = new File(args[1]), file2 = new File(args[2]); // Reading in files from command-line
		
		double X1, Y1, X2, Y2, Z1, Z2; // Needed to get the Pixel values
		double difference, percentage; // Helps with precision since this program will use precision if they're not the same!
		
		boolean isSame = false; // Assume both images are not the same by default
		
		// Try to read in images, display error message if either cannot be read in
		
		readInImage(file1, img1);
		readInImage(file2, img2);
		
		
		
		/* TODO:
		 * - Gather pixel heights
		 * - Perform operations needed to gather total pixels for both images
		 * - Do math operation to get difference and percentage
		 * - Call displayAnswer with percentage
		 */
	}
}
