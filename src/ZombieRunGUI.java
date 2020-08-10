import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This class creates a Player GUI that extends the JFrame class.  It has a Board 
 * (JPanel) and includes a constructor method that sets up the frame and adds a key 
 * listener to the board.
 */

public class ZombieRunGUI extends JFrame implements ActionListener{

	//1. Create variables

	//1.1 Make a board object 
	private Board board = new Board();

	//1.2 Create a panel to display the important game stats 
	public static JPanel statsPanel = new JPanel();

	//1.2 Create variables to display the score 
	private JLabel scoreTitle = new JLabel ("SCORE:");
	public static JLabel scoreLabel = new JLabel ("0");

	//1.3 Create variables to display the highscore 
	private JLabel highscoreTitle = new JLabel ("HIGHSCORE:");
	public static JLabel highscoreLabel = new JLabel ("0");

	//1.4 Create variables to display the health 
	private JLabel healthTitle = new JLabel ("HEALTH:");
	public static JLabel healthLabel = new JLabel ("100");

	//1.5 Create variables to display the coins collected
	private JLabel coinsTitle = new JLabel ("COINS:");
	public static JLabel coinsLabel = new JLabel ("0");

	//1.6 Create a variable to user to shop class
	public static JButton upgradeShop = new JButton ("Upgrade Shop");

	//2. Player GUI constructor
	public ZombieRunGUI() {

		//2.1 Setup the initial GUI 
		setSize(1200, 750);
		setTitle("Zombie Run");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		//2.2 Listen for events on the board and add the board to the GUI 
		addKeyListener(board);
		board.setBounds(0, 0, 1200, 550);
		add(board);

		//2.3 Set the Panel to a null layout
		statsPanel.setLayout(null);

		//2.4 Creates the title for the score panel
		scoreTitle.setBounds(40, 0, 100, 100);
		scoreTitle.setForeground(Color.RED);
		scoreTitle.setHorizontalAlignment(JLabel.CENTER);
		scoreTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		statsPanel.add(scoreTitle);

		//2.5 Creates a label for the score 
		scoreLabel.setBounds(10, 20, 150, 150);
		scoreLabel.setForeground(Color.RED);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		statsPanel.add(scoreLabel);

		//2.6 Create the title for the health panel
		healthTitle.setBounds(275, 0, 150, 100);
		healthTitle.setForeground(Color.RED);
		healthTitle.setHorizontalAlignment(JLabel.CENTER);
		healthTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		statsPanel.add(healthTitle);

		//2.7 Create health label
		healthLabel.setBounds(275, 20, 150, 150);
		healthLabel.setForeground(Color.RED);
		healthLabel.setHorizontalAlignment(JLabel.CENTER);
		healthLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		statsPanel.add(healthLabel);

		//2.8 Create the title for the highscore panel
		highscoreTitle.setBounds(725, 0, 200, 100);
		highscoreTitle.setForeground(Color.RED);
		highscoreTitle.setHorizontalAlignment(JLabel.CENTER);
		highscoreTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		statsPanel.add(highscoreTitle);

		//2.9 Create highscore label
		highscoreLabel.setBounds(750, 20, 150, 150);
		highscoreLabel.setForeground(Color.RED);
		highscoreLabel.setHorizontalAlignment(JLabel.CENTER);
		highscoreLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		statsPanel.add(highscoreLabel);

		//2.10 Create the title for the coins panel
		coinsTitle.setBounds(950, 0, 200, 100);
		coinsTitle.setForeground(Color.RED);
		coinsTitle.setHorizontalAlignment(JLabel.CENTER);
		coinsTitle.setFont(new Font ("Marker Felt" , Font.BOLD, 35));
		statsPanel.add(coinsTitle);

		//2.11 Create the coins label 
		coinsLabel.setBounds(950, 20, 150, 150);
		coinsLabel.setForeground(Color.RED);
		coinsLabel.setHorizontalAlignment(JLabel.CENTER);
		coinsLabel.setFont(new Font ("Marker Felt" , Font.BOLD, 30));
		statsPanel.add(coinsLabel);

		//2.12 Create the upgrade shop button 
		upgradeShop.setBounds(500, 40, 150, 40);
		upgradeShop.setHorizontalAlignment(JButton.CENTER);
		upgradeShop.setFont(new Font ("Marker Felt" , Font.BOLD, 20));
		upgradeShop.setFocusable(false);
		upgradeShop.addActionListener(this);
		statsPanel.add(upgradeShop);
		
		//2.13 Create the stats panel (displays the user’s stats)
		statsPanel.setBounds(0, 550, 1200, 200);
		statsPanel.setBackground(Color.BLACK);
		statsPanel.setLayout(null);
		add(statsPanel);
		
		//2.14 Makes the frame NOT resizable 
		setResizable(false);

		//2.15 Make the GUI visible to the user 
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == upgradeShop) {
			new Shop ();
			
		}
	}
}