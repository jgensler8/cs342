import javax.swing.JPanel;

public class BombPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_WIDTH = 10;
	private final int DEFAULT_HEIGHT = 10;
	private final int MAX_WIDTH = 100;
	private final int MAX_HEIGHT = 100;
	private int _width;
	private int _height;
	private BombField _field;
	
	/*
	 * 
	 */
	public BombPanel(){
		super(); //set up whatever jpanel sets up automatically
		_height = DEFAULT_HEIGHT;
		_width = DEFAULT_WIDTH;
		_field = new BombField( _width, _height);
		this.add( _field);
	}
	public BombPanel(int userWidth, int userHeight){
		super(); //ser up whatever jpanel sets up automatically
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
		
		//create the array of buttons
		_field = new BombField( _width, _height);
		this.add( _field);
	}
}
