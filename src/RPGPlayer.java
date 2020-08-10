import javax.swing.Icon;
import javax.swing.ImageIcon;
/*
 * This class is used to create a Player object. It extends the Mover class and
 * includes an array of ImageIcons constants for the various states of Player (walking motion).
 * Also, there is a constructor that sets the initial image of the Player.
 */
public class RPGPlayer {
	
	//1. Create variables to store player stats
	private int playerMaxHealth = 100;
	private int playerHealth = 100;
	private int playerAttack = 30;
	private int playerSpecialAttack = 50;
	private int playerSpeed = 40;
	private int playerArmor = 50;
	private int score = 0;
	private int coins = 0;
	private int healthUp = 3;
	private int attackUp = 3;
	private int armorUp = 3;
	private int specialAttackUp = 3;
	
		
	//1. Creates an array of ImageIcons representing various states of the Player
	public static final ImageIcon[][] IMAGE = {
		
		//1.1 Image icons of all the different ways the player will be oriented 
		
		
		
						//[0][0]											[1][0]
		{ new ImageIcon("images/PlayerLeftClosed.png"), new ImageIcon("images/PlayerLeftWalking.png") },
		
						//[0][1]											[1][1]
		{ new ImageIcon("images/PlayerRightClosed.png"), new ImageIcon("images/PlayerRightWalking.png") }
		
	};
	
	// 2. Constructor method for the player 
	public RPGPlayer(){
		
		//2.1 Sets the Player face a certain direction when the game is started 
		this.setIcon(IMAGE[0][0]);
	}
	private void setIcon(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		
	}
	
	public int getPlayerMaxHealth() {
		return playerMaxHealth;
	}
	
	public void setPlayerMaxHealth(int playerMaxHealth) {
		this.playerMaxHealth = playerMaxHealth;
	}
	
	public int getPlayerHealth() {
		return playerHealth;
	}
	
	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}
	
	public int getPlayerAttack() {
		return playerAttack;
	}
	
	public void setPlayerAttack(int playerAttack) {
		this.playerAttack = playerAttack;
	}
	
	public int getPlayerSpecialAttack() {
		return playerSpecialAttack;
	}
	
	public void setPlayerSpecialAttack(int playerSpecialAttack) {
		this.playerSpecialAttack = playerSpecialAttack;
	}
	
	public int getPlayerSpeed() {
		return playerSpeed;
	}
	
	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}
	
	public int getPlayerArmor() {
		return playerArmor;
	}
	
	public void setPlayerArmor(int playerArmor) {
		this.playerArmor = playerArmor;
	}
	
	public int getPlayerScore() {
		return score;
	}
	
	public void setPlayerScore(int playerScore) {
		this.score = playerScore;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getHealthUp() {
		return healthUp;
	}
	public void setHealthUp(int healthUp) {
		this.healthUp = healthUp;
	}
	public int getAttackUp() {
		return attackUp;
	}
	public void setAttackUp(int attackUp) {
		this.attackUp = attackUp;
	}
	public int getArmorUp() {
		return armorUp;
	}
	public void setArmorUp(int armorUp) {
		this.armorUp = armorUp;
	}
	public int getSpecialAttackUp() {
		return specialAttackUp;
	}
	public void setSpecialAttackUp(int specialAttackUp) {
		this.specialAttackUp = specialAttackUp;
	}		
}