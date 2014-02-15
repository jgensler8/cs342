import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BombButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SIZE = 4;
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
	 * the number to be displayed 
	 */
	private int _adjacentBombs;

	/*
	 * the action listeer for a bomb
	 * will signal the bombfield to start a reveal()
	 * and will signal the end of a game
	 */
	ActionListener bombListener = new ActionListener(){
		public void actionPerformed( ActionEvent event){
			System.out.println("THIS IS A BOMB");
			if(!_isDiscovered){
				//TODO emit a custom action so that bombfield can listen
			}
		}
	};
	/*
	 * the action listener for a normal tile
	 * will signal a floodfill() and a hasWon()
	 */
	ActionListener blankListener = new ActionListener(){
		public void actionPerformed( ActionEvent event){
			System.out.println("THIS IS NORMAL TILE");
			if(!_isDiscovered){
				//TODO emit a custom action so that bombfield can listen
			}
			//TODO MAKE THIS WORK FOR A RIGHT CLICK
			//NOTICE HOW IT SAYS TO DEFINE SOMETHING BEHIND THE BUTTON...
			//http://stackoverflow.com/questions/2006188/right-click-on-jbutton
		}
	};
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("CLICKED");
	}
	
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
	}
	/*
	 * construct a button with a given width and height 
	 */
	public BombButton(int bombType, int userWidthIndex, int userHeightIndex, int width, int height){
		this(bombType, userWidthIndex, userHeightIndex); //set up defaults and jbutton stuff
		if( width < 1) width = DEFAULT_SIZE;
		if( height < 1) height = DEFAULT_SIZE;
		this.setSize( width, height);
	}
	
	/*
	 * set the button up so that bombs are bombs and other squares do their job
	 * TODO THIS MIGHT BE CHANGED SO WHEN THE RESET IS CALLED THE BOMB PANEL
	 * JUST GENERATES ANOTHER BOMB FIELD INSTEAD OF MANUALLY RESETING
	 * THIS IS BECAUSE OF ACTION LISTENERS ETC
	 */
	public void setFields(int bombType){
		if( bombType == -1){
			_adjacentBombs = -1;
			_isBomb = true;
			_isDiscovered = false;
			this.addActionListener( bombListener);
		}
		else{
			_adjacentBombs = bombType;
			_isBomb = false;
			_isDiscovered = false;
			this.addActionListener(blankListener);
			//this.setText( Integer.toString( _adjacentBombs)); //debug to see if the layout of the game is correct
		}
	}
	
	/*
	 * called when the game is over OR when a person hits a bomb
	 * reveals the contents of the square
	 * either:
	 * 1) a bomb
	 * 2) number of adjacent bombs
	 */
	public void reveal(){
		this.setText( Integer.toString( _adjacentBombs));
	}
}
