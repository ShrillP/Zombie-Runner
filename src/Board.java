import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * This class represents the game board and includes methods to handle 
 * keyboard events and actions that relate to the game itself.
 */

public class Board extends JPanel implements KeyListener, ActionListener {

	//Objects and variables for the high score 
	HighscoreManager hm = new HighscoreManager();

	//Timer for game movement 
	public Timer gameTimer = new Timer (250, this);

	//Timer for Player animation 
	private Timer animateTimer = new Timer (2, this);

	//Timer to restart the game after a win/lose
	private Timer restartTimer = new Timer (250, this);

	//Timer for respawning Player after death 
	private Timer respawnTimer = new Timer (250, this);

	//Constants for the .txt file 
	private static final Icon BLANK = new ImageIcon ("images/Blank.png");

	private static final Icon WALL = new ImageIcon ("images/Wall.png");	//Wall

	private static final Icon OILCAN = new ImageIcon ("images/OilBarrel.png");	//Oil Cans 

	private static final Icon KEYS = new ImageIcon ("images/Key.png");	//Keys 

	private static final Icon LOCK = new ImageIcon ("images/Lock.png");	//Lock 

	private static final Icon GRAY = new ImageIcon ("images/Grey.jpg");	//Rendezvous Point (house)

	private static final Icon RED = new ImageIcon ("images/Red.jpg");	//Rendezvous Point (house)

	private static final Icon COIN = new ImageIcon ("images/Coin.png");

	private static final Icon DOOR = new ImageIcon ("images/Door.png");

	private static final Icon LANDMINE = new ImageIcon ("images/Landmine.png");

	private static final Icon FLAME = new ImageIcon ("images/flame.png");

	private static final int ROW_SIZE = 25;

	private static final int COL_SIZE = 50;

	//Array to hold the game board characters from the text file 
	private char [][] maze = new char [ROW_SIZE][COL_SIZE];

	//Array to hold the game board images 
	private JLabel [][] cell = new JLabel[ROW_SIZE][COL_SIZE];

	//Player object 
	private Player player;

	//Array of zombies 
	private boardZombie [] zombie = new boardZombie [4];

	//Tracks the amount of keys on the board 
	private int keys = 0;

	//Track the score of the game
	private int score = 0;

	//Variable to keep track on how many keys are collected
	private int keysCollected = 0;
	
	//Variables to keep track of how many coins are collected
	public static int coinsCollected = 0;
	
	private int levelCounter = 0;

	//A variable to hold the score 
	private int points = 0;

	//Variable to hold the number of lives left 
	private int health = 100;

	//Steps for animating the Player walking/running
	private int pStep;

	//Variable to keep track of the high score 
	private int high = 0;

	//Restart game after a certain amount of time 
	private int restartCount = 0;

	//Respawn player on the board after a certain time
	private int respawnCount = 0; 

	//Variables to hold the Players starting position
	private int playerStartRow = 0;
	private int playerStartColumn = 0;

	//Variables to hold the zombies beginning position 
	private int [] zombieStartRow = new int [4];
	private int [] zombieStartColumn = new int [4];

	/*
	 * Construct the game board including the layout, background, Player and zombiess 
	 * and calls the loadBoard method
	 */
	public Board () {

		//1. Set the layout (grid) and background 
		setLayout(new GridLayout(ROW_SIZE, COL_SIZE));
		setBackground(Color.BLACK);

		//2. Create the player and the zombies 
		player = new Player();

		zombie[0] = new boardZombie(0);
		zombie[1] = new boardZombie(1);
		zombie[2] = new boardZombie(2);
		zombie[3] = new boardZombie(3);

		//3. Load the maze file 
		loadBoard();
		levelCounter = 1;

	}

