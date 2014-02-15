import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BombButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SIZE = 4;
	
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
	 * construct a button with the default size
	 */
	public BombButton(int bombType){
		super(); //set up jbutton stuff
		this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
		addActionListener( this);
		this.setFields(bombType);
	}
	/*
	 * construct a button with a given width and height 
	 */
	public BombButton(int bombType, int width, int height){
		this(bombType); //set up defaults and jbutton stuff
		if( width < 1) width = DEFAULT_SIZE;
		if( height < 1) height = DEFAULT_SIZE;
		this.setSize( width, height);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	public void actionPerformed( ActionEvent event) {
		
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
		}
		else{
			_adjacentBombs = bombType;
			_isBomb = false;
			//this.setText( Integer.toString( _adjacentBombs)); //debug to see if the layout of the game is correct
		}
	}
	
}
