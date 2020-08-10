import javax.swing.ImageIcon;

/*
 * This class is used to create a Player object. It extends the Mover class and
 * includes an array of ImageIcons constants for the various states of Player (walking motion).
 * Also, there is a constructor that sets the initial image of the Player.
 */

public class Player extends Mover {

	//1. Creates an array of ImageIcons representing various states of the Player
	public static final ImageIcon[][] IMAGE = {

			//1.1 Image icons of all the different ways the player will be oriented 
			{ new ImageIcon("images/PlayerLeftClosed.png"), new ImageIcon("images/PlayerLeftWalking.png") },
			{ new ImageIcon("images/PlayerUp.png"), new ImageIcon("images/PlayerUp.png")  },
			{ new ImageIcon("images/PlayerRightClosed.png"), new ImageIcon("images/PlayerRightWalking.png") },
			{ new ImageIcon("images/PlayerDown.png") , new ImageIcon("images/PlayerDown.png")}

	};

	// 2. Constructor method for the player 
	public Player(){

		//2.1 Sets the Player face a certain direction when the game is started 
		this.setIcon(IMAGE[0][0]);
		
	}
}