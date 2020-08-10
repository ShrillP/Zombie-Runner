import javax.swing.ImageIcon;
/**
 * This class is used to create zombie objects.  
 * It includes a constant ImageIcon array to hold the various zombie pictures
 * and a constructor method that sets the zombie' images
 */
public class Zombie{
	/**
	 * creates an array of ImageIcons representing various zombies
	 */
	
	private int zombieHealth = 100;
	private int zombieAttack = 20;
	private int zombieSpeed = 30;
	
	
	//1. Create imageConstants
	public static final ImageIcon[][] IMAGE = {
		//1.1 Image icons of all the different ways the player will be oriented 
		
								//[0][0]								[0][1]										[0][2]									[0][3]							[0][4]										[0][4]
		{ new ImageIcon("RPGimages/attack1.png"), new ImageIcon("RPGimages/attack2.png"), new ImageIcon("RPGimages/attack3.png"), new ImageIcon("RPGimages/attack4.png"), new ImageIcon("RPGimages/attack5.png"), new ImageIcon("RPGimages/attack6.png")},
								//[1][0]								[1][1]										[1][2]									[1][3]							[1][4]										[1][5]
		{ new ImageIcon("RPGimages/walk1.png"), new ImageIcon("RPGimages/walk2.png"), new ImageIcon("RPGimages/walk3.png"), new ImageIcon("RPGimages/walk4.png"), new ImageIcon("RPGimages/walk5.png"), new ImageIcon("RPGimages/walk6.png") },
	};
	//2. Zombie constructor used to set the images for the zombies
	public Zombie (int zNum) {
		//2.1 Sets the Player face a certain direction when the game is started 
				this.setIcon(IMAGE[0][0]);
		
	}
	private void setIcon(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		
	}
	public int getZombieHealth() {
		return zombieHealth;
	}
	
	public void setZombieHealth(int zombieHealth) {
		this.zombieHealth = zombieHealth;
	}
	
	public int getZombieAttack() {
		return zombieAttack;
	}
	
	public void setZombieAttack(int zombieAttack) {
		this.zombieAttack = zombieAttack;
	}
	
	public int getZombieSpeed() {
		return zombieSpeed;
	}
	
	public void setZombieSpeed(int zombieSpeed) {
		this.zombieSpeed = zombieSpeed;
	}
}
