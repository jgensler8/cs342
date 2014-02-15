import java.awt.GridLayout;

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
	private BombField _field;
	
	/*
	 * Constructor to create a bomb panel. Initializes panel to have DEFAULT_HEIGHT and
	 * DEFAULT_WIDTH size.
	 */
	public BombPanel(){
		super(); //set up whatever jpanel sets up automatically
		_height = DEFAULT_HEIGHT;
		_width = DEFAULT_WIDTH;
		_numBombs = DEFAULT_NUM_BOMBS;
		this.setLayout( new GridLayout( _height, _width));
		//create a field inside of this panel (this call adds buttons to *this*)
		_field = new BombField( this, _width, _height, _numBombs);
	}
	/*
	 * Constructor to create a bomb panel. Initializes panel to have user given
	 * height and width
	 */
	public BombPanel(int userWidth, int userHeight, int userNumBombs){
		super(); //set up whatever jpanel sets up automatically
		if( userWidth < 1 || userWidth > MAX_WIDTH){
			_width = DEFAULT_WIDTH;
		}
		else{
			_width = userWidth;
		}
		if( userHeight < 1 || userHeight > MAX_HEIGHT){
			_height = DEFAULT_HEIGHT;
		}
		else{
			_height = userHeight;
		}
		if( userNumBombs < 1 || userNumBombs >= _width*_height){
			_numBombs = DEFAULT_NUM_BOMBS;
		}
		else{
			_numBombs = userNumBombs;
		}
		this.setLayout( new GridLayout( _height, _width));
		//create a field inside of this panel (this call adds buttons to *this*)
		_field = new BombField( this, _width, _height, _numBombs);
	}
	
	/*
	 * this resets all bombs on the field
	 * called from the reset button
	 */
	public void resetField(){
		_field.resetField();
	}
}
