import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * This class creates JLabels for the items that can be purchased in the shop. 
 * It includes a constructor method that sets up the panel and adds a action 
 * listener to the panel.
 */
public class Shop extends JFrame implements ActionListener {

	//Shop Frame Components 
	private JPanel shopPanel = new JPanel ();
	private JLabel shopLabel = new JLabel ("Upgrade Shop");
	
	//Variable to change the amount of coins need to buy upgrades 
	private int coinsNeeded = 5;
	
	//Opject for the player in the RPG
	RPGPlayer RPGPlayer = new RPGPlayer();
	
	//Create a Jlabel to display the number of coins the user has 
	private JLabel coinsLabel = new JLabel ("Coins:" + " ");
	private JLabel coins = new JLabel (String.valueOf(Board.coinsCollected));
	
	//A button to go back to the game 
	private JButton back = new JButton ("Back");

	//Create all attack upgrade labels  
	private JLabel health = new JLabel ("Health Upgrade:"); 
	private JLabel attack = new JLabel ("Attack Upgrade:");
	private JLabel specialAttack = new JLabel ("Special Attack Upgrade:");
	private JLabel speed = new JLabel ("Speed Upgrade:");
	private JLabel armor = new JLabel ("Arrmor Upgrade:");

	//Create all description strings
	private JLabel healthMessage = new JLabel ("Expands the maximum health your player can have.");
	private JLabel attackMessage = new JLabel ("Increase damage done to zombies.");
	private JLabel specialAttackMessage = new JLabel ("Gain a stronger burst attack.");
	private JLabel speedMessage = new JLabel ("Increases the player speed.");
	private JLabel armorMessage = new JLabel ("Take reduced damage from zombies.");

	//Create all purchase buttons 
	private JButton healthButton = new JButton (Integer.toString(coinsNeeded) + " Coins");
	private JButton attackButton = new JButton (Integer.toString(coinsNeeded) + " Coins");
	private JButton specialAttackButton = new JButton (Integer.toString(coinsNeeded) + " Coins");
	private JButton speedButton = new JButton (Integer.toString(coinsNeeded) + " Coins");
	private JButton armorButton = new JButton (Integer.toString(coinsNeeded) + " Coins");

	//Create all the picture labels 
	private JLabel healthIcon = new JLabel (new ImageIcon ("images/healthIcon.png"));
	private JLabel attackIcon = new JLabel (new ImageIcon ("images/attackIcon.png"));
	private JLabel specialAttackIcon = new JLabel (new ImageIcon ("images/specialAttackIcon.png"));
	private JLabel speedIcon = new JLabel (new ImageIcon ("images/speedIcon.png"));
	private JLabel armorIcon = new JLabel (new ImageIcon ("images/armorIcon.png"));

	//This method sets up all of the required frame/panel that make up the Shop part of the program 
	public Shop() {

		frameSetup();
		shopPanelSetup();

	}

	//This method sets up the shop page of the program 
	private void frameSetup() {

		//Makes frame and sets the size and color 
		setLayout(null);
		setSize(800, 650);
		getContentPane().setBackground(new Color (210,105,30));
		setResizable(false);
		setVisible(true);

	}

