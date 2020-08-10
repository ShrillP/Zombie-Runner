import javax.swing.JLabel;

/*
 * This is an abstract template class that extends the JLabel class.
 * It holds the mover row, column, direction, and status
 */

public abstract class Mover extends JLabel{

	//1. Variables for the current position of Mover
	private int row;
	private int column;

	//2. Variables for the current direction (-1, 0, or 1) of Mover
	private int dRow;		 
	private int dColumn;	 

	//3. Variable to hold if the player is dead (Mover)
	private boolean isDead;

	//4. Getters and Setters for all of the different fields
	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}
	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column; 
	}

	public int getDRow() {
		return dRow;
	}

	public void setDRow(int dRow) {
		this.dRow = dRow;
	}

	public int getDColumn() {
		return dColumn;
	}

	public void setDColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	//5. Change Movers current position based on their current direction
	public void move() {

		//5.1 Row of the mover will be determined by adding the new row location 
		row += dRow;
		
		//5.2 Column of the mover will be determined by adding the new column location
		column += dColumn;
		
	}

	//6. Sets the direction of the mover based on a direction parameter
	public void setDirection(int direction) {

		//6.1 Reset the dRow and dColumn
		dRow = 0;
		dColumn = 0;

		//6.2 Set the row or column direction based on the direction parameter

		//Set the row or column direction based on the direction parameter

		if (direction==0) //left
			dColumn = -1;
		else if (direction==1) //up
			dRow = -1;
		else if (direction==2) //right
			dColumn = 1;
		else if (direction==3) //down
			dRow = 1;
		
	}


	//7. Returns the Mover's direction based on their current row or column direction
	public int getDirection() {

		//7.1 If the new row is 0 and new column is -1 
		if (dRow == 0 && dColumn == -1) //left
			return 0;

		//7.2 If the new row is -1 and new column is 0
		else if (dRow == -1 && dColumn == 0) //up
			return 1;

		//7.3 if the new row is 0 and new column is 1
		else if (dRow == 0 && dColumn == 1) //right
			return 2;
		else
			return 3; //down
	}

	//8. Returns the Mover's next row
	public int getNextRow() {
		return row + dRow;
	}

	//9. Returns the Mover's next column
	public int getNextColumn() {
		return column + dColumn;

	}
}
