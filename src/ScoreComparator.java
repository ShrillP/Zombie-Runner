import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
	
	//This methods compares 2 different objects of the type score 
	public int compare(Score score1, Score score2) {

		//Variables to hold 2 different scores 
		int sc1 = score1.getScore();
		int sc2 = score2.getScore();

		//If first score is greater than the second 
		if (sc1 > sc2){
			return -1;		//'-1' = first score is larger 
			
			//Otherwise second score is larger 
		}else if (sc1 < sc2){
			return +1;		//'+1' = first score is smaller 
			
		}else{
			return 0;		//'0' = both have the same score amount 
			
		}
	}
}