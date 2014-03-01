import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BombPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_WIDTH = 10;
	private final int DEFAULT_HEIGHT = 10;
	private final int MAX_WIDTH = 100;
	private final int MAX_HEIGHT = 100;
	private final int DEFAULT_NUM_BOMBS = 10;
	private final int DELAY = 999; // 999 milliseconds
	private int _width;
	private int _height;
	private int _numBombs;
	private int _solvedTiles;
	private int _totalSolvableTiles;
	private BombButton[][] _field;
	private ScoreBoard _scoreBoard;
	private Boolean _isFirstClick;
	private int _totalTime;
	private Timer _timer;
	
	/**
	 * Initializes panel to have DEFAULT_HEIGHT and DEFAULT_WIDTH size (10, each)
	 */
	public BombPanel(){
		super(); //set up whatever jpanel sets up automatically
		_height = DEFAULT_HEIGHT;
		_width = DEFAULT_WIDTH;
		_numBombs = DEFAULT_NUM_BOMBS;
		_totalSolvableTiles = _height * _width - _numBombs;
		_isFirstClick = true;
		_totalTime = 0;
		this.setLayout( new GridLayout( _height, _width));
		initBombArray();
		_timer = new Timer( DELAY, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println(_totalTime);
				++_totalTime;
				if( hasWon() ){
					System.out.println("You've won (from the timer)");
					_timer.stop();
				}
			}
		});
	}
	/**
	 * Initializes panel to have userWidth and userHeight size with userNumBombs bombs
	 * @param userWidth number of tiles in the "x" direction, must be >0 and <100
	 * @param userHeight number of tiles in the "y" direction, must be >0 and <100
	 * @param userNumBombs number of bombs to place on the map, must be >0 and <userWidth*userHeight
	 * @see BombPanel
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
		_isFirstClick = true;
		_totalTime = 0;
		this.setLayout( new GridLayout( _height, _width));
		initBombArray();
		_timer = new Timer( DELAY, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				++_totalTime;
				if( hasWon() ){
					_timer.stop();
					System.out.println("You've won (from the timer)" + _totalTime);
				}
			}
		});
	}
	
	/**
	 * reset the panel with a new field
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
	
	/**
	 * get the time, in seconds, that the game took to complete
	 * @return time, time since first button click to completion of bomb field
	 */
	public int getCompletionTime(){
		return _totalTime;
	}
	
	/**
	 * @param scoreboard the scoreboard to hold the winners of the bomb panel
	 */
	public void setScoreBoard(ScoreBoard userBoard){
		_scoreBoard = userBoard;
	}
	
	/**
	 * stop the internal timer. Used when the board is being reset
	 */
	public void stopTimer(){
		_timer.stop();
	}
	
	/*
	 * return true if the bombfield has been solved
	 */
	private Boolean hasWon(){
		return _solvedTiles == _totalSolvableTiles;
	}
	
	/*
	 * shows all tiles and their contents
	 */
	private void revealBoard(){
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
	 * launch if the game is over and the user has won
	 */
	private void concludeWin(){
		_timer.stop();
		this.revealBoard();
		_scoreBoard.promptScorer( this.getCompletionTime() );
	}
	
	/*
	 * launch if the game is over and the user has lost
	 */
	private void concludeLoss(){
		_timer.stop();
		this.revealBoard();
	}
	
	/**
	 * Bomb button 
	 * used in array fashion to simulate a minesweeper field
	 * 
	 */
	private class BombButton extends JToggleButton{
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
		 * these three Booleans toggle the right mouse click
		 * picture
		 * blank -> questionsmark -> flagged -> blank(loop)
		 */
		private Boolean _isUD_Blank;
		private Boolean _isUD_Flagged;
		private Boolean _isUD_QuestionMark;
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
		 * 
		 */
		MouseListener clickListener = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (SwingUtilities.isRightMouseButton( arg0) ){
					System.out.println("right mouse click");
					if( !_isDiscovered){
						if( _isUD_Blank){
							_isUD_Blank = false;
							_isUD_Flagged = true;
							setIconSafe("img/flag.png", "ERROR Loading image: right click toggle");
						}
						else if( _isUD_Flagged){
							_isUD_Flagged = false;
							_isUD_QuestionMark = true;
							setIconSafe("img/question.png", "ERROR Loading image: right click toggle");
						}
						else if( _isUD_QuestionMark){
							_isUD_QuestionMark = false;
							_isUD_Blank = true;
							setIconSafe("img/blank.png", "ERROR Loading image: right click toggle");
						}
					}
				}
				else if( SwingUtilities.isLeftMouseButton(arg0) ){
					resolveFirstClickFromButton();
					//System.out.println("left mouse click");
					if( _isBomb){
						_wasExploded = true;
						concludeLoss();
					}
					else{
						System.out.println("THIS IS A NORMAL TILE: " + Integer.toString( _widthIndex) 
										+ " " + Integer.toString( _heightIndex));
						floodFill( _widthIndex, _heightIndex);
					}
				}
			}


			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		};
		
		/*
		 * construct a button with the default size
		 */
		public BombButton(int bombType, int userWidthIndex, int userHeightIndex){
			super(); //set up jbutton stuff
			this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
			this.setFields(bombType);
			this.addMouseListener( clickListener);
			_widthIndex = userWidthIndex;
			_heightIndex = userHeightIndex;
			_isDiscovered = false;
			_wasExploded = false;
			_isUD_QuestionMark = false;
			_isUD_Flagged = false;
			_isUD_Blank = true;
			this.setIconSafe("img/blank.png", "ERROR loading image: constructor");
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
				switch(this._numAdjacentBombs){
				case 0: this.setIconSafe("img/blankClicked.png", "ERROR loading image: fill"); break;
				case 1: this.setIconSafe("img/1.png", "ERROR loading image: fill"); break;
				case 2: this.setIconSafe("img/2.png", "ERROR loading image: fill"); break;
				case 3: this.setIconSafe("img/3.png", "ERROR loading image: fill"); break;
				case 4: this.setIconSafe("img/4.png", "ERROR loading image: fill"); break;
				case 5: this.setIconSafe("img/5.png", "ERROR loading image: fill"); break;
				case 6: this.setIconSafe("img/6.png", "ERROR loading image: fill"); break;
				case 7: this.setIconSafe("img/7.png", "ERROR loading image: fill"); break;
				case 8: this.setIconSafe("img/8.png", "ERROR loading image: fill"); break;
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
			_isDiscovered = true;
			if( _isBomb){
				if( _wasExploded) this.setIconSafe("img/bombExploded.png", "ERROR loading image: reaveal");
				else this.setIconSafe("img/bombNormal.png", "ERROR loading image: reaveal");
			}
			else{
				switch(this._numAdjacentBombs){
				case 0: setIconSafe("img/blankClicked.png", "ERROR loading image: reaveal"); break;
				case 1: setIconSafe("img/1.png", "ERROR loading image: reaveal"); break;
				case 2: setIconSafe("img/2.png", "ERROR loading image: reaveal"); break;
				case 3: setIconSafe("img/3.png", "ERROR loading image: reaveal"); break;
				case 4: setIconSafe("img/4.png", "ERROR loading image: reaveal"); break;
				case 5: setIconSafe("img/5.png", "ERROR loading image: reaveal"); break;
				case 6: setIconSafe("img/6.png", "ERROR loading image: reaveal"); break;
				case 7: setIconSafe("img/7.png", "ERROR loading image: reaveal"); break;
				case 8: setIconSafe("img/8.png", "ERROR loading image: reaveal"); break;
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
				this.setText("BOMB"); //DEBUG
			}
			else{
				_numAdjacentBombs = bombType;
				_isBomb = false;
			}
		}
		
		/*
		 * resolve the condition of the first button on the board getting clicked
		 */
		private void resolveFirstClickFromButton() {
			if( _isFirstClick){
				_timer.start();
				_isFirstClick = false;
			}
		}
			
		/*
		 * try and set the icon
		 */
		private void setIconSafe(String path, String errorMessage){
			try {
				ClassLoader cl = this.getClass().getClassLoader();
				this.setIcon( new ImageIcon( cl.getResource(path)));
			  } catch(Exception e){
				  System.out.println( errorMessage);
			}
		}
	}

}