	private void shopPanelSetup() {

		//Creating the border and the sixe of the panel 
		shopPanel.setBounds(15, 15, 770, 600);
		shopPanel.setBackground(new Color (245,222,179));
		shopPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		shopPanel.setLayout(null);
		this.add(shopPanel);

		//Adds all of the upgrades onto the panel
		//Title of the main panel 
		shopLabel.setBounds(285, 5, 200, 60);
		shopLabel.setForeground(new Color (112,128,144));
		shopLabel.setFont(new Font ("Impact" , Font.BOLD, 35));
		shopPanel.add(shopLabel);
		
		//Adds the coins label onto the main panel 
		coins.setBounds(650, 10, 100, 20);
		coins.setForeground(new Color (112,128,144));
		coins.setFont(new Font ("Impact" , Font.BOLD, 20));
		shopPanel.add(coins);
		
		//Adds the coins title onto the panel 
		coinsLabel.setBounds(600, 10, 75, 20);
		coinsLabel.setForeground(new Color (112,128,144));
		coinsLabel.setFont(new Font ("Impact" , Font.BOLD, 20));
		shopPanel.add(coinsLabel);
		
		//Adds the back button 
//		back.setBounds(50, 10, 75, 30);
//		back.setForeground(new Color (112,128,144));
//		back.setFont(new Font ("Impact" , Font.BOLD, 20));
//		shopPanel.add(back);

		//Adds the health upgrade label and picture 
		health.setBounds(20, 70, 200, 30);
		health.setForeground(new Color (112,128,144));
		health.setFont(new Font ("Impact" , Font.BOLD, 30));
		back.addActionListener(this);
		shopPanel.add(health);

		//Adds health upgrade description 
		healthMessage.setBounds(20, 110, 325, 20);
		healthMessage.setForeground(new Color (112,128,144));
		healthMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		shopPanel.add(healthMessage);

		//Adds the health upgrade picture 
		healthIcon.setBounds(400, 60, 100, 100);
		shopPanel.add(healthIcon);

		//Adds health upgrade purchase button
		healthButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		healthButton.setBounds(550, 80, 150, 60);
		healthButton.setBackground(Color.RED);
		healthButton.addActionListener(this);
		shopPanel.add(healthButton);

		//Adds the attack upgrade label and picture 
		attack.setBounds(20, 180, 200, 30);
		attack.setForeground(new Color (112,128,144));
		attack.setFont(new Font ("Impact" , Font.BOLD, 30));
		shopPanel.add(attack);

		//Adds attack upgrade description 
		attackMessage.setBounds(20, 220, 325, 20);
		attackMessage.setForeground(new Color (112,128,144));
		attackMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		shopPanel.add(attackMessage);

		//Adds the attack upgrade picture 
		attackIcon.setBounds(400, 160, 100, 100);
		shopPanel.add(attackIcon);

		//Adds attack upgrade purchase button
		attackButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		attackButton.setBounds(550, 180, 150, 60);
		attackButton.setBackground(Color.RED);
		attackButton.addActionListener(this);
		shopPanel.add(attackButton);

		//Adds the special attack upgrade label and picture 
		specialAttack.setBounds(20, 290, 300, 30);
		specialAttack.setForeground(new Color (112,128,144));
		specialAttack.setFont(new Font ("Impact" , Font.BOLD, 30));
		shopPanel.add(specialAttack);

		//Adds special attack upgrade description 
		specialAttackMessage.setBounds(20, 330, 325, 20);
		specialAttackMessage.setForeground(new Color (112,128,144));
		specialAttackMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		shopPanel.add(specialAttackMessage);

		//Adds the special attack upgrade picture 
		specialAttackIcon.setBounds(400, 260, 100, 100);
		shopPanel.add(specialAttackIcon);

		//Adds special attack upgrade purchase button
		specialAttackButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		specialAttackButton.setBounds(550, 280, 150, 60);
		specialAttackButton.setBackground(Color.RED);
		specialAttackButton.addActionListener(this);
		shopPanel.add(specialAttackButton);

		//Adds the speed upgrade label and picture 
		speed.setBounds(20, 400, 200, 30);
		speed.setForeground(new Color (112,128,144));
		speed.setFont(new Font ("Impact" , Font.BOLD, 30));
		shopPanel.add(speed);

		//Adds spped upgrade description 
		speedMessage.setBounds(20, 440, 325, 20);
		speedMessage.setForeground(new Color (112,128,144));
		speedMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		shopPanel.add(speedMessage);

		//Adds the speed upgrade picture 
		speedIcon.setBounds(400, 380, 100, 100);
		shopPanel.add(speedIcon);

		//Adds speed upgrade purchase button
		speedButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		speedButton.setBounds(550, 390, 150, 60);
		speedButton.setBackground(Color.RED);
		speedButton.addActionListener(this);
		shopPanel.add(speedButton);

		//Adds the armor upgrade label and picture 
		armor.setBounds(20, 510, 210, 30);
		armor.setForeground(new Color (112,128,144));
		armor.setFont(new Font ("Impact" , Font.BOLD, 30));
		shopPanel.add(armor);

		//Adds armor upgrade description 
		armorMessage.setBounds(20, 550, 325, 20);
		armorMessage.setForeground(new Color (112,128,144));
		armorMessage.setFont(new Font ("Impact" , Font.BOLD, 15));
		shopPanel.add(armorMessage);

		//Adds the armor upgrade picture 
		armorIcon.setBounds(400, 490, 100, 100);
		shopPanel.add(armorIcon);

		//Adds armor purchase button
		armorButton.setFont(new Font ("Impact" , Font.BOLD, 20));
		armorButton.setBounds(550, 510, 150, 60);
		armorButton.setBackground(Color.RED);
		armorButton.addActionListener(this);
		shopPanel.add(armorButton);

		//Makes all components visible on the panel 
		setVisible(true);
		repaint();

	}