	//This method loads the board onto the screen from a text file 
	private void loadBoard() {
		
		//Variable to keep track of the rows in the text file 
		int r = 0;

		//1. Open the text file for input
		Scanner input = null;

		try {

			//Inputs the file into the program 
			input = new Scanner (new File("maze.txt"));

			//2. Cycle through all the rows in the maze file reading one row at a time
			while(input.hasNext()) {

				//2.1 Read the next line from the maze file
				maze[r] = input.nextLine().toCharArray();

				//2.2 For each row cycle through all the columns
				for (int c = 0; c < maze[r].length; c++) {

					cell[r][c] = new JLabel();

					/*Depending on the symbol in the maze file assign the 
					 *appropriate picture. *NOTE: This only has to be done
					 *for squares that are not similar to the background.
					 */

					//2.2.1 If the symbol is a wall then assign a wall picture to the current square on the screen
					if (maze[r][c] == 'W') {

						cell[r][c].setIcon(WALL);

						//2.2.2 Otherwise if the symbol is a keys then assign a key picture to the current square on the screen and keep track of the number of items
					} else if (maze[r][c] == 'K') {

						cell[r][c].setIcon(KEYS);
						score++;
						keys++;

						//2.2.3 Otherwise if the symbol is Player then assign the starting image and set Player's row, column, and direction 
					} else if (maze[r][c] == 'P') {

						playerStartRow = r;			//player starting position (row)
						playerStartColumn = c;		//player starting position (column)
						cell[r][c].setIcon(player.getIcon());
						player.setRow(r);
						player.setColumn(c);
						player.setDirection(0);		//player will be facing left when game starts 

						//2.2.4 Otherwise if the symbol is a lock then assign a lock 
					} else if (maze[r][c] == 'L') {

						cell[r][c].setIcon(LOCK);

						//2.2.7 Otherwise if the symbol is an oil can then assign a oil can 
					} else if (maze[r][c] == 'O') {

						cell[r][c].setIcon(OILCAN);

						//2.2.8 Otherwise if the symbol is a shelter then assign a house (gray block part)
					} else if (maze[r][c] == 'G') {

						cell[r][c].setIcon(GRAY);

						//2.2.9 Otherwise if the symbol is a shelter then assign a house (red block part)
					} else if (maze[r][c] == 'R') {

						cell[r][c].setIcon(RED);

						//2.2.10 Otherwise if the symbol is a door then assign a door 
					} else if (maze[r][c] == 'D') {

						cell[r][c].setIcon(DOOR);

						//2.2.11 Otherwise if the symbol is a coin then assign a coin image 
					} else if (maze[r][c] == 'C') {

						cell[r][c].setIcon(COIN);

						//2.2.12 Otherwise if the symbol is a blank then assign a blank 
					} else if (maze[r][c] == 'X') {

						cell[r][c].setIcon(BLANK);


					} else if (maze[r][c] == 'M') {

						cell[r][c].setIcon(LANDMINE);

						//2.2.14 Otherwise if the symbol is a Zombie then assign the appropriate zombies image and set the zombies's row, column
					} else if (maze[r][c] == '0' || maze[r][c] == '1' || maze[r][c] == '2' || maze[r][c] == '3') {

						int zNum = Character.getNumericValue(maze[r][c]);

						//Get the start rows and columns for the 4 ghosts 
						zombieStartRow[zNum] = r;
						zombieStartColumn[zNum] = c;

						cell[r][c].setIcon(zombie[zNum].getIcon());
						zombie[zNum].setRow(r);
						zombie[zNum].setColumn(c);

					}

					//2.2.11 Add the current cell to the board panel
					add(cell[r][c]);

				}

				//2.3 Increment the row
				r++;

			}

			//3. Close the maze text file
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	//This method loads the board onto the screen from a text file 
	private void loadBoard2() {

		//Variable to keep track of the rows in the text file 
		int r = 0;

		//1. Open the text file for input
		Scanner input = null;

		try {

			//Inputs the file into the program 
			input = new Scanner (new File("maze2.txt"));

			//2. Cycle through all the rows in the maze file reading one row at a time
			while(input.hasNext()) {

				//2.1 Read the next line from the maze file
				maze[r] = input.nextLine().toCharArray();

				//2.2 For each row cycle through all the columns
				for (int c = 0; c < maze[r].length; c++) {

					cell[r][c] = new JLabel();

					/*Depending on the symbol in the maze file assign the 
					 *appropriate picture. *NOTE: This only has to be done
					 *for squares that are not similar to the background.
					 */

					//2.2.1 If the symbol is a wall then assign a wall picture to the current square on the screen
					if (maze[r][c] == 'W') {

						cell[r][c].setIcon(WALL);

						//2.2.2 Otherwise if the symbol is a keys then assign a key picture to the current square on the screen and keep track of the number of items
					} else if (maze[r][c] == 'K') {

						cell[r][c].setIcon(KEYS);
						score++;
						points++;

						//2.2.3 Otherwise if the symbol is Player then assign the starting image and set Player's row, column, and direction 
					} else if (maze[r][c] == 'P') {

						playerStartRow = r;			//player starting position (row)
						playerStartColumn = c;		//player starting position (column)
						cell[r][c].setIcon(player.getIcon());
						player.setRow(r);
						player.setColumn(c);
						player.setDirection(0);		//player will be facing left when game starts 

						//2.2.4 Otherwise if the symbol is a lock then assign a lock 
					} else if (maze[r][c] == 'L') {

						cell[r][c].setIcon(LOCK);

						//2.2.7 Otherwise if the symbol is an oil can then assign a oil can 
					} else if (maze[r][c] == 'O') {

						cell[r][c].setIcon(OILCAN);

						//2.2.8 Otherwise if the symbol is a shelter then assign a house (gray block part)
					} else if (maze[r][c] == 'G') {

						cell[r][c].setIcon(GRAY);

						//2.2.9 Otherwise if the symbol is a shelter then assign a house (red block part)
					} else if (maze[r][c] == 'R') {

						cell[r][c].setIcon(RED);

						//2.2.10 Otherwise if the symbol is a door then assign a door 
					} else if (maze[r][c] == 'D') {

						cell[r][c].setIcon(DOOR);

						//2.2.11 Otherwise if the symbol is a coin then assign a coin image 
					} else if (maze[r][c] == 'C') {

						cell[r][c].setIcon(COIN);

						//2.2.12 Otherwise if the symbol is a blank then assign a blank 
					} else if (maze[r][c] == 'X') {

						cell[r][c].setIcon(BLANK);


					} else if (maze[r][c] == 'M') {

						cell[r][c].setIcon(LANDMINE);

						//2.2.14 Otherwise if the symbol is a Zombie then assign the appropriate zombies image and set the zombies's row, column
					}

					//2.2.11 Add the current cell to the board panel
					add(cell[r][c]);

				}

				//2.3 Increment the row
				r++;

			}

			//3. Close the maze text file
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	//This method loads the board onto the screen from a text file 
	private void bossBoard() {

		//Variable to keep track of the rows in the text file 
		int r = 0;

		//1. Open the text file for input
		Scanner input = null;

		try {

			//Inputs the file into the program 
			input = new Scanner (new File("maze3.txt"));

			//2. Cycle through all the rows in the maze file reading one row at a time
			while(input.hasNext()) {

				//2.1 Read the next line from the maze file
				maze[r] = input.nextLine().toCharArray();

				//2.2 For each row cycle through all the columns
				for (int c = 0; c < maze[r].length; c++) {

					cell[r][c] = new JLabel();

					/*Depending on the symbol in the maze file assign the 
					 *appropriate picture. *NOTE: This only has to be done
					 *for squares that are not similar to the background.
					 */

					//2.2.1 If the symbol is a wall then assign a wall picture to the current square on the screen
					if (maze[r][c] == 'W') {

						cell[r][c].setIcon(WALL);

						//2.2.2 Otherwise if the symbol is a keys then assign a key picture to the current square on the screen and keep track of the number of items
					} else if (maze[r][c] == 'K') {

						cell[r][c].setIcon(KEYS);
						score++;
						points++;

						//2.2.3 Otherwise if the symbol is Player then assign the starting image and set Player's row, column, and direction 
					} else if (maze[r][c] == 'P') {

						playerStartRow = r;			//player starting position (row)
						playerStartColumn = c;		//player starting position (column)
						cell[r][c].setIcon(player.getIcon());
						player.setRow(r);
						player.setColumn(c);
						player.setDirection(0);		//player will be facing left when game starts 

						//2.2.4 Otherwise if the symbol is a lock then assign a lock 
					} else if (maze[r][c] == 'L') {

						cell[r][c].setIcon(LOCK);

						//2.2.7 Otherwise if the symbol is an oil can then assign a oil can 
					} else if (maze[r][c] == 'O') {

						cell[r][c].setIcon(OILCAN);

						//2.2.8 Otherwise if the symbol is a shelter then assign a house (gray block part)
					} else if (maze[r][c] == 'G') {

						cell[r][c].setIcon(GRAY);

						//2.2.9 Otherwise if the symbol is a shelter then assign a house (red block part)
					} else if (maze[r][c] == 'R') {

						cell[r][c].setIcon(RED);

						//2.2.10 Otherwise if the symbol is a door then assign a door 
					} else if (maze[r][c] == 'D') {

						cell[r][c].setIcon(DOOR);

						//2.2.11 Otherwise if the symbol is a coin then assign a coin image 
					} else if (maze[r][c] == 'C') {

						cell[r][c].setIcon(COIN);

						//2.2.12 Otherwise if the symbol is a blank then assign a blank 
					} else if (maze[r][c] == 'X') {

						cell[r][c].setIcon(BLANK);


					} else if (maze[r][c] == 'M') {

						cell[r][c].setIcon(LANDMINE);

						//2.2.14 Otherwise if the symbol is a Zombie then assign the appropriate zombies image and set the zombies's row, column
					}

					//2.2.11 Add the current cell to the board panel
					add(cell[r][c]);

				}

				//2.3 Increment the row
				r++;

			}

			//3. Close the maze text file
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	//Handles keyboard entries - to start the game and control player's movements
	public void keyPressed(KeyEvent key) {

		//1. If the game isn't running and player is alive then start the game timer
		if (gameTimer.isRunning() == false && player.isDead() == false)
			gameTimer.start();

		//2. If Player is still alive and the game is not over then
		if (player.isDead() == false) {

			//2.1 Track direction based on the key pressed (37 is the ASCII code)
			int direction = key.getKeyCode() - 37;

			//2.2 Change direction of the player (37-left, 38-up,39-right, 40-down)
			if (direction == 0 && maze[player.getRow()][player.getColumn() - 1] != 'W'  && maze[player.getRow()][player.getColumn() - 1] != 'O' && maze[player.getRow()][player.getColumn() - 1] != 'L')
				player.setDirection(0);

			else if (direction == 1 && maze[player.getRow() - 1][player.getColumn()] != 'W'  && maze[player.getRow() - 1][player.getColumn()] != 'O' && maze[player.getRow()-1][player.getColumn()] != 'L')
				player.setDirection(1);

			else if (direction == 2 && maze[player.getRow()][player.getColumn() + 1] != 'W'  && maze[player.getRow()][player.getColumn() + 1] != 'O' && maze[player.getRow()][player.getColumn() + 1] != 'L')
				player.setDirection(2);

			else if (direction == 3 && maze[player.getRow()+1][player.getColumn()] != 'W' && maze[player.getRow() + 1][player.getColumn()] != 'O' && maze[player.getRow() + 1][player.getColumn()] != 'L')
				player.setDirection(3);

		}
	}

	//Mandatory method to implement KeyListener interface
	public void keyReleased(KeyEvent key) {
		// Not used
	}

	//Mandatory method to implement KeyListener interface
	public void keyTyped(KeyEvent key) {
		// Not used
	}

	//Allows an object to move and updates both on the maze and screen based on: 
	//the object, direction, and change in row and column
	private void performMove(Mover mover) {   

		//Only let the player through the lock if he has collected all the keys 
		if (keysCollected == 4 && maze[player.getRow()+1][player.getColumn()] == 'L') {

			maze[18][5] = 'X';
			cell[18][5].setIcon(BLANK);
			unlockSound();

		}

		//2. If there is no wall in the direction that the Mover object wants to go then
		if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W' && maze[mover.getNextRow()][mover.getNextColumn()] != 'O') {

			//2.1 If the Mover object is the player then animate him walking 
			if (mover == player)
				animateTimer.start();

			//2.2 Otherwise the Mover is a zombie
			else {

				//2.2.1 If the cell where the Zombie is has any other image
				if (maze[mover.getRow()][mover.getColumn()] == 'O') {
					cell[mover.getRow()][mover.getColumn()].setIcon(OILCAN);

				} else if (maze[mover.getRow()][mover.getColumn()] == 'K') {
					cell[mover.getRow()][mover.getColumn()].setIcon(KEYS);

				} else if (maze[mover.getRow()][mover.getColumn()] == 'L') {
					cell[mover.getRow()][mover.getColumn()].setIcon(LOCK);

				} else if (maze[mover.getRow()][mover.getColumn()] == 'G') {
					cell[mover.getRow()][mover.getColumn()].setIcon(GRAY);

				} else if (maze[mover.getRow()][mover.getColumn()] == 'R') {
					cell[mover.getRow()][mover.getColumn()].setIcon(RED);

				} else if (maze[mover.getRow()][mover.getColumn()] == 'C') {
					cell[mover.getRow()][mover.getColumn()].setIcon(COIN);

				} else if (maze[mover.getRow()][mover.getColumn()] == 'M') {
					cell[mover.getRow()][mover.getColumn()].setIcon(LANDMINE);

				} else 
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);

				//2.2.3 Move the zomie’s position
				mover.move();

				//2.2.4 If a collision has occurred then death occurs
				if (collided()) {
					death();
					new RPGboard();

				}

				//2.2.5 Otherwise update the picture on the screen
				else 
					cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());

			}
		}
	}

	//Determines if player has collided with a zombie
	private boolean collided() {

		//1. Cycle through all the zombies to see if anyone has caught the player
		for (boardZombie z: zombie) { 

			//1.1 If the zombies are in the same location then return that a collision 
			if (z.getRow() == player.getRow() && z.getColumn() == player.getColumn() && gameTimer.isRunning() == true) 
				return true;

		}

		//2. If no zombies were in the same locaton as player than return no collisions 
		return false;

	}

	//Stop the game when player and a zombie 'collide'
	private void death() {

		//1. Set player as dead
		player.setDead(true);

		//2. Determine the current location of player on the screen and assign a picture of a death
		cell[player.getRow()][player.getColumn()].setIcon(FLAME);
		deathSound();

		//3. Set player to dead 
		if (health == 0) {

			//4.1 Stop the game 
			stopGame();

			//4.3 Stop the animation and game timers and start the respawn timer 
		} else {
			animateTimer.stop();
			gameTimer.stop();
			respawnTimer.start();

		}
	}

	//Stops the game timer 
	private void stopGame() {

		//1. If player is dead or player reaches the gray area 
		if (health == 0 || maze[player.getRow()][player.getColumn()] == 'G' ) {

			//Stop all timers 
			animateTimer.stop();
			gameTimer.stop();

		}

		//1.1 If the score is greater than the current highscore, replace it with the current points earned 
		if (points > high) {
			high = points;
			ZombieRunGUI.highscoreLabel.setText(Integer.toString(high));

		}

		//The restart timer begins at this point
		restartTimer.start();

	}

	//Moves the zombies in a random pattern
	private void moveZombies(){

		//1. Cycle through all the zombies
		for (boardZombie z: zombie) {

			int dir=0;

			//1.1 Keep selecting random directions to avoid
			do {
				dir = (int)(Math.random()*4);
			} while (Math.abs(z.getDirection() - dir) == 2);

			//1.2 If cell is 'L', don't let the zombie in 
			if(maze[z.getRow()][z.getColumn()] == 'L')
				dir = 1;

			//1.3 Set the zombie direction
			z.setDirection(dir);

			//1.4 Allows the zombies to chase player when zombies come near it (chase AI)
			boolean wall = false;

			if (z.getRow() == player.getRow()) { //if player is in the same row

				if (z.getColumn() - player.getColumn() > 0) { //player to the left
					wall = false;

					for (int W = player.getColumn(); W < z.getColumn(); W++) {

						if (maze[z.getRow()][W] == 'W') {
							wall = true;

						}
					}

					if (wall == false)
						z.setDirection(0); //left

				} else { //player to the right

					wall = false;

					for (int W = z.getColumn(); W < player.getColumn(); W++) {

						if (maze[z.getRow()][W] == 'W') {

							wall = true;

						}
					}

					if (wall == false)
						z.setDirection(2); //right

				}
			} else if (z.getColumn() == player.getColumn()) { //if player is in the same column

				if (z.getRow() - player.getRow() > 0) { //player above

					wall = false;

					for (int W = player.getRow(); W < z.getRow(); W++) {

						if (maze[W][z.getColumn()] == 'W') {

							wall = true;

						}
					}

					if (wall == false)

						z.setDirection(1); //up

				} else { //player below

					wall = false;

					for (int W = z.getRow(); W < player.getRow(); W++) {

						if (maze[W][z.getColumn()] == 'W') {

							wall = true;

						}
					}

					if (wall == false)
						z.setDirection(3); //down

				}

			} else {

				z.setDirection(dir);

			}

			//1.5 Move the zombies
			if (levelCounter == 1) {
				performMove(z);
				
			} else if (levelCounter == 2) {
				performMove(z);
				performMove(z);
				
			} else if (levelCounter == 3) {
				performMove(z);
				performMove(z);
				performMove(z);
				
			}

		}
	}

	//Determines the source of the action as either the game timer or 
	//the animation timer and then performs the corresponding actions
	public void actionPerformed(ActionEvent e) {

		//1. If the action is the game timer
		if (e.getSource() == gameTimer) {

			//1.1. Then move the player and the zombies
			performMove(player);
			moveZombies();

			//2. Otherwise, if the action is the animation timer
		} else if (e.getSource() == animateTimer) {

			//2.1. Animate player through the current step
			animatePlayer();

			//2.2. Increment the step number
			pStep++;

			//2.3. If the step is the last step then reset the step to 0
			if (pStep == 3)
				pStep = 0;

			//3. Otherwise, if the action is the restart timer 
		} else if (e.getSource() == restartTimer) {

			//3.1 Increment the restart counter 
			restartCount++;

			//3.2 If the restart count has hit 10 then 
			if (respawnCount == 10) {

				//3.2.1 Reset the count 
				restartCount = 0;

				//3.2.2 Stop the timer 
				restartTimer.stop();

				//3.2.3 Restart the game 
				restartPlayer();

			}

			//4. Otherwise, if the action is the respawn timer 
		} else if (e.getSource() == respawnTimer) {

			//4.1 Increment the respawn counter 
			respawnCount++;

			//4.2 If the respawn count has hit 10 then 
			if (respawnCount == 10) {

				//4.2.1 Reset the count 
				respawnCount = 0;

				//4.2.2 Stop the timer 
				respawnTimer.stop();

				//4.2.3 Respawn the player 
				respawnPlayer();

			}
		} 
	}

	private void animatePlayer() {

		//If the first level is clear, load second
		if (maze[player.getRow()][player.getColumn()] == 'G' && keysCollected == 4) {

			levelCounter = 2;
			removeAll();
			loadBoard2();
			this.setVisible(true);

		}

		//If the second level is clear, load third
		if (maze[player.getRow()][player.getColumn()] == 'G' && keysCollected == 18) {

			levelCounter = 3;
			removeAll();
			bossBoard();
			this.setVisible(true);

		}

		//1. If it is step 0 of animation
		if (pStep == 0) {

			//1.1 Assign appropriate image for the player
			cell[player.getRow()][player.getColumn()].setIcon(Player.IMAGE[player.getDirection()][1]);

			//1.2 Delay the animation timer
			animateTimer.setDelay(100);

			//2. Otherwise if it is step 1 of animation
		} else if (pStep == 1)

			//2.1. Blank the current cell
			cell[player.getRow()][player.getColumn()].setIcon(BLANK);

		//3. Otherwise if it is step 2 of animation
		else if (pStep == 2) {

			//3.1. Move the player
			player.move();

			//3.2. If there is any key in the new square on the maze and the Mover is player then
			if (maze[player.getRow()][player.getColumn()] == 'K') {

				//play the sound when player picks up a key
				keySound();

				//3.2.1. Increment the score
				score++;

				//3.2.2 Increase the points by 50 if player collects a key
				if (maze[player.getRow()][player.getColumn()] == 'K')
					points += 50;

				//Update score label 
				ZombieRunGUI.scoreLabel.setText(Integer.toString(points));

				//Mark space as empty 
				maze[player.getRow()][player.getColumn()] = 'E';

				//Increment keys that are collected
				keysCollected++;

				//3.2.3 Increase by 100 points if player unlocks the lock
			} else if (maze[player.getRow()][player.getColumn()] == 'L') {
				points += 100;

				//Update score label 
				ZombieRunGUI.scoreLabel.setText(Integer.toString(points));

				//Mark space as empty 
				maze[player.getRow()][player.getColumn()] = 'E';

				//3.2.4 Decrease the score by 25 if player steps on land mine 
			} else if (maze[player.getRow()][player.getColumn()] == 'M') {
				health -= 5;

				//play sound 
				deathSound();

				//Update score label 
				ZombieRunGUI.healthLabel.setText(Integer.toString(health));

				//Mark space as empty 
				maze[player.getRow()][player.getColumn()] = 'E';

			} else if (maze[player.getRow()][player.getColumn()] == 'C') {
				
				points += 25;
				coinsCollected++;

				//play sound 
				coinSound();

				//Update score label 
				ZombieRunGUI.scoreLabel.setText(Integer.toString(points));
				
				//Update the coins label 
				ZombieRunGUI.coinsLabel.setText(Integer.toString(coinsCollected));

				//Mark space as empty 
				maze[player.getRow()][player.getColumn()] = 'E';

			}

			//3.3. Stop the animation timer
			animateTimer.stop();

			//3.4. If player is dead then show a skull
			if (player.isDead())
				cell[player.getRow()][player.getColumn()].setIcon(FLAME);

			//3.5 Otherwise show the appropriate player based on its direction
			else 
				cell[player.getRow()][player.getColumn()].setIcon(Player.IMAGE[player.getDirection()][0]);

		}
	}

	//A method for when player has lost all health
	public void respawnPlayer () {

		//When player becomes dead, get its location in terms of rows and columns 
		player.setDead(false);
		ZombieRunGUI.scoreLabel.setText(Integer.toString(points));
		player.setRow(playerStartRow);
		player.setColumn(playerStartColumn);

		cell[playerStartRow][playerStartColumn].setIcon(player.getIcon());

		//For all the zombies...
		for (int count = 0; count < 4; count++) {

			if (maze[zombie[count].getRow()][zombie[count].getColumn()] == 'K') { 
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(KEYS);

			}else if (maze[zombie[count].getRow()][zombie[count].getColumn()] == 'L') {
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(LOCK);

			}else if (maze[zombie[count].getRow()][zombie[count].getColumn()] == 'G') {
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(GRAY);

			}else if (maze[zombie[count].getRow()][zombie[count].getColumn()] == 'R') {
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(RED);

			}else if (maze[zombie[count].getRow()][zombie[count].getColumn()] == 'C') {
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(COIN);

			}else if (maze[zombie[count].getRow()][zombie[count].getColumn()] == 'M') {
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(LANDMINE);

				//Otherwise reset all the cells back to a blank cell
			} else 
				cell[zombie[count].getRow()][zombie[count].getColumn()].setIcon(BLANK);

			//Reset the zombies positions 
			zombie[count].setRow(zombieStartRow[count]);
			zombie[count].setColumn(zombieStartColumn[count]);

			cell[zombieStartRow[count]][zombieStartColumn[count]].setIcon(zombie[count].getIcon());

		}
	}

	//Method to setup the game again once Player has died once 
	public void restartPlayer() {

		//Sets the score and points back to zero 
		score = 0;
		points = 0;
		keys = 0;
		keysCollected = 0;
		ZombieRunGUI.scoreLabel.setText(Integer.toString(points));

		//Set the health back to 100
		health = 100;
		ZombieRunGUI.healthLabel.setText(Integer.toString(health));

		//Player is not dead once the game restarts
		player.setDead(false);

		removeAll();
		updateUI();

	}

	//A method for a sound to be played when player collects a key
	public void keySound() {

		//1. Checks if the sound file exists
		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/Item.wav").getAbsoluteFile());		//imports the sound file 
			Clip clip = AudioSystem.getClip();

			//1.1 Opens the clip
			clip.open(audioInputStream);	

			//1.2 Starts playing the clip
			clip.start();

			//1.3 If file is invalid
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");

		}	
	}

	//A method for a sound to be played when player unlocks the lock 
	public void unlockSound() {

		//1. Checks if the sound file exists
		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/Lock.wav").getAbsoluteFile());		//imports the sound file 
			Clip clip = AudioSystem.getClip();

			//1.1 Opens the clip
			clip.open(audioInputStream);	

			//1.2 Starts playing the clip
			clip.start();

			//1.3 If file is invalid
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");

		}	
	}

	//A method for a sound to be played when player dies/loses a life 
	public void deathSound() {

		//1. Checks if the sound file exists
		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/Death.wav").getAbsoluteFile());		//imports the sound file 
			Clip clip = AudioSystem.getClip();

			//1.1 Opens the clip
			clip.open(audioInputStream);	

			//1.2 Starts playing the clip
			clip.start();

			//1.3 If file is invalid
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");

		}	
	}

	//A method for a sound to be played when zombies move
	public void zombieSound() {

		//1. Checks if the sound file exists
		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/ZombieSound.wav").getAbsoluteFile());		//imports the sound file 
			Clip clip = AudioSystem.getClip();

			//1.1 Opens the clip
			clip.open(audioInputStream);	

			//1.2 Starts playing the clip
			clip.start();

			//1.3 If file is invalid
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");

		}	
	}

	//A method for a sound to be played when coins are collected
	public void coinSound() {

		//1. Checks if the sound file exists
		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/CoinSound.wav").getAbsoluteFile());		//imports the sound file 
			Clip clip = AudioSystem.getClip();

			//1.1 Opens the clip
			clip.open(audioInputStream);	

			//1.2 Starts playing the clip
			clip.start();

			//1.3 If file is invalid
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");

		}	
	}

	public static int getCoinsCollected() {
		return coinsCollected;
	}

	public void setCoinsCollected(int coinsCollected) {
		this.coinsCollected = coinsCollected;
	}
	
}