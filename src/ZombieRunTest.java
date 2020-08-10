/* HEADER:
 * 
 * Names and Responsibilities:
 * 		-Shrill Patel:
 * 			-HowToPlay Class
 * 			-ZombieRunGUI Class
 * 			-ZombieRunTest
 * 			-Player Class
 * 			-Board Class
 * 			-Shop Class
 * 			-HighScores Class
 * 			-boardZombie Class
 * 			-Mover Class
 * 
 * 		-Abhinav Asija: 
 * 			-HomeScreen Class
 * 			-HighScoreManager Class
 * 			-Score Class
 * 			-ScoreComparator Class
 * 			-RPGboard Class
 * 			-Zombie Class
 * 			-RPGPlayer
 * 
 * Date of Submission:
 * 	-January 19, 2018
 * 
 * Course Code and Intructor Name:
 * 	-ICS3U1-02
 * 	-Mr.Fernandes
 * 
 * Title:
 * 	-Zombie Run
 * 
 * Description: Navigate through a maze full of zombies and landmines. If you hit a landmine, your health goes down. 
 * If you touch a zombie, you will get sent to the rpg battle area. They will fight against 3 to 5 zombies (randomly generated) 
 * in a turn based rpg format game where the user can select from a various amount of option on how they are going to battle the 
 * zombies. The player/user must collect all keys on the board before they are allowed to move onto the next level.
 * 
 * Features:
 * - background music/sound effects
 * - animations
 * - user input for username
 * - highscore screen saved to a file for permanent highscores
 * - homescreen
 * - money/shop system
 * - multiple levels
 * - power up (key) collection and use
 * - zombie ai (increases in speed as levels increase)
 * 
 * 
 * 
 * Major Skills:
 * - zombie and player object classes used 
 * - arrays used to create multiple zombie objects
 * - mouseListener used
 * - gui development
 * - saving to a file and retrieving data from said file
 * 
 * 
 * Areas of Concern:
 * - In the rpg battle section, if you use your attacks too frequently without letting the zombie animation finish, the program will crash
 * - In the shop and board class, if the upgrade shop button is pressed, the game timers do not pause and the game goes on while the user is purchasing their upgrades
 * 
 */


/*
 * This class is used to create a new HomeScreen which will start a whole new game
 * (from the beginning). 
 */

public class ZombieRunTest {
	
	//1. Main method to run program and create a new GUI 
	public static void main (String args []) {
	
		//1.1 Create a new Home Screen 
		new HomeScreen ();
		
	}
}