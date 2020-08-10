import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;
import javax.swing.Timer;
/** 
 * This class represents the rpg board and includes methods to handle mouse events and button actions 
 */
public class RPGboard extends JFrame implements MouseListener, ActionListener{
	//1. Create variables to hold objects and details needed for the game
	public static int score;
	public static int health = 100;
	private int charIndex = 0;
	private String dialogueText;
	private int animateNumber = 0;
	private int playerAnimateNumber = 0;
	private int waitAnimateCounter = 0;
	private int waitCounter = 0;
	
	String name = HomeScreen.name;
	//Variable to hold the number of zombies that spawn (between 3 and 5)
	Random rand = new Random();
	int zombieNumber = (rand.nextInt(3) + 3);
	//Variable to check if it is the a zombie's turn to attack
	boolean zombieTurn = false;
	//Variable to check if the inventoryPanel is showing or not
	boolean inventoryScreen = false;
	//Variable to determine which zombie is attacking
	int zombieAttacking = 0;
	//Variable to determine which zombie was chosen to attack
	int zombieSelected;
	//2. Allow the use of the HighScoreManager class
	HighscoreManager hm = new HighscoreManager();
	//3. Allow the use of the RPGPlayer class variables
	RPGPlayer RPGPlayer = new RPGPlayer();
	//Variables to hold player stats and to reset player stats at the end
	int playerAttack;
	int playerSpecialAttack;
	int playerHealth;
	int playerArmor;
	int playerSpeed = RPGPlayer.getPlayerSpeed();
	int basePlayerAttack = RPGPlayer.getPlayerAttack();
	int basePlayerSpecialAttack = RPGPlayer.getPlayerSpecialAttack();
	int basePlayerHealth = RPGPlayer.getPlayerHealth();
	int basePlayerArmor = RPGPlayer.getPlayerArmor();
	//Player object
	private Player player;
	//Array of Zombie objects
	private Zombie[] zombie = new Zombie[zombieNumber];
	//3.3 get the zombie stats
	int zombieHealth;
	int zombieAttack;
	//Timer for game movement
	//Lower number means it moves faster
	private Timer gameTimer = new Timer (100, this);
	//Timer for player animations
	private Timer playerAnimateTimer = new Timer (50, this);
	//Timer for player animations
	private Timer playerWalkBackTimer = new Timer (50, this);
	//Timer for zombie animations
	private Timer zombieAnimateTimer = new Timer (50, this);
	//Timer for zombieAttack animations
	private Timer zombieAttackTimer = new Timer (50, this);
	private Timer zombieWalkBackTimer = new Timer (50, this);
	//Timer for waiting for text to load
	private Timer waitTimer = new Timer (2000, this);
	//Timer for 1 letter at a time text
	private Timer wordAnimateTimer = new Timer(50, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String labelText = dialogue.getText();
			labelText += dialogueText.charAt(charIndex);
			dialogue.setText(labelText);
			charIndex++;
			if (charIndex >= dialogueText.length()) {
				((Timer)e.getSource()).stop();
			}
		}
	});
	//Create various panels to use
	JPanel boardPanel = new JPanel();
	JPanel inventoryPanel = new JPanel();
	JPanel dialogueBoxPanel = new JPanel();
	JPanel battleOptionsPanel = new JPanel();
	JPanel statsPanel = new JPanel();
	
	//Create player and zombie sprites to use
	JLabel playerSprite = new JLabel();
	JLabel zombies[] = new JLabel[5];
	//Create various labels to use
	JLabel inventoryLabel = new JLabel ("Inventory");
	JLabel inventoryHealth = new JLabel ("Health Up: " + RPGPlayer.getHealthUp() ); 
	JLabel inventoryAttack = new JLabel ("Attack Up: " + RPGPlayer.getAttackUp() );
	JLabel inventorySpecialAttack = new JLabel ("Special Attack Up: " + RPGPlayer.getSpecialAttackUp() );
	JLabel inventoryArmor = new JLabel ("Armor Up: " + RPGPlayer.getArmorUp() );
	//Create various buttons to use
	JButton inventoryHealthButton = new JButton ("Use");
	JButton inventoryAttackButton = new JButton ("Use");
	JButton inventorySpecialAttackButton = new JButton ("Use");
	JButton inventoryArmorButton = new JButton ("Use");
	JButton inventoryBackButton = new JButton("Back");
	//Create various pictures to use
	JLabel inventoryHealthIcon = new JLabel (new ImageIcon ("RPGImages/healthIcon.png"));
	JLabel inventoryAttackIcon = new JLabel (new ImageIcon ("RPGImages/attackIcon.png"));
	JLabel inventorySpecialAttackIcon = new JLabel (new ImageIcon ("RPGImages/specialAttackIcon.png"));
	JLabel inventoryArmorIcon = new JLabel (new ImageIcon ("RPGImages/armorIcon.png"));
	//Create various labels to use
	JLabel dialogue = new JLabel();
	JLabel dialogueBox = new JLabel();
	//Create various labels to use
	JLabel scoreTitle = new JLabel("SCORE:");
	JLabel scoreLabel = new JLabel(Integer.toString(RPGPlayer.getPlayerScore()));
	JLabel healthTitle = new JLabel("HEALTH:");
	JLabel healthLabel = new JLabel(Integer.toString(RPGPlayer.getPlayerHealth()));
	JLabel highscoreTitle = new JLabel("HIGHSCORE:");
	JLabel highscoreLabel = new JLabel("0");
	JLabel coinsTitle = new JLabel ("COINS:");
	JLabel coinsLabel = new JLabel (Integer.toString(RPGPlayer.getCoins()));
	//Create various buttons to use
	JButton basicAttack = new JButton("Basic Attack");
	JButton specialAttack = new JButton("Special Attack");
	JButton inventory = new JButton("Inventory");
	JButton flee = new JButton("Flee");
	//1. Create player and zombie objects 
	public RPGboard() {
		//1.2 Create new player object
		player = new Player();
		//1.3 Create new zombie objects
		for (int x = 0; x < zombieNumber; x++) {
			zombie[x] = new Zombie(x);
			zombie[x].setZombieHealth(100);
			
			zombieSound();
		}
		
		//1.4 Add the boardPanel, dialogueBoxPanel, battleOptionsPanel and frameSetup
		boardPanel();
		inventoryPanel();
		dialogueBoxPanel();
		battleOptionsPanel();
		statsPanel();
		frameSetup();
	}
	//1. Setup the frame
	private void frameSetup() {
		//1.1 Close the GUI if the program ends
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		//1.2 Frame Specifications
		this.setLayout(null);
		this.setTitle("RPG Board");
		this.setSize(1200, 950);
		this.setVisible(true);
		this.setResizable(false);
		this.add(boardPanel);
		this.add(inventoryPanel);
		this.add(dialogueBoxPanel);
		this.add(battleOptionsPanel);
		this.add(statsPanel);
	}
	//1. Setup the board panel
	private void boardPanel() {
		//1.1 Add the board panel specifications	
		boardPanel.setBounds(0, 0, 1200, 550);
		boardPanel.setBackground(Color.BLACK);
		boardPanel.setLayout(null);
		//1.2 Add the playerSprite
		playerSprite = new JLabel(new ImageIcon("RPGImages/player.png"));
		playerSprite.setBounds(20, 20, 500, 500);
		playerSprite.setVisible(true);
		boardPanel.add(playerSprite);
		//1.3 Add the zombieSprites
		for (int x = 0; x < zombieNumber; x++){
			zombies[x] = new JLabel(new ImageIcon("RPGImages/walk1.png"));
			if (x < 3) {
				zombies[x].setBounds(1050 - 100*x, 0 + 70*x, 109, 200);
				zombies[x].setVisible(true);
				zombies[x].addMouseListener(this);
				boardPanel.add(zombies[x]);
			}else if (x >= 3){
				zombies[x].setBounds(650 + 100*x, 0 + 85*x, 109, 200);
				zombies[x].setVisible(true);
				zombies[x].addMouseListener(this);
				boardPanel.add(zombies[x]);
			}
		}
		//Allow all objects to be seen
		repaint();
	}
	//1. Setup the inventory panel
	private void inventoryPanel() {
		//1.1 Add the inventory panel specifications	
		inventoryPanel.setBounds(0, 0, 1200, 550);
		inventoryPanel.setBackground(new Color (245,222,179));
		inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));		
		inventoryPanel.setLayout(null);
		inventoryPanel.setVisible(false);
		//Adds all of the upgrades onto the panel 
		//Title of the main panel 
		inventoryLabel.setBounds(500, 5, 200, 60);
		inventoryLabel.setForeground(Color.BLACK);
		inventoryLabel.setFont(new Font ("Impact" , Font.BOLD, 35));
		inventoryPanel.add(inventoryLabel);
		//Adds the health upgrade label and picture 
		inventoryHealth.setBounds(235, 90, 200, 30);
		inventoryHealth.setForeground(Color.BLACK);
		inventoryHealth.setFont(new Font ("Impact" , Font.BOLD, 30));
		inventoryPanel.add(inventoryHealth);
		//Adds health upgrade description 
		//		inventoryHealthMessage.setBounds(20, 110, 325, 20);
		//		inventoryHealthMessage.setForeground(Color.BLACK);
		//		inventoryHealthMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		//		inventoryPanel.add(inventoryHealthMessage);
		//Adds the health upgrade picture 
		inventoryHealthIcon.setBounds(615, 60, 100, 100);
		inventoryPanel.add(inventoryHealthIcon);
		//Adds health upgrade purchase button
		inventoryHealthButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		inventoryHealthButton.setBounds(765, 80, 150, 60);
		inventoryHealthButton.setBackground(Color.RED);
		inventoryHealthButton.addActionListener(this);
		inventoryPanel.add(inventoryHealthButton);
		//Adds the attack upgrade label and picture 
		inventoryAttack.setBounds(235, 200, 200, 30);
		inventoryAttack.setForeground(Color.BLACK);
		inventoryAttack.setFont(new Font ("Impact" , Font.BOLD, 30));
		inventoryPanel.add(inventoryAttack);
		//Adds attack upgrade description 
		//		inventoryAttackMessage.setBounds(20, 220, 325, 20);
		//		inventoryAttackMessage.setForeground(new Color (112,128,144));
		//		inventoryAttackMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		//		inventoryPanel.add(inventoryAttackMessage);
		//Adds the attack upgrade picture 
		inventoryAttackIcon.setBounds(615, 160, 100, 100);
		inventoryPanel.add(inventoryAttackIcon);
		//Adds attack upgrade purchase button
		inventoryAttackButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		inventoryAttackButton.setBounds(765, 180, 150, 60);
		inventoryAttackButton.setBackground(Color.RED);
		inventoryAttackButton.addActionListener(this);
		inventoryPanel.add(inventoryAttackButton);
		//Adds the special attack upgrade label and picture 
		inventorySpecialAttack.setBounds(235, 310, 300, 30);
		inventorySpecialAttack.setForeground(Color.BLACK);
		inventorySpecialAttack.setFont(new Font ("Impact" , Font.BOLD, 30));
		inventoryPanel.add(inventorySpecialAttack);
		//Adds special attack upgrade description 
		//		inventorySpecialAttackMessage.setBounds(20, 330, 325, 20);
		//		inventorySpecialAttackMessage.setForeground(new Color (112,128,144));
		//		inventorySpecialAttackMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		//		inventoryPanel.add(inventorySpecialAttackMessage);
		//Adds the special attack upgrade picture 
		inventorySpecialAttackIcon.setBounds(615, 260, 100, 100);
		inventoryPanel.add(inventorySpecialAttackIcon);
		//Adds special attack upgrade purchase button
		inventorySpecialAttackButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		inventorySpecialAttackButton.setBounds(765, 280, 150, 60);
		inventorySpecialAttackButton.setBackground(Color.RED);
		inventorySpecialAttackButton.addActionListener(this);
		inventoryPanel.add(inventorySpecialAttackButton);
		//Adds the armor upgrade label and picture 
		inventoryArmor.setBounds(235, 420, 200, 30);
		inventoryArmor.setForeground(Color.BLACK);
		inventoryArmor.setFont(new Font ("Impact" , Font.BOLD, 30));
		inventoryPanel.add(inventoryArmor);
		//Adds armor upgrade description 
		//		inventoryArmorMessage.setBounds(20, 440, 325, 20);
		//		inventoryArmorMessage.setForeground(new Color (112,128,144));
		//		inventoryArmorMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		//		inventoryPanel.add(inventoryArmorMessage);
		//Adds the armor upgrade picture 
		inventoryArmorIcon.setBounds(615, 380, 100, 100);
		inventoryPanel.add(inventoryArmorIcon);
		//Adds armor purchase button
		inventoryArmorButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		inventoryArmorButton.setBounds(765, 390, 150, 60);
		inventoryArmorButton.setBackground(Color.RED);
		inventoryArmorButton.addActionListener(this);
		inventoryPanel.add(inventoryArmorButton);
		//Adds armor purchase button
		inventoryBackButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		inventoryBackButton.setBounds(965, 390, 150, 60);
		inventoryBackButton.setBackground(Color.RED);
		inventoryBackButton.addActionListener(this);
		inventoryPanel.add(inventoryBackButton);
		//Makes all objects visible on the panel 
		repaint();
	}
	//1. Setup the dialogue box panel
	private void dialogueBoxPanel() {
		//1.1 Add the dialogue box panel specifications
		dialogueBoxPanel.setBounds(0, 550, 700, 200);
		dialogueBoxPanel.setBackground(Color.GRAY);
		dialogueBoxPanel.setLayout(null);
		//1.2 Add the objects needed on the panel (JLabels)
		//dialogueBox = new JLabel (new ImageIcon("RPGImages/dialogueBox.png"));
		//dialogueBox.setBounds(0, 0, 575, 140);
		//EDIT THE BOX IN PHOTOSHOP TO MAKE IT 200 PIXELS DOWN INSTEAD OF 140
		//dialogueBoxPanel.add(dialogueBox);
		//1.3 Add the dialogue label specifications
		dialogue.setBounds(20, -20, 600, 150);
		dialogue.setForeground(Color.WHITE);
		dialogue.setFont(new Font("Impact", Font.PLAIN, 20));
		dialogueBoxPanel.add(dialogue);
		selectAZombie();
	}
	//1. Setup the dialogue box panel
	private void battleOptionsPanel() {
		//1.1 Add the battle options panel specifications
		battleOptionsPanel.setBounds(700, 550, 500, 200);
		battleOptionsPanel.setBackground(Color.gray);
		battleOptionsPanel.setLayout(null);
		//1.2 Add the objects needed on the panel (JButtons)
		battleOptionsPanel.add(basicAttack);
		battleOptionsPanel.add(specialAttack);
		battleOptionsPanel.add(inventory);
		battleOptionsPanel.add(flee);
		//1.3 Add the basic attack button's specifications
		basicAttack.setVisible(true);
		basicAttack.setBounds(50, 40, 150, 50);
		basicAttack.setBackground(Color.lightGray);
		basicAttack.addActionListener(this);
		//1.4 Add the special attack button's specifications
		specialAttack.setVisible(true);
		specialAttack.setBounds(50, 120, 150, 50);
		specialAttack.setBackground(Color.lightGray);
		specialAttack.addActionListener(this);
		//1.5 Add the inventory button's specifications
		inventory.setVisible(true);
		inventory.setBounds(300, 40, 150, 50);
		inventory.setBackground(Color.lightGray);
		inventory.addActionListener(this);
		//1.6 Add the flee button's specifications
		flee.setVisible(true);
		flee.setBounds(300, 120, 150, 50);
		flee.setBackground(Color.lightGray);
		flee.addActionListener(this);
	}
	// Setup the stats panel
	private void statsPanel() {
		
		//2.1 Create the score panel (displays the user’s score)
		statsPanel.setBounds(0, 750, 1200, 200);
		statsPanel.setBackground(Color.BLACK);
		statsPanel.setLayout(null);
		//2.2 Add all the objects onto the panel
		statsPanel.add(scoreTitle);
		statsPanel.add(scoreLabel);
		statsPanel.add(healthTitle);
		statsPanel.add(healthLabel);
		statsPanel.add(highscoreTitle);
		statsPanel.add(highscoreLabel);
		statsPanel.add(coinsTitle);
		statsPanel.add(coinsLabel);
		//2.3 Creates scoreTitle
		scoreTitle.setBounds(20, 20, 200, 50);
		scoreTitle.setForeground(Color.RED);
		scoreTitle.setHorizontalAlignment(JLabel.CENTER);
		scoreTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		scoreTitle.setVisible(true);
		//2.4 Creates scoreLabel
		scoreLabel.setBounds(20, 70, 180, 50);
		scoreLabel.setForeground(Color.RED);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		scoreLabel.setVisible(true);
		//2.5 Create healthTitle
		healthTitle.setBounds(300, 20, 200, 50);
		healthTitle.setForeground(Color.RED);
		healthTitle.setHorizontalAlignment(JLabel.CENTER);
		healthTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		healthTitle.setVisible(true);
		//2.6 Create healthLabel
		healthLabel.setBounds(300, 70, 180, 50);
		healthLabel.setForeground(Color.RED);
		healthLabel.setHorizontalAlignment(JLabel.CENTER);
		healthLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		healthLabel.setVisible(true);
		//2.7 Create highscoreTitle
		highscoreTitle.setBounds(600, 20, 300, 50);
		highscoreTitle.setForeground(Color.RED);
		highscoreTitle.setHorizontalAlignment(JLabel.CENTER);
		highscoreTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		highscoreTitle.setVisible(true);
		//2.8 Create highscoreLabel
		highscoreLabel.setBounds(600, 70, 280, 50);
		highscoreLabel.setForeground(Color.RED);
		highscoreLabel.setHorizontalAlignment(JLabel.CENTER);
		highscoreLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		highscoreLabel.setVisible(true);
		//2.9 Create coinsTitle
		coinsTitle.setBounds(900, 20, 200, 50);
		coinsTitle.setForeground(Color.RED);
		coinsTitle.setHorizontalAlignment(JLabel.CENTER);
		coinsTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		//2.10 Create coinsLabel
		coinsLabel.setBounds(900, 70, 180, 50);
		coinsLabel.setForeground(Color.RED);
		coinsLabel.setHorizontalAlignment(JLabel.CENTER);
		coinsLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
	}
	//Get button actionListeners
	public void actionPerformed(ActionEvent e) {      
		//If the actionListener came from this timer, run the appropriate method
		if (e.getSource() == playerAnimateTimer) {
			animatePlayer();
		}
		//If the actionListener came from this timer, run the appropriate method
		if (e.getSource() == playerWalkBackTimer) {
			playerWalkBack();
		}
		//If the actionListener came from this timer, run the appropriate method
		if (e.getSource() == zombieAnimateTimer) {
			animateZombie();
		}
		//If the actionListener came from this timer, run the appropriate method
		if (e.getSource() == zombieAttackTimer) {
			animateZombieAttack();
		}
		//If the actionListener came from this timer, run the appropriate method
		if (e.getSource() == waitTimer) {
			waitCounter++;
		}
		//If the actionListener came from this timer, run the appropriate method
		if (e.getSource() == zombieWalkBackTimer) {
			animateZombieWalkBack();
		}
		//1. if zombieTurn = false
		if (zombieTurn == false) {
			//1.1 If the action comes from basicAttackButton and inventoryScreen is false
			if (e.getSource() == basicAttack && inventoryScreen == false) {
				//1.1.1 Run the basicAttack method
				basicAttack();
			}
			//1.2 If the action comes from specialAttackButton and inventoryScreen is false
			if (e.getSource() == specialAttack && inventoryScreen == false) {
				//1.2.1 Run the specialAttack method
				specialAttack();
			}
 
			//1.4 If the action comes from inventoryButton and inventoryScreen is false
			if (e.getSource() == inventory && inventoryScreen == false) {
				//1.4.1 Run the inventory method
				inventory();
			}
			//1.5 If the action comes from fleeButton and inventoryScreen is false
			if (e.getSource() == flee && inventoryScreen == false) {
				//1.5.1 Run the flee method
				flee();
			}
			
			//1.6 If the action comes from the inventoryBackButton
			if (e.getSource() == inventoryBackButton) {
				//1.6.1 Make the boardPanel visible
				boardPanel.setVisible(true);
				
				//1.6.2 Make the inventoryPanel invisible
				inventoryPanel.setVisible(false);
				
				//1.6.3 Make the inventoryScreen variable false
				inventoryScreen = false;
				
				//1.6.4 Run the selectAZombie method
				selectAZombie();
			}
			//2.1 If the action comes from attackButton and inventoryScreen is true
			if (e.getSource() == basicAttack && inventoryScreen == true) {
				//2.1.1 Run the notHere method
				notHere();
			}
			//2.2 If the action comes from specialAttackButton and inventoryScreen is true
			if (e.getSource() == specialAttack && inventoryScreen == true) {
				//2.2.1 Run the notHere method
				notHere();
			}
			//2.3 If the action comes from inventoryButton and inventoryScreen is true
			if (e.getSource() == inventory && inventoryScreen == true)	{
				//2.3.1 Run the notHere method
				notHere();
			}
			//2.4 If the action comes from fleeButton and inventoryScreen is true
			if (e.getSource() == flee && inventoryScreen == true) {
				//2.4.1 Run the notHere method
				notHere();
			}
			//2.5 If the action comes from inventoryHealthButton
			if (e.getSource() == inventoryHealthButton) {
				//2.5.1 Check if there is healthUp, if there is
				if (RPGPlayer.getHealthUp() > 0) {
					//2.5.1.1 Reduce healthUp by 1
					RPGPlayer.setHealthUp(RPGPlayer.getHealthUp() - 1);
					
					//2.5.1.2 Update the healthUp label
					inventoryHealth.setText("Health Up: " + RPGPlayer.getHealthUp());
					
					//2.5.1.3 Update playerHealth
					RPGPlayer.setPlayerHealth(RPGPlayer.getPlayerHealth() + 30);
					
					//2.5.1.4 Update the stats panel health label
					healthLabel.setText(Integer.toString(RPGPlayer.getPlayerHealth()));
					
					//Sound effect
					itemSound();
					
				//2.5.2 If there is no healthUp
				} else if (RPGPlayer.getHealthUp() <= 0) {
					
					//2.5.2.1 Reset the text in the dialogueBox
					dialogue.setText("");
					//2.5.2.2 Print an error message
					dialogueText = "You do not have enough Health Up";
					//2.5.2.3 Restart the char index
					charIndex = 0;
					//2.5.2.4 Start the timer
					wordAnimateTimer.start();
				}
			}
			
			//2.6 If the action comes from inventoryHealthButton
			if (e.getSource() == inventoryAttackButton) {
				//2.6.1 Check if there is attackUp, if there is
				if (RPGPlayer.getAttackUp() > 0) {
					//2.6.1.1 Reduce attackUp by 1
					RPGPlayer.setAttackUp(RPGPlayer.getAttackUp() - 1);
					
					//2.6.1.2 Update the attackUp label
					inventoryAttack.setText("Attack Up: " + RPGPlayer.getAttackUp());
					
					//2.6.1.3 Update playerAttack
					RPGPlayer.setPlayerAttack(RPGPlayer.getPlayerAttack() + 15);
					
					//Sound effect
					itemSound();
			
				//2.6.2 If there is no attackUp
				} else if (RPGPlayer.getAttackUp() <= 0) {
					
					//2.6.2.1 Reset the text in the dialogueBox
					dialogue.setText("");
					//2.6.2.2 Print an error message
					dialogueText = "You do not have enough Attack Up";
					//2.6.2.3 Restart the char index
					charIndex = 0;
					//2.6.2.4 Start the timer
					wordAnimateTimer.start();
					
					
				}
			}
			//2.7 If the action comes from inventoryHealthButton
			if (e.getSource() == inventorySpecialAttackButton) {
				//2.7.1 Check if there is specialAttackUp, if there is
				if (RPGPlayer.getPlayerSpecialAttack() > 0) {
					//2.7.1.1 Reduce specialAttackUp by 1
					RPGPlayer.setSpecialAttackUp(RPGPlayer.getSpecialAttackUp() - 1);
					
					//2.7.1.2 Update the specialAttackUp label
					inventorySpecialAttack.setText("Special Attack Up: " + RPGPlayer.getSpecialAttackUp());
					
					//2.7.1.3 Update playerSpecialAttack
					RPGPlayer.setPlayerSpecialAttack(RPGPlayer.getPlayerSpecialAttack() + 15);
					
					//Sound effect
					itemSound();
					
				//2.7.2 If there is no specialAttackUp
				} else if (RPGPlayer.getSpecialAttackUp() <= 0) {
					
					//2.7.2.1 Reset the text in the dialogueBox
					dialogue.setText("");
					//2.7.2.2 Print an error message
					dialogueText = "You do not have enough Special Attack Up";
					//2.7.2.3 Restart the char index
					charIndex = 0;
					//2.7.2.4 Start the timer
					wordAnimateTimer.start();
				}
			}
			//2.8 If the action comes from inventoryHealthButton
			if (e.getSource() == inventoryArmorButton) {
				//2.8.1 Check if there is armorUp, if there is
				if (RPGPlayer.getArmorUp() > 0) {
					//2.8.1.1 Reduce armorUp by 1
					RPGPlayer.setArmorUp(RPGPlayer.getArmorUp() - 1);
					
					//2.8.1.2 Update the armorUp label
					inventoryArmor.setText("Armor Up: " + RPGPlayer.getArmorUp());
					
					//2.8.1.3 Update playerArmor
					RPGPlayer.setPlayerArmor(RPGPlayer.getPlayerArmor() + 30);
					
					//Sound effect
					itemSound();
					
				//2.8.2 If there is no armorUp
				}else if (RPGPlayer.getArmorUp() <= 0) {
					
					//2.8.2.1 Reset the text in the dialogueBox
					dialogue.setText("");
					//2.8.2.2 Print an error message
					dialogueText = "You do not have enough Armor Up";
					//2.8.2.3 Restart the char index
					charIndex = 0;
					//2.8.2.4 Start the timer
					wordAnimateTimer.start();
				}
			}
		}
		
		//3. if zombieTurn = true
		else if (zombieTurn == true){
			//3.1 If the action comes from basicAttackButton
			if (e.getSource() == basicAttack) {
				//3.1.1 Run the notYourTurn method
				notYourTurn();
			}
			//3.2 If the action comes from specialAttackButton
			if (e.getSource() == specialAttack) {
				//3.2.1 Run the notYourTurn method
				notYourTurn();
			}
			//3.3 If the action comes from inventoryButton
			if (e.getSource() == inventory)	{
				//3.3.1 Run the notYourTurn method
				notYourTurn();
			}
			//3.4 If the action comes from fleeButton
			if (e.getSource() == flee) {
				//3.4.1 Run the notYourTurn method
				notYourTurn();
			}
		}
	}	
	
	private void getPlayerStats() {
		//3. Get the player stats
		playerAttack = RPGPlayer.getPlayerAttack();
		playerSpecialAttack = RPGPlayer.getPlayerSpecialAttack();
		playerHealth = RPGPlayer.getPlayerHealth();
		playerArmor = RPGPlayer.getPlayerArmor();
		playerSpeed = RPGPlayer.getPlayerSpeed();
	}
	private void basicAttack() {
		//Get playerStats
		getPlayerStats();
		
		//Check if the zombieSelected has health, if not, increment zombieSelected
		for (int x = 0; x < zombieNumber; x++){
			if (zombie[zombieSelected].getZombieHealth() < 0) {
				zombieSelected = x;
			}
		}
		
		
		//Hold how much damage the player does in a variable
		int damageDone = zombie[zombieSelected].getZombieHealth() - (zombie[zombieSelected].getZombieHealth() - playerAttack);
		//2. Ask the user to select a zombie			
		//2.1 Clear text in the dialogue box
		//waitTimer.start();
		//if (waitCounter <= 3) {
		//			dialogue.setText("");
		//
		//			//2.2 Print "It is not your turn" in the dialogue box
		//			dialogueText = ("Please select a zombie");
		//
		//			//2.3 Restart the char index
		//			charIndex = 0;
		//
		//			//2.4 Start the timer
		//			wordAnimateTimer.start();	
		//
		//			waitTimer.start();
		//
		
		
		
		//3. Start the animatePlayer timer
		//playerAnimateTimer.start();
		//4. print dialogue in the dialogue box
		//4.1 Clear the text in the dialogue box
		dialogue.setText("");
		//4.2 Print the damage done to the selected zombie	
		dialogueText = "<html>" + "You did " + damageDone + " damage to zombie " + (zombieSelected + 1) + ", zombie " + (zombieSelected + 1) + " now has " + (zombie[zombieSelected].getZombieHealth() - damageDone) + " health. ";
		//4.3 Restart the char index
		charIndex = 0;
		//4.4 Start the timer
		wordAnimateTimer.start();
		//wordAnimateTimer.setInitialDelay(500);
		//} else if (waitCounter > 3) {
		
		
		
		//5. Reinstate zombieHealth
		zombie[zombieSelected].setZombieHealth(zombie[zombieSelected].getZombieHealth() - damageDone);
		//6. make the variable zombieTurn = true
		zombieTurn = true;
		//6.1 run the zombieAttack method
		zombieAttack();
		
		
		//}
	}
	private void specialAttack() {
		
		//Create a 50/50 chance variable to see if specialAttack will hit or not
		int hitChance = (rand.nextInt(2) + 1);
		//If hitChance = 1
		if (hitChance == 1) {
		
		//Get the player stats
		getPlayerStats();
		
		//Check if the zombieSelected has health, if not, increment zombieSelected
		for (int x = 0; x < zombieNumber; x++){
			if (zombie[zombieSelected].getZombieHealth() < 0) {
				zombieSelected = x;
			}
		}
		
		//Hold how much damage the player does in a variable
		int damageDone = zombie[zombieSelected].getZombieHealth() - (zombie[zombieSelected].getZombieHealth() - playerSpecialAttack);
		//2. Ask the user to select a zombie			
		//2.1 Clear text in the dialogue box
//		dialogue.setText("");
//
//		//2.2 Print "It is not your turn" in the dialogue box
//		dialogueText = ("Please select a zombie");
//
//		//2.3 Restart the char index
//		charIndex = 0;
//
//		//2.4 Start the timer
//		wordAnimateTimer.start();	
		
		
		//3. run the animatePlayer method
		//playerAnimateTimer.start();
		
		
		//4. print dialogue in the dialogue box
		//4.1 Clear the text in the dialogue box
		dialogue.setText("");
		//4.2 Print the damage done to the selected zombie	
		dialogueText = "<html>" + "You did " + damageDone + " damage to zombie " + (zombieSelected + 1) + ", zombie " + (zombieSelected + 1) + " now has " + (zombie[zombieSelected].getZombieHealth() - damageDone) + " health. ";
		//4.3 Restart the char index
		charIndex = 0;
		//4.4 Start the timer
		wordAnimateTimer.start();
		//5. Reinstate zombieHealth
		zombie[zombieSelected].setZombieHealth(zombie[zombieSelected].getZombieHealth() - damageDone);
		//6. make the zombieTurn variable true
		zombieTurn = true;
		//7. run the zombieAttack method
		zombieAttack();
		} 
		
		//If hitChance = 2
		else if (hitChance == 2) 
			//Run the specialDepleted method
			specialDepleted();
		
	}
	
	private void specialDepleted() {
		//1. Clear text in the dialogue box
		dialogue.setText("");
		//2 Print error message in the dialogue box
		dialogueText = ("<html>" + "Your special attack missed. ");
		//2.1 Restart the char index
		charIndex = 0;
		//2.2 Start the timer
		wordAnimateTimer.start();
		
		//6. make the zombieTurn variable true
		zombieTurn = true;
		//7. run the zombieAttack method
		zombieAttack();
				
				
	}
	private void selectAZombie() {
		//1. Clear text in the dialogue box
		dialogue.setText("");
		//2 Print message to ask for a mouseListener
		dialogueText = "Select a zombie to attack";
		//2.1 Restart the char index
		charIndex = 0;
		//2.2 Start the timer
		wordAnimateTimer.start();
	}
	private void inventory() {
		//1. Hide the board JPanel
		boardPanel.setVisible(false);
		//2. Make the inventory JPanel appear
		inventoryPanel.setVisible(true);
		//3. Make inventoryScreen true
		inventoryScreen = true;
	}
	private void flee() {
		//1. Create a random number 1 to 5 to see if the player can flee
		int fleeChance = (rand.nextInt(5) + 1);
		//2. If the number = 1 
		if (fleeChance == 1){
			
			//2.1 Reset variables
			zombieAttacking = 0;
			zombieNumber = 0;
			zombieTurn = false;
			charIndex = 0;
			RPGPlayer.setPlayerHealth(basePlayerHealth);
			RPGPlayer.setPlayerAttack(basePlayerAttack);
			RPGPlayer.setPlayerSpecialAttack(basePlayerSpecialAttack);
			RPGPlayer.setPlayerArmor(basePlayerArmor);
			
			//2.2 close the window
			this.dispose();
			
			//2.3 open the board class
			new Board();
			
		} 
		//3. if the number is 2 to 5
		else {
			//3.1 make the zombieTurn variable true
			zombieTurn = true;
			//3.2 run the zombieTurn method
			zombieAttack();
		}
	}
	private void notYourTurn() {
		//1. Clear text in the dialogue box
		dialogue.setText("");
		//2 Print "It is not your turn" in the dialogue box
		dialogueText = ("It is not your turn");
		//2.1 Restart the char index
		charIndex = 0;
		//2.2 Start the timer
		wordAnimateTimer.start();
	}
	private void ItsYourTurn() {
		//1. Clear text in the dialogue box
		dialogue.setText("");
		//2 Print "It is now your turn" in the dialogue box
		dialogueText = ("It is now your turn");
		//2.1 Restart the char index
		charIndex = 0;
		//2.2 Start the timer
		wordAnimateTimer.start();
	}
	private void notHere() {
		//1. Clear text in the dialogue box
		dialogue.setText("");
		//2 Print "It is not your turn" in the dialogue box
		dialogueText = ("You can't attack or flee here");
		//2.1 Restart the char index
		charIndex = 0;
		//2.2 Start the timer
		wordAnimateTimer.start();
	}
	private void zombieAttack() {
		//Get player stats
		getPlayerStats();
		//Hold how much damage the player does in a variable
		int zombieDamage = (zombie[zombieAttacking].getZombieAttack() * 100/(100 + playerArmor));
		//testing and checking print statements
		//		System.out.println(zombieAttacking);
		//		System.out.println(zombieNumber);
		//2. check if zombie[zombieAttacking] is alive
		//2.1 if the zombie is alive
		if (zombie[zombieAttacking].getZombieHealth() > 0) {
			//2.1.1 run the animateZombie method
			zombieAnimateTimer.start();
			//2.1.2 update player health
			RPGPlayer.setPlayerHealth(playerHealth - zombieDamage);
			//2.1.3 Clear the text in the dialogue box
			dialogue.setText("");
			//2.1.4 Print how much damage the zombie did to the player
			dialogueText += "Zombie  " + (zombieAttacking + 1) + " did  " + zombieDamage + " damage to you, you now have " + RPGPlayer.getPlayerHealth() + " health. Select a zombie to attack.";
			//2.1.5 Restart the char index
			charIndex = 0;
			//2.1.6 Start the timer
			wordAnimateTimer.start();		
			//2.1.7Update the health label in the stats panel
			healthLabel.setText(Integer.toString(RPGPlayer.getPlayerHealth()));
			//2.1.8 If playerHealth is less than 0
			if (RPGPlayer.getPlayerHealth() <= 0)
				//2.1.8.1 run the death method
				death();
			//3. Set the zombieTurn variable to false
			zombieTurn = false;
			//4. Run the ItsYourTurn method
			if (charIndex >= dialogueText.length())
				ItsYourTurn();
		}
		//2.2 if the zombie is dead 
		//2.2.1 increment zombie number
		else if (zombie[zombieAttacking].getZombieHealth() <= 0) {
			//Increment zombieAttacking
			zombieAttacking++;
			//Recurse the method
			zombieAttack();
		} 
		//If all zombies are dead
		if (zombieAttacking - 1 == zombieNumber)
			//Run the win method
			win();
	}
	private void win() {
		//1. Reset variables such as zombieTurn and zombieNumber
		
		
		//		for (int x = 0; x <= zombieNumber; x++)
		//			zombie[x].setZombieHealth(100);
		zombieAttacking = 0;
		zombieNumber = 0;
		zombieTurn = false;
		charIndex = 0;
		RPGPlayer.setPlayerHealth(basePlayerHealth);
		RPGPlayer.setPlayerAttack(basePlayerAttack);
		RPGPlayer.setPlayerSpecialAttack(basePlayerSpecialAttack);
		RPGPlayer.setPlayerArmor(basePlayerArmor);
		//Give the player some coins (random number between 5 and 10) and update the coins label
		int giveCoins = (rand.nextInt(5) + 5);
		RPGPlayer.setCoins(RPGPlayer.getCoins() + giveCoins);
		coinsLabel.setText(Integer.toString(RPGPlayer.getCoins()));		
		
		//Give the player 100 points and update the score label
		RPGPlayer.setPlayerScore(RPGPlayer.getPlayerScore() + 100 * zombieNumber);
		scoreLabel.setText(Integer.toString(RPGPlayer.getPlayerScore()));
		
		//2. close the current window
		this.dispose();
		//3. open board window
		new Board();
	}
	private void death() {
		
		
		//Play sound effect
		deathSound();
		
		
		
		//1. Reset variables such as zombieTurn and zombieNumber
		
		
		//		for (int x = 0; x <= zombieNumber; x++)
		//			zombie[x].setZombieHealth(100);
		zombieAttacking = 0;
		zombieNumber = 0;
		zombieTurn = false;
		charIndex = 0;
		
		RPGPlayer.setPlayerHealth(basePlayerHealth);
		RPGPlayer.setPlayerAttack(basePlayerAttack);
		RPGPlayer.setPlayerSpecialAttack(basePlayerSpecialAttack);
		RPGPlayer.setPlayerArmor(basePlayerArmor);
		
		//2. Give the name and score to the highScoreManager class
		hm.addScore(name, RPGPlayer.getPlayerScore());
		//3. close the current window
		this.dispose();
		//4. open the homescreen window
		new HomeScreen();
	}
	private void animatePlayer() {
		//1. Make the player move right in front of the zombie
		//1.1 change the position of the JLabel holding the player sprite by raising its x value and making the y value constant
		playerSprite.setLocation((playerSprite.getX() + 20), playerSprite.getY());
		//2. If the player's x value is greater than a certain point, stop the timer moving it
		if (playerSprite.getX() > 600) {
			playerAnimateTimer.stop();
			playerWalkBackTimer.start();
		}
	}
	
	private void playerWalkBack() {
		//1. Make the player move back into its base position
		//1.1 change the position of the JLabel holding the player sprite by lowering its x value and making the y value constant
		playerSprite.setLocation((playerSprite.getX() + 20), playerSprite.getY());
		//2. If the player's x value is less than its starting point, stop the timer moving it
		if (playerSprite.getX() < 300) {
			playerWalkBackTimer.stop();
		}
	}
	private void animateZombie() {
		//1. make the zombie move left
		//1.1 change the position of the JLabel holding the zombie sprite by lowering its x value and making the y value constant
		zombies[zombieAttacking].setLocation((zombies[zombieAttacking].getX() - 20), zombies[zombieAttacking].getY());
		//2. Increment the animation phase of the zombie
		animateNumber++;
		//3. If the animation reaches the end, reset it 
		if (animateNumber == 6)
			animateNumber = 0;
		//4. Set the zombie's image to the different animation pictures
		zombies[zombieAttacking].setIcon
		(zombie[zombieAttacking].IMAGE[1][animateNumber]);
		//5. check if the zombie is in front of the player
		//5.1 Stop the zombie from moving and start it's attack animation
		if (zombies[zombieAttacking].getX() < 400) {
			zombieAnimateTimer.stop();
			zombieAttackTimer.start();
			animateNumber = 0;
		}
		
	}
	private void animateZombieAttack() {
		//1. Increment the animation phase of the zombie
		animateNumber++;
		//2. If the animation reaches the end, reset it 
		if (animateNumber == 6)
			animateNumber = 0;
		//3. Set the zombie's image to the different animation pictures
		zombies[zombieAttacking].setIcon
		(zombie[zombieAttacking].IMAGE[0][animateNumber]);
		//4. Start a timer used for counting
		waitTimer.start();
		//5. Once the counter reaches a certain number
		//5.1 Stop attacking
		//5.2 Make the zombie move back
		//5.3 Stop the timer used for counting
		//5.4 Reset the counter
		if (waitCounter == 2) {
			zombieAttackTimer.stop();
			animateNumber = 6;
			zombieWalkBackTimer.start();
			waitTimer.stop();
			waitCounter = 0;
		}
	}
	private void animateZombieWalkBack() {
		//1. make the zombie move left
		//1.1 change the position of the JLabel holding the zombie sprite by raising its x value and making the y value constant
		zombies[zombieAttacking].setLocation((zombies[zombieAttacking].getX() + 20), zombies[zombieAttacking].getY());
		//2. Decrement the animation phase of the zombie
		animateNumber--;
		//3. If the animation reaches the end, reset it 
		if (animateNumber == 1)
			animateNumber = 5;
		//4. Set the zombie's image to the different animation pictures
		zombies[zombieAttacking].setIcon
		(zombie[zombieAttacking].IMAGE[1][animateNumber]);
		//5. check if the zombie is back in its original location
		//5.1 Stop the zombie from moving
		if (zombies[zombieAttacking].getX() > 1050 - 100 * zombieAttacking) {
			zombieWalkBackTimer.stop();
			animateNumber = 0;
		}
	}
	public void mouseClicked(MouseEvent e) {			
		//Determine which zombie was selected
		for (int x = 0; x < zombieNumber; x++){
			//Once a zombie image is clicked on and ensure that is has health
			if (e.getSource() == zombies[x] && zombie[x].getZombieHealth() > 0){
				//Declare that zombie the zombieSelected
				zombieSelected = x;
				//1. Clear text in the dialogue box
				dialogue.setText("");
				//2 Print which zombie was selected in the dialogue box
				dialogueText = ("Zombie " + (zombieSelected + 1) + " was selected");
				//2.1 Restart the char index
				charIndex = 0;
				//2.2 Start the timer
				wordAnimateTimer.start();
				
			} 
			
			//If a zombie with less than 0 health is selected, ask the user to click a different zombie
			else if (e.getSource() == zombies[x] && zombie[x].getZombieHealth() <= 0){
				//1. Clear text in the dialogue box
				dialogue.setText("");
				//2 Print which zombie was selected in the dialogue box
				dialogueText = ("Please select another zombie");
				//2.1 Restart the char index
				charIndex = 0;
				//2.2 Start the timer
				wordAnimateTimer.start();
			}
		}
		//		if (zombie[zombieSelected].getZombieHealth() <= 0) {
		//			//1. Clear text in the dialogue box
		//			dialogue.setText("");
		//
		//			//2 Print which zombie was selected in the dialogue box
		//			dialogueText = ("Please select another zombie");
		//
		//			//2.1 Restart the char index
		//			charIndex = 0;
		//
		//			//2.2 Start the timer
		//			wordAnimateTimer.start();;
		//		}
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
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
		
		//A method for a sound to be played when player dies/loses a life 
				public void itemSound() {

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
}
