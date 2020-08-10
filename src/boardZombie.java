/*
 * This class is used to create Ghost objects.  
 * It includes a constant ImageIcon array to hold the various ghost pictures
 * and a constructor method that sets the Ghosts' images
 */

import javax.swing.ImageIcon;

@SuppressWarnings("serial")

public class boardZombie extends Mover{
	
	/*
	 * Creates an array of ImageIcons representing various Ghosts 
	 */
	
	public static final ImageIcon[] IMAGE = {
			
			new ImageIcon("images/walk1.png"),
			new ImageIcon("images/walk1.png"),
			new ImageIcon("images/walk1.png"),
			new ImageIcon("images/walk1.png")
			
	};
	
	/*
	 * Ghost Constructor
	 * 
	 * @param gNum ghost number - 0, 1, 2, or 3
	 */
	
	public boardZombie (int zNum) {
		
		this.setIcon(IMAGE[zNum]);
		
	}
}