/**
 * Written class by Abdulbasid Guled and Jaekyoung An, and tested by Jaekyoung An
 * This program compares 2 similar images based on their RGB values
 * This program does not assume that 2 images that are the same but with differing sizes are equal
 * 
 * A breakdown of the RGB values, followed by calculations of the hue difference between the two are what will 
 * determine the similarity of our program.
 * 
 * Main program is ran by ensuring images are loaded into the command-line as command-line arguments
 * Ensure proper file paths for images are correct when entering in arguments
 * 
 * As of the making of this program, only 2 images can be loaded. Not loading 2 images or loading in more than 2
 * throws an IOException
 * 
 * Was originally going to implement JavaCV, and the jar files included here will be kept for project reference, however,
 * our group had substantial trouble with loading the proper classes as the ClassNotFound exception would often be ran.
 * 
 * @since 30/03/2020
 */

import java.awt.BorderLayout; // Needed for BorderLayout to get display working properly
import java.awt.FlowLayout; // Needed for FlowLayout, same reason as BorderLayout
import java.awt.image.BufferedImage; // Needed for BufferedImage to store in Image
import javax.imageio.ImageIO; // Needed for ImageIO functions to read in Images
import java.io.File; // Needed because ImageIO.read(File filename) requires a File object as parameter 
import java.io.IOException; // Needed to throw the Java IOException for our application
import javax.swing.JFrame; // Needed to make frame to display both images side by side
import javax.swing.JLabel; // Needed to store the images onto the frame
import javax.swing.ImageIcon; // Needed to store images together 

public class Main extends JFrame
{ 
	private ImageIcon image1, image2; // Used to load in image
	private JLabel lbl1, lbl2; // Used to load in image onto a label
	
	private BufferedImage imgA = null, imgB = null; // Used to store image
	
	/**
	 * Overloaded constructor, accepts 2 parameters
	 * Reads in the image files, stores them in the label, and sets up the BufferedImage objects to be used 
	 * @param filename1
	 * @param filename2
	 * @throws Exception 
	 */
	Main(String filename1, String filename2) {
		setLayout(new FlowLayout());
		
		if (filename1 == null || filename1 == "" || filename2 == null || filename2 == "") {
			imgA = null;
			imgB = null;
		}
		else 
		{	
			image1 = new ImageIcon(filename1);
			lbl1 = new JLabel(image1);
			add(lbl1);
			
			image2 = new ImageIcon(filename2);
			lbl2 = new JLabel(image2);
			add(lbl2);
		
			imgA = readInImage(filename1);
		    imgB = readInImage(filename2);
		}
	}
	

	 public static void main(String[] args) 
	 { 	 
		 Main obj = new Main(args[0], args[1]);
		 
		 obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 obj.setVisible(true);
		 obj.pack();
		 obj.setTitle("Image Comparison Application");
		 
	     boolean isSame = false; // Assume they aren't equal
	        
	     // Get the total height and width of both pictures
	        
         int width1 = obj.imgA.getWidth(); 
         int width2 = obj.imgB.getWidth(); 
         int height1 = obj.imgA.getHeight(); 
         int height2 = obj.imgB.getHeight(); 
	        
	     // Since a pixel has 3 rgb values, we multiply by 3 to get them all
         // Only done with 1 image because our program assumes that both images will have the same height and width
         double total_pixels = width1 * height1 * 3; 
	  
	        /*
	         * If the images are not the same size, then we assume they aren't equal due to 
	         * differing size, which affects the calculations of the RGB values
	         * Our program compares 
	         */
	        
	        if ((width1 != width2) || (height1 != height2)) 
	            System.out.println("Image sizes are different, and therefore, can't be the same pixel wise"); 
	        else
	        { 
	        	
	        /*
	         * Calculates difference
	         * Is done by doing the following:
	         * 
	         * - Get the RGB using getRGB method of ImageIO class
	         * - Calculate RGB pixels for all 3 colors for both images (Using bit-wise AND and right shift)
	         * - Calculate total difference for all 3 colors for both images using abs method of the Math Class (For absolute value)
	         */
	            long difference = 0; 
	            for (int y = 0; y < height1; y++) 
	            { 
	                for (int x = 0; x < width1; x++) 
	                { 
	                    int rgbA = obj.imgA.getRGB(x, y); 
	                    int rgbB = obj.imgB.getRGB(x, y); 
	                    int redA = (rgbA >> 16) & 255; 
	                    int greenA = (rgbA >> 8) & 255; 
	                    int blueA = (rgbA) & 255; 
	                    int redB = (rgbB >> 16) & 255; 
	                    int greenB = (rgbB >> 8) & 255; 
	                    int blueB = (rgbB) & 255; 
	                    difference += Math.abs(redA - redB); 
	                    difference += Math.abs(greenA - greenB); 
	                    difference += Math.abs(blueA - blueB); 
	                } 
	            } 

	            double percentage = calculateDifference(difference, total_pixels);
	            
	            isSame = percentage == 0.0 ? true : false; // Set flag based on value of the percentage difference
	            
	            displayAnswer(percentage, isSame); // displays answer to the console
	           
	        } 
	    } 
	 /**
	  * Reads in image from command-line and stores in a BufferedImage object to be returned
	  * Throws IOException: Wrong filepath given
	  * @param Filename
	  * @return BufferedImage
	  */
	 public static BufferedImage readInImage(String Filename)
	 {
		 BufferedImage newImage = null;
		 
		 try {
			 File fileA = new File(Filename);
	         newImage = ImageIO.read(fileA);  
	      
		 } catch (IOException ex)
		 {
			 System.out.println("Image could not be read in");
			 ex.printStackTrace();
		 }
		 		 
		 return newImage;
	 }
	
	 /**
	  * Calculates total difference between the calculated difference and the total number of pixels
	  * Should return 0 if both images are the same, else the percentage difference between the two
	  * @param num1
	  * @param num2
	  * @return double
	  */
	 public static double calculateDifference(double num1, double num2)
	 {
         // Normalizing the value of different pixels 
         double avg_different_pixels = num1 / num2; 

         // Calculate total percentage difference assuming 255 pixels exists in an image
         double difference = (avg_different_pixels / 255) * 100; 
         
         return difference;
	 }
	 
	 /**
	  * Displays answer to the console based on a boolean flag
	  * The flag is used to check the value of the difference to know whether the images are the same or not 
	  * @param difference
	  * @param flag
	  * @return void
	  */
	 public static void displayAnswer(double difference, boolean flag)
	 {
		 if (flag)
			 System.out.println("Images are the same!\n"
			 		+ "Difference is: " + (int) difference);
		 else
			 System.out.println("Images are not the same!\n"
			 		+ "Difference is: " + String.format("%.2f", difference) + "%");
	 }
} 