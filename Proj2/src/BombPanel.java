import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BombPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_WIDTH = 10;
	private final int DEFAULT_HEIGHT = 10;
	private final int MAX_WIDTH = 100;
	private final int MAX_HEIGHT = 100;
	private final int DEFAULT_NUM_BOMBS = 10;
	private int _width;
	private int _height;
	private int _numBombs;
	private int _solvedTiles;
	private int _totalSolvableTiles;
	private BombButton[][] _field;
	
	/*
	 * Constructor to create a bomb panel. Initializes panel to have DEFAULT_HEIGHT and
	 * DEFAULT_WIDTH size.
	 */
	public BombPanel(){
		super(); //set up whatever jpanel sets up automatically
		_height = DEFAULT_HEIGHT;
		_width = DEFAULT_WIDTH;
		_numBombs = DEFAULT_NUM_BOMBS;
		_totalSolvableTiles = _height * _width - _numBombs;
		this.setLayout( new GridLayout( _height, _width));
		initBombArray();
	}
	/*
	 * Constructor to create a bomb panel. Initializes panel to have user given
	 * height and width
	 */
	public BombPanel(int userWidth, int userHeight, int userNumBombs){
		super(); //set up whatever jpanel sets up automatically
		if( userWidth < 1 || userWidth > MAX_WIDTH)
			_width = DEFAULT_WIDTH;
		else
			_width = userWidth;
		if( userHeight < 1 || userHeight > MAX_HEIGHT)
			_height = DEFAULT_HEIGHT;
		else
			_height = userHeight;
		if( userNumBombs < 1 || userNumBombs >= _width*_height)
			_numBombs = DEFAULT_NUM_BOMBS;
		else
			_numBombs = userNumBombs;
		_totalSolvableTiles = _height * _width - _numBombs;
		this.setLayout( new GridLayout( _height, _width));
		initBombArray();
	}
	
	/*
	 * reset all spaces on the field and create a new game board
	 */
	public void resetField(){
		System.out.println("RESETING THE BOARD (generating a new one)");
		int[][] newField = fieldCreator( _width, _height, _numBombs);
		for( int heightIndex = 0; heightIndex < _height; ++heightIndex){
			for( int widthIndex = 0; widthIndex < _width; ++widthIndex){
				_field[widthIndex][heightIndex].setFields( newField[widthIndex][heightIndex]);
			}
		}
	}
	
	/*
	 * return true if the bombfield has been solved
	 */
	public Boolean hasWon(){
		return _solvedTiles == _totalSolvableTiles;
	}
	
	/*
	 * shows all tiles and their contents
	 * TODO
	 */
	public void revealBoard(){
		for( int heightIndex = 0; heightIndex < _height; ++heightIndex){
			for( int widthIndex = 0; widthIndex < _width; ++widthIndex){
				_field[widthIndex][heightIndex].reveal();
			}
		}
	}
	
	/*
	 * initialize the field of bomb buttons
	 */
	private void initBombArray(){
		int[][] createdField = fieldCreator(_width, _height, _numBombs);
		_field = new BombButton[_width][_height];
		for(int heightIndex = 0; heightIndex < _height; ++heightIndex){
			for(int widthIndex = 0; widthIndex < _width; ++widthIndex){
				_field[widthIndex][heightIndex] = new BombButton( createdField[widthIndex][heightIndex], widthIndex, heightIndex);
				this.add( _field[widthIndex][heightIndex]);
			}
		}
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

	/*
	 * only non edge squares initiate floodfills
	 * 
	 * edge tiles can be called upon by floodfill (this is when the are revealed)
	 * but will never initiate a floodfill
	 */
	private void floodFill( int wIndex, int hIndex){
		if( _field[wIndex][hIndex].isDiscovered() ) return; //prevent infinite loops
		//System.out.println("FLOODING: " + wIndex + " " + hIndex);
		_field[wIndex][hIndex].fill();
		if( !_field[wIndex][hIndex].isEdge()){ //if I don't neighbor a bomb, expand in all directions
			//corners
			if( wIndex+1 < _width && hIndex+1 < _height) floodFill(wIndex+1, hIndex+1);
			if( wIndex+1 < _width && hIndex-1 >= 0) floodFill(wIndex+1, hIndex-1);
			if( wIndex-1 >= 0 && hIndex+1 < _height) floodFill(wIndex-1, hIndex+1);
			if( wIndex-1 >= 0 && hIndex-1 >= 0) floodFill(wIndex-1, hIndex-1);
			//plus shape
			if( hIndex+1 < _height) floodFill(wIndex, hIndex+1);
			if( hIndex-1 >= 0) floodFill(wIndex, hIndex-1);
			if( wIndex+1 < _width) floodFill(wIndex+1, hIndex);
			if( wIndex-1 >= 0) floodFill(wIndex-1, hIndex);
		}
	}
	
	/*
	 * 
	 */
	private void concludeWin(){
		this.revealBoard();
	}
	
	/*
	 * 
	 */
	private void concludeLoss(){
		this.revealBoard();
	}
	
	/**
	 * Bomb button 
	 * used in array fashion to simulate a minesweeper field
	 * 
	 */
	public class BombButton extends JButton{
		private static final long serialVersionUID = 1L;
		private static final int DEFAULT_SIZE = 6;
		private int _heightIndex;
		private int _widthIndex;
		
		/*
		 * is a space hasn't been discovered, it's picture can be changed to 
		 * help the user identify a possible bomb
		 */
		private Boolean _isDiscovered;
		/*
		 * flag identifying a bomb on the board
		 */
		private Boolean _isBomb;
		/*
		 * identify the bomb that the user clicked on
		 * so that its image can be set to a different picture
		 */
		private Boolean _wasExploded;
		/*
		 * the number to be displayed s
		 */
		private int _numAdjacentBombs;
		/*
		 * will signal the bombfield to start a reveal()
		 * and will signal the end of a game
		 */
		ActionListener bombListener = new ActionListener(){
			public void actionPerformed( ActionEvent event){
				_wasExploded = true;
				concludeLoss();
			}
		};
		/*
		 * the action listener for a normal tile
		 * will signal a floodfill()
		 */
		ActionListener normalTileListener = new ActionListener(){
			public void actionPerformed( ActionEvent event){
				System.out.println("THIS IS A NORMAL TILE: " + Integer.toString( _widthIndex) 
								+ " " + Integer.toString( _heightIndex));
				floodFill( _widthIndex, _heightIndex);
			}
		};
		
		/*
		 * construct a button with the default size
		 */
		public BombButton(int bombType, int userWidthIndex, int userHeightIndex){
			super(); //set up jbutton stuff
			this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
			this.setFields(bombType);
			//addActionListener( this);
			_widthIndex = userWidthIndex;
			_heightIndex = userHeightIndex;
			_isDiscovered = false;
			_wasExploded = false;
			try {
				ClassLoader cl = this.getClass().getClassLoader();
				this.setIcon(new ImageIcon(cl.getResource("img/blank.png")));
				}catch(Exception e){
				  System.out.println("ERROR loading image: constructor");
			}
		}
		
		/*
		 * return if this tile has been discovered (filled)
		 */
		public Boolean isDiscovered(){
			return this._isDiscovered;
		}

		/*
		 * returns if the tile is bordering a bomb
		 * OR is, in fact, a bomb
		 * called from flood fill algorithm
		 */
		public Boolean isEdge(){
			if(_isBomb || _numAdjacentBombs >= 1) return true;
			else return false;
		}
		
		/*
		 * display the number of adjacent bombs 
		 * called from floodfill.
		 */
		public void fill() {
			if( !_isDiscovered){
				try {
					ClassLoader cl = this.getClass().getClassLoader();
					switch(this._numAdjacentBombs){
					case 0: this.setIcon(new ImageIcon(cl.getResource("img/blank.png"))); break;
					case 1: this.setIcon(new ImageIcon(cl.getResource("img/1.png"))); break;
					case 2: this.setIcon(new ImageIcon(cl.getResource("img/2.png"))); break;
					case 3: this.setIcon(new ImageIcon(cl.getResource("img/3.png"))); break;
					case 4: this.setIcon(new ImageIcon(cl.getResource("img/4.png"))); break;
					case 5: this.setIcon(new ImageIcon(cl.getResource("img/5.png"))); break;
					case 6: this.setIcon(new ImageIcon(cl.getResource("img/6.png"))); break;
					case 7: this.setIcon(new ImageIcon(cl.getResource("img/7.png"))); break;
					case 8: this.setIcon(new ImageIcon(cl.getResource("img/8.png"))); break;
					}
				  } catch(Exception e){
					  System.out.println("ERROR loading image: fill");
				}
				
				this._isDiscovered = true;
				_solvedTiles++;
				if(hasWon()){
					concludeWin();
				}
			}
		}
		
		/*
		 * reveals the contents of the square
		 * called when either the game is over (user has won)
		 * OR when a bomb is click (user has lost)
		 */
		public void reveal(){
			ClassLoader cl = this.getClass().getClassLoader();
			if( _isBomb){
				try {
					if( _wasExploded) this.setIcon(new ImageIcon(cl.getResource("img/bombExploded.png")));
					else this.setIcon(new ImageIcon(cl.getResource("img/bombNormal.png")));
				  } catch(Exception e){
					  System.out.println("ERROR loading image: reveal");
				}
			}
			else{
				try {
					switch(this._numAdjacentBombs){
					case 0: this.setIcon(new ImageIcon(cl.getResource("img/blank.png"))); break;
					case 1: this.setIcon(new ImageIcon(cl.getResource("img/1.png"))); break;
					case 2: this.setIcon(new ImageIcon(cl.getResource("img/2.png"))); break;
					case 3: this.setIcon(new ImageIcon(cl.getResource("img/3.png"))); break;
					case 4: this.setIcon(new ImageIcon(cl.getResource("img/4.png"))); break;
					case 5: this.setIcon(new ImageIcon(cl.getResource("img/5.png"))); break;
					case 6: this.setIcon(new ImageIcon(cl.getResource("img/6.png"))); break;
					case 7: this.setIcon(new ImageIcon(cl.getResource("img/7.png"))); break;
					case 8: this.setIcon(new ImageIcon(cl.getResource("img/8.png"))); break;
					}
				  } catch(Exception e){
					  System.out.println("ERROR loading image: reveal");
				}
			}
		}
		
		/*
		 * set the button up so that bombs are bombs and other squares do their job
		 */
		public void setFields(int bombType){
			if( bombType == -1){
				_numAdjacentBombs = -1;
				_isBomb = true;
				this.addActionListener( bombListener);
				//this.setText("BOMB"); //DEBUG
			}
			else{
				_numAdjacentBombs = bombType;
				_isBomb = false;
				this.addActionListener( normalTileListener);
			}
		}
	}
}
