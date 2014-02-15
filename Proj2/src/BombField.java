/*
 * counting scheme for _bombArray:
 *    <--- width --- >
 *   ^ (0,0) (1,0) ...
 * h | (0,1)  ...
 * e |  ...
 * i |
 * g |
 * h |
 * t V
 */

public class BombField {
	/*
	 * tiles in the x direction
	 */
	private int _width;
	/*
	 * tiles in the y direction
	 */
	private int _height;
	/*
	 * spaces on the board that do not contain bomb and that
	 * the user has "revealed"
	 */
	private int _solvedSpaces;
	/*
	 * the total number of solvable space on the board
	 * the user must solve all of these spaces to win the game
	 */
	private int _totalSolvableSpaces;
	/*
	 * the numbers of bombs on the field
	 */
	private int _numBombs;
	/*
	 * the array of bombButtons that compose the field
	 */
	private BombButton[][] _bombArray;
	
	/*
	 * Creates a bombfield of a given width and height
	 * no error checking becaues this object should always be used in a BombPanel
	 */
	public BombField(BombPanel panel, int userWidth, int userHeight, int numBombs) {
		_width = userWidth;
		_height = userHeight;
		_numBombs = numBombs;
		int[][] createdField = fieldCreator(_width, _height, _numBombs);
		_bombArray = new BombButton[userWidth][userHeight];
		for(int heightIndex = 0; heightIndex < _height; ++heightIndex){
			for(int widthIndex = 0; widthIndex < _width; ++widthIndex){
				_bombArray[widthIndex][heightIndex] = new BombButton( createdField[widthIndex][heightIndex]);
				panel.add( _bombArray[widthIndex][heightIndex]);
			}
		}
	}

	/*
	 * reset all spaces on the field and create a new game board
	 */
	public void resetField(){
		int[][] newField = fieldCreator( _width, _height, _numBombs);
		for( int heightIndex = 0; heightIndex < _height; ++heightIndex){
			for( int widthIndex = 0; widthIndex < _width; ++widthIndex){
				_bombArray[widthIndex][heightIndex].setFields( newField[widthIndex][heightIndex]);
			}
		}
		//TODO
	}
	
	/*
	 * return true if the bombfield has been solved
	 */
	public Boolean hasWon(){
		return _solvedSpaces == _totalSolvableSpaces;
	}
	
	/*
	 * shows all bombs and recalculates 
	 */
	public void revealBoard(){
		
	}
	
	/*
	 * creates a bomb field represented as a 2D int array
	 * CODES:
	 * -1: bomb here
	 * 0: number of adjacent bombs
	 * 1: number of adjacent bombs
	 * ...
	 * 8: number of adjacent bombs
	 */
	private int[][] fieldCreator( int height, int width, int numBombs){
		int[][] field = new int[height][width];
		//first, initialize all to zero
		for(int heightIndex = 0; heightIndex < height; ++heightIndex)
			for(int widthIndex = 0; widthIndex < width; ++widthIndex)
				field[heightIndex][widthIndex] = 0;
		//second, create the bombs
		int bombWIndex;
		int bombHIndex;
		Boolean bombPlanted = false;
		for(int bombCounter = 0; bombCounter < numBombs; ++bombCounter){
			bombPlanted = false;
			do{
				bombWIndex = (int)(Math.random()*100) % width;
				bombHIndex = (int)(Math.random()*100) % height;
				if( field[bombHIndex][bombWIndex] != -1){
					field[bombHIndex][bombWIndex] = -1;
					bombPlanted = true;
				}
			}while(!bombPlanted);
		}
		//lastly, count the adjacent bombs
		for(int heightIndex = 0; heightIndex < height; ++heightIndex){
			for(int widthIndex = 0; widthIndex < width; ++widthIndex){
				if( field[heightIndex][widthIndex] != -1){
					//corners
					//bottom left
					if( heightIndex > 0 && widthIndex > 0 && field[heightIndex-1][widthIndex-1] == -1) ++field[heightIndex][widthIndex];
					//bottom right
					if( heightIndex > 0 && widthIndex < width-1 && field[heightIndex-1][widthIndex+1] == -1) ++field[heightIndex][widthIndex];
					//top left
					if( heightIndex < height-1 && widthIndex > 0 && field[heightIndex+1][widthIndex-1] == -1) ++field[heightIndex][widthIndex];
					//top right
					if( heightIndex < height-1 && widthIndex < width-1 && field[heightIndex+1][widthIndex+1] == -1) ++field[heightIndex][widthIndex];
					//edges
					//left
					if( widthIndex > 0 && field[heightIndex][widthIndex-1] == -1) ++field[heightIndex][widthIndex];
					//right
					if( widthIndex < width-1 && field[heightIndex][widthIndex+1] == -1) ++field[heightIndex][widthIndex];
					//top
					if( heightIndex < height-1 && field[heightIndex+1][widthIndex] == -1) ++field[heightIndex][widthIndex];
					//bottom
					if( heightIndex > 0 && field[heightIndex-1][widthIndex] == -1) ++field[heightIndex][widthIndex];
				}
			}
		}
		return field;
	}
}
