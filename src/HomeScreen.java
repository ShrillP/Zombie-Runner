import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
public class HomeScreen extends JFrame implements ActionListener {
	//Home Screen Components 
	private JLabel title;
	private JButton proceed;
	private JButton highscores;
	private JLabel background;
	Clip clip;
	public static String name = "fivee";
	//constructor method
	public HomeScreen () {
		//Makes frame and sets size and color
		setLayout(null);
		setSize(623, 626);
		getContentPane().setBackground(new Color (95,158,160));
		setResizable(false);
		//Closes program if the exit option is clicked.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Add background to frame 
		setLayout(new BorderLayout());
		background = new JLabel (new ImageIcon("RPGImages/background.jpg"));
		add(background);
		
		//Adds the title and sets the font 
		title = new JLabel ("Zombie Run");
		title.setForeground(new Color (187, 10, 30));
		title.setFont(new Font("Cracked" , Font.BOLD, 75));
		title.setBounds(100, 0, 500, 150);
		background.add(title);
		//Adds continue button and make it clickable 
		proceed = new JButton ("Continue");
		proceed.setFont(new Font("Impact" , Font.PLAIN, 35));
		proceed.setBounds(338, 500, 250, 75);
		proceed.setBackground(Color.RED);
		proceed.addActionListener(this);
		background.add(proceed);
		//Adds highscores button and make it clickable 
		highscores = new JButton ("High Scores");
		highscores.setFont(new Font("Impact" , Font.PLAIN, 35));
		highscores.setBounds(38, 500, 250, 75);
		highscores.setBackground(Color.RED);
		highscores.addActionListener(this);
		background.add(highscores);
		//Makes every component of the home screen visible 
		setVisible(true);
		repaint();
		
		//Plays music
		backgroundMusic();
	}
	//A method for the sound when game starts 
	public void homeScreenMusic () {
		//checks if the sound file exists
		try {
			//convert to .wav 
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/.wav").getAbsoluteFile());		//imports the sound file 
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);		//opens the clip
			clip.start();		//starts playing the clip
			//if file is invalid 
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");
		}
	}
	//A method for the sound when game starts 
	public void instructionsMusic () {
		//checks if the sound file exists
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/.wav").getAbsoluteFile());		//imports the sound file 
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);		//opens the clip
			clip.start();		//starts playing the clip
			//if file is invalid 
		} catch(Exception  ex) {
			System.out.println("Error with playing sound");
		}
	}
		private String getTheName() {
			name = "fivee";
			while (name.length() != 4) {
				name = JOptionPane.showInputDialog(String.format("Please enter your four character username"));
				// if the user selects 'cancel'
				if (name == null) {
					// verify choice to 'quit'
					int choice = JOptionPane.showOptionDialog(null,
							"Do you wish to quit?", "Quit?",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null);
					// if verified then exit system
					if (choice == JOptionPane.YES_OPTION)
						System.exit(0);	
				}
			}
			return name;
		}
	public void actionPerformed(ActionEvent event) {
		//If the button on home screen is pressed, close home frame 
		if (event.getSource() == proceed) {
			this.setVisible(false);
			//clip.stop();
			instructionsMusic();
			getTheName();
			new HowToPlay();
		}
		if (event.getSource() == highscores) {
			this.dispose();
			new HighScores();
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		HomeScreen.name = name;
	}
	
	//A method for a sound to be played when mario eats 
		public void backgroundMusic() {

			//checks if the sound file exists 
			try {

				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sound/BackgroundMusic.wav").getAbsoluteFile());		//imports the sound file 
				Clip clip = AudioSystem.getClip();		
				clip.open(audioInputStream);		//opens the clip 
				clip.start();		//starts playing the clip

				//if file is invalid 
			} catch(Exception  ex) {
				System.out.println("Error with playing sound");

			}	
		}
}
