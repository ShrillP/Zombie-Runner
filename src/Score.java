import java.io.Serializable;

public class Score implements Serializable {
	
	//Objects to store the name and score of the player 
	private int score;
	private String name;
	
	//Get the score 
    public int getScore() {
        return score;
    }

    //Get the name 
    public String getName() {
        return name;
    }

    //Sort the data containing the name and score 
    public Score(String naam, int score) {
        this.score = score;
        this.name = naam;
    }
}
