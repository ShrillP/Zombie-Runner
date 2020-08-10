import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * This class is used to create a instruction screen to provide the user with 
 * instruction on how to play the game.
 */

public class HowToPlay extends JFrame implements ActionListener {

	//Home Screen Components 
	private JLabel title;
	private JButton play;
	private JLabel pic1;
	private JLabel instructions;
	Clip clip;
	
	String name = HomeScreen.name;
	
	private String message = "Use the arrow keys to control " + name + ". " + "Run away from the zombies "
			+ "and try to collect all of the keys to unlock the lock on the safe house. There are "
			+ "obstacles to throw you off track. When ontop of a landmine, you will lose 5 health. With "
			+ "each ecounter with a zombie, you will have to face it and its fellow friends. Good luck and "
			+ "be safe!";

	//constructor method
	public HowToPlay () {

		
		System.out.println(name);
		
		//Makes frame and sets size and color
		setLayout(null);
		setSize(800, 625);
		getContentPane().setBackground(new Color (112,128,144));

		//Closes program if the exit option is clicked.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Adds the title and sets the font 
		title = new JLabel ("How To Play?");
		title.setForeground(new Color (218, 16, 16));
		title.setFont(new Font("Marker Felt" , Font.BOLD, 75));
		title.setBounds(220, 0, 450, 150);
		add(title);
		
		//Add a small text box to help the user with the controls of the game 
		instructions = new JLabel ("<html>"+ message +"</html>");		//uses the 'html' tages to form a paragraph using a JLabel
		instructions.setForeground(Color.WHITE);
		instructions.setBackground(new Color (112,128,144));
		instructions.setFont(new Font("Marker Felt" , Font.BOLD, 28));
		instructions.setBounds(40, 80, 400, 400);
		add(instructions);
		

		//Add pictures to frame 
		pic1 = new JLabel (new ImageIcon("images/keys.png"));
		pic1.setBounds(400, 150, 400, 283);
		add(pic1);

		//Adds continue button and make it clickable 
		play = new JButton ("Play Game");
		play.setFont(new Font("STSong" , Font.PLAIN, 35));
		play.setBounds(300, 475, 200, 75);
		play.setBackground(Color.RED);
		add(play);
		play.addActionListener(this);

		//Makes every componet of the home screen visible 
		setVisible(true);
		repaint();

	}
	
	//A method for the sound when game starts 
	public void soundPacManIntro () {
		
		//checks if the sound file exists
		try {
			
			 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/pacmanintro (better quality).wav").getAbsoluteFile());		//imports the sound file 
		       Clip clip = AudioSystem.getClip();
		       clip.open(audioInputStream);		//opens the clip
		       clip.start();		//starts playing the clip
		       
		   //if file is invalid 
		   } catch(Exception  ex) {
	        System.out.println("Error with playing sound");
	        
		}
	}

	public void actionPerformed(ActionEvent event) {
		
		//If the button on home screen is pressed, close home frame 
		if (event.getSource() == play) {
			this.setVisible(false);
			soundPacManIntro();
			new ZombieRunGUI();

		}
	}
}