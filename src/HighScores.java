import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class HighScores extends JFrame implements ActionListener {

	HighscoreManager hm = new HighscoreManager();

	//variables
	JPanel highScorePanel = new JPanel();
	JButton backButton = new JButton("Back");
	JLabel title = new JLabel("High Scores");
	String score = hm.getHighscoreString();
	JLabel highScore = new JLabel("<html>"+ score +"</html>");

	//private Board board = new Board();

	public HighScores() {

		setBackground(Color.BLACK);		
		highScorePanel();
		backButton();	
		frameSetup(); 

	}

	private void frameSetup() {

		//closes GUI if the program ends
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		//Frame Specifications
		this.setLayout(null);

		this.setTitle("High Scores");
		this.setSize(600, 600);

		this.add(title);
		this.add(highScore);
		this.add(backButton);
		this.add(highScorePanel);

		this.setVisible(true);

	}

	//This method sets up the weightings panel.
	private void highScorePanel() {
		
//		hm.addScore("Bean", 1000);
//		hm.addScore("Bean", 900);
//		hm.addScore("Bean", 800);
//		hm.addScore("Bean", 700);
//		hm.addScore("Bean", 600);
//		hm.addScore("Bean", 500);
//		hm.addScore("Bean", 400);
//		hm.addScore("Bean", 300);
//		hm.addScore("Bean", 200);
//		hm.addScore("Bean", 100);
		
		//panel details
		highScorePanel.setSize(600, 600);
		highScorePanel.setBackground(new Color(112,128,144));
		highScorePanel.setLayout(null);

		title.setForeground(new Color (218, 16, 16));
		title.setFont(new Font("Impact", Font.BOLD, 50));
		title.setBounds(175, 0, 300, 100);


		highScore.setForeground(Color.white);
		highScore.setFont(new Font("ComicSans", Font.BOLD, 20));
		highScore.setBounds(230, 50, 150, 400);		

	}

	//Only shows after hovering over
	private void backButton() {
		
		//"back" button details
		setVisible(true);
		backButton.setBounds(225, 450, 150, 50);
		backButton.setBackground(new Color (47,79,79));
		backButton.setFont(new Font("STSong" , Font.PLAIN, 25));
		backButton.addActionListener(this);
	
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			this.dispose();
			new HomeScreen();
		}
	}

}