	//2. Check if the user has enough money to purchase whatever item they would like
	//2.1 If it is not a valid amount of money then prompt a error messages 
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == healthButton) {
			
			if (Board.getCoinsCollected() >= coinsNeeded) {
				
				Board.coinsCollected -= coinsNeeded;
				coins.setText(Integer.toString(Board.coinsCollected));
				
				RPGPlayer.setPlayerHealth(RPGPlayer.getPlayerHealth() + 10);
				RPGPlayer.setPlayerMaxHealth(RPGPlayer.getPlayerMaxHealth() + 10);
				
				
			} else {
				
				JOptionPane.showMessageDialog(null,  "You do not have enough money to purchase a health upgrade.");
				
			}
		}
		
		if (event.getSource() == attackButton) {
			
			if (Board.getCoinsCollected() >= coinsNeeded) {
				
				Board.coinsCollected -= coinsNeeded;
				coins.setText(Integer.toString(Board.coinsCollected));
				
				RPGPlayer.setPlayerAttack(RPGPlayer.getPlayerAttack() + 15);
				
			} else {
				
				JOptionPane.showMessageDialog(null,  "You do not have enough money to purchase a attack upgrade.");
				
			}
		}
		
		if (event.getSource() == specialAttackButton) {
			
			if (Board.getCoinsCollected() >= coinsNeeded) {
				
				Board.coinsCollected -= coinsNeeded;
				coins.setText(Integer.toString(Board.coinsCollected));
				
				RPGPlayer.setPlayerSpecialAttack(RPGPlayer.getPlayerSpecialAttack() + 20);	
				
			} else {
				
				JOptionPane.showMessageDialog(null,  "You do not have enough money to purchase a special attack upgrade.");
				
			}
		}
		
		if (event.getSource() == speedButton) {
			
			if (Board.getCoinsCollected() >= coinsNeeded) {
				
				Board.coinsCollected -= coinsNeeded;
				coins.setText(Integer.toString(Board.coinsCollected));
				
				RPGPlayer.setPlayerSpeed(RPGPlayer.getPlayerSpeed() + 10);
				
			} else {
				
				JOptionPane.showMessageDialog(null,  "You do not have enough money to purchase a speed upgrade.");
				
			}
		}
		
		if (event.getSource() == armorButton) {
			
			if (Board.getCoinsCollected() >= coinsNeeded) {
				
				Board.coinsCollected -= coinsNeeded;
				coins.setText(Integer.toString(Board.coinsCollected));
				
				RPGPlayer.setPlayerArmor(RPGPlayer.getPlayerArmor() + 10);
				
			} else {
				
				JOptionPane.showMessageDialog(null,  "You do not have enough money to purchase a armor upgrade.");
				
			}
		} 
	}
}